package com.esports.view;

import com.esports.model.Atleta;
import com.esports.model.Esport;

import java.util.List;
import java.util.Scanner;

public class View {

    private final Scanner sc = new Scanner(System.in);

    public Esport esportForm() {
        System.out.print("Nom de l'esport: ");
        String nom = sc.nextLine().trim();
        return new Esport(nom);
    }

    public Atleta atletaForm(List<Esport> esports) {
        System.out.print("Nom de l'atleta: ");
        String nom = sc.nextLine().trim();

        System.out.println("Selecciona un esport:");
        llistaEsports(esports);

        System.out.print("Codi de l'esport: ");
        int codEsport = Integer.parseInt(sc.nextLine().trim());

        Atleta atleta = new Atleta(nom);
        atleta.setCodDeporte(codEsport);

        return atleta;
    }

    public int demanaEsport(List<Esport> esports) {
        System.out.println("Llista d'esports:");
        llistaEsports(esports);
        System.out.print("Indica el codi de l'esport: ");
        return Integer.parseInt(sc.nextLine().trim());
    }

    public void llistaAtletes(List<Atleta> atletes, List<Esport> esports) {
        if (atletes.isEmpty()) {
            System.out.println("No hi ha atletes.");
            return;
        }

        for (Atleta a : atletes) {
            String nomEsport = esports.stream()
                    .filter(e -> e.getCod() == a.getCodDeporte())
                    .map(Esport::getNombre)
                    .findFirst()
                    .orElse("Desconegut");

            System.out.println(a.getCod() + " | " + a.getNombre() + " | " + nomEsport);
        }
    }

    public void llistaAtletes(List<String> llista) {
        if (llista.isEmpty()) {
            System.out.println("No hi ha resultats.");
            return;
        }

        for (String s : llista) {
            System.out.println(s);
        }
    }

    public void llistaEsports(List<Esport> esports) {
        if (esports.isEmpty()) {
            System.out.println("No hi ha esports.");
            return;
        }

        for (Esport e : esports) {
            System.out.println(e.getCod() + ". " + e.getNombre());
        }
    }
}
