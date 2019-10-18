package modèle;

import java.util.ArrayList;
import java.util.List;

public class DemandeLivraison {

	
	private Entrepot entrepotLivraison;
	private List<Livraison> livraisons;
	
	public DemandeLivraison() {
		entrepotLivraison = new Entrepot();
		livraisons = new ArrayList<Livraison>();
	}
	
	public Entrepot getEntrepotLivraison() {
		return entrepotLivraison;
	}

	public List<Livraison> getLivraisons() {
		return livraisons;
	}

	public void AjouterLivraison(Livraison uneLivraison) {
		livraisons.add(uneLivraison);
	}
	
	public void SupprimerLivraison(Livraison uneLivraison) {
		livraisons.remove(uneLivraison);
	}
	
	public void getInfo() {
		System.out.println("Heure de départ de l'entrepot: " + entrepotLivraison.getHeureDepart() );
		System.out.println("nombre de livraisons: " + livraisons.size() );
		System.out.println(livraisons.get(3).getDureeEnlevement());
		
	}
	
}
