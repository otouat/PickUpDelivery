package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plan {
	Map<String, Noeud> noeuds;
	List<Troncon> troncons;

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
	
	public List<Troncon> getTroncons(){
		return troncons;
	}

	public void getInfo() {
		System.out.println("nombre de noeuds: " + noeuds.size() + " nombre de troncons" + troncons.size());
		System.out.println(troncons.get(3).GetNomRue());
	}
}