package controlleur;

import modele.DataContainer;//
import modele.DemandeLivraison;
import modele.Plan;
import modele.Tournee;
import modele.Noeud;
import vue.MainControlleur;

public class Controleur {
	private DataContainer dataContainer;
	private Plan plan;
	private DemandeLivraison demandeLivraison;
	private MainControlleur fenetre;
	private ListeDeCommandes listeDeCommandes;
	private Tournee tournee;
	private Etat etatCourant;
	//
	// Instances associees a chaque etat possible du controleur
	public final EtatInit etatInit = new EtatInit();
	public final EtatPlanCharge etatPlanCharge = new EtatPlanCharge();
	public final EtatDemandeCharge etatDemandeCharge = new EtatDemandeCharge();
	public final EtatTourneeModifiee etatTourneeModifiee = new EtatTourneeModifiee();
	public final EtatAjouterLivraison etatAjouter = new EtatAjouterLivraison();
	public final EtatSupprimerLivraison etatSupprimer = new EtatSupprimerLivraison();
	public final EtatModifierNoeudLivraison etatModifieNoeudLivraison = new EtatModifierNoeudLivraison();
	public final EtatFeuilleDeRouteEditee etatFeuilleDeRouteEditee = new EtatFeuilleDeRouteEditee();
	

	/**
	 * Cree le controleur de l'applcation
	 * @param datacontainer le datacontainer avec le plan,demande de livraison
	 * @param echelle l'echelle de la vue graphique de p
	 */
	public Controleur(DataContainer dataContainer,MainControlleur fenetre) {
		this.dataContainer=dataContainer;
		this.fenetre = fenetre;
		//plan=dataContainer.GetPlan();
		//demandeLivraison= dataContainer.GetDemandeLivraison();
		//listeDeCommandes = new ListeDeCommandes();
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
	
	public void modifierTournee() {
		etatCourant.modifierTournee(this,fenetre);
	};
	
	public void validerTournee() {
		etatCourant.validerTournee(this,fenetre);
	};
	
	public void consulterTournee() {
		etatCourant.consulterTournee(this,fenetre);
	};

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

	
	//GETTER AND SETTER
	
	public DataContainer getDataContainer() {
		return dataContainer;
	}

	public void setDataContainer(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public MainControlleur getFenetre() {
		return fenetre;
	}

	public void setFenetre(MainControlleur fenetre) {
		this.fenetre = fenetre;
	}

	public DemandeLivraison getDemandeLivraison() {
		return demandeLivraison;
	}

	public void setDemandeLivraison(DemandeLivraison demandeLivraison) {
		this.demandeLivraison = demandeLivraison;
	}

	public Tournee getTournee() {
		return tournee;
	}

	public void setTournee(Tournee tournee) {
		this.tournee = tournee;
	}
	
	
	
	
	
	
}
	


