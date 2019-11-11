package vue;


import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import modele.DataContainer;
import modele.DemandeLivraison;
import modele.Plan;

public class MainControlleur {
	@FXML
	private Button chargerPlanBoutton;
	@FXML
	private Button chargerDemandeButton;
	
	@FXML
	private BorderPane paneMap;
	
	@FXML
	private TextArea console;
	
	
	
	private DemandeLivraison demande;
	private Plan plan;
	private final Scale zoom = new Scale(1.2,1.2);
	private final Translate translate = new Translate(-45,-45); 
	private DataContainer dataContainer= new DataContainer() ;
	
	private Group lignesPlan;
	private Group noeuds;
	private Group livraisons;
	private double x;
	private double y;
	
	
	public MainControlleur() {
	}
	
	public File selectFileXML() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		File selectedFile = fc.showOpenDialog(null);
		
		return selectedFile;
		
	}
	
	public void chargerPlanAction(ActionEvent event) {
		File selectedFile = selectFileXML();
		if (selectedFile != null) {
			System.out.println(selectedFile.getName());
			
			try {
				dataContainer.chargerPlan(selectedFile.getAbsolutePath());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			plan = dataContainer.GetPlan();
			VueUtils.initalisationDonnees(plan,paneMap);
			paneMap.setClip(new Rectangle (450,450));
			lignesPlan=VueTroncon.drawTroncons(plan, paneMap);
			noeuds= VueNoeud.drawClikableNoeud(plan, paneMap);
			
			paneMap.setOnMousePressed(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
			    x = lignesPlan.getLayoutX() - mouseEvent.getSceneX();
			    y = lignesPlan.getLayoutY() - mouseEvent.getSceneY();
			  }
			});
			paneMap.setOnMouseDragged(new EventHandler<MouseEvent>() {
				  @Override public void handle(MouseEvent mouseEvent) {
				    lignesPlan.setLayoutX(x+mouseEvent.getSceneX());
				    System.out.println(lignesPlan.getLayoutX());
				    noeuds.setLayoutX(x+mouseEvent.getSceneX());
				    lignesPlan.setLayoutY(y+mouseEvent.getSceneY());
				    noeuds.setLayoutY(y+mouseEvent.getSceneY());
				  }
				});
			console.setText("Charger une demande de livraison. ");
			chargerDemandeButton.setDisable(false);
		}
		
	}
	
	public void chargerDemandeLivraison(ActionEvent event) {
		File selectedFile = selectFileXML();
		if (selectedFile != null) {
			System.out.println(selectedFile.getName());
			
			try {
				dataContainer.chargerDemandeLivraison(selectedFile.getAbsolutePath());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			demande = dataContainer.GetDemandeLivraison();
			lignesPlan.getTransforms().clear();
			noeuds.getTransforms().clear();
			livraisons = VueDemandeLivraison.drawDemandeLivraison(plan, demande, paneMap);
			//console.setText("Charger une demande de livraison. ");
			//chargerDemandeButton.setDisable(false);
			
		}
	}
	
	public void Zoomer (ActionEvent event) {
		
		lignesPlan.getTransforms().add(zoom);
		lignesPlan.getTransforms().add(translate);
		if (noeuds!=null) {
			noeuds.getTransforms().add(zoom);
			noeuds.getTransforms().add(translate);
		}
		if(livraisons!=null) {
			livraisons.getTransforms().add(zoom);
			livraisons.getTransforms().add(translate);
		}
		
	}
	
	public void DeZoomer (ActionEvent event) throws NonInvertibleTransformException {	
		if (!lignesPlan.getTransforms().isEmpty()) {
			lignesPlan.getTransforms().remove(lignesPlan.getTransforms().size()-1);
			lignesPlan.getTransforms().remove(lignesPlan.getTransforms().size()-1);
			if (noeuds!=null) {
				noeuds.getTransforms().remove(noeuds.getTransforms().size()-1);
				noeuds.getTransforms().remove(noeuds.getTransforms().size()-1);
			}
			if(livraisons!=null) {
				livraisons.getTransforms().remove(livraisons.getTransforms().size()-1);
				livraisons.getTransforms().remove(livraisons.getTransforms().size()-1);
			}
		}
		
	}
	
}
