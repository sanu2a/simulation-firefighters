package Evenements;

import Simulation.*;

/**
 * Une classe abstract d'un evenement ayant une date et un simulateur
 */
public abstract class Evenement {
    /**
     * la date de l'évenement
     */
    protected long date;
    /**
     * Le simulateur
     */
    protected Simulateur simulateur;

    /**
     * Le constructeur de la classe Evenement
     * 
     * @param date       La date de l'évenement
     * @param simulateur La classe qui gère la simulation
     */
    public Evenement(long date, Simulateur simulateur) {
        this.date = date;
        this.simulateur = simulateur;
    }

    /**
     * Retourne la date de l'évenement
     * 
     * @return la date de l'évenement
     */
    public long getDate() {
        return this.date;
    }

    /**
     * Execute l'evenement
     */
    public abstract void execute();
}
