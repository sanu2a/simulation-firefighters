package Carte;

import java.util.*;

/**
 * Une classe case est caractérisée par ses coordonnnées
 * (ligne, colonne) et la nature du terrain qu’elle représente : terrain libre,
 * forêt, roche, eau ou habitat ainsi que distance modelisant le poids d'un
 * sommet dans notre graphe (carte) et un plus court chemin aà partir d'une case
 * ajouté lors de l'execution de Dijikstra
 */
public class Case {
    /**
     * La ligne où se trouve la case
     */
    private int ligne;
    /**
     * La colonne où se trouve la case
     */
    private int colonne;
    /**
     * La nature du terrain qu'elle represente
     */
    private NatureTerrain natureTerrain;
    /**
     * Un plus court chemin a partir d'une case
     */
    private LinkedList<Case> plusCourtChemin = new LinkedList<Case>();
    /**
     * attribut distance utilisé pour le calcul du plus court chemin
     */
    private Integer distance = Integer.MAX_VALUE;

    /**
     * Constructeur de la classe Case
     * 
     * @param ligne         La ligne ou se trouve la case
     * @param colonne       La colonne ou se trouve la case
     * @param natureTerrain La nature du terrain qu'elle represente
     */
    public Case(int ligne, int colonne, NatureTerrain natureTerrain) {
        this.colonne = colonne;
        this.ligne = ligne;
        this.natureTerrain = natureTerrain;
    }

    /**
     * Associe l'attribut plusCourtChemin a la case
     * 
     * @param plusCourtChemin le chemin le plus proche a la case en question a
     *                        partir d'une case source
     */
    public void setPlusCourtChemin(LinkedList<Case> plusCourtChemin) {
        this.plusCourtChemin = plusCourtChemin;
    }

    /**
     * Ajoute une case dans plusCourtChemin
     * 
     * @param casse La case à ajouté dans le plus court chemin
     */
    public void insereAuPlusCourtChemin(Case casse) {
        this.plusCourtChemin.add(casse);
    }

    /**
     * Associe l'attribut distance a la case
     * 
     * @param distance la distance (poids) de la case (sommet)
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     * Retourne la distance de la case (poids du sommet)
     * 
     * @return La distance de la case
     */
    public Integer getDistance() {
        return this.distance;
    }

    /**
     * Retourne le plus court chemin a la case aprés avoir executé Dijikstra
     * 
     * @return PlusCourtChemin de la case
     */
    public LinkedList<Case> getPlusCourtChemin() {
        return this.plusCourtChemin;
    }

    /**
     * Retourne la ligne ou se trouve la case
     * 
     * @return La ligne de la case
     */
    public int getLigne() {
        return this.ligne;
    }

    /**
     * Retourne la colonne ou se trouve la case
     * 
     * @return La colonne de la case
     */
    public int getColonne() {
        return this.colonne;
    }

    /**
     * Retourne La nature du terrain que la case représente
     * 
     * @return La nature du terrain de la case
     */
    public NatureTerrain getNature() {
        return this.natureTerrain;
    }

    /**
     * Affiche les attributs de la case
     * 
     * @return Une definition de la case par son numero de ligne et numero colonne
     */
    @Override
    public String toString() {
        return "Case " + this.ligne + " " + this.colonne + " Nature " + this.natureTerrain;
    }

    /**
     * Réinitialise la distance et le plusCourtChemin de la case.
     * Cette fonction est appelée sur cette case chaque fois qu'on veut exécuter
     * algorithme de dijkstra
     */
    public void reset() {
        this.setDistance(Integer.MAX_VALUE);
        this.setPlusCourtChemin(new LinkedList<Case>());
    }
}