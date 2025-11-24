package com.esports.model.dao;

import com.esports.connection.ConnectionManager;
import com.esports.model.Atleta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtletaDAO implements DAO<Atleta> {

    @Override
    public void add(Atleta a) {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO DEPORTISTAS (NOMBRE, COD_DEPORTE) VALUES (?, ?)");

            ps.setString(1, a.getNombre());
            ps.setInt(2, a.getCodDeporte());
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Atleta> getAll() {
        List<Atleta> list = new ArrayList<>();

        try (Connection con = ConnectionManager.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT COD, NOMBRE, COD_DEPORTE FROM DEPORTISTAS");

            while (rs.next()) {
                list.add(new Atleta(
                        rs.getInt("COD"),
                        rs.getString("NOMBRE"),
                        rs.getInt("COD_DEPORTE")));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<String> buscarPorNombre(String texto) {
        List<String> result = new ArrayList<>();

        String sql = """
                SELECT d.NOMBRE AS atleta, d.COD, e.NOMBRE AS deporte
                FROM DEPORTISTAS d
                LEFT JOIN DEPORTES e ON e.COD = d.COD_DEPORTE
                WHERE LOWER(d.NOMBRE) LIKE LOWER(?)
                """;

        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + texto + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(
                        rs.getString("atleta") + " (" +
                                rs.getInt("COD") + ") - " +
                                rs.getString("deporte"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<String> atletasPorDeporte(int id) {
        List<String> lista = new ArrayList<>();

        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM GET_DEPORTISTAS(?)");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(
                        rs.getString("deportista_nombre") +
                                " (" + rs.getInt("deportista_cod") + ")" +
                                " - " + rs.getString("deporte_nombre"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
