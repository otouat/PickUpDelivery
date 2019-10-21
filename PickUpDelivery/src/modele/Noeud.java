package modele;

import java.util.ArrayList;
import java.util.List;

public class Noeud {
	protected String idNoeud;
	protected double latitude;
	protected double longitude;
	protected List<Noeud> noeudsAdjacents;

	public Noeud() {
		super();
		this.noeudsAdjacents = new ArrayList<Noeud>();
	}
	
	public Noeud(String idNoeud, double latitude, double longitude) {
		super();
		this.idNoeud = idNoeud;
		this.latitude = latitude;
		this.longitude = longitude;
		this.noeudsAdjacents = new ArrayList<Noeud>();
	}

	public Noeud(String idNoeud, double latitude, double longitude, List<Noeud> noeudsAdjacents) {
		super();
		this.idNoeud = idNoeud;
		this.latitude = latitude;
		this.longitude = longitude;
		this.noeudsAdjacents = noeudsAdjacents;
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

	public void setNoeudsAdjacents(List<Noeud> noeudsAdjacents) {
		this.noeudsAdjacents = noeudsAdjacents;
	}
	
	public void AjouterNoeudAdjacent(Noeud unNoeud) {
		this.noeudsAdjacents.add(unNoeud);
	}
	
	
}