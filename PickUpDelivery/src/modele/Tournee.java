package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Tournee {
	private Noeud entrepot;
	private List<Livraison> livraisons;
	private HashMap<Integer, Triplet<Noeud,Livraison,Boolean>> noeudAVisiter;
	private HashMap<Noeud, Integer> mapNoeuds;
	private Plan plan;
	private HashMap<Integer, Integer> mapDureeVisite;
	private List<HashMap<Noeud, Noeud>> plusCourtChemins;
	private HashMap<Integer, Integer> precedence;
	List<Noeud> enchainementNoeud;
	List<Noeud> enchainementNoeudAVisiter;

	public Tournee(Noeud entrepot, List<Livraison> livraisons, Plan plan) {
		String idNoeudEntrepot = entrepot.GetIdNoeud();
		this.entrepot  = plan.getNoeuds().get(idNoeudEntrepot);
		this.livraisons = livraisons;
		this.plan = plan;
		this.enchainementNoeud = new ArrayList<Noeud>();
		this.enchainementNoeudAVisiter = new ArrayList<Noeud>();
		this.mapNoeuds = new HashMap<Noeud, Integer>();
		this.noeudAVisiter = new HashMap<Integer, Triplet<Noeud,Livraison,Boolean>>();
		this.plusCourtChemins = new ArrayList<HashMap<Noeud, Noeud>>();
		setNoeudAVisiter();
		setNoeudPlan();
	}
	
	//Constructeur test IHM
	public Tournee() {}

	public List<Noeud> calculTournee() {

		int[][] cout = new int[noeudAVisiter.size()][noeudAVisiter.size()];
		int[] distanceDijkstra = new int[plan.getNoeuds().size()];
		int[] dureeVisite = new int[noeudAVisiter.size()];

		// System.out.println(mapNoeuds.toString());
		for (Integer i = 0; i < noeudAVisiter.size(); i++) {
			// System.out.println(noeudAVisiter.get(i).toString());
			distanceDijkstra = dijkstra(noeudAVisiter.get(i).getFirst());
			for (Integer j = 0; j > i && j < noeudAVisiter.size(); j++) {
				cout[i][j] = distanceDijkstra[mapNoeuds.get(noeudAVisiter.get(j).getFirst())];
				cout[j][i] = distanceDijkstra[mapNoeuds.get(noeudAVisiter.get(j).getFirst())];
			}
			dureeVisite[i] = mapDureeVisite.get(i);
		}
		calculPrecedence();
		System.out.println(precedence);

		TSP2 voyageurCommerce = new TSP2();
		voyageurCommerce.chercheSolution(2000, noeudAVisiter.size(), cout, dureeVisite,precedence);
		
		Integer indiceNoeud;
		Integer indiceNoeudSuivant;
		HashMap<Noeud, Noeud> courtChemin;
		Noeud noeudActuel;
		Noeud noeudSuivant;
		
		for (Integer i = 0; i < noeudAVisiter.size(); i++) {
			System.out.println(noeudAVisiter.get(voyageurCommerce.getMeilleureSolution(i)).getFirst());
		}
		System.out.println("--------------------------\n");
		ArrayList<Noeud> chemin = new ArrayList<Noeud>();
		for (Integer i = 0; i < noeudAVisiter.size()-1; i++) {
			indiceNoeud = voyageurCommerce.getMeilleureSolution(i);
			courtChemin = plusCourtChemins.get(indiceNoeud);
			indiceNoeudSuivant = voyageurCommerce.getMeilleureSolution(i + 1);
			noeudActuel = (Noeud)noeudAVisiter.get(indiceNoeud).getFirst();
			this.enchainementNoeudAVisiter.add(noeudActuel);
			noeudSuivant = (Noeud)noeudAVisiter.get(indiceNoeudSuivant).getFirst();
			chemin = parcoursChemin(courtChemin, noeudSuivant, noeudActuel);
			
			this.enchainementNoeud.addAll(chemin);
			
		}
		this.enchainementNoeudAVisiter.add(noeudAVisiter.get(voyageurCommerce.getMeilleureSolution(noeudAVisiter.size() - 1)).getFirst());
		chemin = parcoursChemin(plusCourtChemins.get(voyageurCommerce.getMeilleureSolution(noeudAVisiter.size() - 1)),
				(Noeud) this.entrepot, noeudAVisiter.get(voyageurCommerce.getMeilleureSolution(noeudAVisiter.size() - 1)).getFirst());
		this.enchainementNoeud.addAll(chemin);
		return this.enchainementNoeud;

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

	private ArrayList<Noeud> parcoursChemin(HashMap<Noeud, Noeud> courtChemin, Noeud noeudSuivant, Noeud noeudActuel) {
		ArrayList<Noeud> chemin = new ArrayList<Noeud>();
		Noeud current = noeudSuivant;
		chemin.add(noeudSuivant);
		if(courtChemin.get(current).equal(noeudActuel)) {
			chemin.add(noeudActuel);
		}
		else {	
			
			while (!courtChemin.get(current).equal(noeudActuel)) {
				chemin.add(courtChemin.get(current));
				current=courtChemin.get(current);
				
			}
			chemin.add(noeudActuel);
		}
		Collections.reverse(chemin);
		return chemin;
	
	}

	private int[] dijkstra(Noeud source) {
		HashMap<Noeud, Noeud> tableauPrecedence=new HashMap<Noeud, Noeud>();
		PriorityQueue<Pair<Integer, Noeud>> listeAttente = new PriorityQueue<Pair<Integer, Noeud>>();
		Integer nombreNoeuds = plan.getNoeuds().size();
		int[] distanceAuNoeudSource = new int[nombreNoeuds];

		for (Integer i = 0; i < nombreNoeuds; i++) {
			distanceAuNoeudSource[i] = Integer.MAX_VALUE;
		}
		Pair<Integer, Noeud> src = new Pair<Integer, Noeud>(0, source);
		// distanceAuNoeudSource[mapNoeuds.get(source)] = 0;

		Integer index = mapNoeuds.get(source);
		distanceAuNoeudSource[index] = 0;

		listeAttente.add(src);
		tableauPrecedence.put(source, null);
		while (!listeAttente.isEmpty()) {
			Noeud n = listeAttente.poll().getNoeud();
			for (Troncon troncon : n.tronconsDepuisLeNoeud) {
				Noeud noeud = troncon.GetNoeudDestination();
				Integer cout = (int) (troncon.GetLongueur()/4.166);
				if (distanceAuNoeudSource[mapNoeuds.get(noeud)] > distanceAuNoeudSource[mapNoeuds.get(n)] + cout) {
					distanceAuNoeudSource[mapNoeuds.get(noeud)] = distanceAuNoeudSource[mapNoeuds.get(n)] + cout;
					tableauPrecedence.put(noeud, n);
					Pair<Integer, Noeud> paireCoutNoeud = new Pair<Integer, Noeud>(
							distanceAuNoeudSource[mapNoeuds.get(noeud)], noeud);
					listeAttente.add(paireCoutNoeud);
				}
			}
		}
		plusCourtChemins.add(tableauPrecedence);
		return distanceAuNoeudSource;
	}

	private void setNoeudAVisiter() {



		mapDureeVisite = new HashMap<Integer, Integer>();

		mapDureeVisite.put(0, 0);
		Integer indice = 1;
		Integer indiceBis = 1;
		for (Livraison it : livraisons) {
			Triplet<Noeud,Livraison,Boolean> noeudEnlevement=new Triplet<Noeud,Livraison,Boolean>(it.getNoeudEnlevement(),it,true);
			Triplet<Noeud,Livraison,Boolean> noeudLivraison=new Triplet<Noeud,Livraison,Boolean>(it.getNoeudLivraison(),it,false);
			noeudAVisiter.put(indiceBis++, noeudEnlevement);
			noeudAVisiter.put(indiceBis++, noeudLivraison);
			mapDureeVisite.put(indice++, it.getDureeEnlevement());
			mapDureeVisite.put(indice++, it.getDureeLivraison());
		}

		Triplet<Noeud,Livraison,Boolean> tripletEntrepot=new Triplet<Noeud,Livraison,Boolean>(this.entrepot,null,null);
		noeudAVisiter.put(0, tripletEntrepot);
		

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
		precedence = new HashMap<Integer, Integer>();
		for (int i = 2; i < noeudAVisiter.size(); i = i + 2) {
			precedence.put(i, i-1);
		}
	}

	/*
	 * public FeuilleDeRoute GenererFeuilleDeRoute() {
	 * 
	 * 
	 * }
	 */
}