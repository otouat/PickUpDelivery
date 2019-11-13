package controlleur;
import java.io.File;

import modele.DataContainer;
import modele.Livraison;
import modele.Plan;
import modele.Noeud;
import vue.MainControlleur;

public interface Etat {
		
	/*
		 * Methode appelee par le controleur apres un clic sur le bouton "Charger un plan"
		 * @param c le controleur
		 * @param f la fenetre //
		 */
	public void chargerPlan(Controleur c, MainControlleur f);
		
		/*
		 * Methode appelee par controleur apres un clic sur le bouton "Charger une demande de livraison"
		 * @param c le controleur
		 * @param f la fenetre
		 */
	public void chargerDemandeLivraison(Controleur c, MainControlleur f);
		
		
	public void calculerTournee(Controleur c, MainControlleur f);
	
	/**
	 * Methode appelee par controleur apres un clic sur le bouton "Ajouter une
	 * Livraison"
	 * 
	 * @param controleur
	 * @param fenetre
	 */
	public void ajouterLivraison(Controleur controleur,MainControlleur fenetre);

	/**
	 * Methode appelee par controleur apres un clic sur le bouton "Retirer une
	 * Livraison"
	 * 
	 * @param controleur
	 * @param fenetre
	 */
	
	public void supprimerLivraison(Controleur controleur,MainControlleur fenetre);

	/**
	 * Methode appelee par controleur apres un clic sur le bouton "Retirer une
	 * Livraison"
	 * 
	 * @param controleur
	 * @param fenetre
	 */
	public void modifierOrdreLivraison(Controleur controleur, MainControlleur fenetre);

	/**
	 * Methode appelee par controleur 
	 * @param controleur
	 * @param fenetre
	 */
	
	public void modifierNoeudLivraison(Controleur controleur, MainControlleur fenetre);

	/**
	 * Methode appelee par controleur apres un clic sur le bouton "Valider Tournee"
	 * 
	 * @param controleur
	 * @param fenetre
	 */
	
	public void modifierTournee(Controleur controleur, MainControlleur fenetre);
	
	public void validerTournee(Controleur controleur, MainControlleur fenetre);
	
	
	public void consulterTournee(Controleur controleur, MainControlleur fenetre);

	
		
	public void genererFeuilleDeRoute(Controleur c, MainControlleur f);
	
	/**
	 * Methode appelee par le controleur apres un clic sur le bouton "Undo"
	 * @param l la liste des commandes en cours
	 */
	
	
	
	public default void undo(Controleur controleur, ListeDeCommandes listeDeCommandes, MainControlleur fenetre){};
		
		/**
		 * Methode appelee par le controleur apres un clic sur le bouton "Redo"
		 * @param l la liste des commandes en cours
		 */
	public default void redo(Controleur controleur, ListeDeCommandes listeDeCommandes, MainControlleur fenetre){};
		
		/**
		 * Methode appelee par le controleur en "passant" sur un noeud
		 * @param p le plan
		 * @param f la fenetre
		 */
	
	}


