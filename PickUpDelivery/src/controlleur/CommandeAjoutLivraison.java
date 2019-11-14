package controlleur;

import modele.Tournee;
import vue.LivraisonDisplay;
import vue.MainControlleur;
import vue.VueDemandeLivraison;
import vue.VueTroncon;

import java.util.List;

import javafx.util.Pair;
import modele.Livraison;
import modele.Noeud;

public class CommandeAjoutLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	private Noeud noeudBeforePickUp;
	private Noeud noeudBeforeDelivery;
	private MainControlleur fenetre;
	private Pair<LivraisonDisplay,LivraisonDisplay> pair;

	/**
	 * Cree la commande qui ajoute a la position position la livraison livraison
	 */
	public CommandeAjoutLivraison(MainControlleur fenetre, Noeud noeudBeforePickUp, Noeud noeudBeforeDelivery, Livraison livraison, Tournee tournee) {
		this.fenetre=fenetre;
		this.livraison = livraison;
		this.tournee = tournee;
		this.noeudBeforePickUp = noeudBeforePickUp;
		this.noeudBeforeDelivery = noeudBeforeDelivery;
		
	}

	@Override
	public void doCommande() {
		
		//Ajout graphique
		pair = VueDemandeLivraison.ajouterLivraison(fenetre.livraisonsVue, livraison, fenetre.livraisons, true);
		List<Noeud> listeNoeuds = tournee.recalculTourneeApresAjoutLivraison( livraison,noeudBeforePickUp,noeudBeforeDelivery);
		
		//Ajout textuel
		VueDemandeLivraison.updateLivraisonsVueAjout(noeudBeforePickUp,pair.getKey(),fenetre.livraisonsVue);
		VueDemandeLivraison.updateLivraisonsVueAjout(noeudBeforeDelivery,pair.getValue(),fenetre.livraisonsVue);
		fenetre.reInitialiseListView(tournee.getenchainementNoeudAVisiterAvecInfos());
		
		//Draw nouvelle Tournee
		VueTroncon.drawTournee(listeNoeuds, fenetre.tourneePane);
		
	}

	@Override
	public void undoCommande() {
		
		List<Noeud> listeNoeuds = tournee.recalculTourneeApresSupressionLivraison(livraison);
		fenetre.reInitialiseListView(tournee.getenchainementNoeudAVisiterAvecInfos());
		
		VueTroncon.drawTournee(listeNoeuds, fenetre.tourneePane);
		//remove textuel
		VueDemandeLivraison.removeLivraisonTextuellement(pair.getKey(),fenetre.livraisonsVue);
		fenetre.initialiseListView();
		//remove graphiquement
		VueDemandeLivraison.removeLivraisonGraphiquement(fenetre.livraisons, pair.getKey().getColor());
	}

}
