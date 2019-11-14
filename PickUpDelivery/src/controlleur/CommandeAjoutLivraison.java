package controlleur;

import modele.Tournee;
import modele.Triplet;
import vue.LivraisonDisplay;
import vue.MainControlleur;
import vue.VueDemandeLivraison;
import vue.VueTroncon;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import modele.Livraison;
import modele.Noeud;

public class CommandeAjoutLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	private Noeud noeudBeforePickUp;
	private Noeud noeudBeforeDelivery;
	private MainControlleur fenetre;
	private Pair<LivraisonDisplay,LivraisonDisplay> pair;
	private List< Triplet<Noeud, Livraison, Boolean>> liste;
	
	private List<LivraisonDisplay> nouvelleLivraisonsVue;
	private List<Noeud> nouvelleTournee;
 	private Group nouveauGroupLivraison ;
	
	private List<LivraisonDisplay> ancienneLivraisonsVue;
	private List<Noeud> ancienneTournee;
	private Group ancienGroupLivraison ; 

	/**
	 * Cree la commande qui ajoute a la position position la livraison livraison
	 */
	public CommandeAjoutLivraison(MainControlleur fenetre, Noeud noeudBeforePickUp, Noeud noeudBeforeDelivery, Livraison livraison, Tournee tournee) {
		this.fenetre=fenetre;
		this.livraison = livraison;
		this.tournee = tournee;
		this.noeudBeforePickUp = noeudBeforePickUp;
		this.noeudBeforeDelivery = noeudBeforeDelivery;
		
		liste = new ArrayList<Triplet<Noeud, Livraison, Boolean>>();
		for(Triplet<Noeud, Livraison, Boolean> t : tournee.getenchainementNoeudAVisiterAvecInfos()) {
			this.liste.add(t);
		}
		
		ancienneTournee =new ArrayList<Noeud>();
		for(Noeud n : tournee.calculTournee()) {
			ancienneTournee.add(n);
		}
		
		nouvelleTournee =new ArrayList<Noeud>();
		for (Noeud n :tournee.recalculTourneeApresAjoutLivraison( livraison,noeudBeforePickUp,noeudBeforeDelivery) )
		{		nouvelleTournee.add(n);
		
		}
		
		ancienneLivraisonsVue = new ArrayList<LivraisonDisplay>();
		nouvelleLivraisonsVue = new ArrayList<LivraisonDisplay>();
		for ( LivraisonDisplay lD : fenetre.livraisonsVue) {
			ancienneLivraisonsVue.add(lD);
		}
		
		ancienGroupLivraison= new Group();
		ancienGroupLivraison.getChildren().addAll(fenetre.livraisons.getChildren());
		nouveauGroupLivraison = new Group();
	}

	@Override
	public void doCommande() {
		
		nouvelleLivraisonsVue.clear();
		nouveauGroupLivraison.getChildren().clear();
		for(Node n : ancienGroupLivraison.getChildren()) {	
			if(n instanceof Circle) {
			Circle s = new Circle(((Circle) n).getCenterX(),((Circle) n).getCenterY(),((Circle) n).getRadius(), ((Circle) n).getFill());
			nouveauGroupLivraison.getChildren().add(s);
			} else if (n  instanceof Rectangle ) {
				Rectangle s = new Rectangle(((Rectangle) n).getX(),((Rectangle) n).getY(),((Rectangle) n).getWidth(),((Rectangle) n).getHeight());	
				s.setFill(((Rectangle) n).getFill());
				nouveauGroupLivraison.getChildren().add(s);
				
			}else {
				Polygon s = new Polygon();
				s.getPoints().addAll(((Polygon)n).getPoints());
				nouveauGroupLivraison.getChildren().add(s);

			}
		}
		//remettre a jour nouvelleLivraisonsVue
		for ( LivraisonDisplay lD : ancienneLivraisonsVue) {
			nouvelleLivraisonsVue.add(lD);
		}

		
		//Ajout graphique
		pair = VueDemandeLivraison.ajouterLivraison(nouvelleLivraisonsVue, livraison, nouveauGroupLivraison, true);
		fenetre.setGroupLivraison(nouveauGroupLivraison);
		
		//Ajout textuel
		VueDemandeLivraison.updateLivraisonsVueAjout(noeudBeforePickUp,pair.getKey(),nouvelleLivraisonsVue);
		VueDemandeLivraison.updateLivraisonsVueAjout(noeudBeforeDelivery,pair.getValue(),nouvelleLivraisonsVue);
		fenetre.setLivraisonsVue(nouvelleLivraisonsVue);
		fenetre.reInitialiseListView(tournee.getenchainementNoeudAVisiterAvecInfos());
		
		//Draw nouvelle Tournee
		VueTroncon.drawTournee(nouvelleTournee, fenetre.tourneePane);
		
	}

	@Override
	public void undoCommande() {
		
		//REAFFICHAGE TEXTUEL
		VueDemandeLivraison.removeLivraisonTextuellement(pair.getKey(),nouvelleLivraisonsVue);
		fenetre.setLivraisonsVue(nouvelleLivraisonsVue);
		fenetre.initialiseListView();
		
		
		//affichage graphique
		VueDemandeLivraison.removeLivraisonGraphiquement(nouveauGroupLivraison, pair.getKey().getColor());
		fenetre.setGroupLivraison(nouveauGroupLivraison);
		
		// recalcul avec algo
		VueTroncon.drawTournee(ancienneTournee,fenetre.tourneePane);
		
		//fenetre.reInitialiseListView(tournee.getenchainementNoeudAVisiterAvecInfos());
		
		//VueTroncon.drawTournee(ancienneTournee, fenetre.tourneePane);
		//remove textuel

		
		//remove graphiquement
		//VueDemandeLivraison.removeLivraisonGraphiquement(fenetre.livraisons, pair.getKey().getColor());
	}

}
