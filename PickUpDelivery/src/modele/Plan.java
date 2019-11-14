/**
 * Un plan contient un map de noeuds rangé selon son id et une liste de troncons.
 */

package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Plan {
	private Map<String, Noeud> noeuds;
	private List<Troncon> troncons;

	public Plan() {
		noeuds = new HashMap<String, Noeud>();
		troncons = new ArrayList<Troncon>();
	}

	public void AjouterNoeud(Noeud unNoeud) {
		noeuds.put(unNoeud.GetIdNoeud(), unNoeud);
	}

	public void AjouterTroncon(Troncon unTroncon) {
		troncons.add(unTroncon);
	}

	public Noeud ChercherNoeudSelonId(String idNoeud) {
		return noeuds.get(idNoeud);
	}

	public Map<String, Noeud> getNoeuds() {
		return noeuds;
	}

	public void setNoeuds(Map<String, Noeud> noeuds) {
		this.noeuds = noeuds;
	}

	public List<Troncon> getTroncons() {
		return troncons;
	}

	public void setTroncons(List<Troncon> troncons) {
		this.troncons = troncons;
	}

	public void getInfo() {
		System.out.println("nombre de noeuds: " + noeuds.size() + " nombre de troncons" + troncons.size());
		// System.out.println(troncons.get(3).GetNomRue());
	}

	public String toString() {
		String infoPlan = "Ce plan contient " + noeuds.size() + " noeuds," + troncons.size() + " troncons \n";
		for (String idNoeud : noeuds.keySet()) {
			Noeud noeud = noeuds.get(idNoeud);
			infoPlan = infoPlan.concat(noeud.toString());
			// TODO: this
		}
		for (int i = 0; i < troncons.size(); i++) {
			infoPlan = infoPlan.concat(troncons.get(i).toString());
		}
		return infoPlan;
	}

	/**
	 * Cette méthode calcule la latitude minimume de ce plan
	 * 
	 * @return latitude minimum
	 */
	public double CalculMinLatitude() {
		Iterator<Map.Entry<String, Noeud>> iterator = noeuds.entrySet().iterator();
		double min = 50;
		while (iterator.hasNext()) {
			Map.Entry<String, Noeud> entry = iterator.next();
			double lat = entry.getValue().GetLatitude();
			min = (lat > min) ? min : lat;
		}
		System.out.println("Latitude Mininale " + min);
		return min;
	}

	/**
	 * Cette méthode calcule la latitude maximum de ce plan
	 * 
	 * @return latitude maximum
	 */
	public double CalculMaxLatitude() {
		Iterator<Map.Entry<String, Noeud>> iterator = noeuds.entrySet().iterator();
		double max = 0;
		while (iterator.hasNext()) {
			Map.Entry<String, Noeud> entry = iterator.next();
			double lat = entry.getValue().GetLatitude();
			max = (max > lat) ? max : lat;
		}
		System.out.println(" Latitude Maximale " + max);
		return max;
	}

	/**
	 * Cette méthode calcule la longitude minimum de ce plan
	 * 
	 * @return longitude minimum
	 */
	public double CalculMinLongitude() {
		Iterator<Map.Entry<String, Noeud>> iterator = noeuds.entrySet().iterator();
		double min = 50;
		while (iterator.hasNext()) {
			Map.Entry<String, Noeud> entry = iterator.next();
			double lat = entry.getValue().GetLongitude();
			min = (lat > min) ? min : lat;
		}
		System.out.println("Longitude Mininale " + min);
		return min;
	}

	/**
	 * Cette méthode calcule la longitude maximum de ce plan
	 * 
	 * @return longitude maximum
	 */
	public double CalculMaxLongitude() {
		Iterator<Map.Entry<String, Noeud>> iterator = noeuds.entrySet().iterator();
		double max = 0;
		while (iterator.hasNext()) {
			Map.Entry<String, Noeud> entry = iterator.next();
			double lat = entry.getValue().GetLongitude();
			max = (max > lat) ? max : lat;
		}
		System.out.println(" Longitude Maximale " + max);
		return max;
	}

	public double CalculEcartLatitude() {
		double ecart = this.CalculMaxLatitude() - this.CalculMinLatitude();
		System.out.println("  Ecart entre la Latitude Maximale et Minimale " + ecart);
		return ecart;
	}

	public double CalculEcartLongitude() {
		double ecart = this.CalculMaxLongitude() - this.CalculMinLongitude();
		System.out.println("  Ecart entre la Longitude Maximale et Minimale " + ecart);
		return ecart;
	}

	/**
	 * Cette méthode calcule le nombre de troncon depuis d'un noeud du plan
	 * 
	 * @param idNoeud : un id du noeud
	 * @return nombre de troncon depuis ce noeud
	 */
	public int CalculerNombreDeTronconDepuisNoeud(String idNoeud) {
		int nombreTroncon = 0;
		for (int i = 0; i < troncons.size(); i++) {
			if (troncons.get(i).GetNoeudOrigine().GetIdNoeud().contentEquals(idNoeud)) {
				nombreTroncon++;
			}
		}
		return nombreTroncon;
	}

	/**
	 * Cette méthode permet de chercher un tronçon ayant noeudOrigine comme origine
	 * et noeudDest comme destination
	 * 
	 * @param idOrigine     : id du noeudOrigine
	 * @param idDestination : id du noeudDest
	 * @return le tronçon trouvé,null si ce troncon n'existe pas
	 */
	public Troncon ChercherTronconDepuisDeuxNoeuds(String idOrigine, String idDestination) {
		Troncon troncon = null;
		for (int i = 0; i < troncons.size(); i++) {
			if (troncons.get(i).GetNoeudOrigine().GetIdNoeud().contentEquals(idOrigine)
					&& troncons.get(i).GetNoeudDestination().GetIdNoeud().contentEquals(idDestination)) {
				troncon = troncons.get(i);
				break;
			}
		}
		return troncon;
	}

	public void AfficheTronconsDepuisNoeud() {
		Iterator<Map.Entry<String, Noeud>> iterator = noeuds.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Noeud> entry = iterator.next();
			List<Troncon> tronconsnode = entry.getValue().GetTronconsDepuisLeNoeud();
			System.out.println(" idNoeudOrigine " + entry.getValue().GetIdNoeud() + "\n");
			System.out.println(tronconsnode.size() + " troncon(s) " + "\n");
			for (int i = 0; i < tronconsnode.size(); i++) {
				System.out.println(tronconsnode.get(i).toString());
			}
		}
	}
}
