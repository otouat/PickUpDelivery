package vue;

import java.util.List;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import modele.Noeud;
import modele.Plan;
import modele.Troncon;

public class TronconIHM {
	
	static public final int LARGEUR_PANE = 450;
	static public final int LONGUEUR_PANE = 450;
	static public double largeurPlanReel = 0.0531659;
	static public double longueurPlanReel = 0.07610079;
	static public double latMin = 45.727353;
	static public double longMin = 4.8314376;
	
	public static void drawTroncons(Plan plan, BorderPane paneMap) {
		//initalise largeur longeur réel
		
		List<Troncon> tronconList = plan.getTroncons();
		
		for(Troncon troncon : tronconList) {
			draw(troncon,paneMap);
		}
	}
	
	public static void draw(Troncon troncon, BorderPane paneMap) {
		int x1 = getNewX(troncon.GetNoeudOrigine().GetLatitude());
		int y1 = getNewY(troncon.GetNoeudOrigine().GetLongitude());
		
		int x2 = getNewX(troncon.GetNoeudDestination().GetLatitude());
		int y2 = getNewY(troncon.GetNoeudDestination().GetLongitude());
	
		Line line = new Line(x1, y1, x2, y2);
		paneMap.getChildren().add(line); 
	}
	
	public static int getNewX(double latitude) {
		
		return (int) (((latitude-latMin) * LARGEUR_PANE)/largeurPlanReel);
	}
	
public static int getNewY(double longitude) {
	return (int) (((longitude-longMin) * LONGUEUR_PANE)/longueurPlanReel);
	}
	
	
}
