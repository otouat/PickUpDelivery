package vue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
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
	private Button supprimer;
	
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
	private Group livraisons;
	private List<LivraisonDisplay> livraisonsVue = new ArrayList<LivraisonDisplay>();
	private ObservableList<LivraisonDisplay> observable = FXCollections.observableArrayList();
	
	
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
			livraisons = VueDemandeLivraison.drawDemandeLivraison(plan, demande, livraisonPane, livraisonsVue);
			
			initialiseListView();
			console.setText("Charger une tournee. ");
			calculerTourneeButton.setDisable(false);
			
		}
		
	}
	
	
	public void initialiseListView(){
		
		if (!listview.getItems().isEmpty()){
			listview.getItems().clear();
		}

		
		
		for (LivraisonDisplay l : livraisonsVue) {
			observable.add(l);	
		}
		System.out.println(observable.size());
		
		listview.setItems(observable);
	/*	for (LivraisonDisplay l : livraisonsVue) {
			listview.getItems().add(l);	
		}*/
		listview.setCellFactory(livraisonListView -> new LivraisonListViewCell());
		
		listview.refresh();
		listview.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			LivraisonDisplay l = (LivraisonDisplay) listview.getSelectionModel().getSelectedItem();		
		           // System.out.println(l.getNoeud().GetIdNoeud());
		           //recherche par id
		            DropShadow b  = new DropShadow();	                
		            for (Node n : livraisons.getChildren()) {
		            	Shape s = (Shape)n;
		            	s.setEffect(null);
		            	if(n.getId()==l.getNoeud().GetIdNoeud()) {
					        s.setEffect(b);
		            	}
		            }
		        });
	}
	
	public void chargerTournee(ActionEvent event){
		
		tournee = new Tournee();
		
		tournee = new Tournee(demande.getEntrepotLivraison(),demande.getLivraisons(),plan);
		tourneePane.getChildren().clear();
		VueTroncon.drawTournee(tournee.calculTournee(), tourneePane);
		
		console.setText("Vous pouvez maintenant modifier la tournée ou générer une feuille de route. ");
		genererFeuilleRouteButton.setDisable(false);
	}
	
	public void supprimerLivraison(ActionEvent event) {
		LivraisonDisplay l = (LivraisonDisplay) listview.getSelectionModel().getSelectedItem();
		LivraisonDisplay PickASupprimer = l ;
		LivraisonDisplay DeliveryASupprimer = l;
		System.out.println(livraisonsVue.size());
		for(LivraisonDisplay lD : livraisonsVue ) {
			if (lD.getColor()== l.getColor() && lD.getIsPickup()) {
				PickASupprimer= lD;
			}else if (lD.getColor()== l.getColor() && !lD.getIsPickup()) {
				DeliveryASupprimer= lD;
			}
		}
		
		//suppression vue textuelle
		livraisonsVue.remove(PickASupprimer);
		livraisonsVue.remove(DeliveryASupprimer);
		for(LivraisonDisplay test : livraisonsVue) {
			System.out.println(test.getColor());
		}
		
		observable.remove(PickASupprimer);
		//listview.getItems().clear();
		initialiseListView();
		
		
		System.out.println(livraisonsVue.size());
		
		VueDemandeLivraison.removeLivraison(livraisons, l.getColor());
	
	}
	
	

}
