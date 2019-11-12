package controlleur;

import modele.Tournee;
import modele.Livraison;

public class CommandeAjoutLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	private int positionAjout;
	private int rangPreEnlevement;
	private int rangPreLivraison;

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
		tournee.recalculTourneeApresAjoutLivraison( livraison,rangPreEnlevement,rangPreLivraison);
	}

	@Override
	public void undoCommande() {
		tournee.recalculTourneeApresSupressionLivraison(livraison);

	}

}
