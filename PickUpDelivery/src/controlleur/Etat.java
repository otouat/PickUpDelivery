package controlleur;
import java.io.File;

import modele.DataContainer;
import modele.Plan;
import modele.Noeud;
import vue.MainControlleur;

public interface Etat {
		
	/*
		 * Methode appelee par le controleur apres un clic sur le bouton "Charger un plan"
		 * @param c le controleur
		 * @param f la fenetre
		 */
	public default void chargerPlan(Controleur c, MainControlleur f){};
		
		/*
		 * Methode appelee par controleur apres un clic sur le bouton "Charger une demande de livraison"
		 * @param c le controleur
		 * @param f la fenetre
		 */
	public default void chargerDemandeLivraison(Controleur c, MainControlleur f){};
		
		/*
		 * Methode appelee par le controleur apres un clic sur le bouton "Supprimer"
		 * @param c le controleur
		 * @param f la fenetre
		 */
	public default void supprimer(Controleur c, MainControlleur f){};
		
		/**
		 * Methode appelee par le controleur apres un clic sur le bouton "Deplacer une forme"
		 * @param c le controleur
		 * @param f la fenetre
		 */
	public default void calculerTournee(Controleur c, MainControlleur f){};
		
		/**
		 * Methode appelee par le controleur apres un clic sur le bouton "Diminuer echelle"
		 * @param f la fenetre
		 */
	
	public default void genererFeuilleDeRoute(Controleur c, MainControlleur f){};
	
	/**
	 * Methode appelee par le controleur apres un clic sur le bouton "Diminuer echelle"
	 * @param f la fenetre
	 */
	public default void diminuerEchelle(MainControlleur f){};
		
		/**
		 * Methode appelee par le controleur quand l'utilisateur clique sur le bouton "Augmenter echelle"
		 * @param f la fenetre
		 */
	public default void augmenterEchelle(MainControlleur f){};
		
		/**
		 * Methode appelee par le controleur apres un clic sur le bouton "Undo"
		 * @param l la liste des commandes en cours
		 */
	public default void undo(ListeDeCdes l){};
		
		/**
		 * Methode appelee par le controleur apres un clic sur le bouton "Redo"
		 * @param l la liste des commandes en cours
		 */
	public default void redo(ListeDeCdes l){};
		
		/**
		 * Methode appelee par le controleur apres un clic sur le bouton "Sauver le plan"
		 * @param p le plan
		 * @param f la fenetre
		 */
	public default void sauver(Plan p, MainControlleur f){};
		
		/**
		 * Methode appelee par controleur apres un clic sur le bouton "Ouvrir un plan"
		 * @param p le plan
		 * @param l la liste des commandes en cours
		 * @param f la fenetre
		 */
	//public default File ouvrir(){};

		/**
		 * Methode appelee par le controleur apres un deplacement de la souris sur la vue graphique du plan
		 * Precondition : p != null
		 * @param plan le plan
		 * @param p le point correspondant a la position de la souris
		 */
	public default void sourisBougee(Plan plan, Noeud n){};
		
		/**
		 * Methode appelee par le controleur apres la saisie d'un caractere au clavier
		 * @param p le plan
		 * @param l la liste de commandes en cours
		 * @param codeCar le code ASCII du caractere saisi
		 */
	public default void carSaisi(Plan p, ListeDeCdes l, int codeCar){};
		
		/**
		 * Methode appelee par le controleur apres un clic droit
		 * @param c le controleur
		 * @param f la fenetre
		 * @param l la liste de commandes en cours
		 */
	public default void clicDroit(Controleur c, MainControlleur f, ListeDeCdes l){
		//f.autoriseBoutons(true);
		c.setEtatCourant(c.etatInit);
		//f.afficheMessage("");
		 }
		
		/**
		 * Methode appelee par controleur apres un clic gauche sur un point de la vue graphique
		 * Precondition : p != null
		 * @param c le controleur
		 * @param f la fenetre
		 * @param plan le plan
		 * @param l la liste de commandes en cours
		 * @param p les coordonnees du noeud clique
		 */
		public default void clicGauche(Controleur c, MainControlleur f, Plan plan, ListeDeCdes l, Noeud n){};
	}


