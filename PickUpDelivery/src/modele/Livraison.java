package modele;

public class Livraison {

	private Noeud noeudEnlevement;
	private Noeud noeudLivraison;
	private int dureeEnlevement;
	private int dureeLivraison;

	public Livraison(Noeud noeudEnlevement, Noeud noeudLivraison, int dureeEnlevement, int dureeLivraison) {
		this.noeudEnlevement = noeudEnlevement;
		this.noeudLivraison = noeudLivraison;
		this.dureeEnlevement = dureeEnlevement;
		this.dureeLivraison = dureeLivraison;
	}

	public Noeud getNoeudEnlevement() {
		return noeudEnlevement;
	}

	public Noeud getNoeudLivraison() {
		return noeudLivraison;
	}

	public int getDureeEnlevement() {
		return dureeEnlevement;
	}

	public int getDureeLivraison() {
		return dureeLivraison;
	}

	public String toString() {
		return "Livraison noeud enlevement:" + noeudEnlevement.GetIdNoeud() + ", noeud livraison:"
				+ noeudLivraison.GetIdNoeud() + ", durée d'enlevement" + dureeEnlevement + ", durée de livraison"
				+ dureeLivraison;
	}
}
