
import java.awt.Color;

import java.util.Scanner;


import Simulation.*;

import io.LecteurDonnees;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import gui.GUISimulator;

/**
 * Une classe TestSimulateur pour lancer la simulation
 */
public class TestSimulateur {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        try {
            System.out.println("Selectionner la strategie a utilisee:\n1-Stratégie elementaire\n2-Strategie évoluée");
            Scanner in = new Scanner(System.in);
            boolean estEvoluee;
            while (true) {
                int num = in.nextInt();
                if (num == 1) {
                    estEvoluee = false;
                    break;
                }
                if (num == 2) {
                    estEvoluee = true;
                    break;
                }
                System.out.println("Selectionner la strategie a utilisee:\n1-Stratégie elementaire\n2-Strategie évoluée");
            }
            DonneesSimulation d = LecteurDonnees.creeDonnees(args[0]);
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
            Simulateur simulateur = new Simulateur(gui, d, args[0], estEvoluee);
            simulateur.draw();
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }

}
