package Robot;
import Carte.*;

/**
 * Un robot a roues de capacité maximale 5000 litres. Un robot de telle type ne
 * peut se déplacer que sur du terrain libre ou habitat.
 */
public class Roues extends Robot {
    /**
     * Retourne la vitesse du robot à roues lorsqu'il se trouve dans une case d'une
     * nature donnée
     * La vitesse du robot à roues reste constante quelle que soit la nature du
     * terrain
     * 
     * @param natureTerrain la nature du terrain
     * @return La vitesse du robot à roues selon la nature du terrain donnée
     */
    public double getVitesse(NatureTerrain natureTerrain) {
        return this.vitesse;
    }

    /**
     * Constructeur produit un robot à roues
     * 
     * @param stringVitesse chaine de caractères contenant la vitesse du robot à
     *                      roues si
     *                      elle est lue du fichier.
     * @param casse         la case contenant le robot à roues
     * @param carte         la carte associe au robot à roues
     */
    public Roues(String stringVitesse, Case casse, Carte carte) {
        super(carte, casse);
        this.capReservoirMax = 5000;
        this.capReservoirAct = 5000;
        this.dureeRemplissage = 600;
        this.tempsParUnite = 5;
        this.eauParUnite = 100;
        if (stringVitesse != null) {
            int vitesse = Integer.parseInt(stringVitesse);
            this.vitesse = vitesse;
        } else {
            this.vitesse = 80;
        }
    }

    @Override
    /**
     * Renvoie un boolean indiquant si le robot à roues peut se déplacer à une case
     * de nature donnée
     * un robot à roues ne peut se rendre que sur terrain libre ou habitat.
     * 
     * @param natureTerrain la nature du terrain dont on veut déplacer le robot à
     *                      roues
     * @return true si le robot a roues peut se rendre a une case de telle nature,
     *         sinon renvoie false
     */
    public boolean peutSeDeplacer(NatureTerrain natureTerrain) {
        if (natureTerrain == NatureTerrain.TERRAIN_LIBRE || natureTerrain == NatureTerrain.HABITAT) {
            return true;
        }
        return false;
    }

    @Override
    /**
     * Retourne une chaine de caractères indiquant la nature du robot et sa position
     * 
     * @return Chaine de caractères indiquant la nature du robot et sa position
     */
    public String toString() {
        return "Robot à roues" + " " + this.vitesse + " " + this.casse.getLigne() + " " + this.casse.getColonne() + " ";
    }
}
