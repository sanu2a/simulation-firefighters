package Evenements;

import Simulation.*;
import Robot.*;


/**
 * Un Evenement de remplissage du reservoir d'un robot.
 */
public class RemplirReservoir extends Evenement {
    /**
     * le robot dont le réservoir doit se remplir
     */
    private Robot robot;

    /**
     * Constructeur d'un nouvel événement de remplissage du réservoir
     * 
     * @param date       la date de l'événement
     * @param robot      le robot concerné par le remplissage
     * @param simulateur le simulateur
     */
    public RemplirReservoir(long date, Robot robot, Simulateur simulateur) {
        super(date, simulateur);
        this.robot = robot;
    }

    @Override
    /**
     * Exécute l'événement du remplissage en appelant la fonction remplirReservoir
     * du robot concernée
     * Le robot doit être indisponible au moment du remplissage et donc son état est
     * remis à false
     */
    public void execute() {
        this.robot.remplirReservoir();
        this.robot.setEtat(false);
    }
}
