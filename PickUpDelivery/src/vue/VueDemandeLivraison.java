package vue;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
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
        livraisonPane.getChildren().add(triangle);
        
        //Create Pick up
        int i=0;
        for (Livraison liv : demande.getLivraisons()) {
        	LivraisonDisplay lPickUp = new LivraisonDisplay(liv.getNoeudEnlevement(),false,couleurs.get(i));
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

}
