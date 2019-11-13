package controlleur;

import modele.Tournee;
import modele.Livraison;
import modele.Noeud;

public class CommandeAjoutLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	//private int positionAjout;
	//private int rangPreEnlevement;
	//private int rangPreLivraison;
	private Noeud noeudBeforePickUp;
	private Noeud noeudBeforeDelivery;

	/**
	 * Cree la commande qui ajoute a la position position la livraison livraison
	 * 
	 * @param position
	 * @param livraison
	 * @param calculateurTournee
	 */
	/*CommandeAjoutLivraison(int positionAjout, Livraison livraison, Tournee tournee) {
		this.livraison = livraison;
		this.tournee = tournee;
		this.positionAjout = positionAjout;
	}*/
	public CommandeAjoutLivraison(Noeud noeudBeforePickUp, Noeud noeudBeforeDelivery, Livraison livraison, Tournee tournee) {
		this.livraison = livraison;
		this.tournee = tournee;
		this.noeudBeforePickUp = noeudBeforePickUp;
		this.noeudBeforeDelivery = noeudBeforeDelivery;
		
	}

	@Override
	public void doCommande() {
		//tournee.recalculTourneeApresAjoutLivraison( livraison,rangPreEnlevement,rangPreLivraison);
		//TODO : modif graphique
	}

	@Override
	public void undoCommande() {
		tournee.recalculTourneeApresSupressionLivraison(livraison);
		//TODO : modif graphique
	}

}
