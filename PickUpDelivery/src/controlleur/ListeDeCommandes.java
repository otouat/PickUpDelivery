package controlleur;

import java.util.LinkedList;

public class ListeDeCommandes {
	private LinkedList<Commande> listeDeCommandes;
	private int indiceCrt;

	public ListeDeCommandes() {
		indiceCrt = -1;
		listeDeCommandes = new LinkedList<Commande>();
	}

	/**
	 * Ajout de la commande commande a la liste this
	 * 
	 * @param commande
	 *            = la commande a ajouter
	 */
	public void ajoute(Commande commande) {
		int i = indiceCrt + 1;
		while (i < listeDeCommandes.size()) {
			listeDeCommandes.remove(i);
		}
		indiceCrt++;
		listeDeCommandes.add(indiceCrt, commande);
		commande.doCommande();
	}

	/**
	 * Annule temporairement la derniere commande ajoutee (cette commande pourra
	 * etre remise dans la liste avec redo)
	 */
	public void undo() {
		if (indiceCrt >= 0) {
			Commande commande = listeDeCommandes.get(indiceCrt);
			indiceCrt--;
			commande.undoCommande();
		}
	}

	/**
	 * Supprime definitivement la derniere commande ajoutee (cette commande ne
	 * pourra pas etre remise dans la liste avec redo)
	 */
	public void annule() {
		if (indiceCrt >= 0) {
			Commande commande = listeDeCommandes.get(indiceCrt);
			listeDeCommandes.remove(indiceCrt);
			indiceCrt--;
			commande.undoCommande();
		}
	}

	/**
	 * Remet dans la liste la derniere commande annulee avec undo
	 */
	public void redo() {
		if (indiceCrt < listeDeCommandes.size() - 1) {
			indiceCrt++;
			Commande commande = listeDeCommandes.get(indiceCrt);
			commande.doCommande();
		}
	}

	/**
	 * Supprime definitivement toutes les commandes de liste
	 */
	public void reset() {
		indiceCrt = -1;
		listeDeCommandes.clear();
	}

}
