package vue;

import java.util.List;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import modele.DemandeLivraison;
import modele.Entrepot;
import modele.Plan;


public class VueDemandeLivraison {
	
	
	public static void drawDemandeLivraison(Plan plan,DemandeLivraison demande, BorderPane paneMap) {
	
		Entrepot entrepot = demande.getEntrepotLivraison();
		VueUtils.initalisationDonnees(plan, paneMap);
		double x_entrepot = VueUtils.getNewX(entrepot.GetLongitude());
		double y_entrepot = VueUtils.getNewY(entrepot.GetLatitude());

		// Create Entrepot
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(x_entrepot, y_entrepot-7,  x_entrepot-6,y_entrepot+4,x_entrepot+6, y_entrepot+4);
        triangle.setFill(Color.RED);
        triangle.setStroke(Color.BLACK);
        
        paneMap.getChildren().add(triangle);
		
		/*Circle cercle = new Circle();
		cercle.setCenterX(x_entrepot);
		cercle.setCenterY(100.0f);
		cercle.setRadius(50.0f);*/
		
		
	}

}
