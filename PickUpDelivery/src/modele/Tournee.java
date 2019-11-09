package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javafx.util.Pair;

public class Tournee {
	private Entrepot entrepot;
	private Map<Integer, Livraison> livraisons;
	private HashMap<Integer, Noeud> noeudAVisiter;
	private HashMap<Noeud, Integer> mapNoeuds;
	private Plan plan;

	public Tournee(Entrepot entrepot, Map<Integer, Livraison> livraisons, Plan plan) {
		this.entrepot = entrepot;
		this.livraisons = livraisons;
		this.plan = plan;
		setNoeudAVisiter();
	}
	
	//Constructeur test IHM
	public Tournee() {}

	public List<Noeud> calculTournee() {

		List<Noeud> enchainementNoeud = new ArrayList<Noeud>();

		TSP1 voyageurCommerce = new TSP1();
		// voyageurCommerce.chercheSolution(tpsLimite, noeudAVisiter.size(), cout,
		// duree);
		Integer indiceNoeud;
		for (Integer i = 0; i < noeudAVisiter.size(); i++) {
			indiceNoeud = voyageurCommerce.getMeilleureSolution(i);
			enchainementNoeud.add(noeudAVisiter.get(indiceNoeud));
		}

		return enchainementNoeud;

	}
	
	public List<Noeud> FausseTourneePetitIHM(){
		List<Noeud> enchainementNoeud = new ArrayList<Noeud>();
		Noeud n = new Noeud("342873658",45.76038,4.8775625);
		enchainementNoeud.add(n);
		Noeud n1 = new  Noeud("342872879",45.760693,4.8777256);
		enchainementNoeud.add(n1);
		Noeud n2 = new  Noeud("2456932713",45.760902,4.877833);
		enchainementNoeud.add(n2);
		enchainementNoeud.add(n2);
		Noeud n3 = new Noeud("342867241",45.76051,4.879188 );
		enchainementNoeud.add(n3);
		Noeud n4 = new Noeud("342869317",45.759945,4.87886);
		enchainementNoeud.add(n4);
		
		return enchainementNoeud;
		
	}

	private int[] dijkstra(Noeud source) {
		PriorityQueue<Pair<Integer, Noeud>> listeAttente = new PriorityQueue<Pair<Integer, Noeud>>();
		Integer nombreNoeuds = plan.getNoeuds().size();
		int[] distanceAuNoeudSource = new int[nombreNoeuds];

		for (Integer i = 0; i < nombreNoeuds; i++) {
			distanceAuNoeudSource[i] = Integer.MAX_VALUE;
		}
		Pair<Integer, Noeud> src = new Pair<Integer, Noeud>(0, source);
		distanceAuNoeudSource[mapNoeuds.get(source)] = 0;
		listeAttente.add(src);

		while (!listeAttente.isEmpty()) {
			Noeud n = listeAttente.poll().getValue();
			for (Troncon troncon : n.tronconsDepuisLeNoeud) {
				Noeud noeud = troncon.GetNoeudDestination();
				Integer cout = (int) troncon.GetLongueur();
				if (distanceAuNoeudSource[mapNoeuds.get(noeud)] > distanceAuNoeudSource[mapNoeuds.get(n)] + cout) {
					distanceAuNoeudSource[mapNoeuds.get(noeud)] = distanceAuNoeudSource[mapNoeuds.get(n)] + cout;
					Pair<Integer, Noeud> paireCoutNoeud = new Pair<Integer, Noeud>(
							distanceAuNoeudSource[mapNoeuds.get(noeud)], noeud);
					listeAttente.add(paireCoutNoeud);
				}
			}
		}

		return distanceAuNoeudSource;
	}

	private void setNoeudAVisiter() {

		ArrayList<Noeud> ensembleNoeudAVisiter = new ArrayList<Noeud>();
		noeudAVisiter = new HashMap<Integer, Noeud>();
		Set<Map.Entry<Integer, Livraison>> ensembleLivraison = livraisons.entrySet();

		for (Map.Entry<Integer, Livraison> it : ensembleLivraison) {
			ensembleNoeudAVisiter.add(it.getValue().getNoeudEnlevement());
			ensembleNoeudAVisiter.add(it.getValue().getNoeudLivraison());
		}

		Integer indice = 0;

		for (Noeud it : ensembleNoeudAVisiter) {
			noeudAVisiter.put(indice, it);
		}

	}

	private void setCoutPlan() {
		mapNoeuds = new HashMap<Noeud, Integer>();
		Integer indice = 0;
		Set<Map.Entry<String, Noeud>> ensembleNoeud = plan.getNoeuds().entrySet();
		Integer nombreNoeuds = ensembleNoeud.size();
		List<Troncon> listeTroncons = plan.getTroncons();

		for (Troncon it : listeTroncons) {
			if (!mapNoeuds.containsKey(it.GetNoeudOrigine())) {
				mapNoeuds.put(it.GetNoeudOrigine(), indice++);
			}
			if (!mapNoeuds.containsKey(it.GetNoeudDestination())) {
				mapNoeuds.put(it.GetNoeudDestination(), indice++);
			}
		}

	}

	/*
	 * public FeuilleDeRoute GenererFeuilleDeRoute() {
	 * 
	 * 
	 * }
	 */
}