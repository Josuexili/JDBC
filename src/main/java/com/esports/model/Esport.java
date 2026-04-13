package com.esports.model;

import com.esports.persistence.HibernateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DEPORTES")
public class Esport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD")
    private int cod;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "esport")
    private List<Atleta> atletes = new ArrayList<>();

    public Esport() {
    }

    public Esport(String nombre) {
        this.nombre = nombre;
    }

    public Esport(int cod, String nombre) {
        this.cod = cod;
        this.nombre = nombre;
    }

    public int getCod() {
        return cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Atleta> getAtletes() {
        return atletes;
    }

    public void setAtletes(List<Atleta> atletes) {
        this.atletes = atletes;
    }

    public static void add(Esport esport) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(esport);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("No s'ha pogut guardar l'esport.", e);
        }
    }

    public static List<Esport> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Esport order by cod", Esport.class).list();
        } catch (Exception e) {
            throw new RuntimeException("No s'han pogut recuperar els esports.", e);
        }
    }

    @Override
    public String toString() {
        return cod + " - " + nombre;
    }
}
