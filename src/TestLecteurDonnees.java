
import io.LecteurDonnees;
import Carte.*;
import Simulation.*;
import Robot.*;
import Evenements.*;
import ChefPompier.*;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestLecteurDonnees {
/**
 * Fonction test
 * @param args
 */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            DonneesSimulation d = LecteurDonnees.creeDonnees(args[0]);
            for (Robot robot : d.getRobots()) {
                System.out.println(robot.toString());
            }
            for (Incendie incendie : d.getIncendies()) {
                System.out.println(incendie.toString());
            }
            Carte carte = d.getCarte();
            for (int i = 0; i < carte.getNbLignes(); i++) {
                for (int j = 0; j < carte.getNbColonnes(); j++) {
                    System.out.println(carte.getCase(i, j));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }

}
