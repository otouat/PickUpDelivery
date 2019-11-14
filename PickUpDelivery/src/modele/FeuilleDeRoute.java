/**
 * Une feuille de route est l'itinéraire détaillé indiquant au livreur l’ordre des lieux à visiter.
 * La classe FeuilleDeRoute permet de générer la feuille de route depuis une tournée calculée.
 * @author Tianming
 * @see Tournee
 */

package modele;

import java.util.ArrayList;
import java.util.List;

public class FeuilleDeRoute {

	private List<Instruction> instructions;
	private Tournee tournee;

	private List<Noeud> listeTournee;
	private Plan plan;

	/**
	 * Constructeur de FeuilleDeRoute, qui calcule et remplit la liste d'instruction
	 * 
	 * @param listeTournee : la liste de noeud qui représente le chemin optimisée
	 *                     calculé par une tournée de type Tournee
	 * @param plan         : plan chargé
	 * @param tournee      : la tournée qui génère la listeTournee
	 */
	public FeuilleDeRoute(List<Noeud> listeTournee, Plan plan, Tournee tournee) {
		this.tournee = tournee;
		this.plan = plan;
		this.instructions = new ArrayList<Instruction>();
		this.listeTournee = listeTournee;
		calculerListeInstruction();
	}

	/**
	 * Cette méthode permet de calculer et remplir la liste d'instructions. Elle
	 * parcour la liste de noeuds du chemin puis génère une instruction depuis
	 * chaque trois noeuds, si elle trouve un noeud spécial (noeud d'enlèvement ou
	 * noeud de livraison), elle va générer une instruction spéciale
	 */
	private void calculerListeInstruction() {
		int n = 1;
		for (int i = 0; i < listeTournee.size() - 2; i++) {
			Noeud noeudCourant = listeTournee.get(i);
			Noeud noeudSuivant = listeTournee.get(i + 1);
			Noeud noeudApres = listeTournee.get(i + 2);
			if (noeudCourant.GetIdNoeud().contentEquals(noeudSuivant.GetIdNoeud())) {
				continue;
			} else if (noeudSuivant.GetIdNoeud().contentEquals(noeudApres.GetIdNoeud())) {
				Integer indice = tournee.getEnchainementNoeudAVisiter().get(n++);
				System.out.println(indice);
				System.out.println(tournee.getNoeudAVisiter().get(indice));
				boolean typeNoeud = tournee.getNoeudAVisiter().get(indice).getThird();
				String toDo = "";
				if (typeNoeud == true) {
					toDo = "recuperer";
				} else {
					toDo = "livrer";
				}

				noeudApres = listeTournee.get(i + 3);
				Instruction instructionTemp = new Instruction(noeudCourant, noeudSuivant, noeudApres, plan);
				String directionTemp = instructionTemp.getDirection();
				Instruction instruction = new Instruction(noeudCourant, noeudSuivant, plan, toDo, directionTemp);
				instructions.add(instruction);
			} else {
				Instruction instruction = new Instruction(noeudCourant, noeudSuivant, noeudApres, plan);
				instructions.add(instruction);
			}
		}
		int taille = listeTournee.size();
		Instruction instruction = new Instruction(listeTournee.get(taille - 2), listeTournee.get(taille - 1), plan,
				"termine", ""); //
		instructions.add(instruction);
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

	public String toString() {
		String resultat = "";
		for (int i = 0; i < instructions.size(); i++) {
			resultat += "Instruction " + (i + 1) + ":\n" + instructions.get(i).toString() + ".\n";
		}
		return resultat;
	}

}