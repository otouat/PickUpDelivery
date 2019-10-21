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

}
