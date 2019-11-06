package vue;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import modele.Noeud;
import modele.Plan;

public class VueNoeud {
	public static Group drawClikableNoeud(Plan plan, BorderPane paneMap) {
		Map<String,Noeud> mapNoeud = plan.getNoeuds();
		Group noeuds =new Group();
		Set cles = mapNoeud.keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()){
			   Object cle = it.next();
			   Noeud noeudMap = mapNoeud.get(cle);
			   
			   Circle circle = new Circle();
				circle.setCenterX(VueUtils.getNewX(noeudMap.GetLongitude()));
				circle.setCenterY(VueUtils.getNewY(noeudMap.GetLatitude()));
				circle.setId(noeudMap.GetIdNoeud());
				circle.setRadius(1.5f);
				
				circle.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
		            System.out.println("clique noeud");
		        });
				
				circle.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
		            System.out.println("noeud over");
		            paneMap.getScene().setCursor(Cursor.CROSSHAIR);
		            
		        });
				
				circle.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
		            System.out.println("noeud exited");
		            paneMap.getScene().setCursor(Cursor.DEFAULT);
		            
		        });
				
				noeuds.getChildren().add(circle); 
			}
		
		paneMap.getChildren().add(noeuds); 
		return noeuds;

	}
}
