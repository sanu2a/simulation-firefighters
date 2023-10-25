package ChefPompier;
import Simulation.*;
import Evenements.*;

/**
 * Evenement d'intervention du chef pompier pour executer une strategie pour
 * organiser les déplacements et interventions des différents robots afin
 * d’éteindre tous les incendies au plus vite.
 */
public class InterventionChef extends Evenement {
	/**
	 * Attribut de type ChefPompier permettant d'exécuter la stratégie choisie
	 */
	ChefPompier chefPompier;

	/**
	 * Le constructeur de la classe Interventionchef qui permit d'exécuter la
	 * stratégie chaque pas de temps
	 * 
	 * @param chefPompier le chef pompier
	 * @param simulateur  Le simulateur a associer
	 * @param date        La date d'exécution de la stratégie
	 */
	public InterventionChef(ChefPompier chefPompier, Simulateur simulateur, long date) {
		super(date, simulateur);
		this.chefPompier = chefPompier;

	}

	@Override
	/**
	 * La fonction qui s'occupe d'exécuter la stratégie
	 */
	public void execute() {
		this.chefPompier.intervention(this.simulateur);
	}
}
