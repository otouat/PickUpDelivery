package vue;

import java.util.List;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import modele.Noeud;
import modele.Plan;
import modele.Troncon;

public class TronconIHM {
	
	static public double largeur_pane = 450;
	static public double longueur_pane = 450;
	static public double largeurPlanReel = 0;
	static public double longueurPlanReel = 0;
	static public double latMin = 0;
	static public double longMin = 0;
	
	public static void drawTroncons(Plan plan, BorderPane paneMap) {
		initalisationDonnees(plan,paneMap);
		
		
		
		List<Troncon> tronconList = plan.getTroncons();
		
		for(Troncon troncon : tronconList) {
			draw(troncon,paneMap);
		}
	}
	
	public static void draw(Troncon troncon, BorderPane paneMap) {
		int y1 = (int) -(getNewX(troncon.GetNoeudOrigine().GetLatitude()) - largeur_pane);
		int x1 = getNewY(troncon.GetNoeudOrigine().GetLongitude());
		
		int y2 = (int) -(getNewX(troncon.GetNoeudDestination().GetLatitude()) -longueur_pane);
		int x2 = getNewY(troncon.GetNoeudDestination().GetLongitude());
	
		
		Line line = new Line(x1, y1, x2, y2);
		paneMap.getChildren().add(line); 
	}
	
	public static int getNewX(double latitude) {
		
		return (int) (((latitude-latMin) * largeur_pane)/largeurPlanReel);
	}
	
public static int getNewY(double longitude) {
	return (int) (((longitude-longMin) * longueur_pane)/longueurPlanReel);
	}
	
	public static void initalisationDonnees(Plan plan, BorderPane paneMap) {
		largeurPlanReel = plan.CalculEcartLatitude();
		longueurPlanReel = plan.CalculEcartLongitude();
		latMin = plan.CalculMinLatitude();
		longMin = plan.CalculMinLongitude();
		
		largeur_pane = paneMap.getWidth();
		longueur_pane = paneMap.getHeight();
		
		
	}
	
}
