package vue;

import javafx.scene.layout.BorderPane;
import modele.Plan;

public class VueUtils {

	static public double largeur_pane = 450;
	static public double longueur_pane = 450;
	static public double largeurPlanReel = 0;
	static public double longueurPlanReel = 0;
	static public double latMin = 0;
	static public double longMin = 0;
	
	public static void initalisationDonnees(Plan plan, BorderPane paneMap) {
		largeurPlanReel = plan.CalculEcartLatitude();
		longueurPlanReel = plan.CalculEcartLongitude();
		latMin = plan.CalculMinLatitude();
		longMin = plan.CalculMinLongitude();
		
		largeur_pane = paneMap.getWidth();
		longueur_pane = paneMap.getHeight();
		
	}
	
	public static int getNewY(double latitude) {
		return (int) -((((latitude-latMin) * largeur_pane)/largeurPlanReel)- largeur_pane) ;
	}
	
	public static int getNewX(double longitude) {
		return (int) (((longitude-longMin) * longueur_pane)/longueurPlanReel);
	}
	
}
