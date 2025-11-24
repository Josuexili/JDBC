package com.esports.controller;

import com.esports.model.Atleta;
import com.esports.model.Esport;
import com.esports.model.dao.AtletaDAO;
import com.esports.model.dao.EsportDAO;
import com.esports.view.View;

import java.util.List;
import java.util.Scanner;

public class SportsController {

    private final EsportDAO esportDAO = new EsportDAO();
    private final AtletaDAO atletaDAO = new AtletaDAO();
    private final View view = new View();
    private final Scanner sc = new Scanner(System.in);

    public void start() {

        boolean sortir = false;

        while (!sortir) {
            System.out.println("""
                        \nMENÚ PRINCIPAL
                        1. Afegir esport
                        2. Afegir atleta
                        3. Cercar atleta per nom
                        4. Llistar atletes per esport
                        5. Llistar esports
                        6. Sortir
                    """);

            System.out.print("Opció: ");
            int op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1 -> afegirEsport();
                case 2 -> afegirAtleta();
                case 3 -> cercarAtleta();
                case 4 -> llistarAtletesPerEsport();
                case 5 -> llistarEsports();
                case 6 -> sortir = true;
            }
        }
    }

    private void afegirEsport() {
        Esport e = view.esportForm();
        esportDAO.add(e);
    }

    private void afegirAtleta() {
        List<Esport> esports = esportDAO.getAll();
        Atleta a = view.atletaForm(esports);
        atletaDAO.add(a);
    }

    private void cercarAtleta() {
        System.out.print("Introdueix el nom a cercar: ");
        String text = sc.nextLine();
        List<String> result = atletaDAO.buscarPorNombre(text);
        view.llistaAtletes(result);
    }

    private void llistarAtletesPerEsport() {
        List<Esport> esports = esportDAO.getAll();
        int id = view.demanaEsport(esports);
        List<String> result = atletaDAO.atletasPorDeporte(id);
        view.llistaAtletes(result);
    }

    private void llistarEsports() {
        view.llistaEsports(esportDAO.getAll());
    }
}
