package controlleur;
import java.io.File;

import javafx.stage.FileChooser;
import modele.DataContainer;
import modele.Plan;
import vue.MainControlleur;

public class EtatInit implements Etat {
	public File selectFileXML() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		File selectedFile = fc.showOpenDialog(null);
		
		return selectedFile;
		
	}
	 
	public void chargerPlan(Controleur c,MainControlleur fenetre,File selectedFile) {
		DataContainer dataContainer= new DataContainer();//change
		try {
			dataContainer.chargerPlan(selectedFile.getAbsolutePath());
		} catch (Exception e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		}
		// setEtatPlanCh
	}
}
