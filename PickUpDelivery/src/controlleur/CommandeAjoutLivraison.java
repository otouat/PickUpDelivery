package controlleur;

import modele.Tournee;
import vue.MainControlleur;
import vue.VueTroncon;

import java.util.List;

import modele.Livraison;
import modele.Noeud;

public class CommandeAjoutLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	private Noeud noeudBeforePickUp;
	private Noeud noeudBeforeDelivery;
	private MainControlleur fenetre;

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
		List<Noeud> listeNoeuds = tournee.recalculTourneeApresAjoutLivraison( livraison,noeudBeforePickUp,noeudBeforeDelivery);
		fenetre.reInitialiseListView(tournee.getenchainementNoeudAVisiterAvecInfos());
		VueTroncon.drawTournee(listeNoeuds, fenetre.tourneePane);
		
		
	}

	@Override
	public void undoCommande() {
		tournee.recalculTourneeApresSupressionLivraison(livraison);
		//TODO : modif graphique
	}

}
