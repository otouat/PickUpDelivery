/**
 * Une entrepôt est l'adresse de départ de toutes tournées de livraison
 * La classe Entrepôt hérite la classe Noeud, elle contient une horaire de départ en plus
 * @author Tianming
 * @see Noeud
 */

package modele;

public class Entrepot extends Noeud {

	private String heureDepart;

	public Entrepot(String idNoeud, double latitude, double longitude, String heureDepart) {
		super(idNoeud, latitude, longitude);
		this.heureDepart = heureDepart;
	}

	public Entrepot() {
		super();
	}

	public Entrepot(String idNoeud, double latitude, double longitude) {
		super(idNoeud, latitude, longitude);
	}

	public void setHeureDepart(String heureDepart) {
		this.heureDepart = heureDepart;
	}

	public String getHeureDepart() {
		return heureDepart;
	}

	public String toString() {
		return "Entrepot id " + idNoeud + ", heure de départ:" + heureDepart;
	}

	/*
	 * public Noeud getNoeudEntrepot() { Noeud noeud = new Noeud(this.idNoeud,
	 * this.latitude, this.longitude); return noeud; }
	 */
}
