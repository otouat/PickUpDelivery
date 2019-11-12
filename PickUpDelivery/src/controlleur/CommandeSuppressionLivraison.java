package controlleur;

import modele.Tournee;
import modele.Livraison;

public class CommandeSuppressionLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	private int positionAjout;

	/**
	 * Cree la commande qui ajoute a la position position la livraison livraison
	 * 
	 * @param position
	 * @param livraison
	 * @param calculateurTournee
	 */
	CommandeSuppressionLivraison(int positionAjout, Livraison livraison, Tournee tournee) {
		this.livraison = livraison;
		this.tournee = tournee;
		this.positionAjout = positionAjout;
	}

	@Override
	public void doCommande() {
		// demandeLivraison.supprimerLivraison(livraison);
		// recalcul avec algo
		tournee.supprimerLivraison(livraison);
	}

	@Override
	public void undoCommande() {
		tournee.ajouterLivraison(positionAjout,livraison);

	}

}
