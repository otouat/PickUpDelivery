package vue;

import java.util.List;

import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import modele.Plan;
import modele.Troncon;

public class VueTroncon {
	
	static public double largeur_pane = 450;
	static public double longueur_pane = 450;
	static public double largeurPlanReel = 0;
	static public double longueurPlanReel = 0;
	static public double latMin = 0;
	static public double longMin = 0;
	
	public static void drawTroncons(Plan plan, BorderPane paneMap) {
		initalisationDonnees(plan,paneMap);
		
		//Verifier si le fichier est bon ?
		
		List<Troncon> tronconList = plan.getTroncons();
		paneMap.getChildren().clear();
		for(Troncon troncon : tronconList) {
			draw(troncon,paneMap);
		}
	}
	
	public static void draw(Troncon troncon, BorderPane paneMap) {
		int x1 = getNewX(troncon.GetNoeudOrigine().GetLongitude());
		int y1 = getNewY(troncon.GetNoeudOrigine().GetLatitude());
		
		int x2 = getNewX(troncon.GetNoeudDestination().GetLongitude());
		int y2 = getNewY(troncon.GetNoeudDestination().GetLatitude());
	
		Line line = new Line(x1, y1, x2, y2);
		paneMap.getChildren().add(line); 
	}
	
	public static int getNewY(double latitude) {
		
		return (int) -((((latitude-latMin) * largeur_pane)/largeurPlanReel)- largeur_pane) ;
	}
	
	public static int getNewX(double longitude) {
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
