package vue;

import java.io.File;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import modele.DataContainer;
import modele.DemandeLivraison;
import modele.Plan;
import modele.Livraison;

public class MainControlleur {
	@FXML
	private Button chargerPlanBoutton;
	@FXML
	private Button chargerDemandeButton;
	
	@FXML
	private BorderPane paneMap;
	
	@FXML
	private TextArea console;
	
	@FXML
	private ListView listview;
	
	
	
	private DemandeLivraison demande;
	private Plan plan;
	private DataContainer dataContainer= new DataContainer() ;
	
	
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

			VueTroncon.drawTroncons(plan, paneMap);
			VueNoeud.drawClikableNoeud(plan, paneMap);
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
			VueDemandeLivraison.drawDemandeLivraison(plan, demande, paneMap);
			
			initialiseListView();
			//console.setText("Charger une demande de livraison. ");
			//chargerDemandeButton.setDisable(false);
			
		}
		
	}
	
	
	public void initialiseListView(){
		ObservableList<Livraison> observable = FXCollections.observableArrayList();
		
		observable.addAll(
                demande.getLivraisons()
        );
		
		listview.setItems(observable);
		listview.setCellFactory(livraisonListView -> new LivraisonListViewCell());
	}

}
