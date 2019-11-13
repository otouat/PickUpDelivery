package controlleur;

import modele.Tournee;
import modele.Livraison;

public class CommandeSuppressionLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	/**
	 * Cree la commande qui ajoute a la position position la livraison livraison
	 * 
	 * @param position
	 * @param livraison
	 * @param calculateurTournee
	 */
	CommandeSuppressionLivraison(Livraison livraison, Tournee tournee) {
		this.livraison = livraison;
		this.tournee = tournee;
	}

	@Override
	public void doCommande() {
		// demandeLivraison.supprimerLivraison(livraison);
		// recalcul avec algo
		tournee.recalculTourneeApresSupressionLivraison(livraison);
		//TODO : modif graphique
	}

	@Override
	public void undoCommande() {
		
		//tournee.recalculTourneeApresAjoutLivraison( livraison,rangPreEnlevement,rangPreLivraison);
		//TODO : modif graphique
	}

}
