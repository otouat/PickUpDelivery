package vue;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;
import modele.DemandeLivraison;
import modele.Livraison;
import modele.Noeud;
import modele.Entrepot;
import modele.Plan;


public class VueDemandeLivraison {
	
	public static List<Color> couleurs = Arrays.asList(Color.CORNFLOWERBLUE,Color.GREEN,Color.DARKORANGE,Color.FIREBRICK,Color.GOLD,Color.HOTPINK,Color.BLUEVIOLET,Color.CHOCOLATE,Color.RED,Color.DARKBLUE,Color.DARKTURQUOISE);
	

		
	public static Group drawDemandeLivraison(Plan plan,DemandeLivraison demande, AnchorPane livraisonPane, List<LivraisonDisplay> livraisonsVue) {
	
		Group livraisons = new Group();
		Entrepot entrepot = demande.getEntrepotLivraison();
		VueUtils.initalisationDonnees(plan, livraisonPane);
		double x_entrepot = VueUtils.getNewX(entrepot.GetLongitude());
		double y_entrepot = VueUtils.getNewY(entrepot.GetLatitude());

		// Create Entrepot
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(x_entrepot, y_entrepot-7,  x_entrepot-6,y_entrepot+4,x_entrepot+6, y_entrepot+4);
        triangle.setFill(Color.RED);
        triangle.setStroke(Color.BLACK);
        livraisons.getChildren().add(triangle);
        //livraisonPane.getChildren().add(triangle);
        
        //Create Pick up
        int i=0;
        for (Livraison liv : demande.getLivraisons()) {
        	LivraisonDisplay lPickUp = new LivraisonDisplay(liv.getNoeudEnlevement(),true,couleurs.get(i));
        	livraisonsVue.add(lPickUp);
        	Noeud pickup = lPickUp.getNoeud();
        	Circle cercleP = new Circle(VueUtils.getNewX(pickup.GetLongitude()),VueUtils.getNewY(pickup.GetLatitude()),5,lPickUp.getColor());
    		cercleP.setId(pickup.GetIdNoeud());
        	
    		LivraisonDisplay lDelivery = new LivraisonDisplay(liv.getNoeudLivraison(),false,couleurs.get(i));
    		livraisonsVue.add(lDelivery);
    		Noeud delivery = lDelivery.getNoeud();
        	Rectangle rectangle = new Rectangle(VueUtils.getNewX(delivery.GetLongitude())-5,VueUtils.getNewY(delivery.GetLatitude())-5,10,10);
        	rectangle.setFill(lDelivery.getColor());
        	rectangle.setId(delivery.GetIdNoeud());
        	
        	livraisons.getChildren().addAll(cercleP,rectangle);
        	i++;
        }
        livraisonPane.getChildren().add(livraisons);
        return livraisons;
		
		
	}
	
	public static void removeLivraisonGraphiquement(Group livraisons ,Color couleur) {
		
		Shape CercleASupprimer = new Circle();
		Shape RectASupprimer =new Rectangle() ;
		for (Node n : livraisons.getChildren()) {
        	Shape s = (Shape)n;
        	if(s.getFill()==couleur && s instanceof Circle) {
        		CercleASupprimer=s;
        	}else if (s.getFill()==couleur && s instanceof Rectangle) {
        		RectASupprimer=s;
        	}
        }
		
		//Supression graphique
		livraisons.getChildren().remove(CercleASupprimer);
		livraisons.getChildren().remove(RectASupprimer);
		
	}
	
	public static void removeLivraisonTextuellement(LivraisonDisplay l, List<LivraisonDisplay> livraisonsVue) {
		LivraisonDisplay PickASupprimer = l ;
		LivraisonDisplay DeliveryASupprimer = l;
		
		for(LivraisonDisplay lD : livraisonsVue ) {
			if (lD.getColor()== l.getColor() && lD.getIsPickup()) {
				PickASupprimer= lD;
			}else if (lD.getColor()== l.getColor() && !lD.getIsPickup()) {
				DeliveryASupprimer= lD;
			}
		}
		
		//suppression vue textuelle
		livraisonsVue.remove(PickASupprimer);
		livraisonsVue.remove(DeliveryASupprimer);
	}
	
	public static Pair<LivraisonDisplay,LivraisonDisplay> ajouterLivraison(List<LivraisonDisplay> livraisonsVue, Livraison l, Group livraisons, Boolean isNew) {
		Color c= Color.WHITE;
		Noeud delivery = l.getNoeudLivraison();
		Noeud pickup = l.getNoeudEnlevement();
		LivraisonDisplay pickUpAAjouter=null;
		LivraisonDisplay deliveryAAjouter=null;
		if(!isNew) {
			for(LivraisonDisplay lD : livraisonsVue ) {
				if (lD.getIsPickup() && lD.getNoeud().GetIdNoeud()==l.getNoeudEnlevement().GetIdNoeud()) {
					c=lD.getColor();
					break;
				}else if (!lD.getIsPickup() && lD.getNoeud().GetIdNoeud()==delivery.GetIdNoeud()) {
					c=lD.getColor();
					break;
				}
			}
		}else{
			c= couleurs.get(livraisonsVue.size()/2);
			pickUpAAjouter = new LivraisonDisplay(l.getNoeudEnlevement(),true,c);
			deliveryAAjouter = new LivraisonDisplay(l.getNoeudLivraison(),false,c);
		}
		Circle cercle = new Circle(VueUtils.getNewX(pickup.GetLongitude()),VueUtils.getNewY(pickup.GetLatitude()),5,c);
		cercle.setId(pickup.GetIdNoeud());
		
		Rectangle rectangle = new Rectangle(VueUtils.getNewX(delivery.GetLongitude())-5,VueUtils.getNewY(delivery.GetLatitude())-5,10,10);
    	rectangle.setFill(c);
    	rectangle.setId(delivery.GetIdNoeud());
		
    	livraisons.getChildren().addAll(rectangle,cercle);
    	return new Pair<LivraisonDisplay,LivraisonDisplay>(pickUpAAjouter,deliveryAAjouter);
	}
	
	//Ajout d'un élément après noeudBefore
	public static void updateLivraisonsVueAjout(Noeud noeudBefore, LivraisonDisplay liv ,List<LivraisonDisplay> livraisonsVue) {
		int index =0;
		for(int i=0 ; i<livraisonsVue.size() ;i++) {
			if(noeudBefore.GetIdNoeud()==livraisonsVue.get(i).getNoeud().GetIdNoeud()) {
				index= i+1;
				break;
			}		
		}
		livraisonsVue.add(index, liv);
	}

}
