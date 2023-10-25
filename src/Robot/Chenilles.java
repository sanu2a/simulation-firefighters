package Robot;
import Carte.*;

/**
 * Un robot à chenilles est un robot de capacité maximale 2000 litres.
 * Sa vitesse est diminuée de 50% en forêt. En plus,peut pas se rendre sur de
 * l’eau ou du rocher.
 */
public class Chenilles extends Robot {
    /**
     * Retourne la vitesse du robot à chenilles lorsqu'il se trouve dans une case
     * d'une nature donnée
     * La vitesse du robot à chenilles est diminuée de 50% en forêt
     * 
     * @param natureTerrain la nature du terrain
     * @return La vitesse du robot à chenilles selon la nature du terrain donnée
     */
    public double getVitesse(NatureTerrain natureTerrain) {
        if (natureTerrain == NatureTerrain.FORET) {
            return (double) this.vitesse / 2;
        }
        return this.vitesse;
    }

    /**
     * Constructeur produit un robot a Chenilles
     * 
     * @param stringVitesse Chaine de caractères contenant la vitesse du robot si
     *                      elle est
     *                      lue du fichier dans la limite de 80km/h, si ce n'est pas
     *                      le
     *                      cas,une vitesse par défaut 60km/h et attribue au robot à
     *                      chenilles.
     * @param casse         La case contenant le robot à chenilles
     * @param carte         La carte associe au robot à chenilles
     */
    public Chenilles(String stringVitesse, Case casse, Carte carte) {
        super(carte, casse);
        this.vitesse = 60;
        this.capReservoirMax = 2000;
        this.capReservoirAct = 2000;
        this.dureeRemplissage = 300;
        this.eauParUnite = 100;
        this.tempsParUnite = 8;
        if (stringVitesse != null) {
            int vitesse = Integer.parseInt(stringVitesse);
            if (vitesse < 80) {
                this.vitesse = vitesse;
            } else {
                this.vitesse = 80;
            }
        }
    }

    /**
     * Renvoie un boolean indiquant si le robot à chenilles peut se déplacer à une
     * case de telle nature.
     * un robot à chenilles ne peut pas se rendre sur de l’eau ou du rocher.
     * 
     * @param natureTerrain la nature du terrain dont on veut déplacer le robot à
     *                      chenilles
     * @return true si le robot à chenilles peut se rendre à une case de telle
     *         nature, sinon renvoie false
     */
    public boolean peutSeDeplacer(NatureTerrain natureTerrain) {
        if (natureTerrain != NatureTerrain.EAU && natureTerrain != NatureTerrain.ROCHE) {
            return true;
        }
        return false;
    }

    /** Retourne une chaine de caractères indiquant la nature du robot et sa position
     * @return Chaine de caractères indiquant la nature du robot et sa position
     */
    @Override
    public String toString() {
        return "robot à chenilles" + " " + this.vitesse + " " + this.casse.getLigne() + " " + this.casse.getColonne()
                + " ";
    }
}
