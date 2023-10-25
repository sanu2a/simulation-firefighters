package Simulation;
import Carte.*;
import Robot.*;

/**
 * La classe qui permet de stocker les entités de l'environnement de travail (la
 * cartes, les robots, les incendies ..)
 */
public class DonneesSimulation {
    /**
     * La carte sur laquelle la simulation est lancée
     */
    private Carte carte;
    /**
     * Les incendies à eteindre
     */
    private Incendie incendies[];
    /**
     * Les robots existant
     */
    private Robot[] robots;

    /**
     * Le constructeur de la classe de DonneesSimulation
     * 
     * @param carte     La carte de l'environnement.
     * @param incendies La liste des incendies.
     * @param robots    La liste des robots.
     */
    public DonneesSimulation(Carte carte, Incendie incendies[], Robot robots[]) {
        this.carte = carte;
        this.incendies = incendies;
        this.robots = robots;
    }

    /**
     * Retourne la carte utilisée dans la simulation
     * 
     * @return La carte de DonneesSimulation
     */
    public Carte getCarte() {
        return this.carte;
    }

    /**
     * Retourne la liste des incendies dans la carte
     * 
     * @return La liste des incendies
     */
    public Incendie[] getIncendies() {
        return this.incendies;
    }

    /**
     * Retourne la liste des robots dans la carte
     * 
     * @return La liste des robots
     */
    public Robot[] getRobots() {
        return this.robots;
    }
}
