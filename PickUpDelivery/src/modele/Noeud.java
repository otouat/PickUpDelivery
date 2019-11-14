/**
 * Noeud est un élément d'un plan. Un noeud contient un id, une latitude et une longitude ainsi que
 * une liste de troncons depuis ce noeud
 * @author Tianming
 */

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
		this.tronconsDepuisLeNoeud = new ArrayList<Troncon>();
	}

	public Noeud(String idNoeud, double latitude, double longitude, List<Troncon> tronconsDepuisLeNoeud) {
		super();
		this.idNoeud = idNoeud;
		this.latitude = latitude;
		this.longitude = longitude;
		this.tronconsDepuisLeNoeud = tronconsDepuisLeNoeud;
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

	public void setTronconsDepuisLeNoeud(List<Troncon> tronconsDepuisLeNoeud) {
		this.tronconsDepuisLeNoeud = tronconsDepuisLeNoeud;
	}

	public void AjouterTroncon(Troncon unTroncon) {
		this.tronconsDepuisLeNoeud.add(unTroncon);
	}

	public String toString() {
		return "Noeud id:" + idNoeud + ", Longitude:" + longitude + ", Latitude:" + latitude + ". \n";
	}

	/**
	 * Cette méthode calcule l'égalité de deux noeuds. Si deux noeuds ont le même
	 * id, ils sont égals.
	 * 
	 * @param obj : le noeud à comparer avec
	 * @return : true si les deux noeuds sont égals
	 */
	public boolean equal(Noeud obj) {
		if (this.idNoeud.equals(obj.GetIdNoeud())) {
			return true;
		} else {
			return false;
		}
	}
}