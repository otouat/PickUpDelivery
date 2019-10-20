package controlleur;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class MainControlleur {
	@FXML
	private Button chargerPlanBoutton;
	
	public void chargerPlanAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		File selectedFiles = fc.showOpenDialog(null);
		
		if (selectedFiles != null) {
			System.out.println(selectedFiles.getName());
		}
	
		
	}
}
