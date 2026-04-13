package com.esports.model;

import com.esports.persistence.HibernateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DEPORTISTAS")
public class Atleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD")
    private int cod;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COD_DEPORTE")
    private Esport esport;

    @Transient
    private Integer codDeporteTemporal;

    public Atleta() {
    }

    public Atleta(String nombre) {
        this.nombre = nombre;
    }

    public Atleta(int cod, String nombre, int codDeporte) {
        this.cod = cod;
        this.nombre = nombre;
        setCodDeporte(codDeporte);
    }

    public Atleta(int cod, String nombre) {
        this.cod = cod;
        this.nombre = nombre;
    }

    public int getCod() {
        return cod;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCodDeporte() {
        if (esport != null) {
            return esport.getCod();
        }
        return codDeporteTemporal == null ? 0 : codDeporteTemporal;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodDeporte(int codDeporte) {
        this.codDeporteTemporal = codDeporte;
    }

    public Esport getEsport() {
        return esport;
    }

    public void setEsport(Esport esport) {
        this.esport = esport;
        this.codDeporteTemporal = esport == null ? null : esport.getCod();
    }

    public static void add(Atleta atleta) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Esport esport = session.get(Esport.class, atleta.getCodDeporte());
            if (esport == null) {
                throw new RuntimeException("L'esport seleccionat no existeix.");
            }

            atleta.setEsport(esport);
            session.persist(atleta);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("No s'ha pogut guardar l'atleta.", e);
        }
    }

    public static List<Atleta> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "select a from Atleta a left join fetch a.esport order by a.cod",
                    Atleta.class
            ).list();
        } catch (Exception e) {
            throw new RuntimeException("No s'han pogut recuperar els atletes.", e);
        }
    }

    public static List<String> buscarPorNombre(String texto) {
        List<Atleta> atletes;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            atletes = session.createQuery(
                            "select a from Atleta a left join fetch a.esport " +
                                    "where lower(a.nombre) like :texto order by a.cod",
                            Atleta.class
                    )
                    .setParameter("texto", "%" + texto.toLowerCase() + "%")
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("No s'han pogut cercar els atletes.", e);
        }

        List<String> resultat = new ArrayList<>();
        for (Atleta atleta : atletes) {
            String nomEsport = atleta.getEsport() == null ? "Sense esport" : atleta.getEsport().getNombre();
            resultat.add(atleta.getNombre() + " (" + atleta.getCod() + ") - " + nomEsport);
        }

        return resultat;
    }

    public static List<String> atletasPorDeporte(int id) {
        List<Atleta> atletes;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            atletes = session.createQuery(
                            "select a from Atleta a join fetch a.esport e " +
                                    "where e.cod = :id order by a.cod",
                            Atleta.class
                    )
                    .setParameter("id", id)
                    .list();
        } catch (Exception e) {
            throw new RuntimeException("No s'han pogut recuperar els atletes de l'esport.", e);
        }

        List<String> resultat = new ArrayList<>();
        for (Atleta atleta : atletes) {
            resultat.add(atleta.getNombre() + " (" + atleta.getCod() + ") - " + atleta.getEsport().getNombre());
        }

        return resultat;
    }

    @Override
    public String toString() {
        return cod + " - " + nombre;
    }
}
