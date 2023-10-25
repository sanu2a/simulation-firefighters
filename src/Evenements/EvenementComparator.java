package Evenements;


import java.util.Comparator;

/**
 * Une classe qui realise l'interface Comparator.Elle sert a comprarer deux
 * evenements en se basant sur leur dates.
 */
public class EvenementComparator implements Comparator<Evenement> {
    /**
     * Retourne un entier indiquant l'ordre entre les deux evenements passes en
     * parametre.
     * 
     * @param evenementUn   le premier evenement
     * @param evenementDeux le deuxieme evenement
     * @return 0 si les deux evenements ont la meme date, -1 si l'evenement un est
     *         apres l'evenement 2, 1 sinon.
     */
    public int compare(Evenement evenementUn, Evenement evenementDeux) {
        if (evenementUn.date < evenementDeux.date)
            return -1;
        else if (evenementUn.date > evenementDeux.date)
            return 1;
        return 0;
    }
}
