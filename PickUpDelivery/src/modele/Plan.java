package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
	
	
	public double CalculEcartLatitude(){
		double ecart= this.CalculMaxLatitude()-this.CalculMinLatitude();
		System.out.println("  Ecart entre la Latitude Maximale et Minimale "+ ecart);
		return ecart;
	}
	
	public double CalculEcartLongitude(){
		double ecart= this.CalculMaxLongitude()-this.CalculMinLongitude();
		System.out.println("  Ecart entre la Longitude Maximale et Minimale "+ ecart);
		return ecart;
	}
	
	
	public void AfficheNoeudsAdjacents() {
		 Iterator<Map.Entry<String, Noeud>> iterator = noeuds.entrySet().iterator();
	        while (iterator.hasNext()) {
	            Map.Entry<String, Noeud> entry = iterator.next();
	             List<Noeud>noeudsAdj =entry.getValue().getNoeudsAdjacents();
	             System.out.println(" idNoeudOrigine " +entry.getValue().GetIdNoeud() );
	             for(int i = 0; i < noeudsAdj.size(); i++)
	     		{
	     			System.out.println(" idNoeudAdjacent " + i + " = " + noeudsAdj.get(i).GetIdNoeud());
	     		}
	        }
	      }
		
       
	}
