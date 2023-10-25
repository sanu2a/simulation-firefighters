package Evenements;
import Carte.*;
import Simulation.*;
import Robot.*;

/**
 * Evenement du deplacement d'un robot donnée dans une direction donnée
 */
public class DeplaceRobot extends Evenement {
    /**
     * le robot a déplacé
     */
    private Robot robot;
    /** 
     * la direction du déplacement
     */ 
    private Direction direction;

    /**
     * Constructeur d'un nouvel événement du déplacement d'un robot dans une
     * direction donnée
     * 
     * @param date       date de l'événement
     * @param direction  direction du déplacement
     * @param robot      le robot à déplacer
     * @param simulateur le simulateur
     */
    public DeplaceRobot(long date, Direction direction, Robot robot, Simulateur simulateur) {
        super(date, simulateur);
        this.direction = direction;
        this.robot = robot;
    }

    /**
     * Exécute l'événement du déplacement en appelant la fonction deplaceRobot sur
     * le robot concerné
     */
    @Override
    public void execute() {
        this.robot.deplaceRobot(this.direction);

    }
}
