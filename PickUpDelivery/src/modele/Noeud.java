package modele;

public class Noeud {
	protected String idNoeud;
	private double latitude;
	private double longitude;

	public Noeud() {

	}

	public Noeud(String idNoeud, double latitude, double longitude) {
		this.idNoeud = idNoeud;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String GetIdNoeud() {
		return idNoeud;
	}

	public double GetLatitude() {
		return latitude;
	}

	public double GetLongitude() {
		return longitude;
	}

	public String toString() {
		return "Noeud id:" + idNoeud + ", latitude:" + latitude + ", longitude:" + longitude + "\n";
	}
}