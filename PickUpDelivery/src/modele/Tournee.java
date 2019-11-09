package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Tournee {
	private Entrepot entrepot;
	private List<Livraison> livraisons;
	private HashMap<Integer, Noeud> noeudAVisiter;
	private HashMap<Noeud, Integer> mapNoeuds;
	private Plan plan;
	private HashMap<Integer, Integer> mapDureeVisite;
	private HashMap<Noeud, Noeud> tableauPrecedence;
	private List<HashMap<Noeud, Noeud>> plusCourtChemins;
	private Integer[][] precedence;

	public Tournee(Entrepot entrepot, List<Livraison> livraisons, Plan plan) {
		this.entrepot = entrepot;
		this.livraisons = livraisons;
		this.plan = plan;
		mapNoeuds = new HashMap<Noeud, Integer>();
		noeudAVisiter = new HashMap<Integer, Noeud>();
		tableauPrecedence = new HashMap<Noeud, Noeud>();
		plusCourtChemins = new ArrayList<HashMap<Noeud, Noeud>>();
		setNoeudAVisiter();
		setNoeudPlan();
		System.out.println(noeudAVisiter.get(0));
	}

	public List<Noeud> calculTournee() {

		List<Noeud> enchainementNoeud = new ArrayList<Noeud>();
		int[][] cout = new int[noeudAVisiter.size()][noeudAVisiter.size()];
		int[] distanceDijkstra = new int[plan.getNoeuds().size()];
		int[] dureeVisite = new int[noeudAVisiter.size()];

		// System.out.println(mapNoeuds.toString());
		for (Integer i = 0; i < noeudAVisiter.size(); i++) {
			// System.out.println(noeudAVisiter.get(i).toString());
			distanceDijkstra = dijkstra(noeudAVisiter.get(i));
			plusCourtChemins.add(tableauPrecedence);
			for (Integer j = 0; j > i && j < noeudAVisiter.size(); i++) {
				cout[i][j] = distanceDijkstra[mapNoeuds.get(noeudAVisiter.get(i))];
				cout[j][i] = distanceDijkstra[mapNoeuds.get(noeudAVisiter.get(i))];
			}
			System.out.println("Durée Visite:" + mapDureeVisite.get(i));
			dureeVisite[i] = mapDureeVisite.get(i);
		}

		TSP1 voyageurCommerce = new TSP1();
		voyageurCommerce.chercheSolution(20000, noeudAVisiter.size(), cout, dureeVisite);
		Integer indiceNoeud;
		Integer indiceNoeudSuivant;
		HashMap<Noeud, Noeud> courtChemin;
		Noeud noeudActuel;
		Noeud noeudSuivant;
		/*
		 * dijkstra(entrepot); ArrayList<Noeud> chemin =
		 * parcoursChemin(tableauPrecedence,
		 * noeudAVisiter.get(voyageurCommerce.getMeilleureSolution(0)), entrepot);
		 * enchainementNoeud.addAll(chemin);
		 */
		ArrayList<Noeud> chemin = new ArrayList<Noeud>();
		for (Integer i = 0; i < noeudAVisiter.size(); i++) {
			indiceNoeud = voyageurCommerce.getMeilleureSolution(i);
			courtChemin = plusCourtChemins.get(indiceNoeud);
			indiceNoeudSuivant = voyageurCommerce.getMeilleureSolution(i + 1);
			noeudActuel = noeudAVisiter.get(indiceNoeud);
			noeudSuivant = noeudAVisiter.get(indiceNoeudSuivant);
			chemin = parcoursChemin(courtChemin, noeudSuivant, noeudActuel);
			enchainementNoeud.addAll(chemin);
		}
		chemin = parcoursChemin(plusCourtChemins.get(voyageurCommerce.getMeilleureSolution(noeudAVisiter.size() - 1)),
				entrepot.getNoeudEntrepot(),
				noeudAVisiter.get(voyageurCommerce.getMeilleureSolution(noeudAVisiter.size() - 1)));
		enchainementNoeud.addAll(chemin);
		return enchainementNoeud;

	}

	private ArrayList<Noeud> parcoursChemin(HashMap<Noeud, Noeud> courtChemin, Noeud noeudSuivant, Noeud noeudActuel) {
		ArrayList<Noeud> chemin = new ArrayList<Noeud>();
		Noeud current = noeudSuivant;
		chemin.add(courtChemin.get(noeudSuivant));
		while (!courtChemin.get(current).equals(noeudActuel)) {
			noeudActuel = courtChemin.get(current);
			chemin.add(noeudActuel);
		}

		Collections.reverse(chemin);
		return chemin;
	}

	private int[] dijkstra(Noeud source) {
		tableauPrecedence.clear();
		PriorityQueue<Pair<Integer, Noeud>> listeAttente = new PriorityQueue<Pair<Integer, Noeud>>();
		Integer nombreNoeuds = plan.getNoeuds().size();
		int[] distanceAuNoeudSource = new int[nombreNoeuds];

		for (Integer i = 0; i < nombreNoeuds; i++) {
			distanceAuNoeudSource[i] = Integer.MAX_VALUE;
		}
		Pair<Integer, Noeud> src = new Pair<Integer, Noeud>(0, source);
		System.out.println(source.toString());
		// distanceAuNoeudSource[mapNoeuds.get(source)] = 0;
		System.out.println(mapNoeuds.get(plan.getNoeuds().get("2")) + "111");
		if (source.GetIdNoeud() == entrepot.GetIdNoeud()) {
			String idNoeudEntrepot = entrepot.GetIdNoeud();
			Integer index = mapNoeuds.get(plan.getNoeuds().get(idNoeudEntrepot));
			distanceAuNoeudSource[index] = 0;
		} else {
			Integer index = mapNoeuds.get(source);
			distanceAuNoeudSource[index] = 0;
		}
		listeAttente.add(src);

		while (!listeAttente.isEmpty()) {
			Noeud n = listeAttente.poll().getNoeud();
			for (Troncon troncon : n.tronconsDepuisLeNoeud) {
				Noeud noeud = troncon.GetNoeudDestination();
				Integer cout = (int) troncon.GetLongueur();
				if (distanceAuNoeudSource[mapNoeuds.get(noeud)] > distanceAuNoeudSource[mapNoeuds.get(n)] + cout) {
					distanceAuNoeudSource[mapNoeuds.get(noeud)] = distanceAuNoeudSource[mapNoeuds.get(n)] + cout;
					tableauPrecedence.put(noeud, n);
					Pair<Integer, Noeud> paireCoutNoeud = new Pair<Integer, Noeud>(
							distanceAuNoeudSource[mapNoeuds.get(noeud)], noeud);
					listeAttente.add(paireCoutNoeud);
				}
			}
		}
		for (int i = 0; i < nombreNoeuds; i++) {
			System.out.println(distanceAuNoeudSource[i]);
		}
		return distanceAuNoeudSource;
	}

	private void setNoeudAVisiter() {

		ArrayList<Noeud> ensembleNoeudAVisiter = new ArrayList<Noeud>();

		mapDureeVisite = new HashMap<Integer, Integer>();

		mapDureeVisite.put(0, 0);
		// ?????????????????????????????????????/

		// Integer indice = 0;
		Integer indice = 1;
		for (Livraison it : livraisons) {
			ensembleNoeudAVisiter.add(it.getNoeudEnlevement());
			mapDureeVisite.put(indice++, it.getDureeEnlevement());
			ensembleNoeudAVisiter.add(it.getNoeudLivraison());
			mapDureeVisite.put(indice++, it.getDureeLivraison());
		}

		indice = 1;

		for (Noeud it : ensembleNoeudAVisiter) {
			noeudAVisiter.put(indice++, it);
		}
		// Noeud noeudEntrepot = new Noeud(entrepot.GetIdNoeud(),
		// entrepot.GetLatitude(), entrepot.GetLongitude());
		// noeudAVisiter.put(0, entrepot.getNoeudEntrepot());
		noeudAVisiter.put(0, (Noeud) entrepot);

	}

	private void setNoeudPlan() {

		Integer indice = 0;
		List<Troncon> listeTroncons = plan.getTroncons();

		for (Troncon it : listeTroncons) {
			if (!mapNoeuds.containsKey(it.GetNoeudOrigine())) {
				mapNoeuds.put(it.GetNoeudOrigine(), indice);
				indice = indice + 1;
			}
			if (!mapNoeuds.containsKey(it.GetNoeudDestination())) {
				mapNoeuds.put(it.GetNoeudDestination(), indice);
				indice = indice + 1;
			}
		}

	}

	private void calculPrecedence() {
		precedence = new Integer[noeudAVisiter.size()][2];
		int j = 0;
		for (int i = 2; i < noeudAVisiter.size(); i = i + 2) {
			precedence[j][0] = i;
			precedence[j][1] = i - 1;
		}
	}

	/*
	 * public FeuilleDeRoute GenererFeuilleDeRoute() {
	 * 
	 * 
	 * }
	 */
}