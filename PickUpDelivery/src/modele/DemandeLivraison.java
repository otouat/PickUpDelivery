/**
 * Une demande de livraison est une liste de livraison pour un livreur partant d’un entrepôt
 * La classe DemandeLivraison contient une entrepôt et une liste de livraisons
 * @author Tianming
 * @see Entrepot
 * @see Livraison
 */

package modele;

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

	public void setLivraisons(List<Livraison> livraisons) {
		this.livraisons = livraisons;
	}

	public void setEntrepotLivraison(Entrepot entrepotLivraison) {
		this.entrepotLivraison = entrepotLivraison;
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
		// System.out.println("Heure de depart de l'entrepot: " +
		// entrepotLivraison.getHeureDepart());
		System.out.println("nombre de livraisons: " + livraisons.size());
		System.out.println(livraisons.get(4).getDureeEnlevement());

	}

	public String toString() {

		String infoDemande = "nombre de livraisons: " + livraisons.size() + "\n";
		infoDemande = infoDemande + entrepotLivraison.toString() + "\n";
		for (int i = 0; i < livraisons.size(); i++) {
			infoDemande = infoDemande + livraisons.get(i).toString() + "\n";
		}
		return infoDemande;
	}

}
