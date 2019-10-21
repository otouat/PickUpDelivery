package modele;

public class Entrepot {

	private Noeud noeudEntrepot;
	private String heureDepart;

	public Entrepot() {
		super();
	}

	public Entrepot(Noeud noeudEntrepot, String heureDepart) {
		this.noeudEntrepot = noeudEntrepot;
		this.heureDepart = heureDepart;
	}

	public Noeud getNoeudEntrepot() {
		return noeudEntrepot;
	}

	public String getHeureDepart() {
		return heureDepart;
	}

}
