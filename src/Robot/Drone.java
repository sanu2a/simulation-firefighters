package Robot;
import Carte.*;
import Simulation.*;
import Evenements.*;


import java.util.LinkedList;

/**
 * Le drone est un robot qui peut se déplacer sur toutes les cases, quelle que
 * soit leur nature, à vitesse constante.
 * Il a une capacite du reservoir de 10000 litres.
 */
public class Drone extends Robot {
    /**
     * Retourne la vitesse du Drone lorsqu'il se trouve dans une case d'une nature
     * donnée
     * La vitesse du drone reste constante quelle que soit la nature du terrain
     * 
     * @param natureTerrain la nature du terrain
     * @return La vitesse du robot selon la nature du terrain donnée
     */
    public double getVitesse(NatureTerrain natureTerrain) {
        return this.vitesse;
    }

    /**
     * Constructeur produit un robot de type drone
     * 
     * @param stringVitesse chaine de caractères contenant la vitesse du drone si
     *                      elle est
     *                      lue du fichier dans la limite de 150km/h, si ce n'est
     *                      pas le
     *                      cas,une vitesse par défaut 100km/h est attribué au
     *                      drone.
     * @param casse         la case contenant le drone
     * @param carte         la carte associe au drone
     */
    public Drone(String stringVitesse, Case casse, Carte carte) {
        super(carte, casse);
        this.vitesse = 100;
        this.capReservoirMax = 10000;
        this.capReservoirAct = 10000;
        this.dureeRemplissage = 1800;
        this.eauParUnite = this.capReservoirMax;
        this.tempsParUnite = 30;
        if (stringVitesse != null) {
            int vitesse = Integer.parseInt(stringVitesse);
            if (vitesse < 150) {
                this.vitesse = vitesse;
            } else {
                this.vitesse = 150;
            }
        }
    }

    /**
     * Retourne un boolean indiquant si la position du Drone lui permet de remplir
     * le réservoir ou non
     * Le drone se remplit sur une case contenant l'eau,
     * 
     * @return true si le drone peut se remplir et false sinon
     */
    @Override
    protected boolean peutSeRemplir() {
        if (this.casse.getNature() == NatureTerrain.EAU) {
            return true;
        }
        return false;
    }

    /**
     * Programme la série d'événements élémentaires (de case en case) du
     * plus court Chemin
     * pour aller remplir l'eau après avoir vidé son réservoir
     * pour le drone on cherche le chemin optimal à la case contenant l'eau
     * 
     * @param simulateur le simulateur contenant la liste des événements à mettre à
     *                   jour
     */
    @Override
    public void programmeEvenementsRemplissage(Simulateur simulateur) {
        long coutMin = Integer.MAX_VALUE;
        Case destOpt = null;
        this.getCarte().reset();
        this.carte.calculePlusCourtChemin(this);
        for (Case cas : this.carte.getEau()) {
            LinkedList<Case> currentChemin = cas.getPlusCourtChemin();
            if (currentChemin.size() != 0) {
                long date = this.calculCoutChemin(currentChemin, cas);
                if (date < coutMin) {
                    coutMin = date;
                    destOpt = cas;
                }
            }
        }
        this.programmeEvenementsDeplacement(destOpt, simulateur);
        simulateur.ajouteEvenement(new RemplirReservoir(
                simulateur.getDateCourante() + coutMin + this.getDureeRemplissage(), this, simulateur));
    }

    /**
     * Retourne une chaine de caractères indiquant la nature du robot et sa position
     * 
     * @return Chaine de caractères indiquant la nature du robot et sa position
     */
    @Override
    public String toString() {
        return "drone" + " " + this.vitesse + " " + this.casse.getLigne() + " " + this.casse.getColonne() + " ";
    }

    /**
     * Renvoie un boolean indiquant si le drone peut se déplacer à une case de telle
     * nature.
     * un drone ne peut pas se rendre sur toutes les natures du terrain.
     * 
     * @param nature la nature du terrain dont on veut déplacer drone
     * @return true
     */
    @Override
    public boolean peutSeDeplacer(NatureTerrain nature) {
        return true;
    }

}
