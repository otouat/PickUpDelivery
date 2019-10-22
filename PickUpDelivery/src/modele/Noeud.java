package modele;

import java.util.ArrayList;
import java.util.List;

public class Noeud {
	protected String idNoeud;
	protected double latitude;
	protected double longitude;
	protected List<Troncon> tronconsDepuisLeNoeud;

	public Noeud() {
		super();
		this.tronconsDepuisLeNoeud = new ArrayList<Troncon>();
	}

	public Noeud(String idNoeud, double latitude, double longitude) {
		super();
		this.idNoeud = idNoeud;
		this.latitude = latitude;
		this.longitude = longitude;
		this.tronconsDepuisLeNoeud = new ArrayList<Troncon>(); // changer par this.tronconsDepuisLeNoeud = new
																// ArrayList<Troncon>();
	}

	public Noeud(String idNoeud, double latitude, double longitude, List<Troncon> tronconsDepuisLeNoeud) { // changer
																											// par
																											// List<Troncon>
																											// tronconsDepuisLeNoeud;
		super();
		this.idNoeud = idNoeud;
		this.latitude = latitude;
		this.longitude = longitude;
		this.tronconsDepuisLeNoeud = tronconsDepuisLeNoeud; // changer par this.tronconsDepuisLeNoeud =
															// tronconsDepuisLeNoeud;
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

	public List<Troncon> GetTronconsDepuisLeNoeud() {
		return tronconsDepuisLeNoeud;
	}

	public void setTronconsDepuisLeNoeud(List<Troncon> tronconsDepuisLeNoeud) { // changer par setTronconsDepuisNoeud
																				// (List<Troncon> tronconsDepuisLeNoeud)
		this.tronconsDepuisLeNoeud = tronconsDepuisLeNoeud; // changer par this.tronconsDepuisLeNoeud =
															// tronconsDepuisLeNoeud;
	}

	public void AjouterTroncon(Troncon unTroncon) { // changer par AjouterTroncon(Troncon unTroncon)
		this.tronconsDepuisLeNoeud.add(unTroncon); // changer par this.tronconsDepuisLeNoeud.add(unTroncon)
	}

	public String toString() {
		return "Noeud id:" + idNoeud + ", Longitude:" + longitude + ", Latitude:" + latitude + ". \n";
	}
}