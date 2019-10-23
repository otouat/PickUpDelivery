package vue;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import modele.DataContainer;
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
	
	public void chargerPlanAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		File selectedFile = fc.showOpenDialog(null);
		
		if (selectedFile != null) {
			System.out.println(selectedFile.getName());
			
			DataContainer dataContainer = new DataContainer();
			try {
				dataContainer.chargerPlan(selectedFile.getAbsolutePath());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Plan plan = dataContainer.GetPlan();
			TronconIHM.drawTroncons(plan, paneMap);
			console.setText("Charger une demande de livraison. ");
			chargerDemandeButton.setDisable(false);
			
			
			
		}
		
		
	
	}
}
