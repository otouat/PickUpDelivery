package modele;

import java.util.ArrayList;

public class FeuilleDeRoute {
	private Entrepot entrepot;
	private ArrayList<Instruction> instructions;

	public FeuilleDeRoute(Entrepot entrepot, ArrayList<Instruction> instructions) {
		this.entrepot = entrepot;
		this.instructions = instructions;
	}

}