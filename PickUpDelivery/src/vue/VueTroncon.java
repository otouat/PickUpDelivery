package vue;

import java.util.List;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import modele.Noeud;
import modele.Plan;
import modele.Troncon;

public class VueTroncon {
	
	public static void drawTroncons(Plan plan, BorderPane paneMap) {
		
		//Verifier si le fichier est bon ?
		
		List<Troncon> tronconList = plan.getTroncons();
		for(Troncon troncon : tronconList) {
			draw(troncon,paneMap);
		}
		
	}
	
	public static void drawTournee(List<Noeud> noeuds, AnchorPane tourneePane) {
		Path path = new Path();
		System.out.println("Noeud erreur " + noeuds);
		path.getElements().add(new MoveTo(VueUtils.getNewX(noeuds.get(0).GetLongitude()),VueUtils.getNewY(noeuds.get(0).GetLatitude())));
		for(int i=0; i<noeuds.size()-1;i++) {
			if (!noeuds.get(i).equals(noeuds.get(i+1))) {
				drawTournee(noeuds.get(i),noeuds.get(i+1),tourneePane);
				path.getElements().add(new LineTo(VueUtils.getNewX(noeuds.get(i).GetLongitude()),VueUtils.getNewY(noeuds.get(i).GetLatitude())));		
			}
		}
		path.getElements().add(new LineTo(VueUtils.getNewX(noeuds.get(noeuds.size()-1).GetLongitude()),VueUtils.getNewY(noeuds.get(noeuds.size()-1).GetLatitude())));		
		
		Circle circuit = new Circle(3.5,Color.CRIMSON);
		
		PathTransition pathT = new PathTransition();
		double time = noeuds.size()*0.3 ;
		pathT.setDuration(Duration.seconds(time));
		pathT.setPath(path);
		pathT.setNode(circuit);
		pathT.setCycleCount(Timeline.INDEFINITE);
		pathT.play();

		tourneePane.getChildren().add(circuit);

		
	}
	
	
	public static void draw(Troncon troncon, BorderPane paneMap) {
		int x1 = VueUtils.getNewX(troncon.GetNoeudOrigine().GetLongitude());
		int y1 = VueUtils.getNewY(troncon.GetNoeudOrigine().GetLatitude());
		
		int x2 = VueUtils.getNewX(troncon.GetNoeudDestination().GetLongitude());
		int y2 = VueUtils.getNewY(troncon.GetNoeudDestination().GetLatitude());
		
		Line line = new Line(x1, y1, x2, y2);
		paneMap.getChildren().add(line); 
	}
	
	public static void drawTournee(Noeud n1, Noeud n2, AnchorPane tourneePane) {
		int x1 = VueUtils.getNewX(n1.GetLongitude());
		int y1 = VueUtils.getNewY(n1.GetLatitude());
		
		int x2 = VueUtils.getNewX(n2.GetLongitude());
		int y2 = VueUtils.getNewY(n2.GetLatitude());
		
		Line line = new Line(x1, y1, x2, y2);
		line.setStrokeWidth(2.5);
		line.setStroke(Color.CRIMSON);
		tourneePane.getChildren().add(line); 
	}
	
}
