package modele;

import java.util.Map;

public class Tournee {
	private Entrepot entrepot;
	private Map<Integer, Livraison> livraisons;

	public Tournee(Entrepot entrepot, Map<Integer, Livraison> livraisons) {
		this.entrepot = entrepot;
		this.livraisons = livraisons;
	}

	/*
	 * public FeuilleDeRoute GenererFeuilleDeRoute() {
	 * 
	 * 
	 * }
	 */
}