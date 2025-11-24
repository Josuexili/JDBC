package com.esports.model.dao;

import com.esports.connection.ConnectionManager;
import com.esports.model.Esport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EsportDAO implements DAO<Esport> {

    @Override
    public void add(Esport e) {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO DEPORTES (NOMBRE) VALUES (?)");
            ps.setString(1, e.getNombre());
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Esport> getAll() {
        List<Esport> list = new ArrayList<>();

        try (Connection con = ConnectionManager.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT COD, NOMBRE FROM DEPORTES ORDER BY COD");

            while (rs.next()) {
                list.add(new Esport(
                        rs.getInt("COD"),
                        rs.getString("NOMBRE")));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
}
