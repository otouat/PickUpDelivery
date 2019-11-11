package controlleur;

import modele.DataContainer;
import modele.DemandeLivraison;
import modele.Plan;
import modele.Noeud;
import vue.MainControlleur;

public class Controleur {
	private DataContainer dataContainer ;
	private Plan plan;
	private DemandeLivraison demandeLivraison;
	private MainControlleur fenetre;
	private ListeDeCommandes listeDeCommandes;
	private Etat etatCourant;
	
	// Instances associees a chaque etat possible du controleur
	protected final EtatInit etatInit = new EtatInit();
	protected final EtatPlanCharge etatPlanCharge = new EtatPlanCharge();
	protected final EtatDemandeCharge etatDemandeCharge = new EtatDemandeCharge();
	protected final EtatTourneeCalculee etatTourneeCalculee = new EtatTourneeCalculee();
	protected final EtatTourneeModifiee etatTourneeModifiee = new EtatTourneeModifiee();
	protected final EtatFeuilleDeRouteEditee etatFeuilleDeRouteEditee = new EtatFeuilleDeRouteEditee();
	protected final EtatFeuilleDeRouteModifiee etatFeuilleDeRouteModifiee = new EtatFeuilleDeRouteModifiee();
	protected final EtatSupprimer etatSupprimer = new EtatSupprimer();

	/**
	 * Cree le controleur de l'applcation
	 * @param datacontainer le datacontainer avec le plan,demande de livraison
	 * @param echelle l'echelle de la vue graphique de p
	 */
	public Controleur(DataContainer datacontainer) {
		this.dataContainer=datacontainer;
		plan=dataContainer.GetPlan();
		demandeLivraison= dataContainer.GetDemandeLivraison();
		listeDeCommandes = new ListeDeCommandes();
		etatCourant = etatInit;
		
	}
	
	/**
	 * Change l'etat courant du controleur
	 * @param etat le nouvel etat courant
	 */
	protected void setEtatCourant(Etat etat){
		etatCourant = etat;
	}

	// Methodes correspondant aux evenements utilisateur
	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Charger un plan"
	 */
	public void chargerPlan() {
		etatCourant.chargerPlan(this,fenetre);
	}
	

	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Charger une demande de livraison"
	 */
	public void chargerDemandeLivraison() {
		etatCourant.chargerDemandeLivraison(this,fenetre);
	}
	
    
	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Supprimer des formes"
	 */
	public void supprimer() {
		etatCourant.supprimerLivraison(this, fenetre);
	}

	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Deplacer une forme"
	 */
	public void calculerTournee() {
		etatCourant.calculerTournee(this,fenetre);
	}
	
	public void genererFeuilleDeRoute() {
		etatCourant.genererFeuilleDeRoute(this, fenetre);
	}
	
	/**
	 * Methode appelee par la fenetre quand l'utilisateur clique sur le bouton "Undo"
	 */
	public void undo(){
		etatCourant.undo(this,  listeDeCommandes, fenetre);
	}

	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Redo"
	 */
	public void redo(){
		etatCourant.redo(this,listeDeCommandes,fenetre);
	}
	
	
	
	
}
	


