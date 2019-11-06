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
	private ListeDeCdes listeDeCdes;
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
		listeDeCdes = new ListeDeCdes();
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
		etatCourant.supprimer(this, fenetre);
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
	 * Methode appelee par fenetre apres un clic sur le bouton "Diminuer echelle"
	 */
	public void diminuerEchelle(){
		etatCourant.diminuerEchelle( fenetre);
	}

	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Augmenter echelle"
	 */
	public void augmenterEchelle(){
		etatCourant.augmenterEchelle(fenetre);
	}
	
	/**
	 * Methode appelee par la fenetre quand l'utilisateur clique sur le bouton "Undo"
	 */
	public void undo(){
		etatCourant.undo(listeDeCdes);
	}

	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Redo"
	 */
	public void redo(){
		etatCourant.redo(listeDeCdes);
	}
	
	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Sauver le plan"
	 */
	public void sauver() {
		etatCourant.sauver(plan, fenetre);
	}

	/**
	 * Methode appelee par fenetre apres un clic sur le bouton "Ouvrir un plan"
	 */
	public void ouvrir() {
		
	}

	/**
	 * Methode appelee par fenetre apres un clic gauche sur un point de la vue graphique
	 * Precondition : p != null
	 * @param p = coordonnees du plan correspondant au point clique
	 */
	public void clicGauche(Noeud n) {
		etatCourant.clicGauche(this, fenetre,plan,listeDeCdes,n);
	}
	/**
	 * Methode appelee par fenetre apres un clic droit
	 */
	public void clicDroit(){
		etatCourant.clicDroit(this, fenetre, listeDeCdes);
	}

	/**
	 * Methode appelee par fenetre apres un deplacement de la souris sur la vue graphique du plan
	 * Precondition : p != null
	 * @param p = coordonnees du plan correspondant a la position de la souris
	 */
	public void sourisBougee(Noeud n) {
		etatCourant.sourisBougee(plan, n);
	}

	/**
	 * Methode appelee par fenetre apres la saisie d'un caractere au clavier
	 * @param codeCar le code ASCII du caractere saisi
	 */
	public void caractereSaisi(int codeCar) {
		etatCourant.carSaisi(plan, listeDeCdes, codeCar);
	}
}
	


