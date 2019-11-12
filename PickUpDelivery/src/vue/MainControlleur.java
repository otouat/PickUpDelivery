package vue;

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import modele.DataContainer;
import modele.DemandeLivraison;
import modele.Plan;
import modele.Tournee;
import modele.Triplet;
import modele.Livraison;
import modele.Noeud;

public class MainControlleur {
	@FXML
	public Button chargerPlanBoutton;
	@FXML
	public Button chargerDemandeButton;
	@FXML
	public Button calculerTourneeButton;
	@FXML
	public Button genererFeuilleRouteButton;
	@FXML
	public Button ajoutLivraisonBoutton;	
	@FXML
	public Button saveButtonAjoutLivraison;	
	@FXML
	public Button annulerAjoutBoutton;
	
	
	@FXML
	public BorderPane paneMap;
	@FXML 
	public AnchorPane tourneePane;
	@FXML 
	public AnchorPane livraisonPane;
	@FXML
	public AnchorPane ajoutBouttonAnchorPane;
	
	
	@FXML
	public TextArea console;
	@FXML
	public ListView listview;
	
	
	

	@FXML
	public TextField dureeEnlevementTextField;
	@FXML
	public TextField dureeLivraisonTextField;

	
	public static Noeud noeudPickUp;
	public static Noeud noeudBeforePickUp;
	public static Noeud noeudDelivery;
	public static Noeud noeudBeforeDelivery;
	public static Boolean isPickUpAdded=false;
	public static Boolean isNoeudBeforePickUpAdded=false;
	
	
	public DemandeLivraison demande;
	public Plan plan;
	public DataContainer dataContainer= new DataContainer() ;
	public Tournee tournee;
	public Group livraisons;
	public List<LivraisonDisplay> livraisonsVue = new ArrayList<LivraisonDisplay>();
	//public ObservableList<LivraisonDisplay> observable = FXCollections.observableArrayList();
	
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
			
			console.setText("Charger une demande de livraison. ");
			chargerDemandeButton.setDisable(false);
		}
		
	}
	
	//Cache le pane pour ajouter un boutton
	private void cacherAjoutBouttonPane(Boolean value) {
		ajoutBouttonAnchorPane.setVisible(value);
		ajoutLivraisonBoutton.setVisible(value);
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
		
		ObservableList<LivraisonDisplay> observable = FXCollections.observableArrayList();

		for (LivraisonDisplay l : livraisonsVue) {
			observable.add(l);	
		}
		listview.setItems(observable);
		listview.setCellFactory(livraisonListView -> new LivraisonListViewCell());
	
		listview.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			LivraisonDisplay l = (LivraisonDisplay) listview.getSelectionModel().getSelectedItem();
		           
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
		
		tournee = new Tournee(demande.getEntrepotLivraison(),demande.getLivraisons(),plan);
		tourneePane.getChildren().clear();
		VueTroncon.drawTournee(tournee.calculTournee(), tourneePane);
		
		console.setText("Vous pouvez maintenant modifier la tournee ou generer une feuille de route. ");
		genererFeuilleRouteButton.setDisable(false);
		
		reInitialiseListView();
		
		ajoutLivraisonBoutton.setVisible(true);
		ajoutLivraisonBoutton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	livraisonPane.getChildren().clear();
	        		VueNoeud.drawClikableNoeud(plan, livraisonPane,MainControlleur.this);
	            	//VueNoeud.drawClikableNoeudOfTournee(tournee, livraisonPane, MainControlleur.this);
	        		VueDemandeLivraison.drawDemandeLivraison(plan, demande, livraisonPane,livraisonsVue);
	        		
	                ajoutBouttonAnchorPane.setVisible(true);
	                console.setText("Vous entrez en mode ajout de livraison : "
	                		+ "\n- Commencez par renseigner la durée de l'enlevement et de la livraison"
	                		+ "\n- Cliquer sur un noeud pour spécifier le lieu du pick-up "
	                		+ "\n- Cliquer sur un noeud pour spécifier le noeud avant le pick-up "
	                		+ "\n- Cliquer sur un noeud pour spécifier le noeud avant le delivery ");
	            }
	        });
		annulerAjoutBoutton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	isPickUpAdded=false;
	            	isNoeudBeforePickUpAdded = false;
	            	livraisonPane.getChildren().clear();
	        		VueDemandeLivraison.drawDemandeLivraison(plan, demande, livraisonPane, livraisonsVue);
	        		
	                ajoutBouttonAnchorPane.setVisible(false);
	                console.setText("Vous pouvez maintenant modifier la tournée ou générer une feuille de route. ");
	            }
	        });
		
		saveButtonAjoutLivraison.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	if(!((estUnEntier(dureeEnlevementTextField.getText()))&& (estUnEntier(dureeLivraisonTextField.getText())))){
	            		console.setText("La duree de l'enlevement et de livraison doivent être des entiers");
	            		return;
	            	} else if(noeudPickUp == null){
	            		console.setText("Veuillez cliquer sur le noeud representant le lieu du pick-up");
	            		return;
	            	} else if(noeudBeforePickUp == null){
	            		console.setText("Veuillez cliquer sur le noeud avant le pick-up");
	            		return;
	            	} else if(noeudDelivery == null){
	            		console.setText("Veuillez cliquer sur le noeud representant le lieu du delivery");
	            		return;
	            	} else if(noeudBeforeDelivery == null){
	            		console.setText("Veuillez cliquer sur le noeud avant le delivery");
	            		return;
	            	}
	            	
	            	Livraison new_livraison = new Livraison(noeudPickUp,noeudDelivery,Integer.valueOf(dureeEnlevementTextField.getText()),Integer.valueOf(dureeLivraisonTextField.getText()));
	            	demande.AjouterLivraison(new_livraison);
	            	
	            	System.out.println(noeudBeforeDelivery);
	            	System.out.println(noeudBeforePickUp);
	            	System.out.println(noeudPickUp);
	            	System.out.println(noeudDelivery);
	            	
	            	// TODO : RECALCUL TOURNEE
	            	
	            }
	        });
	}
	
	private boolean estUnEntier(String chaine) {
		try {
			Integer.parseInt(chaine);
		} catch (NumberFormatException e){
			return false;
		}
 
		return true;
	}
	
	public void reInitialiseListView(){
		listview.getItems().clear();
		
		HashMap<Integer, Triplet<Noeud, Livraison, Boolean>> hashMAp = tournee.getNoeudAVisiter();

		ObservableList<LivraisonDisplay> observable = FXCollections.observableArrayList();
		
		List<LivraisonDisplay> temp = new ArrayList<LivraisonDisplay>();
		int i =0;
		for (Entry<Integer, Triplet<Noeud, Livraison, Boolean>> e : hashMAp.entrySet()) {
			if(i!=0) {
				Color c = Color.WHITE ;
				for(LivraisonDisplay l : livraisonsVue) {
					if(l.getNoeud().GetIdNoeud() == e.getValue().getFirst().GetIdNoeud() ) {
						c=l.getColor();
					}
				}
				LivraisonDisplay livraisonDisplay = new LivraisonDisplay( e.getValue().getFirst(), e.getValue().getThird(), c);
				temp.add(livraisonDisplay);
			}
			i++;
		}
		System.out.println("Size normale : "+livraisonsVue.size()+" Size temp : "+temp.size());
		
		livraisonsVue.clear();
		
		for(LivraisonDisplay l : temp) {
			//MAJ de la liste qu'on utilise
			livraisonsVue.add(l);
			observable.add(l);
		}
		
		listview.setItems(observable);
		listview.setCellFactory(livraisonListView -> new LivraisonListViewCell());
		
	}
	
	public void supprimerLivraison(ActionEvent event) {
		if(livraisonsVue.size()>2) {
		LivraisonDisplay l = (LivraisonDisplay) listview.getSelectionModel().getSelectedItem();
		
		VueDemandeLivraison.removeLivraisonTextuellement(l,livraisonsVue);
		
		initialiseListView();
		
		VueDemandeLivraison.removeLivraisonGraphiquement(livraisons, l.getColor());
		//VueTroncon.drawTournee(tournee.recalculTourneeApresSupression(), tourneePane);
	
		}else {
			console.setText("Vous ne pouvez pas supprimer toutes les livraisons. ");
		}
	
	}
	
	

}
