package modele;

import java.util.ArrayList;
import java.util.List;

public class FeuilleDeRoute {
	private Entrepot entrepot;
	private List<Instruction> instructions;
	private Tournee tournee;

	private List<Noeud> listeTournee;
	Plan plan;

	public FeuilleDeRoute(Entrepot entrepot, List<Noeud> listeTournee, Plan plan, Tournee tournee) {
		this.tournee = tournee;
		this.entrepot = entrepot;
		this.instructions = new ArrayList<Instruction>();
		this.listeTournee = listeTournee;
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
				Instruction instruction = new Instruction(noeudCourant, noeudSuivant, plan, typeNoeud);
				instructions.add(instruction);
			} else {
				Instruction instruction = new Instruction(noeudCourant, noeudSuivant, noeudApres, plan);
				instructions.add(instruction);
			}
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