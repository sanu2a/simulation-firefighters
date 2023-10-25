package ChefPompier;
import Carte.*;
import Simulation.*;
import Robot.*;
import Evenements.*;
import java.util.*;

/**
 * La classe ChefPompier permettant de donner les consignes au robot
 * pour eteindre les incendies le plus vite possible en fonction de la stratégie
 * choisie
 */
public class ChefPompier {
	/**
	 * un attribut donne contenant la carte, les incednies et la liste des robots
	 */
	private DonneesSimulation donnee;
	/**
	 * Un boolean indiquant si on choisit la strategie evoluée ou elementaire
	 */
	private boolean estEvoluee;

	/**
	 * Constructeur de ChefPompier
	 * 
	 * @param donnee     DonneesSimulation permettant de savoir l'environnement de
	 *                   la
	 *                   simulation (robot, incendies, sources de l'eau...).
	 * @param estEvoluee Un boolean indiquant si on choisit la strategie evoluée ou
	 *                   elementaire
	 */
	public ChefPompier(DonneesSimulation donnee, boolean estEvoluee) {
		this.donnee = donnee;
		this.estEvoluee = estEvoluee;
	}

	/**
	 * La StrategieElementaire consiste à envoyer des robots non occupé pour
	 * intervenir
	 * sur des incendies non affectée sans
	 * recherche d’optimisation du processus global. Les robots ne cherchent pas a
	 * remplir leur réservoir.
	 * Le chefPompier refait le precessus d'affectation chaque 100 pas de temps
	 * 
	 * @param simulateur Pour récupérer la date courante de la simulateur et insérer
	 *                   l'ensemble des évènements à executer
	 */
	public void strategieElementaire(Simulateur simulateur) {
		// boolean existeIncendie = false;
		Robot Robots[] = donnee.getRobots();
		Incendie Incendies[] = donnee.getIncendies();
		for (Incendie incendie : Incendies) {
			if (incendie.getEtat() == false) {
				if (incendie.getQuantiteEau() > 0) {
					// existeIncendie = true;
					for (Robot robot : Robots) {
						if (!robot.getEtat() && robot.getCapReservoirAct() > 0) {
							if (robot.peutEteindre(incendie)) {
								incendie.setEtat(true);
								robot.setEtat(true);
								VerseEau verseEau = new VerseEau(
										simulateur.getDateCourante() + robot.getTempsParUnite(), robot, incendie,
										simulateur,
										false);
								simulateur.ajouteEvenement(verseEau);
								break;
							}
							LinkedList<Case> chemin = robot.plusCourtChemin(incendie.getCase());
							if (chemin.size() != 0) {
								incendie.setEtat(true);
								robot.setEtat(true);
								robot.programmeEvenementsDeplacement(incendie.getCase(), simulateur);
								long date = simulateur.getDateCourante()
										+ robot.calculCoutChemin(chemin, incendie.getCase());
								VerseEau verseEau = new VerseEau(date + robot.getTempsParUnite(), robot, incendie,
										simulateur,
										false);
								simulateur.ajouteEvenement(verseEau);
								break;
							}
						}
					}
				}

			}
		}
		for (Robot robot : simulateur.getDonnee().getRobots()) {
			if (robot.getEtat()) {
				InterventionChef interventioChefPompier = new InterventionChef(this, simulateur,
						simulateur.getDateCourante() + 100);
				simulateur.ajouteEvenement(interventioChefPompier);
				break;
			}
		}
	}

	/**
	 * StrategieEvoluee consiste a affecter une incendie au robot disponible le plus
	 * proche
	 * Les robots peuvent aller se remplir au point d’eau le plus proche.
	 * 
	 * @param simulateur Pour récupérer la date courante de la simulateur
	 */
	public void strategieEvoluee(Simulateur simulateur) {
		// boolean existeIncendie = false;
		Robot Robots[] = donnee.getRobots();
		Incendie Incendies[] = donnee.getIncendies();
		for (Incendie incendie : Incendies) {
			if (incendie.getQuantiteEau() > 0) {
				// existeIncendie = true;
				if (incendie.getEtat() == false) {
					Robot robotOptimal = null;
					long coutMin = Integer.MAX_VALUE;
					for (Robot robot : Robots) {
						if (robot.getEtat() || robot.getCapReservoir() <= 0) {
							continue;
						}
						if (robot.peutEteindre(incendie)) {
							coutMin = 0;
							robotOptimal = robot;
							break;
						}
						LinkedList<Case> chemin = robot.plusCourtChemin(incendie.getCase());
						if (chemin.size() != 0) {
							long date = robot.calculCoutChemin(chemin, incendie.getCase());
							if (coutMin > date) {
								coutMin = date;
								robotOptimal = robot;
							}
						}
					}
					if (robotOptimal != null) {
						robotOptimal.setEtat(true);
						incendie.setEtat(true);
						if (!robotOptimal.peutEteindre(incendie)) {
							robotOptimal.programmeEvenementsDeplacement(incendie.getCase(), simulateur);
						}
						long date = simulateur.getDateCourante() + coutMin;
						VerseEau verserEau = new VerseEau(date + robotOptimal.getTempsParUnite(), robotOptimal,
								incendie,
								simulateur, true);
						simulateur.ajouteEvenement(verserEau);
					}
				}
			}
		}
		for (Robot robot : simulateur.getDonnee().getRobots()) {
			if (robot.getEtat()) {
				InterventionChef interventioChefPompier = new InterventionChef(this, simulateur,
						simulateur.getDateCourante() + 100);
				simulateur.ajouteEvenement(interventioChefPompier);
				break;
			}
		}
	}

	/**
	 * Appele la strategie adequate selon estEvoluee
	 * 
	 * @param simulateur le simulateur
	 */
	public void intervention(Simulateur simulateur) {
		if (estEvoluee) {
			this.strategieEvoluee(simulateur);
			return;
		}
		this.strategieElementaire(simulateur);

	}
}