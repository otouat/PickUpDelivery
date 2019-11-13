/**
 * La classe CommandeAjoutLivraison permet de modifier une livraison dans
 * la liste de demande 
 */
package controlleur;

import modele.Livraison;//
import modele.Noeud;
import modele.Tournee;

public class CommandeModifierNoeudLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	private Noeud noeudChange;
	private Noeud noeudActuel;
	private int rangNoeudAChanger;

	@Override
	public void doCommande() {
		// TODO Auto-generated method stub
		tournee.recalculTourneeApresChangementNoeud(noeudChange, rangNoeudAChanger);
	}

	@Override
	public void undoCommande() {
		// TODO Auto-generated method stub
		tournee.recalculTourneeApresChangementNoeud(noeudActuel, rangNoeudAChanger);

	}

}
