import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import io.LecteurDonnees;
import gui.GUISimulator;
import Carte.*;
import Simulation.*;
import Robot.*;
import Evenements.*;


public class TestScenarioUn {
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
            Robot robot = d.getRobots()[1];
            Incendie incendie = d.getIncendies()[4];
            long cout = robot.calculTempsDeplacement(robot.getPosition().getNature(),NatureTerrain.HABITAT);
            long tempsViderReservoir = robot.getEauParUnite()*robot.getTempsParUnite();
            long tempsRemplissage = robot.getDureeRemplissage();
            Evenement eve1 = new DeplaceRobot(cout,Direction.NORD , robot, simulateur);
            Evenement eve2 = new VerseEau(2*cout,robot,incendie,simulateur, false);
            Evenement eve3 = new DeplaceRobot(2*cout + tempsViderReservoir ,Direction.OUEST ,robot, simulateur);
            Evenement eve4 = new DeplaceRobot(3*cout + tempsViderReservoir ,Direction.OUEST , robot, simulateur);
            Evenement eve5 = new RemplirReservoir(4*cout + tempsViderReservoir,robot,simulateur);
            Evenement eve6 = new DeplaceRobot(4*cout + tempsViderReservoir + tempsRemplissage,Direction.EST ,robot, simulateur);
            Evenement eve7 = new DeplaceRobot(5*cout + tempsViderReservoir + tempsRemplissage,Direction.EST , robot, simulateur);
            Evenement eve8 = new VerseEau(5*cout + 2*tempsViderReservoir + tempsRemplissage,robot,incendie,simulateur, false);
            simulateur.ajouteEvenement(eve1);
            simulateur.ajouteEvenement(eve2);
            simulateur.ajouteEvenement(eve3);
            simulateur.ajouteEvenement(eve4);
            simulateur.ajouteEvenement(eve5);
            simulateur.ajouteEvenement(eve6);
            simulateur.ajouteEvenement(eve7);
            simulateur.ajouteEvenement(eve8);
            simulateur.draw();
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }

}
