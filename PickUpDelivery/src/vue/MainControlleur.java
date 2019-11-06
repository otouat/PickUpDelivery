package vue;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.BorderPane;
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

			lignesPlan=VueTroncon.drawTroncons(plan, paneMap);
			noeuds= VueNoeud.drawClikableNoeud(plan, paneMap);
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
		lignesPlan.getTransforms().add(zoom.createInverse());
		lignesPlan.getTransforms().add(new Translate(54,54));
		if (noeuds!=null) {
			noeuds.getTransforms().add(zoom.createInverse());
			noeuds.getTransforms().add(new Translate(54,54));
		}
		if(livraisons!=null) {
			livraisons.getTransforms().add(zoom.createInverse());
			livraisons.getTransforms().add(new Translate(54,54));
		}
	}
	
}
