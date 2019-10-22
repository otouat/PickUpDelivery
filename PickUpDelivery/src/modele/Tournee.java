package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Tournee {
	private Entrepot entrepot;
	private Map<Integer, Livraison> livraisons;
	private HashMap<Noeud,Integer> noeudAVisiter;

	public Tournee(Entrepot entrepot, Map<Integer, Livraison> livraisons,Plan plan) {
		this.entrepot = entrepot;
		this.livraisons = livraisons;
	}
	
	/*
	 * public void calculTournee() {
	 * 
	 * }
	 * 
	 * private void Dijkstra(Plan plan, Noeud entrepot) {
	 * 
	 * 
	 * }
	 */
	
	
	
	/*
	 * private void setNoeudAVisiter() {
	 * 
	 * ArrayList<Noeud> ensembleNoeudAVisiter=new ArrayList<Noeud>();
	 * mapNoeudAVisiter=new HashMap<Noeud,Integer>(); Set<Map.Entry<
	 * Integer,Livraison>> ensembleLivraison = livraisons.entrySet();
	 * 
	 * for (Map.Entry< Integer,Livraison> it: ensembleLivraison) {
	 * ensembleNoeudAVisiter.add(it.getValue().getNoeudEnlevement());
	 * ensembleNoeudAVisiter.add(it.getValue().getNoeudLivraison()); }
	 * 
	 * nombreNoeudAVisiter=ensembleNoeudAVisiter.size(); Integer indice=0;
	 * 
	 * for(Noeud it: ensembleNoeudAVisiter) { mapNoeudAVisiter.put(it,indice); } }
	 * 
	 * static public int[][] setCoutPlan(Plan plan){ int[][] coutPlan; mapNoeuds=new
	 * HashMap<Noeud,Integer>(); Integer indice=0; Set<Map.Entry<String, Noeud>>
	 * ensembleNoeud = plan.getNoeuds().entrySet(); Integer
	 * nombreNoeuds=ensembleNoeud.size(); List<Troncon> listeTroncons =
	 * plan.getTroncons(); coutPlan=new int[nombreNoeuds][nombreNoeuds];
	 * 
	 * for(Integer i=0;i<nombreNoeuds;i++) { for(Integer j=0;j<nombreNoeuds;j++) {
	 * coutPlan[i][j]=Integer.MAX_VALUE; } coutPlan[i][i]=0; }
	 * 
	 * for (Troncon it: listeTroncons) {
	 * 
	 * if (!mapNoeuds.containsKey(it.GetNoeudOrigine())) {
	 * mapNoeuds.put(it.GetNoeudOrigine(),indice++); } if
	 * (!mapNoeuds.containsKey(it.GetNoeudDestination())) {
	 * mapNoeuds.put(it.GetNoeudDestination(),indice++); }
	 * coutPlan[mapNoeuds.get(it.GetNoeudOrigine())][mapNoeuds.get(it.
	 * GetNoeudDestination())]=(int)it.GetLongueur(); }
	 * 
	 * return coutPlan; }
	 */
	
	
	/*
	 * public FeuilleDeRoute GenererFeuilleDeRoute() {
	 * 
	 * 
	 * }
	 */
}