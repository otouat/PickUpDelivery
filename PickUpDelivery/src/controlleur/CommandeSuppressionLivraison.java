package controlleur;

import modele.Tournee;
import modele.Triplet;
import vue.LivraisonDisplay;
import vue.MainControlleur;
import vue.VueDemandeLivraison;
import vue.VueTroncon;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.Group;
import javafx.scene.Node;
import modele.Livraison;
import modele.Noeud;

public class CommandeSuppressionLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	private LivraisonDisplay l;
	private MainControlleur fenetre;
	private List< Triplet<Noeud, Livraison, Boolean>> liste;
	
	private List<LivraisonDisplay> nouvelleLivraisonsVue;
	private List<Noeud> nouvelleTournee;
 	private Group nouveauGroupLivraison ;
	
	private List<LivraisonDisplay> ancienneLivraisonsVue;
	private List<Noeud> ancienneTournee;
	private Group ancienGroupLivraison ; 
	

	/**
	 * Cree la commande qui ajoute a la position position la livraison livraison
	 * 
	 * @param position
	 * @param livraison
	 * @param calculateurTournee
	 */
	public CommandeSuppressionLivraison( MainControlleur fenetre, Group livraisons,Livraison livraison, LivraisonDisplay l,Tournee tournee,List< Triplet<Noeud, Livraison, Boolean>> liste ) {
		this.fenetre=fenetre;
		this.livraison = livraison;
		this.l = l;
		this.tournee = tournee;
		this.liste = new ArrayList<Triplet<Noeud, Livraison, Boolean>>();
		for(Triplet<Noeud, Livraison, Boolean> t : tournee.getenchainementNoeudAVisiterAvecInfos()) {
			this.liste.add(t);
			System.out.println("CEST ICI ----"+t.getFirst().GetIdNoeud());
		}
		
		ancienneTournee =new ArrayList<Noeud>();
		for(Noeud n : tournee.calculTournee()) {
			ancienneTournee.add(n);
		}
		nouvelleTournee =new ArrayList<Noeud>();
		for (Noeud n :tournee.recalculTourneeApresSupressionLivraison(livraison) )
		{		nouvelleTournee.add(n);
		
		}
		
		ancienneLivraisonsVue = new ArrayList<LivraisonDisplay>();
		nouvelleLivraisonsVue = new ArrayList<LivraisonDisplay>();
		for ( LivraisonDisplay lD : fenetre.livraisonsVue) {
			ancienneLivraisonsVue.add(lD);
		}
		
		ancienGroupLivraison= new Group();
		ancienGroupLivraison.getChildren().addAll(livraisons.getChildren());
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
		//nouveauGroupLivraison.getChildren().addAll(ancienGroupLivraison.getChildren());
		//remettre a jour nouvelleLivraisonsVue
		for ( LivraisonDisplay lD : ancienneLivraisonsVue) {
			nouvelleLivraisonsVue.add(lD);
		}
		
		
		//affichage textuel
		VueDemandeLivraison.removeLivraisonTextuellement(l,nouvelleLivraisonsVue);
		fenetre.setLivraisonsVue(nouvelleLivraisonsVue);
		fenetre.initialiseListView();
		
		//affichage graphique
		VueDemandeLivraison.removeLivraisonGraphiquement(nouveauGroupLivraison, l.getColor());
		fenetre.setGroupLivraison(nouveauGroupLivraison);
	
		
		// recalcul avec algo
		VueTroncon.drawTournee(nouvelleTournee,fenetre.tourneePane);
	
	}

	@Override
	public void undoCommande() {
		
		//affichage textuel
		fenetre.setLivraisonsVue(ancienneLivraisonsVue);
		fenetre.reInitialiseListView(liste);
		
		//affichage graphique
		VueDemandeLivraison.ajouterLivraison(ancienneLivraisonsVue,livraison,nouveauGroupLivraison,false);
		fenetre.setGroupLivraison(nouveauGroupLivraison);
		//affichage tournee
		VueTroncon.drawTournee(ancienneTournee, fenetre.tourneePane);

	}

}
