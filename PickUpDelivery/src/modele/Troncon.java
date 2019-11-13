/**
 * Un tronçon est un couple de noeuds (nommés destination et origine) associé à une longueur
 * ( correspondant à la distance géographique entre les noeuds) et à un nom de voie existant.
 * @author Tianming
 */
package modele;

public class Troncon {
	private Noeud noeudOrigine;
	private Noeud noeudDestination;
	private String nomRue;
	private double longueur;

	public Troncon(Noeud noeudOrigine, Noeud noeudDestination, String nomRue, double longueur) {
		this.noeudOrigine = noeudOrigine;
		this.noeudDestination = noeudDestination;
		this.nomRue = nomRue;
		this.longueur = longueur;
	}

	public Noeud GetNoeudOrigine() {
		return noeudOrigine;
	}

	public Noeud GetNoeudDestination() {
		return noeudDestination;
	}

	public String GetNomRue() {
		return nomRue;
	}

	public double GetLongueur() {
		return longueur;
	}

	public String toString() {
		return "Troncon noeud origine:" + noeudOrigine.GetIdNoeud() + ", noeud destination:"
				+ noeudDestination.GetIdNoeud() + ", nom de rue:" + nomRue + ", longueur:" + longueur + "\n";
	}
}