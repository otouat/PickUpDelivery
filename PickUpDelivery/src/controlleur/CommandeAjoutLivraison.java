/**
 * La classe CommandeAjoutLivraison permet d'ajouter une livraison dans
 * la liste de demande à la position indiquée
 */
package controlleur;

import modele.Livraison;
import modele.Noeud;
import modele.Tournee;

public class CommandeAjoutLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	private Noeud noeudBeforePickUp;
	private Noeud noeudBeforeDelivery;

	/**
	 * Cree la commande qui ajoute a la position indiquée une livraison
	 */
	public CommandeAjoutLivraison(Noeud noeudBeforePickUp, Noeud noeudBeforeDelivery, Livraison livraison,
			Tournee tournee) {
		this.livraison = livraison;
		this.tournee = tournee;
		this.noeudBeforePickUp = noeudBeforePickUp;
		this.noeudBeforeDelivery = noeudBeforeDelivery;

	}

	@Override
	public void doCommande() {//
		// tournee.recalculTourneeApresAjoutLivraison(
		// livraison,rangPreEnlevement,rangPreLivraison);
		// TODO : modif graphique
	}

	@Override
	public void undoCommande() {
		tournee.recalculTourneeApresSupressionLivraison(livraison);
		// TODO : modif graphique
	}

}
