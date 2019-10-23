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
	private Map<Integer, Livraison> livraisons;
	private HashMap<Integer,Noeud> noeudAVisiter;
	private HashMap<Noeud,Integer> mapNoeuds;
	private Plan plan;
	private HashMap<Integer,Integer> mapDureeVisite;

	public Tournee(Entrepot entrepot, Map<Integer, Livraison> livraisons,Plan plan) {
		this.entrepot = entrepot;
		this.livraisons = livraisons;
		this.plan=plan;
		setNoeudAVisiter();
	}
	
	public List<Noeud> calculTournee() {
		
		List<Noeud> enchainementNoeud=new ArrayList<Noeud>();
		int[][] cout = new int[noeudAVisiter.size()][noeudAVisiter.size()];
		int[] distanceDijkstra = new int[plan.getNoeuds().size()];
		int[] dureeVisite = new int[noeudAVisiter.size()];
		
		for (Integer i=0; i<noeudAVisiter.size();i++) {
			distanceDijkstra = dijkstra(noeudAVisiter.get(i));
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
	
	
	private int[] dijkstra(Noeud source){
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
					Pair<Integer,Noeud> paireCoutNoeud=new Pair<Integer,Noeud>(distanceAuNoeudSource[mapNoeuds.get(noeud)],noeud);
					listeAttente.add(paireCoutNoeud);
				}
			}
		}
		
		return distanceAuNoeudSource;
	}
	
	
	
	private void setNoeudAVisiter() {
		
		ArrayList<Noeud> ensembleNoeudAVisiter=new ArrayList<Noeud>();
		noeudAVisiter=new HashMap<Integer,Noeud>(); Set<Map.Entry<
		Integer,Livraison>> ensembleLivraison = livraisons.entrySet();
		mapDureeVisite=new HashMap<Integer,Integer>();
		
		mapDureeVisite.put(0,0);
		Integer indice=1;
		for (Map.Entry< Integer,Livraison> it: ensembleLivraison) {
			ensembleNoeudAVisiter.add(it.getValue().getNoeudEnlevement());
			mapDureeVisite.put(indice++,it.getValue().getDureeEnlevement());
			ensembleNoeudAVisiter.add(it.getValue().getNoeudLivraison()); 
			mapDureeVisite.put(indice++,it.getValue().getDureeLivraison());
			
			
			
		}

		indice=1;

		for(Noeud it: ensembleNoeudAVisiter) { 
			noeudAVisiter.put(indice++,it); 
		} 
		noeudAVisiter.put(0,entrepot);

	}
	
	
	  
	
	private void setCoutPlan(){ 
		mapNoeuds=new HashMap<Noeud,Integer>();
		Integer indice=0;
		Set<Map.Entry<String, Noeud>> ensembleNoeud = plan.getNoeuds().entrySet();
		Integer nombreNoeuds=ensembleNoeud.size(); 
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