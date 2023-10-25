package Carte;

/**
 * Une classe incendie qui est défini par la case sur laquelle il se situe et le
 * nombre de
 * litres d’eau nécessaires pour l’éteindre.
 */
public class Incendie {
	/**
	 * la case contenant l'incendie
	 */
	private Case casse;
	/**
	 * le nombre de litres d'eau encore nécessaire pour éteindre l'incendie
	 */
	private int quantiteEau;
	/**
	 * boolean indiquant si un robot est en train d'éteindre cet incendie
	 */
	private boolean etat = false;

	/**
	 * Constructeur qui produit un incendie
	 * 
	 * @param casse       la position de l'incendie
	 * @param quantiteEau nombre de litres d'eau pour éteindre l'incendie
	 */
	public Incendie(Case casse, int quantiteEau) {
		this.casse = casse;
		this.quantiteEau = quantiteEau;
	}

	/**
	 * @return La case contenant l'incendie
	 */
	public Case getCase() {
		return this.casse;
	}

	/**
	 * Retourne la quantité d'eau nécessaire pour éteindre l'incendie
	 * 
	 * @return Le nombre de litres nécessaire pour éteindre l'incendie
	 */
	public int getQuantiteEau() {
		return this.quantiteEau;
	}

	/**
	 * Retourne true si un robot est déjà entrain d'éteindre l'incendie,retourne
	 * false sinon
	 * 
	 * @return true si un robot est déjà entrain d'éteindre l'incendie, renvoie
	 *         false sinon.
	 */
	public boolean getEtat() {
		return this.etat;
	}

	/**
	 * Affecte à l'incendie un état donné,true si l'incendie est affecté à un
	 * robot pour l'éteindre, et false dans le cas contraire
	 * 
	 * @param etat l'etat de l'incendie
	 */
	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	/**
	 * Décrémente un volume d'eau versée de nombre de litres nécessaire pour
	 * éteindre l'incendie
	 * 
	 * @param eauVerse Le volume versé à soustraire du nombre de litres
	 *                 nécessaire pour éteindre l'incendie
	 */
	public void decrementerEau(int eauVerse) {
		int diff = this.quantiteEau - eauVerse;
		if (diff <= 0) {
			this.quantiteEau = 0;
		} else {
			this.quantiteEau = diff;
		}
	}

	/**
	 * Retourne une chaine de caractères indiquant qu'il s'agit d'un incendie sa
	 * position
	 * 
	 * @return Chaine de caractères indiquant qu'il s'agit d'un incendie sa position
	 */
	@Override
	public String toString() {
		return "Incendie " + this.getCase().getLigne() + " " + this.getCase().getColonne() + " nombre d'eau "
				+ this.quantiteEau;
	}
}