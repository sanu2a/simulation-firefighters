package Carte;
import Robot.*;
import java.util.*;
/**
 * Une classe Carte sous forme d'une matrice de n = nbLignes lignes et m =
 * nbColonnes colonnes. Elle contient n x m cases.
 */
public class Carte {
    /**
     * Nombre de lignes de la carte
     */
    private int nbLignes;
    /**
     * Nombre de colonnes de la carte
     */
    private int nbColonnes;
    /**
     * Une taille commune a toutes les cases de la carte
     */
    private int tailleCase;
    /**
     * Les cases contenues dans la carte
     */
    private Case cases[][];
    /**
     * Les cases contenant l'eau
     */
    private ArrayList<Case> eau;

    /**
     * Ce constructeur produit une carte avec les paramètres spécifiés.
     * 
     * @param nbLignes   Nombre de lignes de la carte
     * @param nbColonnes Nombre de lignes de la carte
     * @param tailleCase La taille de chaque Case
     * @param cases      Les cases contenues dans la carte
     * @param eau        ArrayList des cases qui contient l'eau
     */
    public Carte(int nbLignes, int nbColonnes, int tailleCase, Case cases[][], ArrayList<Case> eau) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.tailleCase = tailleCase;
        this.cases = cases;
        this.eau = eau;
    }

    /**
     * Retourne le nombre de lignes de la carte
     * 
     * @return Le nombre de lignes de la carte
     */
    public int getNbLignes() {
        return this.nbLignes;
    }

    /**
     * Retourne le nombre de colonnes de la carte
     * 
     * @return Le nombre de colonnes de la carte
     */
    public int getNbColonnes() {
        return this.nbColonnes;
    }

    /**
     * Retourne la taille des cases de la carte
     * 
     * @return La taille des cases de la carte
     */
    public int getTailleCase() {
        return this.tailleCase;
    }

    /**
     * Retourne la case dans une position (ligne, colonne) donnée
     * 
     * @param ligne   le numéro de la ligne
     * @param colonne le numéro de la colonne
     * @return La case qui se trouve dans la position donnée
     */
    public Case getCase(int ligne, int colonne) {
        return this.cases[ligne][colonne];
    }

    /**
     * Rtourne un tableau qui contient les cases de la cartes de nature eau.
     * 
     * @return Les cases contenant l'eau
     */
    public ArrayList<Case> getEau() {
        return this.eau;

    }

    /**
     * Retourne un boolean indiquant si une case voisine a la case donnée dans une
     * direction donnée existe ou non
     * 
     * @param casse     La case dont cherche le voisin
     * @param direction La direction dans laquelle on cherche le voisin
     * @return true si une case voisine existe dans la direction donnée et false
     *         sinon
     */
    public boolean voisinExiste(Case casse, Direction direction) {
        int ligne = casse.getLigne();
        int colonne = casse.getColonne();
        switch (direction) {
            case NORD:
                if (ligne != 0)
                    return true;
                return false;
            case SUD:
                if (ligne != this.nbLignes - 1)
                    return true;
                return false;
            case OUEST:
                if (colonne != 0)
                    return true;
                return false;
            case EST:
                if (colonne != this.nbColonnes - 1)
                    return true;
                return false;
        }
        return false;
    }

    /**
     * Retourne la case voisine à la case donnée dans une direction donnée si elle
     * existe, sinon retourne null.
     * 
     * @param casse     La case dont cherche le voisin
     * @param direction La direction dans laquelle on cherche le voisin
     * @return La case voisine si elle existe et null sinon
     */
    public Case getVoisin(Case casse, Direction direction) {
        int ligne = casse.getLigne();
        int colonne = casse.getColonne();
        switch (direction) {
            case NORD:
                if (ligne != 0)
                    return this.cases[ligne - 1][colonne];
            case SUD:
                if (ligne != this.nbLignes - 1)
                    return this.cases[ligne + 1][colonne];
            case OUEST:
                if (colonne != 0)
                    return this.cases[ligne][colonne - 1];
            case EST:
                if (colonne != this.nbColonnes - 1)
                    return this.cases[ligne][colonne + 1];
        }
        return null;
    }

    /**
     * Réinitialise l'attribut distance de toutes les cases de notre carte pour
     * pouvoir exécuter Dijikstra
     */
    public void reset() {
        for (Case[] cases : this.cases) {
            for (Case casse : cases) {
                casse.reset();
            }
        }
    }

    /**
     * Exécute l'algorithme Dijkstra pour trouver le plus court chemin à toutes les
     * cases (accessible au robot)
     * de notre graphe (carte) et le stocke dans le plus Court Chemin de chaque case
     * à
     * l'aide de la méthode calculDistanceMinimale
     * 
     * @param robot Le robot à partir du quel on calcule les plus courts chemins
     */
    public void calculePlusCourtChemin(Robot robot) {
        robot.getPosition().setDistance(0);
        Set<Case> casesVisitees = new HashSet<>();
        Set<Case> casesNonVisitees = new HashSet<>();
        casesNonVisitees.add(robot.getPosition());
        while (casesNonVisitees.size() != 0) {
            Case caseCourante = getDistanceMin(casesNonVisitees);
            casesNonVisitees.remove(caseCourante);
            for (Direction direction : Direction.values()) {
                if (robot.getCarte().voisinExiste(caseCourante, direction)
                        && robot.peutSeDeplacer(robot.getCarte().getVoisin(caseCourante, direction).getNature())) {
                    Case caseAdjacente = robot.getCarte().getVoisin(caseCourante, direction);
                    Integer coutDeplacementt = caseAdjacente.getDistance();
                    if (!casesVisitees.contains(caseAdjacente)) {
                        calculDistanceMinimale(caseAdjacente, coutDeplacementt, caseCourante, robot);
                        casesNonVisitees.add(caseAdjacente);
                    }
                }
            }
            casesVisitees.add(caseCourante);
        }
    }

    /**
     * Met à jour de la case adjacente (sommet) si la distance déjà existante est
     * supérieure a celle obtenu en ajoutant le temps du déplacement du robot (le
     * cout de l'arc)
     * entre la case courante (supposant le robot y est) et la case adjacente à la
     * distance de la case ajoutée
     * 
     * @param robot            Le robot à partir du quel on calcule les plus courts
     *                         chemins
     * @param caseAdjacente    La case adjacente
     * @param coutDeplacementt Le cout de déplacement de la case courante (supposant
     *                         contient le robot) et la case adjacente
     * @param caseCourante     La case du départ supposant contient le robot
     */
    private static void calculDistanceMinimale(Case caseAdjacente, Integer coutDeplacementt, Case caseCourante,
            Robot robot) {
        Integer sourceDistance = caseCourante.getDistance();
        if (robot.calculTempsDeplacement(caseAdjacente.getNature(), caseCourante.getNature())
                + sourceDistance < caseAdjacente
                        .getDistance()) {
            caseAdjacente
                    .setDistance(sourceDistance
                            + robot.calculTempsDeplacement(caseAdjacente.getNature(), caseCourante.getNature()));
            LinkedList<Case> plusCourtChemin = new LinkedList<Case>(caseCourante.getPlusCourtChemin());
            plusCourtChemin.add(caseCourante);
            caseAdjacente.setPlusCourtChemin(plusCourtChemin);
        }
    }

    /**
     * Renvoie la case avec la distance minimale parmi les cases (sommets)
     *         non visitees
     * @param caseNonVisitees Ensemble des sommets non marqués
     * @return La case avec la distance minimale parmi les cases (sommets)
     *         non visitees
     */
    private static Case getDistanceMin(Set<Case> casesNonVisitees) {
        Case casePlusProche = null;
        int distanceMinimale = Integer.MAX_VALUE;
        for (Case casse : casesNonVisitees) {
            int caseDistance = casse.getDistance();
            if (caseDistance < distanceMinimale) {
                distanceMinimale = caseDistance;
                casePlusProche = casse;
            }
        }
        return casePlusProche;
    }
}
