package controlleur;

import modele.Tournee;
import modele.Livraison;

public class CommandeAjoutLivraison implements Commande {

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
	CommandeAjoutLivraison(int positionAjout, Livraison livraison, Tournee tournee) {
		this.livraison = livraison;
		this.tournee = tournee;
		this.positionAjout = positionAjout;
	}

	@Override
	public void doCommande() {
		// demandeLivraison.ajouterLivraison(livraison);
		// recalcul avec algo
		//tournee.ajouterLivraison(position, livraison);
	}

	@Override
	public void undoCommande() {
		//tournee.supprimerLivraison(livraison);

	}

}
