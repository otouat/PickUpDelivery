package vue;

import java.util.Arrays;
import java.util.List;

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
	

		
	public static void drawDemandeLivraison(Plan plan,DemandeLivraison demande, AnchorPane livraisonPane) {
	

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
        for (Livraison l : demande.getLivraisons()) {
        	
        	Noeud pickup = l.getNoeudEnlevement();
        	Circle cercleP = new Circle(VueUtils.getNewX(pickup.GetLongitude()),VueUtils.getNewY(pickup.GetLatitude()),5,couleurs.get(i));
    		
        	Noeud delivery = l.getNoeudLivraison();
        	Rectangle rectangle = new Rectangle(VueUtils.getNewX(delivery.GetLongitude())-5,VueUtils.getNewY(delivery.GetLatitude())-5,10,10);
        	rectangle.setFill(couleurs.get(i));
        	
        	livraisonPane.getChildren().addAll(cercleP,rectangle);
        	i++;
        }
		
		
		
	}

}
