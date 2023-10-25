
package Robot;
import Carte.*;

/**
 * Un robot a pattes de capacité infinie. Ce robot Utilise de la poudre.Ne peut
 * pas se rendre sur de l’eau et il ne se remplit jamais.
 */
public class Pattes extends Robot {
    /**
     * Retourne la vitesse du robot à pattes lorsqu'il se trouve dans une case d'une
     * nature donnée
     * La vitesse du robot à pattes est réduite à 10 km/h sur du rocher.
     * 
     * @param natureTerrain la nature du terrain
     * @return La vitesse du robot à pattes selon la nature du terrain donnée
     */
    public double getVitesse(NatureTerrain natureTerrain) {
        if (natureTerrain == NatureTerrain.ROCHE) {
            return this.vitesse - 10;
        }
        return this.vitesse;
    }

    /**
     * Constructeur produit un robot à pattes, vitesse de base est 30km/h
     * 
     * @param stringVitesse la vitesse du pattes n'est pas lue du fichier donc
     *                      ce parametre est toujours null
     * @param casse         la case contenant le robot à pattes
     * @param carte         la carte associée au robot à pattes
     */
    public Pattes(String stringVitesse, Case casse, Carte carte) {
        super(carte, casse);
        this.vitesse = 30;
        this.capReservoirMax = Integer.MAX_VALUE;
        this.capReservoirAct = Integer.MAX_VALUE;
        this.dureeRemplissage = 0;
        this.eauParUnite = 10;
        this.tempsParUnite = 1;
    }

    /**
     * Pour un robot à pattes, le réservoir est infini, pas besoin de le modifier.
     */
    @Override
    public void deverserEau() {
        return;
    }

    /**
     * Renvoie une chaine de caractères indiquant la nature du robot et sa position
     * 
     * @return Chaine de caractères indiquant la nature du robot et sa position
     */
    @Override
    public String toString() {
        return "pattes" + " " + this.vitesse + " " + this.casse.getLigne() + " " + this.casse.getColonne() + " ";
    }

    /**
     * Renvoie un boolean indiquant si le robot à pattes peut se déplacer à une case
     * de telle nature.
     * un robot à pattes ne peut pas se rendre sur de l’eau.
     * 
     * @param nature la nature du terrain dont on veut déplacer le robot à pattes
     * @return true si le robot à pattes peut se rendre à une case de telle nature,
     *         sinon renvoie false
     */
    public boolean peutSeDeplacer(NatureTerrain nature) {
        if (nature != NatureTerrain.EAU) {
            return true;
        }
        return false;
    }
}
