package controlleur;

import modele.Tournee;
import modele.Triplet;
import vue.LivraisonDisplay;
import vue.MainControlleur;
import vue.VueDemandeLivraison;
import vue.VueTroncon;

import java.util.List;

import modele.Livraison;
import modele.Noeud;

public class CommandeSuppressionLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	private LivraisonDisplay l;
	private MainControlleur fenetre;
	private List< Triplet<Noeud, Livraison, Boolean>> liste;
	

	/**
	 * Cree la commande qui ajoute a la position position la livraison livraison
	 * 
	 * @param position
	 * @param livraison
	 * @param calculateurTournee
	 */
	public CommandeSuppressionLivraison( MainControlleur fenetre, Livraison livraison, LivraisonDisplay l,Tournee tournee,List< Triplet<Noeud, Livraison, Boolean>> liste ) {
		this.fenetre=fenetre;
		this.livraison = livraison;
		this.l = l;
		this.tournee = tournee;
		this.liste=liste;
		
	}

	@Override
	public void doCommande() {
		
		VueDemandeLivraison.removeLivraisonTextuellement(l,fenetre.livraisonsVue);
		fenetre.initialiseListView();
		
		VueDemandeLivraison.removeLivraisonGraphiquement(fenetre.livraisons, l.getColor());
	
		// recalcul avec algo
		VueTroncon.drawTournee(tournee.recalculTourneeApresSupressionLivraison(livraison),fenetre.tourneePane);
	}

	@Override
	public void undoCommande() {
		
		//rajouter la couleur 
		fenetre.reInitialiseListView(liste);
		//TODO : modif graphique
	}

}
