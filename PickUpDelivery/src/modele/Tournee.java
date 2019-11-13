package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Tournee {
	private Noeud entrepot;
	private List<Livraison> livraisons;
	private HashMap<Integer, Triplet<Noeud, Livraison, Boolean>> noeudAVisiter;
	// Associe les indices des noeuds a visiter avec les noeuds a visiter
	private HashMap<Noeud, Integer> mapNoeuds;
	// Associe les noeuds du plan a un indice unique
	private Plan plan;
	private HashMap<Integer, Integer> mapDureeVisite;
	// Associe l'indice du noeud a visiter avec la duree de la visite du noeud
	// (selon l'action enlevement ou livraison)
	private List<HashMap<Noeud, Noeud>> plusCourtChemins;
	// Une liste stockant le tableaux de precedence range selon le noeud a visiter
	// qui represente ici le noeud source
	private HashMap<Integer, Integer> precedence;
	// Associe l'indice d'un noeud 1 a visiter avec celui d'un noeud 2 selon la
	// relation "2 avant 1"
	private List<Integer> enchainementNoeudAVisiter;
	// une liste stockant les indices des noeuds a visiter ranges dans l'ordre de
	// visite
	List<Noeud> enchainementNoeud;
	// une liste stockant tous les noeuds du plan sur lesquels on passe pour
	// effectuer la tournee
	List<Triplet<Noeud, Livraison, Boolean>> enchainementNoeudAVisiterAvecInfos;
	// Une liste stockant les noeuds a visiter dans l'ordre de visite avec la
	// livraison et un booleen a valeur 'true' si le noeud correspond a un
	// enlevement

	public Tournee(Noeud entrepot, List<Livraison> livraisons, Plan plan) {
		String idNoeudEntrepot = entrepot.GetIdNoeud();
		this.entrepot = plan.getNoeuds().get(idNoeudEntrepot);
		this.livraisons = livraisons;
		this.plan = plan;
		this.mapNoeuds = new HashMap<Noeud, Integer>();
		this.noeudAVisiter = new HashMap<Integer, Triplet<Noeud, Livraison, Boolean>>();
		this.plusCourtChemins = new ArrayList<HashMap<Noeud, Noeud>>();
		this.enchainementNoeud = new ArrayList<Noeud>();
		this.enchainementNoeudAVisiterAvecInfos = new ArrayList<Triplet<Noeud, Livraison, Boolean>>();
		this.enchainementNoeudAVisiter = new ArrayList<Integer>();
		this.mapDureeVisite = new HashMap<Integer, Integer>();
		setNoeudAVisiter();
		setNoeudPlan();
		calculPrecedence();
	}

	public HashMap<Integer, Triplet<Noeud, Livraison, Boolean>> getNoeudAVisiter() {
		return noeudAVisiter;
	}

	public void setNoeudAVisiter(HashMap<Integer, Triplet<Noeud, Livraison, Boolean>> noeudAVisiter) {
		this.noeudAVisiter = noeudAVisiter;
	}

	public HashMap<Noeud, Integer> getMapNoeuds() {
		return mapNoeuds;
	}

	public void setMapNoeuds(HashMap<Noeud, Integer> mapNoeuds) {
		this.mapNoeuds = mapNoeuds;
	}

	public List<Integer> getEnchainementNoeudAVisiter(){
		return this.enchainementNoeudAVisiter;
	}
	public List<Triplet<Noeud, Livraison, Boolean>> getenchainementNoeudAVisiterAvecInfos(){
		return enchainementNoeudAVisiterAvecInfos;
	}

	/**
	 * Methode calculant la tourn�e optimis�e � partir des attributs indiqu�s plus
	 * t�t dans le constructeur
	 * 
	 * @return une liste stockant tous les noeuds du plan sur lesquels on passe pour
	 *         effectuer la tournee
	 */
	public List<Noeud> calculTournee() {

		Integer indiceNoeud; 
		Integer indiceNoeudSuivant;
		HashMap<Noeud, Noeud> courtChemin; 
		Noeud noeudActuel;
		Noeud noeudSuivant;
		int[][] cout = new int[noeudAVisiter.size()][noeudAVisiter.size()]; //Tableau de couts de tous les chemins du plan
		int[] distanceDijkstra = new int[plan.getNoeuds().size()]; //Initialise le tableau de cout a partir d'un noeud
		int[] dureeVisite = new int[noeudAVisiter.size()]; //Initialise le tableau de cout a partir d'un noeud
		TSP2 voyageurCommerce = new TSP2();//Initialise la classe de calcul du tsp
		ArrayList<Noeud> chemin = new ArrayList<Noeud>(); //liste de noeud qui represente le chemin qui reliera deux noeuds a visiter
		this.plusCourtChemins.clear();
		this.enchainementNoeudAVisiter.clear();
		this.enchainementNoeudAVisiterAvecInfos.clear();
		this.enchainementNoeud.clear();

		//On calcule ici le tableau des couts
		for (Integer i = 0; i < noeudAVisiter.size(); i++) {
			//Recupere le tableau de cout en executant dijkstra sur le plan depuis le noeud a visiter indicie par i ( dans noeudAVisiter)
			distanceDijkstra = dijkstra(noeudAVisiter.get(i).getFirst());
			
			for (Integer j = 0; j < noeudAVisiter.size(); j++) {
				//Recupere les couts de trajet entre les noeuds a visiter reperees par leur indice dans mapNoeuds
				cout[i][j] = distanceDijkstra[mapNoeuds.get(noeudAVisiter.get(j).getFirst())];
			}
			
			//Recupere la duree de visite du noeud a visiter indicie par i ( dans noeudAVisiter)
			dureeVisite[i] = mapDureeVisite.get(i);
		}
		
		//Execute la m�thode de calcul de la tournee
		voyageurCommerce.chercheSolution(300000, noeudAVisiter.size(), cout, dureeVisite, precedence);

		for (Integer i = 0; i < noeudAVisiter.size(); i++) {
			System.out.println("-------- "+noeudAVisiter.get(voyageurCommerce.getMeilleureSolution(i)).getFirst());
		}

		//Etablit l'enchainement de noeuds final a renvoyer de l'entrepot vers le dernier noeud a visiter
		for (Integer i = 0; i < noeudAVisiter.size() - 1; i++) {
			//Recupere les indices des noeuds a visiter depuis le resultat du tsp ( les indices dans noeudAVisiter)
			indiceNoeud = voyageurCommerce.getMeilleureSolution(i);
			indiceNoeudSuivant = voyageurCommerce.getMeilleureSolution(i + 1);
			
			//Recupere les noeuds a visiter ( actuel et suivant) 
			noeudActuel = (Noeud) noeudAVisiter.get(indiceNoeud).getFirst();
			noeudSuivant = (Noeud) noeudAVisiter.get(indiceNoeudSuivant).getFirst();
			
			//Recupere le tableau de precedence depuis le noeud Actuel
			courtChemin = plusCourtChemins.get(indiceNoeud);
			
			//Ajoute le noeud actuel aux listes enchainementNoeudAVisiter et enchainementNoeudAVisiterAvecInfos
			this.enchainementNoeudAVisiter.add(indiceNoeud);
			this.enchainementNoeudAVisiterAvecInfos.add(noeudAVisiter.get(indiceNoeud));
			
			//Recupere le chemin entre noeudActuel et noeud Suivant
			chemin = parcoursChemin(courtChemin, noeudSuivant, noeudActuel);
			
			//Ajoute le chemin dans enchainementNoeud
			this.enchainementNoeud.addAll(chemin);
		}
		this.enchainementNoeudAVisiter.add(voyageurCommerce.getMeilleureSolution(noeudAVisiter.size() - 1));
		this.enchainementNoeudAVisiterAvecInfos
				.add(noeudAVisiter.get(voyageurCommerce.getMeilleureSolution(noeudAVisiter.size() - 1)));
		chemin = parcoursChemin(plusCourtChemins.get(voyageurCommerce.getMeilleureSolution(noeudAVisiter.size() - 1)),
				(Noeud) this.entrepot,
				noeudAVisiter.get(voyageurCommerce.getMeilleureSolution(noeudAVisiter.size() - 1)).getFirst());
		this.enchainementNoeud.addAll(chemin);
		
		return this.enchainementNoeud;

	}

	/**
	 * Methode recalculant la tourn�e � partir du changement d'un noeud parmi ceux �
	 * visiter
	 * 
	 * @param noeudChange:      le nouveau noeud remplacant de l'ancien
	 * @param rangNoeudAChanger : rang du noeud � changer
	 * @return un iterateur permettant d'iterer sur tous les sommets de nonVus
	 */
	public List<Noeud> recalculTourneeApresChangementNoeud(Noeud noeudChange, Integer rangNoeudAChanger) {

		Integer indiceNoeud;
		Integer indiceNoeudSuivant;
		HashMap<Noeud, Noeud> courtChemin;
		Noeud noeudActuel;
		Noeud noeudSuivant;
		List<Noeud> enchainementNoeudBis = new ArrayList<Noeud>();
		List<Integer> enchainementNoeudAVisiterBis = new ArrayList<Integer>();
		ArrayList<Noeud> chemin = new ArrayList<Noeud>();
		Integer indiceNoeudAChanger = enchainementNoeudAVisiter.get(rangNoeudAChanger);

		Triplet<Noeud, Livraison, Boolean> tripletAChanger = this.noeudAVisiter.get(indiceNoeudAChanger);
		tripletAChanger.setFirst(noeudChange);
		noeudAVisiter.put(indiceNoeudAChanger, tripletAChanger);
		dijkstra(noeudChange);
		courtChemin = plusCourtChemins.get(plusCourtChemins.size() - 1);
		plusCourtChemins.set(indiceNoeudAChanger,courtChemin);
		plusCourtChemins.remove(plusCourtChemins.size() - 1);
		
		for (Integer i = 0; i < noeudAVisiter.size() - 1; i++) {

			indiceNoeud = this.enchainementNoeudAVisiter.get(i);
			indiceNoeudSuivant = this.enchainementNoeudAVisiter.get(i + 1);
			noeudActuel = (Noeud) noeudAVisiter.get(indiceNoeud).getFirst();
			enchainementNoeudAVisiterBis.add(indiceNoeud);
			noeudSuivant = (Noeud) noeudAVisiter.get(indiceNoeudSuivant).getFirst();

			courtChemin = plusCourtChemins.get(indiceNoeud);

			chemin = parcoursChemin(courtChemin, noeudSuivant, noeudActuel);

			enchainementNoeudBis.addAll(chemin);

		}


		courtChemin = plusCourtChemins.get(noeudAVisiter.size() - 1);

		enchainementNoeudAVisiterBis.add(this.enchainementNoeudAVisiter.get(noeudAVisiter.size() - 1));
		chemin = parcoursChemin(courtChemin, (Noeud) this.entrepot,
				noeudAVisiter.get(noeudAVisiter.size() - 1).getFirst());

		enchainementNoeudBis.addAll(chemin);

		this.enchainementNoeud.clear();
		this.enchainementNoeudAVisiter.clear();
		this.enchainementNoeud.addAll(enchainementNoeudBis);
		this.enchainementNoeudAVisiter.addAll(enchainementNoeudAVisiterBis);

		return enchainementNoeudBis;

	}
	
	/**
	 * Methode qui recalcule la tourn�e apr�s suppression d'une livraison de la tourn�e
	 * 
	 * @param livraisonASupprimer: l'objet de classe livraison a supprimer
	 * @return un iterateur permettant d'iterer sur tous les sommets de nonVus
	 */
	public List<Noeud> recalculTourneeApresSupressionLivraison(Livraison livraisonASupprimer) {
		
		this.livraisons.remove(livraisonASupprimer);
		setNoeudAVisiter();
		calculPrecedence();		
		calculTournee();
		return this.enchainementNoeud;	
	}

	public List<Noeud> recalculTourneeApresAjoutLivraison(Livraison livraisonAAjouter,Noeud noeudPreEnlevement,Noeud noeudPreLivraison) {
		
		this.livraisons.add(livraisonAAjouter);		
		setNoeudAVisiter();
		
		Integer rangPreEnlevement=0;
		Integer rangPreLivraison=0;
		
		for(int i=0;i<enchainementNoeudAVisiter.size();i++ ) {
			if(noeudAVisiter.get(enchainementNoeudAVisiter.get(i)).getFirst().equal(noeudPreEnlevement)) {
				rangPreEnlevement=i;
			}
			if(noeudAVisiter.get(enchainementNoeudAVisiter.get(i)).getFirst().equal(noeudPreLivraison)) {
				rangPreLivraison=i;
			}
		}
		enchainementNoeudAVisiter.add(rangPreEnlevement,this.noeudAVisiter.size()-2);
		enchainementNoeudAVisiter.add(rangPreLivraison,this.noeudAVisiter.size()-1);
		
		if(rangPreEnlevement==0 || rangPreLivraison==0) {
			return null;
		}
		
		plusCourtChemins.clear();
		
		// On calcule ici le tableau des couts
		for (Integer i = 0; i < noeudAVisiter.size(); i++) {
			// Recupere le tableau de cout en executant dijkstra sur le plan depuis le noeud
			// a visiter indicie par i ( dans noeudAVisiter)
			dijkstra(noeudAVisiter.get(i).getFirst());
		}
		recalculTournee();
		return this.enchainementNoeud;
		
	}


	public List<Noeud> FausseTourneePetitIHM() {
		List<Noeud> enchainementNoeud = new ArrayList<Noeud>();
		Noeud n = new Noeud("342873658", 45.76038, 4.8775625);
		enchainementNoeud.add(n);
		Noeud n1 = new Noeud("342872879", 45.760693, 4.8777256);
		enchainementNoeud.add(n1);
		Noeud n2 = new Noeud("2456932713", 45.760902, 4.877833);
		enchainementNoeud.add(n2);
		enchainementNoeud.add(n2);
		Noeud n3 = new Noeud("342867241", 45.76051, 4.879188);
		enchainementNoeud.add(n3);
		Noeud n4 = new Noeud("342869317", 45.759945, 4.87886);
		enchainementNoeud.add(n4);

		return enchainementNoeud;

	}

	/**
	 * Methode qui donne le plus court chemin entre noeudActuel et noeudSuivant
	 * 
	 * @param courtChemin: tableau de pr�c�dence ayant comme noeud source le
	 *                     noeudActuel
	 * @param noeudSuivant : noeud qui suit le noeud Actuel dans l'ordre donn�e par
	 *                     le tsp
	 * @param noeudActuel  : noeud Actuel dans l'ordre donn�e par le tsp
	 * @return une liste qui stocke les noeuds d�crivant le chemin de noeudActuel
	 *         vers noeudSuivant
	 */
	private ArrayList<Noeud> parcoursChemin(HashMap<Noeud, Noeud> courtChemin, Noeud noeudSuivant, Noeud noeudActuel) {
		ArrayList<Noeud> chemin = new ArrayList<Noeud>();
		Noeud current = noeudSuivant;
		chemin.add(noeudSuivant);
		System.out.println("CC "+courtChemin);
		System.out.println("current "+current);
		System.out.println("Na "+noeudActuel);
		System.out.println("court . get "+courtChemin.get(current));
		if (courtChemin.get(current).equal(noeudActuel)) {
			chemin.add(noeudActuel);
		} else {

			while (!courtChemin.get(current).equal(noeudActuel)) {
				chemin.add(courtChemin.get(current));
				current = courtChemin.get(current);

			}
			chemin.add(noeudActuel);
		}
		Collections.reverse(chemin);
		return chemin;

	}

	/**
	 * Methode qui donne le plus court chemin entre noeudActuel et noeudSuivant
	 * 
	 * @param source: un noeud qu'on d�finit comme point de d�part
	 * @return un tableau d'entiers correspondant aux couts des chemins entre les
	 *         noeuds du plan et le noeud source ( ici exprim�s en secondes)
	 */
	private int[] dijkstra(Noeud source) {
		HashMap<Noeud, Noeud> tableauPrecedence = new HashMap<Noeud, Noeud>();
		PriorityQueue<Pair<Integer, Noeud>> listeAttente = new PriorityQueue<Pair<Integer, Noeud>>();
		Integer nombreNoeuds = plan.getNoeuds().size();
		int[] distanceAuNoeudSource = new int[nombreNoeuds];

		for (Integer i = 0; i < nombreNoeuds; i++) {
			distanceAuNoeudSource[i] = Integer.MAX_VALUE;
		}
		Pair<Integer, Noeud> src = new Pair<Integer, Noeud>(0, source);
		distanceAuNoeudSource[mapNoeuds.get(source)] = 0;

		Integer index = mapNoeuds.get(source);
		distanceAuNoeudSource[index] = 0;

		listeAttente.add(src);
		tableauPrecedence.put(source, null);
		while (!listeAttente.isEmpty()) {
			Noeud n = listeAttente.poll().getNoeud();
			for (Troncon troncon : n.tronconsDepuisLeNoeud) {
				Noeud noeud = troncon.GetNoeudDestination();
				Integer cout = (int) (troncon.GetLongueur() / 4.166);
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

	/**
	 * Methode qui initialise la map noeudAVisiter ainsi que la mapDureeVisite
	 */
	private void setNoeudAVisiter() {

		noeudAVisiter.clear();
		mapDureeVisite.clear();

		mapDureeVisite.put(0, 0);
		Integer indice = 1;
		Integer indiceBis = 1;
		for (Livraison it : livraisons) {
			Triplet<Noeud, Livraison, Boolean> noeudEnlevement = new Triplet<Noeud, Livraison, Boolean>(
					it.getNoeudEnlevement(), it, true);
			Triplet<Noeud, Livraison, Boolean> noeudLivraison = new Triplet<Noeud, Livraison, Boolean>(
					it.getNoeudLivraison(), it, false);
			noeudAVisiter.put(indiceBis++, noeudEnlevement);
			noeudAVisiter.put(indiceBis++, noeudLivraison);
			mapDureeVisite.put(indice++, it.getDureeEnlevement());
			mapDureeVisite.put(indice++, it.getDureeLivraison());
		}

		Triplet<Noeud, Livraison, Boolean> tripletEntrepot = new Triplet<Noeud, Livraison, Boolean>(this.entrepot, null,
				null);
		noeudAVisiter.put(0, tripletEntrepot);

	}

	/**
	 * Methode qui initialise la map mapNoeuds
	 */
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

	/**
	 * Methode qui initialise la map precedence
	 */
	private void calculPrecedence() {
		precedence = new HashMap<Integer, Integer>();
		for (int i = 2; i < noeudAVisiter.size(); i = i + 2) {
			precedence.put(i, i - 1);
		}
	}

	/**
	 * Methode qui recalcule la tourn�e ( au niveau de l'enchainement
	 * des noeuds du plan ) a partir des attributs deja d�finies
	 */
	private void recalculTournee() {

		Integer indiceNoeud;
		Integer indiceNoeudSuivant;
		HashMap<Noeud, Noeud> courtChemin;
		Noeud noeudActuel;
		Noeud noeudSuivant;
		List<Noeud> enchainementNoeudBis = new ArrayList<Noeud>();
		List<Integer> enchainementNoeudAVisiterBis = new ArrayList<Integer>();
		ArrayList<Noeud> chemin = new ArrayList<Noeud>();

		for (Integer i = 0; i < noeudAVisiter.size() - 1; i++) {

			indiceNoeud = this.enchainementNoeudAVisiter.get(i);
			indiceNoeudSuivant = this.enchainementNoeudAVisiter.get(i + 1);
			noeudActuel = (Noeud) noeudAVisiter.get(indiceNoeud).getFirst();
			enchainementNoeudAVisiterBis.add(indiceNoeud);
			noeudSuivant = (Noeud) noeudAVisiter.get(indiceNoeudSuivant).getFirst();
			courtChemin = plusCourtChemins.get(indiceNoeud);
			chemin = parcoursChemin(courtChemin, noeudSuivant, noeudActuel);
			enchainementNoeudBis.addAll(chemin);

		}

		courtChemin = plusCourtChemins.get(noeudAVisiter.size() - 1);
		
		enchainementNoeudAVisiterBis.add(this.enchainementNoeudAVisiter.get(noeudAVisiter.size() - 1));
		
		chemin = parcoursChemin(courtChemin, (Noeud) this.entrepot,
				noeudAVisiter.get(noeudAVisiter.size() - 1).getFirst());

		enchainementNoeudBis.addAll(chemin);

		this.enchainementNoeud.clear();
		this.enchainementNoeud.addAll(enchainementNoeudBis);
	}

}