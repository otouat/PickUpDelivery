package controlleur;

import modele.Tournee;
import modele.Triplet;
import vue.LivraisonDisplay;
import vue.MainControlleur;
import vue.VueDemandeLivraison;
import vue.VueTroncon;

import java.util.ArrayList;
import java.util.List;

import modele.Livraison;
import modele.Noeud;

public class CommandeSuppressionLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	private LivraisonDisplay l;
	private MainControlleur fenetre;
	private List< Triplet<Noeud, Livraison, Boolean>> liste;
	private List<LivraisonDisplay> ancienLivraisonsVue;
	private List<Noeud> ancienneTournee;
	

	/**
	 * Cree la commande qui ajoute a la position position la livraison livraison
	 * 
	 * @param position
	 * @param livraison
	 * @param calculateurTournee
	 */
	public CommandeSuppressionLivraison( MainControlleur fenetre, Livraison livraison, LivraisonDisplay l,Tournee tournee) {
		this.fenetre=fenetre;
		this.livraison = livraison;
		this.l = l;
		this.tournee = tournee;
		this.liste = new ArrayList<Triplet<Noeud, Livraison, Boolean>>();
		for(Triplet<Noeud, Livraison, Boolean> t : tournee.getenchainementNoeudAVisiterAvecInfos()) {
			this.liste.add(t);
		}
		ancienneTournee =new ArrayList<Noeud>();
		for(Noeud n : tournee.calculTournee()) {
			ancienneTournee.add(n);
		}
		ancienLivraisonsVue = new ArrayList<LivraisonDisplay>();
		
	}

	@Override
	public void doCommande() {
		
		
		for ( LivraisonDisplay l : fenetre.livraisonsVue) {
			ancienLivraisonsVue.add(l);
		}
		//affichage textuel
		VueDemandeLivraison.removeLivraisonTextuellement(l,fenetre.livraisonsVue);
		fenetre.initialiseListView();
		//affichage graphique
		VueDemandeLivraison.removeLivraisonGraphiquement(fenetre.livraisons, l.getColor());
	
		// recalcul avec algo
		VueTroncon.drawTournee(tournee.recalculTourneeApresSupressionLivraison(livraison),fenetre.tourneePane);
	}

	@Override
	public void undoCommande() {
		//affichage textuel
		fenetre.setLivraisonsVue(ancienLivraisonsVue);
		fenetre.reInitialiseListView(liste);
		
		//affichage graphique
		VueDemandeLivraison.ajouterLivraison(fenetre.livraisonsVue,livraison,fenetre.livraisons,false);
		//affichage tournee
		VueTroncon.drawTournee(ancienneTournee, fenetre.tourneePane);
		//TODO : modif graphique
	}

}
