package controlleur;

import modele.Tournee;
import vue.MainControlleur;
import modele.Livraison;

public class CommandeAjoutLivraison implements Commande {

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
	CommandeAjoutLivraison(MainControlleur fenetre, int positionAjout, Livraison livraison, Tournee tournee) {
		this.fenetre=fenetre;
		this.livraison = livraison;
		this.tournee = tournee;
		this.positionAjout = positionAjout;
	}

	@Override
	public void doCommande() {
		//tournee.recalculTourneeApresAjoutLivraison( livraison,rangPreEnlevement,rangPreLivraison);
	}

	@Override
	public void undoCommande() {
		tournee.recalculTourneeApresSupressionLivraison(livraison);

	}

}
