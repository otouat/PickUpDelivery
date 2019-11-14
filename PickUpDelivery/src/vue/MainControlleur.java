package vue;

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import controlleur.CommandeSuppressionLivraison;
import controlleur.Controleur;
import controlleur.CommandeAjoutLivraison;
import controlleur.CommandeModifierNoeudLivraison;
import controlleur.ListeDeCommandes;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import modele.DataContainer;
import modele.DemandeLivraison;
import modele.FeuilleDeRoute;
import modele.Livraison;
import modele.Noeud;
import modele.Plan;
import modele.Tournee;
import modele.Triplet;

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
	public Button supprimerBoutton;
	@FXML
	public Button modifierEmplacementNoeud;
	

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
	
	//utiliser pour modif emplacement noeud
	public static Noeud newNoeudEmplacement;


	public static FeuilleDeRoute feuilleDeRoute;
	public static Boolean isPickUpAdded=false;
	public static Boolean isNoeudBeforePickUpAdded=false;
	
	@FXML
	public Button undoButton;
	@FXML
	public Button redoButton;
	public ListeDeCommandes listeDeCommandes = new ListeDeCommandes();
	
	
	
	public DemandeLivraison demande;
	public Plan plan;
	public DataContainer dataContainer = new DataContainer();
	public Tournee tournee;
	public Group livraisons;
	public List<LivraisonDisplay> livraisonsVue = new ArrayList<LivraisonDisplay>();
	
	
	public Controleur controlleur = new Controleur(new DataContainer(),this);
	
	public File selectFileXML() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		File selectedFile = fc.showOpenDialog(null);

		return selectedFile;

	}
	
	public void resetVue() {
		listview.getItems().clear();
	}
	
	public void chargerPlanAction(ActionEvent event) {
		controlleur.chargerPlan();
	}

	// Cache le pane pour ajouter un boutton
	private void cacherAjoutBouttonPane(Boolean value) {
		ajoutBouttonAnchorPane.setVisible(value);
		ajoutLivraisonBoutton.setVisible(value);
	}

	public void chargerDemandeLivraison(ActionEvent event) {
		controlleur.chargerDemandeLivraison();
	}
	
	public void initialiseListView(){
		
		if (!listview.getItems().isEmpty()){
			listview.getItems().clear();
		}
		
		ObservableList<LivraisonDisplay> observable = FXCollections.observableArrayList();

		remplirObservable(livraisonsVue,observable);
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
		
		controlleur.calculerTournee();
	}

	public void reset() {
		isPickUpAdded=false;
    	isNoeudBeforePickUpAdded = false;
    	noeudBeforeDelivery = null;
    	noeudBeforePickUp = null;
    	livraisonPane.getChildren().clear();
		//VueDemandeLivraison.drawDemandeLivraison(plan, demande, livraisonPane, livraisonsVue);
		livraisonPane.getChildren().add(livraisons);
        ajoutBouttonAnchorPane.setVisible(false);
        console.setText("Vous pouvez maintenant modifier la tourn�e ou generer une feuille de route. ");
	}
	public boolean estUnEntier(String chaine) {
		try {
			Integer.parseInt(chaine);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public void reInitialiseListView(	List< Triplet<Noeud, Livraison, Boolean>> liste ) {
		listview.getItems().clear();
		
		ObservableList<LivraisonDisplay> observable = FXCollections.observableArrayList();
		
		List<LivraisonDisplay> temp = new ArrayList<LivraisonDisplay>();
		
		int i =0;
		for( Triplet<Noeud, Livraison, Boolean> e : liste) {
			if(i!=0) {
				Color c = Color.WHITE ;
				for(LivraisonDisplay l : livraisonsVue) {
					if(l.getNoeud().GetIdNoeud() == e.getFirst().GetIdNoeud() ) {
						c=l.getColor();
						break;
					}
				}
				System.out.println(e.getFirst());
				LivraisonDisplay livraisonDisplay = new LivraisonDisplay( e.getFirst(), e.getThird(), c);
				temp.add(livraisonDisplay);
			}
			i++;
		}
		
		setLivraisonsVue(temp);
		remplirObservable(livraisonsVue, observable);

		
		listview.setItems(observable);
		listview.setCellFactory(livraisonListView -> new LivraisonListViewCell());

	}
	
	public void supprimerLivraison(ActionEvent event) {
		//S'il reste plus d'une livraison
		if(livraisonsVue.size()>2) {
		LivraisonDisplay l = (LivraisonDisplay) listview.getSelectionModel().getSelectedItem();
		
		Livraison liv = new Livraison(l.getNoeud(),l.getNoeud(),0,0);
		for (Livraison livraison : demande.getLivraisons()) {
			if(l.getIsPickup() && livraison.getNoeudEnlevement().GetIdNoeud()==l.getNoeud().GetIdNoeud()) {
				liv = livraison;
				break;
			}else if (!l.getIsPickup() && livraison.getNoeudLivraison().GetIdNoeud()==l.getNoeud().GetIdNoeud()) {
				liv = livraison;
				break;
			}
		}
		/*CommandeSuppressionLivraison cde = new CommandeSuppressionLivraison(this, liv, l,tournee) ;
		listeDeCommandes.ajoute(cde);*/
		
		}else {
			console.setText("Vous ne pouvez pas supprimer toutes les livraisons. ");
		}
	
	}
	
	public void modifierEmplacementNoeudEvent(ActionEvent event) {
		LivraisonDisplay l = (LivraisonDisplay) listview.getSelectionModel().getSelectedItem();
		if(l==null) {
			console.setText("Commencez par s�lectionner un pick-up ou un delivery dans la liste de droite. ");
			return;
		}
		if(livraisonsVue.size()>2) {
			VueNoeud.drawClikableNoeudForModificationEmplacementNoeud(plan, livraisonPane, this);
			
		}
	
	}
	
	public void modifierEmplacementNoeud() {
		LivraisonDisplay l = (LivraisonDisplay) listview.getSelectionModel().getSelectedItem();
		Noeud ancienNoeud = l.getNoeud();
		Integer ordre = livraisonsVue.size();
		
		CommandeModifierNoeudLivraison cde = new CommandeModifierNoeudLivraison(tournee,newNoeudEmplacement,ancienNoeud,ordre,this) ;
		listeDeCommandes.ajoute(cde);
	}
	
	public List<LivraisonDisplay> setLivraisonsVue(List<LivraisonDisplay> temp){
		livraisonsVue.clear();
		for(LivraisonDisplay l : temp) {
			livraisonsVue.add(l);
		}
		return livraisonsVue;
	}
	
	public void remplirObservable(List<LivraisonDisplay> livraisonsVue, ObservableList<LivraisonDisplay> observable) {
		for(LivraisonDisplay l : livraisonsVue) {
			observable.add(l);
		}
	}
	

}
