import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import io.LecteurDonnees;
import gui.GUISimulator;
import Carte.*;
import Simulation.*;
import Robot.*;
import Evenements.*;


public class TestScenarioZero {
    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }
        try {
            GUISimulator gui = new GUISimulator(800, 600, Color.black);
            DonneesSimulation d = LecteurDonnees.creeDonnees(args[0]);
            Simulateur simulateur = new Simulateur(gui,d,args[0],false);
            simulateur.resetEvenement();
            Robot robot = d.getRobots()[0];
            long cout = robot.calculTempsDeplacement(robot.getPosition().getNature(),NatureTerrain.EAU);
            Evenement eve1 = new DeplaceRobot(cout,Direction.NORD , robot, simulateur);
            Evenement eve2 = new DeplaceRobot(2*cout,Direction.NORD , robot, simulateur);
            Evenement eve3 = new DeplaceRobot(3*cout,Direction.NORD , robot, simulateur);
            Evenement eve4 = new DeplaceRobot(4*cout ,Direction.NORD , robot, simulateur);
            simulateur.ajouteEvenement(eve1);
            simulateur.ajouteEvenement(eve2);
            simulateur.ajouteEvenement(eve3);
            simulateur.ajouteEvenement(eve4);
            simulateur.draw();
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }

}
