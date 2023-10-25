package Simulation;
import Carte.*;

import Robot.*;
import Evenements.*;
import ChefPompier.*;

import java.awt.Color;
import java.util.PriorityQueue;

import io.LecteurDonnees;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.ImageElement;

/**
 * Une classe simulateur implemente l'interface simulable permettant de traiter
 * les évènements de manière adéquate en fonction des données et de l’état de la
 * simulation
 */
public class Simulateur implements Simulable {
    private GUISimulator gui;
    /**
     * les données associées au simulateur
     */
    private DonneesSimulation donnee;
    /**
     * la date courante de la simulation
     */
    private long dateSimulation;
    /*
     * liste des événements a exécuté dans l'ordre
     */
    private PriorityQueue<Evenement> evenements;
    /**
     * le fichier contenant les données
     */
    private String file;

    /**
     * Un boolean indiquant si on choisit la strategie evoluée ou elementaire
     */
    private boolean estEvoluee;

    /**
     * Dessine la carte avec les incendies, les robots et les terrains.
     */
    public void draw() {
        this.gui.reset();
        int height_case = this.gui.getPanelHeight() / this.donnee.getCarte().getNbLignes();
        int width_case = this.gui.getPanelWidth() / this.donnee.getCarte().getNbColonnes();
        Carte carte = this.donnee.getCarte();
        for (int i = 0; i < carte.getNbLignes(); i++) {
            for (int j = 0; j < carte.getNbColonnes(); j++) {
                gui.addGraphicalElement(new Rectangle(j * width_case + width_case / 2,
                        i * height_case + height_case / 2, Color.black, Color.white, width_case, height_case));
                gui.addGraphicalElement(new ImageElement(j * width_case, i * height_case,
                        "images/" + carte.getCase(i, j).getNature() + ".gif",
                        width_case, height_case, null));
            }
        }
        for (Incendie incendie : donnee.getIncendies()) {
            int i = incendie.getCase().getLigne();
            int j = incendie.getCase().getColonne();
            if (incendie.getQuantiteEau() != 0) {
                gui.addGraphicalElement(
                        new ImageElement(j * width_case + width_case / 5, i * height_case + height_case / 5,
                                "images/incendie.gif", 3 * width_case / 4, 3 * height_case / 4, null));
            }
        }
        for (Robot robot : donnee.getRobots()) {
            int i = robot.getPosition().getLigne();
            int j = robot.getPosition().getColonne();
            gui.addGraphicalElement(new ImageElement(j * width_case + width_case / 5, i * height_case + width_case / 5,
                    "images/" + robot.getClass() + ".png", 2 * width_case / 3, 2 * height_case / 3, null));
        }
    }

    /**
     * Constructeur qui crée un nouveau simulateur
     * 
     * @param gui        gui
     * @param donnee     contient les robots, les incendies et la carte
     * @param file       le fichier contenant les données
     * @param estEvoluee Un boolean indiquant si on choisit la strategie evoluée ou
     *                   elementaire
     */
    public Simulateur(GUISimulator gui, DonneesSimulation donnee, String file, boolean estEvoluee) {
        this.evenements = new PriorityQueue<Evenement>(new EvenementComparator());
        this.gui = gui;
        gui.setSimulable(this);
        this.donnee = donnee;
        this.file = file;
        this.estEvoluee = estEvoluee;
        ChefPompier chefPompier = new ChefPompier(donnee, estEvoluee);
        chefPompier.intervention(this);
    }

    /**
     * En cliquant sur début, la lecture est alors arrêtée, et le simulateur revient
     * à l’état initial.
     */
    @Override
    public void restart() {
        this.gui.reset();
        this.dateSimulation = 0;
        try {
            this.evenements = new PriorityQueue<Evenement>(new EvenementComparator());
            this.donnee = LecteurDonnees.creeDonnees(this.file);
            ChefPompier cp = new ChefPompier(donnee, this.estEvoluee);
            cp.intervention(this);
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + this.file + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + this.file + " invalide: " + e.getMessage());
        }
        this.draw();
    }

    /**
     * Incrémente la date courante puis exécute dans l’ordre tous les évènements non
     * encore exécutés jusqu’à cette date
     * tout événement exécuté est par la suite supprimé de la liste des événements
     */
    @Override
    public void next() {
        this.incrementeDate();
        if (simulationTerminee()) {
            return;
        }
        while (!this.evenements.isEmpty() && this.evenements.peek().getDate() == this.dateSimulation) {
            this.evenements.remove().execute();
        }
        this.draw();
    }

    /**
     * Ajoute un événement à la liste des événements
     * 
     * @param e l'événement a ajouté
     */
    public void ajouteEvenement(Evenement e) {
        this.evenements.add(e);
    }

    /**
     * Incrémente la date de simulation
     */
    public void incrementeDate() {
        this.dateSimulation++;
    }

    /**
     * Renitialise la queue des evenements. Cette methode est utilisé pour executer
     * certain tests.
     */
    public void resetEvenement() {
        this.evenements.clear();
    }

    /**
     * Retourne un boolean indiquant si il reste encore un évènement à executer ou
     * non.
     * 
     * @return true si plus aucun évènement n’est en attente d’exécution et false
     *         sinon.
     */
    public boolean simulationTerminee() {
        return evenements.isEmpty();
    }

    /**
     * Retourne les données associées au simulateur
     * 
     * @return Les données associées au simulateur
     */
    public DonneesSimulation getDonnee() {
        return this.donnee;
    }

    /**
     * Retourne La date courante de la simulation
     * 
     * @return La date courante de la simulation
     */
    public long getDateCourante() {
        return this.dateSimulation;
    }

}
