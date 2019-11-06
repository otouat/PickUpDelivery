package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javafx.util.Pair;


public class Tournee {
	private Entrepot entrepot;
	private List<Livraison> livraisons;
	private HashMap<Integer,Noeud> noeudAVisiter;
	private HashMap<Noeud,Integer> mapNoeuds;
	private Plan plan;
	private HashMap<Integer,Integer> mapDureeVisite;
	private Map<Noeud,Noeud> tableauPrecedence;
	private List<Map<Noeud,Noeud>> plusCourtChemins;

	public Tournee(Entrepot entrepot, List<Livraison> livraisons,Plan plan) {
		this.entrepot = entrepot;
		this.livraisons = livraisons;
		this.plan=plan;
		tableauPrecedence=new HashMap<Noeud,Noeud>();
		setNoeudAVisiter();
		setNoeudPlan();
	}
	
	public List<Noeud> calculTournee() {
		
		List<Noeud> enchainementNoeud=new ArrayList<Noeud>();
		int[][] cout = new int[noeudAVisiter.size()][noeudAVisiter.size()];
		int[] distanceDijkstra = new int[plan.getNoeuds().size()];
		int[] dureeVisite = new int[noeudAVisiter.size()];
		
		for (Integer i=0; i<noeudAVisiter.size();i++) {
			distanceDijkstra = dijkstra(noeudAVisiter.get(i));
			plusCourtChemins.add(tableauPrecedence);
			for (Integer j=0; j>i && j<noeudAVisiter.size();i++) {
				cout[i][j] = distanceDijkstra[mapNoeuds.get(noeudAVisiter.get(i))];
				cout[j][i] = distanceDijkstra[mapNoeuds.get(noeudAVisiter.get(i))];
			}
			dureeVisite[i]=mapDureeVisite.get(i);
		}
		
		
		TSP1 voyageurCommerce=new TSP1();
		voyageurCommerce.chercheSolution(20000, noeudAVisiter.size(), cout, dureeVisite);
		Integer indiceNoeud;
		for(Integer i=0;i<noeudAVisiter.size();i++) {
			indiceNoeud=voyageurCommerce.getMeilleureSolution(i);
			enchainementNoeud.add(noeudAVisiter.get(indiceNoeud));
		}
		
		
		return enchainementNoeud;
		
	}
	
	
	private int[]  dijkstra(Noeud source){
		tableauPrecedence.clear();
		PriorityQueue<Pair<Integer,Noeud>> listeAttente=new PriorityQueue<Pair<Integer,Noeud>>();		
		Integer nombreNoeuds=plan.getNoeuds().size();
		int[] distanceAuNoeudSource=new int[nombreNoeuds];
		
		for(Integer i=0;i<nombreNoeuds;i++) { 
			distanceAuNoeudSource[i]=Integer.MAX_VALUE;
		}
		Pair<Integer,Noeud> src=new Pair<Integer,Noeud>(0,source);
		distanceAuNoeudSource[mapNoeuds.get(source)]=0;
		listeAttente.add(src);
		
		while(!listeAttente.isEmpty()) {
			Noeud n=listeAttente.poll().getValue();
			for (Troncon troncon : n.tronconsDepuisLeNoeud) {
				Noeud noeud=troncon.GetNoeudDestination();
				Integer cout=(int) troncon.GetLongueur();
				if( distanceAuNoeudSource[mapNoeuds.get(noeud)] > distanceAuNoeudSource[mapNoeuds.get(n)] + cout ) {
					distanceAuNoeudSource[mapNoeuds.get(noeud)] = distanceAuNoeudSource[mapNoeuds.get(n)] + cout;
					tableauPrecedence.put(noeud,n);
					Pair<Integer,Noeud> paireCoutNoeud=new Pair<Integer,Noeud>(distanceAuNoeudSource[mapNoeuds.get(noeud)],noeud);
					listeAttente.add(paireCoutNoeud);
				}
			}
		}
		
		return distanceAuNoeudSource;
	}
	
	
	
	private void setNoeudAVisiter() {
		
		ArrayList<Noeud> ensembleNoeudAVisiter=new ArrayList<Noeud>();
		noeudAVisiter=new HashMap<Integer,Noeud>();
		mapDureeVisite=new HashMap<Integer,Integer>();
		
		mapDureeVisite.put(0,0);
		Integer indice=1;
		for (Livraison it: livraisons) {
			ensembleNoeudAVisiter.add(it.getNoeudEnlevement());
			mapDureeVisite.put(indice++,it.getDureeEnlevement());
			ensembleNoeudAVisiter.add(it.getNoeudLivraison()); 
			mapDureeVisite.put(indice++,it.getDureeLivraison());			
		}

		indice=1;

		for(Noeud it: ensembleNoeudAVisiter) { 
			noeudAVisiter.put(indice++,it); 
		} 
		noeudAVisiter.put(0,entrepot);

	}
	
	
	  
	
	private void setNoeudPlan(){ 
		mapNoeuds=new HashMap<Noeud,Integer>();
		Integer indice=0;
		List<Troncon> listeTroncons =plan.getTroncons();

		

		for (Troncon it: listeTroncons) {
			if (!mapNoeuds.containsKey(it.GetNoeudOrigine())) {
				mapNoeuds.put(it.GetNoeudOrigine(),indice++);
			} 
			if (!mapNoeuds.containsKey(it.GetNoeudDestination())) {
				mapNoeuds.put(it.GetNoeudDestination(),indice++);
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