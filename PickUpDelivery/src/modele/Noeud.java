package modele;

import java.util.ArrayList;
import java.util.List;

public class Noeud {
	protected String idNoeud;
	protected double latitude;
	protected double longitude;
	protected List<Noeud> noeudsAdjacents; // changer par List<Troncon> tronconsDepuisLeNoeud;

	public Noeud() {
		super();
		this.noeudsAdjacents = new ArrayList<Noeud>(); // changer par this.tronconsDepuisLeNoeud = new ArrayList<Troncon>();
	}
	
	public Noeud(String idNoeud, double latitude, double longitude) {
		super();
		this.idNoeud = idNoeud;
		this.latitude = latitude;
		this.longitude = longitude;
		this.noeudsAdjacents = new ArrayList<Noeud>(); // changer par this.tronconsDepuisLeNoeud = new ArrayList<Troncon>();
	}

	public Noeud(String idNoeud, double latitude, double longitude, List<Noeud> noeudsAdjacents) { // changer par List<Troncon> tronconsDepuisLeNoeud;
		super();
		this.idNoeud = idNoeud;
		this.latitude = latitude;
		this.longitude = longitude;
		this.noeudsAdjacents = noeudsAdjacents; // changer par this.tronconsDepuisLeNoeud = tronconsDepuisLeNoeud;
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

	public List<Noeud> getNoeudsAdjacents() {
		return noeudsAdjacents;
	}

	public void setNoeudsAdjacents(List<Noeud> noeudsAdjacents) { // changer par setTronconsDepuisNoeud (List<Troncon> tronconsDepuisLeNoeud)
		this.noeudsAdjacents = noeudsAdjacents; // changer par this.tronconsDepuisLeNoeud = tronconsDepuisLeNoeud;
	}
	
	public void AjouterNoeudAdjacent(Noeud unNoeud) { //changer par AjouterTroncon(Troncon unTroncon)
		this.noeudsAdjacents.add(unNoeud); // changer par this.tronconsDepuisLeNoeud.add(unTroncon)
	}
	
	
}