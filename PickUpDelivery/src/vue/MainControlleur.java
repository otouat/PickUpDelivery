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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import modele.DataContainer;
import modele.DemandeLivraison;
import modele.Plan;
import modele.Tournee;
import modele.Livraison;

public class MainControlleur {
	@FXML
	private Button chargerPlanBoutton;
	@FXML
	private Button chargerDemandeButton;
	@FXML
	private Button calculerTourneeButton;
	@FXML
	private Button genererFeuilleRouteButton;
	
	@FXML
	private BorderPane paneMap;
	@FXML 
	private AnchorPane tourneePane;
	@FXML 
	private AnchorPane livraisonPane;
	
	@FXML
	private TextArea console;
	
	@FXML
	private ListView listview;
	
	
	
	private DemandeLivraison demande;
	private Plan plan;
	private DataContainer dataContainer= new DataContainer() ;
	private Tournee tournee;
	
	
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
			
			paneMap.getChildren().clear();
			livraisonPane.getChildren().clear();
			tourneePane.getChildren().clear();
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
			
			livraisonPane.getChildren().clear();
			tourneePane.getChildren().clear();
			VueDemandeLivraison.drawDemandeLivraison(plan, demande, livraisonPane);
			
			initialiseListView();
			console.setText("Charger une tournï¿½e. ");
			calculerTourneeButton.setDisable(false);
			
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
	
	public void chargerTournee(ActionEvent event){
		
		tournee = new Tournee();
	
		tournee = new Tournee(demande.getEntrepotLivraison(),demande.getLivraisons(),plan);
		tourneePane.getChildren().clear();
		VueTroncon.drawTournee(tournee.calculTournee(), tourneePane);
		
		console.setText("Vous pouvez maintenant modifier la tournée ou générer une feuille de route. ");
		genererFeuilleRouteButton.setDisable(false);
	}

}
