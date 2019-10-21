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

	public void getInfo() {
		System.out.println("nombre de noeuds: " + noeuds.size() + " nombre de troncons" + troncons.size());
		// System.out.println(troncons.get(3).GetNomRue());
	}
	
	public double CalculMinLatitude(){
        Iterator<Map.Entry<String, Noeud>> iterator = noeuds.entrySet().iterator();
        double min=50;
        while (iterator.hasNext()) {
            Map.Entry<String, Noeud> entry = iterator.next();
            double lat =entry.getValue().GetLatitude(); 
            min= (lat>min)? min:lat;    
        }
        System.out.println("Latitude Mininale "+ min);
		return min;
	}
	
	public double CalculMaxLatitude(){
        Iterator<Map.Entry<String, Noeud>> iterator = noeuds.entrySet().iterator();
        double max=0;
        while (iterator.hasNext()) {
            Map.Entry<String, Noeud> entry = iterator.next();
            double lat =entry.getValue().GetLatitude(); 
            max= (max>lat)? max:lat;    
        }
        System.out.println(" Latitude Maximale "+ max);
		return max;
	}
	
	public double CalculMinLongitude(){
        Iterator<Map.Entry<String, Noeud>> iterator = noeuds.entrySet().iterator();
        double min=50;
        while (iterator.hasNext()) {
            Map.Entry<String, Noeud> entry = iterator.next();
            double lat =entry.getValue().GetLongitude(); 
            min= (lat>min)? min:lat;    
        }
        System.out.println("Longitude Mininale "+ min);
		return min;
	}
	
	public double CalculMaxLongitude(){
        Iterator<Map.Entry<String, Noeud>> iterator = noeuds.entrySet().iterator();
        double max=0;
        while (iterator.hasNext()) {
            Map.Entry<String, Noeud> entry = iterator.next();
            double lat =entry.getValue().GetLongitude(); 
            max= (max>lat)? max:lat;    
        }
        System.out.println(" Longitude Maximale "+ max);
		return max;
	}
	
	
	
}