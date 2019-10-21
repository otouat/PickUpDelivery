package modele;

import java.util.Map;

public class Tournee {
	private Entrepot entrepot;
	private Map<Integer, Livraison> livraisons;

	public Tournee(Entrepot entrepot, Map<Integer, Livraison> livraisons) {
		this.entrepot = entrepot;
		this.livraisons = livraisons;
	}
	
	public void calculTournee() {
	
	}
	private void dijkstra(Plan plan, Noeud entrepot) {
		
	
	}

	/*
	 * public FeuilleDeRoute GenererFeuilleDeRoute() {
	 * 
	 * 
	 * }
	 */
}