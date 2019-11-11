package modele;

import java.util.ArrayList;
import java.util.List;

public class FeuilleDeRoute {
	private Entrepot entrepot;
	private List<Instruction> instructions;
	private List<Noeud> tournee;
	Plan plan;

	public FeuilleDeRoute(Entrepot entrepot, List<Noeud> tournee, Plan plan) {
		this.entrepot = entrepot;
		this.instructions = new ArrayList<Instruction>();
		this.tournee = tournee;
		for (int i = 0; i < tournee.size() - 2; i++) {
			Noeud noeudCourant = tournee.get(i);
			Noeud noeudSuivant = tournee.get(i + 1);
			Noeud noeudApres = tournee.get(i + 2);
			if (noeudCourant.GetIdNoeud().contentEquals(noeudSuivant.GetIdNoeud())) {
				continue;
			}
			Instruction instruction = new Instruction(noeudCourant, noeudSuivant, noeudApres, plan);
			instructions.add(instruction);
		}
	}

	public String toString() {
		String resultat = "";
		for (int i = 0; i < instructions.size(); i++) {
			resultat += "Instruction " + (i + 1) + ":" + instructions.get(i).toString() + ".\n";
		}
		return resultat;
	}

}