package Evenements;
import Carte.*;
import Simulation.*;
import Robot.*;


/**
 * Un evenement pour verser l'eau par un robot donné sur une incendie.
 */
public class VerseEau extends Evenement {
    /**
     * Le robot qui s'occupe de verser eau sur l'incendie
     */
    private Robot robot;
    /**
     * L'incendie sur la quelle le robot intervient
     */
    private Incendie incendie;
    /**
     * Un boolean qui permet de savoir si le robot doit remplir son
     * réservoir apres qu'il le vide ou non en fonction de la stratégie exécutée
     */
    private boolean fautRemplir;

    /**
     * Le Constructeur de l'événement VerseauEau.
     * 
     * @param date        la date de l'evenement
     * @param robot       le robot concerné
     * @param incendie    L'incendie sur la quelle le robot intervient
     * @param simulateur  le simulateur
     * @param fautRemplir Un boolean qui permet de savoir si le robot doit remplir
     *                    son réservoir apres qu'il le vide ou non en fonction de la
     *                    stratégie exécutée
     */
    public VerseEau(long date, Robot robot, Incendie incendie, Simulateur simulateur, boolean fautRemplir) {
        super(date, simulateur);
        this.incendie = incendie;
        this.robot = robot;
        this.fautRemplir = fautRemplir;
    }

    @Override
    /**
     * La fonction permettant de décrémenter le réservoir de robot et le nombre de
     * litres d’eau nécessaires
     * pour l’éteindre en fonction de l'Intervention unitaire. Si le boolean
     * fautRemplir est True et le réservoir est vide, on appelle la méthode
     * programmeEvenementsRemplissage pour programmer le remplissage de réservoir.
     * Sinon on
     * associe l'état "occupé" a ce robot.
     * 
     */
    public void execute() {
        if (this.robot.peutEteindre(this.incendie)) {
            this.incendie.decrementerEau(this.robot.getEauParUnite());
            this.robot.deverserEau();
            if (this.robot.getCapReservoirAct() <= 0) {
                this.incendie.setEtat(false);
                if (this.fautRemplir) {
                    this.robot.programmeEvenementsRemplissage(this.simulateur);
                    return;
                }
                this.robot.setEtat(false);
                return;
            }
            if (this.incendie.getQuantiteEau() <= 0) {
                this.robot.setEtat(false);
                return;
            }
            if (this.incendie.getQuantiteEau() > 0 && this.robot.getCapReservoirAct() > 0) {
                Evenement reverserEau = new VerseEau(this.date + this.robot.getTempsParUnite(), this.robot,
                        this.incendie,
                        this.simulateur, this.fautRemplir);
                this.simulateur.ajouteEvenement(reverserEau);
                return;
            }
        }
    }
}
