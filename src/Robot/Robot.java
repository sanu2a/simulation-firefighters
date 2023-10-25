package Robot;
import Carte.*;
import Simulation.*;
import Evenements.*;
import java.util.*;

/**
 * Une classe robot modelisant des « pompiers élémentaires » afin d'eteindre les
 * incendies.
 * Un robot est situe sur une case et peut se déplacer d’une case à la fois dans
 * les directions cardinales (Nord, Sud, Est ou Ouest) si la case voisine lui
 * est accessible.
 */
public abstract class Robot {
    /**
     * la case contenant le robot
     */
    protected Case casse;
    /**
     * la vitesse du robot
     */
    protected double vitesse;
    /**
     * la capacite du réservoir maximal
     * 
     */
    protected int capReservoirMax;
    /**
     * la capacite du réservoir actuelle
     */
    protected int capReservoirAct;
    /**
     * la carte associée au robot
     */
    protected Carte carte;
    /**
     * la durée du remplissage complet du réservoir du robot
     */
    protected long dureeRemplissage;
    /**
     * boolean indiquant la disponibilité du robot
     */
    protected boolean etat = false;
    /**
     * temps par intervention unitaire
     */
    protected int tempsParUnite;
    /**
     * la quantité d'eau versée par intervention unitaire
     */
    protected int eauParUnite;

    /**
     * Ce constructeur produit un robot dans une case et carte données
     * 
     * @param carte  carte associée au robot
     * @param casse   case du robot
     */
    public Robot(Carte carte, Case casse) {
        this.carte = carte;
        this.casse = casse;
    }

    /**
     * Retourne true si le robot peut se déplacer à une case de nature donnée et
     * false sionon
     * 
     * @param natureTerrain la nature du terrain dont on veut déplacer le robot
     * @return true si le robot peut se déplacer à une case de nature donnée, sinon
     *         renvoie false
     */
    public abstract boolean peutSeDeplacer(NatureTerrain natureTerrain);

    /**
     * Retourne la vitesse du robot lorsqu'il se trouve dans une case d'une nature
     * donnée
     * 
     * @param natureTerrain la nature du terrain
     * @return La vitesse du robot selon la nature du terrain donnée
     */
    public abstract double getVitesse(NatureTerrain natureTerrain);

    /**
     * Retourne la carte associée au robot
     * 
     * @return La carte associée au robot
     */
    public Carte getCarte() {
        return this.carte;
    }

    /**
     * Retourne la case dans laquelle se trouve le robot
     * 
     * @return La case dans laquelle se trouve le robot
     */
    public Case getPosition() {
        return this.casse;
    }

    /**
     * Change la position du robot à une case donnée
     * 
     * @param casse La nouvelle position du robot
     */
    public void setPosition(Case casse) {
        this.casse = casse;
    }

    /**
     * Retourne la capacité maximale du réservoir du robot
     * 
     * @return La capacité maximale du réservoir du robot
     */
    public int getCapReservoir() {
        return this.capReservoirMax;
    }

    /**
     * Retourne la durée du remplissage complet du réservoir du robot
     * 
     * @return La durée du remplissage complet du réservoir du robot
     */
    public long getDureeRemplissage() {
        return this.dureeRemplissage;
    }

    /**
     * Retourne un boolean indiquant si le robot est occupé
     * 
     * @return true si le robot est occuppé etfalsee sinon
     */
    public boolean getEtat() {
        return etat;
    }

    /**
     * Retourne la capacite actuelle du réservoir du robot
     * 
     * @return La capacite actuelle du réservoir du robot
     */
    public int getCapReservoirAct() {
        return capReservoirAct;
    }

    /**
     * Associe l'état de la disponibilité au robot
     * 
     * @param etat true pour indiquer que le robot est occupé et false pour
     *             indiquer que le robot est disponible
     */
    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    /**
     * Déplace le robot dans une direction donnée
     * 
     * @param direction La direction dans laquelle on veut déplacer le robot
     */
    public void deplaceRobot(Direction direction) throws IllegalArgumentException{
        if (carte.voisinExiste(casse, direction)
                && this.peutSeDeplacer(this.carte.getVoisin(casse, direction).getNature())) {
            this.casse = this.carte.getVoisin(this.casse, direction);
        } else {
            throw new IllegalArgumentException("Le robot " + this + " ne peut pas se déplacer vers la direction "+ direction);
        }
    }

    /**
     * Remplit le reservoir du robot
     */
    public void remplirReservoir() throws IllegalArgumentException{
        if (this.peutSeRemplir()) {
            this.capReservoirAct = this.capReservoirMax;
        } else {
            throw new IllegalArgumentException("Le robot " + this + " ,e peut pas se remplir");
        }
    }

    /**
     * Effectue une intervention unitaire
     */
    public void deverserEau() {
        this.capReservoirAct = Math.max(this.capReservoirAct - this.getEauParUnite(), 0);
    }

    /**
     * Retourne un boolean indiquant si la position du robot lui permet de remplir
     * le réservoir ou non
     * Le drone se remplit sur une case contenant l'eau,
     * les autres robots peuvent se remplir à une case adjacente a une case
     * contenant l'eau
     * 
     * @return true si le robot peut se remplir et false sinon
     */
    protected boolean peutSeRemplir() {
        for (Direction dir : Direction.values()) {
            if (this.carte.voisinExiste(this.casse, dir)) {
                if (this.carte.getVoisin(casse, dir).getNature() == NatureTerrain.EAU) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retourne un boolean indiquant si le robot peut intervenir pour éteindre un
     * incendie donne en fonction de sa position
     * Un robot peut intervenir s'il est avec l'incendie dans la même position
     * @param incendie l'incendie en question
     * @return true si le robot peut intervenir et false sinon
     * 
     */
    public boolean peutEteindre(Incendie incendie) {
        if (incendie.getCase() == this.casse) {
            return true;
        }
        return false;
    }

    /**
     * Retourne le temps nécessaire pour se déplacer entre deux cases adjacentes
     * On suppose que le robot parcourt chaque case selon sa vitesse dans cette
     * dernière
     * 
     * @param natureSource      nature du terrain de la case Source
     * @param natureDestination nature du terrain de la case Destination
     * @return Le temps de déplacement entre les deux cases
     */
    public int calculTempsDeplacement(NatureTerrain natureSource, NatureTerrain natureDestination) {
        int distance = this.carte.getTailleCase();
        double vitesseMoyenne = (this.getVitesse(natureSource) + this.getVitesse(natureDestination)) / 2;
        return (int) ((distance * 3.6) / vitesseMoyenne);
    }

    /**
     * Retourne le plus court chemin à partir du case contenant le robot à une case
     * destination donnée
     * 
     * @param casse la case destination
     * @return Une liste chainée contenant les cases à parcourir dans l'ordre pour
     *         arriver à la case donnée
     */
    public LinkedList<Case> plusCourtChemin(Case casse) {
        this.carte.reset();
        this.carte.calculePlusCourtChemin(this);
        return casse.getPlusCourtChemin();
    }

    /**
     * Programme la série d'événements élémentaires (de case en case) du
     * plus court Chemin pour aller du robot a une case donnée
     * 
     * @param casse      la casse sur laquelle le robot doit se rendre
     * @param simulateur le simulateur contenant la liste des événements à mettre à
     *                   jour
     */
    public void programmeEvenementsDeplacement(Case casse, Simulateur simulateur) {
        LinkedList<Case> chemin = this.plusCourtChemin(casse);
        chemin.add(casse);
        long dateCourante = simulateur.getDateCourante();
        Case caseCourante = this.getPosition();
        for (Case c : chemin) {
            if (c == this.getPosition()) {
                continue;
            } else {
                for (Direction direction : Direction.values()) {
                    if (this.carte.voisinExiste(caseCourante, direction)) {
                        Case caseTo = this.carte.getVoisin(caseCourante, direction);
                        if (caseTo.getColonne() == c.getColonne() && caseTo.getLigne() == c.getLigne()) {
                            long date = this.calculTempsDeplacement(caseCourante.getNature(), caseTo.getNature());
                            dateCourante = dateCourante + date;
                            simulateur
                                    .ajouteEvenement(new DeplaceRobot(dateCourante, direction, this, simulateur));
                            caseCourante = c;
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Retourne le cout d'un chemin donne
     * 
     * @param chemin          liste contentant un chemin à une case Destination
     * @param caseDestination la case destination
     * @return Le cout d'un chemin donne
     */
    public long calculCoutChemin(LinkedList<Case> chemin, Case caseDestination) {
        long date = 0;
        for (int i = 0; i < chemin.size() - 1; i++) {
            date = date + this.calculTempsDeplacement(chemin.get(i).getNature(), chemin.get(i + 1).getNature());
        }
        date = date
                + this.calculTempsDeplacement(chemin.get(chemin.size() - 1).getNature(), caseDestination.getNature());
        return date;
    }

    /**
     * Programme la série d'événements élémentaires (de case en case) du
     * plus court Chemin
     * pour aller remplir l'eau après avoir vidé son réservoir
     * pour le drone on cherche le chemin optimal à la case contenant l'eau
     * pour les autres robots on cherche le chemin optimal à la case la plus proche
     * adjacente à une case contenant l'eau
     * 
     * @param simulateur le simulateur contenant la liste des événements à mettre à
     *                   jour
     */
    public void programmeEvenementsRemplissage(Simulateur simulateur) {
        if (this.peutSeRemplir()){
            simulateur.ajouteEvenement(new RemplirReservoir(
                simulateur.getDateCourante() + this.getDureeRemplissage(), this, simulateur));
                return;
        }
        long coutMinimal = Integer.MAX_VALUE;
        Case destinationOptimal = null;
        this.carte.reset();
        this.carte.calculePlusCourtChemin(this);
        for (Case cas : this.carte.getEau()) {
            for (Direction direction : Direction.values()) {
                if (this.getCarte().voisinExiste(cas, direction)
                        && this.getCarte().getVoisin(cas, direction).getPlusCourtChemin().size() != 0) {
                    Case caseDestination = this.getCarte().getVoisin(cas, direction);
                    LinkedList<Case> currentChemin = caseDestination.getPlusCourtChemin();
                    long date = this.calculCoutChemin(currentChemin, caseDestination);
                    if (date < coutMinimal) {
                        coutMinimal = date;
                        destinationOptimal = caseDestination;
                    }
                }
            }
        }
        this.programmeEvenementsDeplacement(destinationOptimal, simulateur);
        simulateur.ajouteEvenement(new RemplirReservoir(
                simulateur.getDateCourante() + coutMinimal + this.getDureeRemplissage(), this, simulateur));
    }

    /**
     * Retourne la quantité d'eau a versé lors d'une intervention unitaire par le
     * robot.
     * 
     * @return la quantité d'eau a versé lors
     *         d'une intervention unitaire.
     */
    public int getEauParUnite() {
        return this.eauParUnite;
    }

    /**
     * Retourne le temps nécessaire pour effectuer une intervention unitaire.
     * 
     * @return le temps nécessaire pour effectuer une intervention
     *         unitaire.
     */
    public int getTempsParUnite() {
        return this.tempsParUnite;
    }
}
