package school.hei.patrimoine.modele.possession;

import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static java.time.Month.*;

public class ZetyDateTest {

    public static void main(String[] args) {
        // Date de référence : 17 septembre 2024
        LocalDate au17septembre24 = LocalDate.of(2024, SEPTEMBER, 17);

        // Création des possessions de Zety jusqu'au 21 septembre 2024
        Materiel ordinateur = new Materiel(
                "Ordinateur",
                au17septembre24,
                1_200_000,
                au17septembre24.minusDays(2),
                -0.10);

        Materiel vetements = new Materiel(
                "Vêtements",
                au17septembre24,
                1_500_000,
                au17septembre24.minusDays(2),
                -0.50);

        // Création d'espèces avec une date d'ouverture le 17 septembre 2024
        Argent especes = new Argent("Espèces", au17septembre24, 800_000);
        Argent compteBancaire = new Argent("Compte bancaire", au17septembre24, 100_000);

        // Flux d'argent pour les frais de scolarité
        FluxArgent fraisScolarite = new FluxArgent(
                "Frais de scolarité",
                compteBancaire, au17septembre24, au17septembre24,
                -2_500_000, au17septembre24.getDayOfMonth());

        // Flux d'argent pour les transferts mensuels des parents
        FluxArgent donMensuel = new FluxArgent(
                "Don mensuel",
                especes, LocalDate.of(2024, JANUARY, 15),
                LocalDate.of(2025, FEBRUARY, 13),
                100_000, 15);

        // Flux d'argent pour les dépenses mensuelles de Zety
        FluxArgent trainDeVie = new FluxArgent(
                "Train de vie",
                especes, LocalDate.of(2024, OCTOBER, 1),
                LocalDate.of(2025, FEBRUARY, 13),
                -250_000, 1);

        // Création du patrimoine de Zety jusqu'au 21 septembre 2024
        Set<Possession> possessionsZety = new HashSet<>();
        possessionsZety.add(ordinateur);
        possessionsZety.add(vetements);
        possessionsZety.add(especes);
        possessionsZety.add(compteBancaire);
        possessionsZety.add(fraisScolarite);
        possessionsZety.add(donMensuel);
        possessionsZety.add(trainDeVie);

        Patrimoine patrimoineZety = new Patrimoine(
                "Patrimoine de Zety",
                new Personne("Zety"),
                au17septembre24,
                possessionsZety);

        // Simulation à partir du 1 octobre 2024 pour déterminer quand Zety n'a plus d'espèces
        LocalDate dateSimulation = LocalDate.of(2024, OCTOBER, 1);
        int depensesMensuelles = 250_000;
        int espècesRestantes = especes.getValeurComptable();

        // Boucle de simulation jusqu'à épuisement des espèces
        while (espècesRestantes > 0) {
            // Dépenses mensuelles
            if (dateSimulation.getDayOfMonth() == 1) {
                especes = new Argent(especes.getNom(), dateSimulation, dateSimulation, especes.getValeurComptable() - depensesMensuelles);
                espècesRestantes -= depensesMensuelles;
            }

            // Ajouter le don mensuel des parents tous les 15 de chaque mois
            if (dateSimulation.getDayOfMonth() == 15) {
                especes = new Argent(especes.getNom(), dateSimulation, dateSimulation, especes.getValeurComptable() + 100_000);
                espècesRestantes += 100_000;
            }

            // Passage au jour suivant
            dateSimulation = dateSimulation.plusDays(1);
        }

        System.out.println("Zety n'a plus d'espèces à partir du : " + dateSimulation);
    }
}