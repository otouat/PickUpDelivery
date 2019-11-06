package vue;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import modele.Noeud;
import modele.Plan;
import modele.Troncon;

public class VueTroncon {
	
	public static Group drawTroncons(Plan plan, BorderPane paneMap) {
		
		//Verifier si le fichier est bon ?
		Group lignesPlan = new Group();
		List<Troncon> tronconList = plan.getTroncons();
		paneMap.getChildren().clear();
		for(Troncon troncon : tronconList) {
			draw(troncon,lignesPlan);
		}
		//lignesPlan.getTransforms().add(scale);
		paneMap.getChildren().add(lignesPlan);
		return lignesPlan;
	}
	
	public static void draw(Troncon troncon, Group group) {
		int x1 = VueUtils.getNewX(troncon.GetNoeudOrigine().GetLongitude());
		int y1 = VueUtils.getNewY(troncon.GetNoeudOrigine().GetLatitude());
		
		int x2 = VueUtils.getNewX(troncon.GetNoeudDestination().GetLongitude());
		int y2 = VueUtils.getNewY(troncon.GetNoeudDestination().GetLatitude());
		
		Line line = new Line(x1, y1, x2, y2);
		group.getChildren().add(line); 
	}
	
	
	
}
