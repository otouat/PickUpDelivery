package controlleur;

import modele.Tournee;
import vue.MainControlleur;
import modele.Livraison;

public class CommandeSuppressionLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	private int positionAjout;
	private int rangPreEnlevement;
	private int rangPreLivraison;
	private MainControlleur fenetre;

	/**
	 * Cree la commande qui ajoute a la position position la livraison livraison
	 * 
	 * @param position
	 * @param livraison
	 * @param calculateurTournee
	 */
	CommandeSuppressionLivraison( MainControlleur fenetre, Livraison livraison, Tournee tournee) {
		this.fenetre=fenetre;
		this.livraison = livraison;
		this.tournee = tournee;
		this.positionAjout = positionAjout; //
	}

	@Override
	public void doCommande() {
		// demandeLivraison.supprimerLivraison(livraison);
		// recalcul avec algo
		tournee.recalculTourneeApresSupressionLivraison(livraison);
	}

	@Override
	public void undoCommande() {
		
		tournee.recalculTourneeApresAjoutLivraison( livraison,rangPreEnlevement,rangPreLivraison);

	}

}
