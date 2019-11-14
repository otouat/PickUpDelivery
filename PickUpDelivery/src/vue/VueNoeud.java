package vue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import modele.Livraison;
import modele.Noeud;
import modele.Plan;
import modele.Tournee;
import modele.Triplet;

public class VueNoeud {
	public static void drawClikableNoeud(Plan plan, AnchorPane paneMap, MainControlleur mainControlleur) {
		Map<String,Noeud> mapNoeud = plan.getNoeuds();
		
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
					if(!MainControlleur.isPickUpAdded) {
						MainControlleur.noeudPickUp = noeudMap;
						
						mainControlleur.console.setText("Renseigner le noeud avant le pick-up");
						
						mainControlleur.livraisonPane.getChildren().clear();
						VueNoeud.drawClikableNoeudOfTournee(mainControlleur.tournee, mainControlleur.livraisonPane, mainControlleur);
						mainControlleur.livraisonPane.getChildren().add(mainControlleur.livraisons);
						//VueDemandeLivraison.drawDemandeLivraison(plan, mainControlleur.demande, mainControlleur.livraisonPane,mainControlleur.livraisonsVue);

		        		
		        		MainControlleur.isPickUpAdded = true;
						
					}else {
						MainControlleur.noeudDelivery = noeudMap;
						
						mainControlleur.console.setText("Renseigner le noeud avant le delivery");
						
						mainControlleur.livraisonPane.getChildren().clear();
		        		VueNoeud.drawClikableNoeudOfTournee(mainControlleur.tournee, mainControlleur.livraisonPane, mainControlleur);
		        		mainControlleur.livraisonPane.getChildren().add(mainControlleur.livraisons);
		        		//VueDemandeLivraison.drawDemandeLivraison(plan, mainControlleur.demande, mainControlleur.livraisonPane,mainControlleur.livraisonsVue);
					}
				
		        });
				
				circle.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
		            System.out.println("noeud over");
		            paneMap.getScene().setCursor(Cursor.CROSSHAIR);
		            
		        });
				
				circle.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
		            System.out.println("noeud exited");
		            paneMap.getScene().setCursor(Cursor.DEFAULT);
		            
		        });
				
				paneMap.getChildren().add(circle); 
			}
		

	}
	
	public static void drawClikableNoeudOfTournee(Tournee tournee, AnchorPane paneMap,MainControlleur mainControlleur) {
		HashMap<Integer, Triplet<Noeud, Livraison, Boolean>> noeuds = tournee.getNoeudAVisiter();

		for (Entry<Integer, Triplet<Noeud, Livraison, Boolean>> e : noeuds.entrySet()) {
			Noeud noeud =  e.getValue().getFirst();
			
			  Circle circle = new Circle();
				circle.setCenterX(VueUtils.getNewX(noeud.GetLongitude()));
				circle.setCenterY(VueUtils.getNewY(noeud.GetLatitude()));
				circle.setId(noeud.GetIdNoeud());
				circle.setRadius(15.5f);
				
				circle.addEventHandler(MouseEvent.MOUSE_CLICKED, f -> {
					if(!MainControlleur.isNoeudBeforePickUpAdded) {
						MainControlleur.noeudBeforePickUp = noeud;
						
						mainControlleur.console.setText("Renseigner le noeud correspondant au delivery");
						
						mainControlleur.livraisonPane.getChildren().clear();
						
		        		VueNoeud.drawClikableNoeud(mainControlleur.plan, mainControlleur.livraisonPane, mainControlleur);
		        		mainControlleur.livraisonPane.getChildren().add(mainControlleur.livraisons);
		        		//VueDemandeLivraison.drawDemandeLivraison(mainControlleur.plan, mainControlleur.demande, mainControlleur.livraisonPane,mainControlleur.livraisonsVue);
		        		MainControlleur.isNoeudBeforePickUpAdded = true;
						
					}else {
						MainControlleur.noeudBeforeDelivery = noeud;
						mainControlleur.console.setText("Appuyez maintenant sur enregistrer pour ajouter la livraison");
						
						mainControlleur.livraisonPane.getChildren().clear();
						mainControlleur.livraisonPane.getChildren().add(mainControlleur.livraisons);
						//VueDemandeLivraison.drawDemandeLivraison(mainControlleur.plan, mainControlleur.demande, mainControlleur.livraisonPane,mainControlleur.livraisonsVue);
					}
				
		        });
				
				circle.addEventHandler(MouseEvent.MOUSE_ENTERED, f -> {
		            System.out.println("noeud over");
		            paneMap.getScene().setCursor(Cursor.CROSSHAIR);
		            
		        });
				
				circle.addEventHandler(MouseEvent.MOUSE_EXITED, f -> {
		            System.out.println("noeud exited");
		            paneMap.getScene().setCursor(Cursor.DEFAULT);
		            
		        });
				
				paneMap.getChildren().add(circle); 
		}
		
	}
	
	
	public static void drawClikableNoeudForModificationEmplacementNoeud(Plan plan, AnchorPane paneMap, MainControlleur mainControlleur) {
		Map<String,Noeud> mapNoeud = plan.getNoeuds();
		
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
					
						MainControlleur.newNoeudEmplacement = noeudMap;
						
						mainControlleur.console.setText("Le nouvel emplacement du noeud � bien �t� pris en compte");
						
						mainControlleur.modifierEmplacementNoeud();
						
						
						VueNoeud.drawClikableNoeudOfTournee(mainControlleur.tournee, mainControlleur.livraisonPane, mainControlleur);
						mainControlleur.livraisonPane.getChildren().clear();
						mainControlleur.livraisonPane.getChildren().add(mainControlleur.livraisons);
						//VueDemandeLivraison.drawDemandeLivraison(plan, mainControlleur.demande, mainControlleur.livraisonPane,mainControlleur.livraisonsVue);

		        });
				
				circle.addEventHandler(MouseEvent.MOUSE_ENTERED, f -> {
		            System.out.println("noeud over");
		            paneMap.getScene().setCursor(Cursor.CROSSHAIR);
		            
		        });
				
				circle.addEventHandler(MouseEvent.MOUSE_EXITED, f -> {
		            System.out.println("noeud exited");
		            paneMap.getScene().setCursor(Cursor.DEFAULT);
		            
		        });
				
				
				paneMap.getChildren().add(circle); 
			}
		

	}
	
}
