/**
 * La classe CommandeAjoutLivraison permet de modifier une livraison dans
 * la liste de demande 
 */

package controlleur;

import modele.Livraison;
import modele.Tournee;
import vue.MainControlleur;

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
	public CommandeSuppressionLivraison(MainControlleur fenetre, Livraison livraison, Tournee tournee) {
		this.fenetre = fenetre;
		this.livraison = livraison;
		this.tournee = tournee;
	}

	@Override
	public void doCommande() {

		// demandeLivraison.supprimerLivraison(livraison);

		// recalcul avec algo
		tournee.recalculTourneeApresSupressionLivraison(livraison);
		// TODO : modif graphique
	}

	@Override
	public void undoCommande() {

		// tournee.recalculTourneeApresAjoutLivraison(
		// livraison,rangPreEnlevement,rangPreLivraison);
		// TODO : modif graphique
	}

}
