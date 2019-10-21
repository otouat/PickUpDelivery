package modele;

import java.util.Map;
import java.util.Set;

public class Tournee {
	private Entrepot entrepot;
	private Map<Integer, Livraison> livraisons;
	private int[][] cout;
	private int nombreNoeuds;
	private Map<Integer, Noeud> correspNoeudEntier;

	public Tournee(Entrepot entrepot, Map<Integer, Livraison> livraisons) {
		this.entrepot = entrepot;
		this.livraisons = livraisons;
		
	}
	
	public void calculTournee() {
	
	}
	private void dijkstra(Plan plan, Noeud entrepot) {
		
	
	}
	private void dimensionnement() {
		Set<Noeud> ensembleNoeud;
		
	}

	/*
	 * public FeuilleDeRoute GenererFeuilleDeRoute() {
	 * 
	 * 
	 * }
	 */
}