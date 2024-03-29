/**
 * Cette classe est le main contrôleur de notre vue.
 */
package vue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controlleur.CommandeSuppressionLivraison;
import controlleur.Controleur;
import controlleur.CommandeAjoutLivraison;
import controlleur.CommandeSuppressionLivraison;
import controlleur.ListeDeCommandes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
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

	public static FeuilleDeRoute feuilleDeRoute;
	public static Boolean isPickUpAdded = false;
	public static Boolean isNoeudBeforePickUpAdded = false;

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
	//ppublic ObservableList<LivraisonDisplay> observable = FXCollections.observableArrayList();
	
	public Controleur controlleur = new Controleur(new DataContainer(),this);
	
	public File selectFileXML() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		File selectedFile = fc.showOpenDialog(null);

		return selectedFile;

	}

	public void chargerPlanAction(ActionEvent event) {
	/*	File selectedFile = selectFileXML();
		resetVue();
		if (selectedFile != null) {
			System.out.println(selectedFile.getName());

			try {
				Boolean success = dataContainer.chargerPlan(selectedFile.getAbsolutePath());
				if (!success) {
					console.setText("Echec du chargement du plan avec ce fichier ");
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			plan = dataContainer.GetPlan();

			paneMap.getChildren().clear();
			livraisonPane.getChildren().clear();
			tourneePane.getChildren().clear();
			VueUtils.initalisationDonnees(plan, paneMap);

			VueTroncon.drawTroncons(plan, paneMap);

			console.setText("Charger une demande de livraison. ");
			chargerDemandeButton.setDisable(false);

			undoButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					listeDeCommandes.undo();
				}
			});

			redoButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	listeDeCommandes.redo();
	            }
	        });
			
		} */

		controlleur.chargerPlan();
	}

	// Cache le pane pour ajouter un boutton
	private void cacherAjoutBouttonPane(Boolean value) {
		ajoutBouttonAnchorPane.setVisible(value);
		ajoutLivraisonBoutton.setVisible(value);
	}

	public void chargerDemandeLivraison(ActionEvent event) {
		File selectedFile = selectFileXML();
		if (selectedFile != null) {
			System.out.println(selectedFile.getName());

			try {
				Boolean success = dataContainer.chargerDemandeLivraison(selectedFile.getAbsolutePath());
				if (!success) {
					console.setText("Echec du chargement des livraisons avec ce fichier ");
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			demande = dataContainer.GetDemandeLivraison();

			livraisonPane.getChildren().clear();
			tourneePane.getChildren().clear();
			livraisonsVue.clear();
			livraisons = VueDemandeLivraison.drawDemandeLivraison(plan, demande, livraisonPane, livraisonsVue);

			initialiseListView();
			console.setText("Charger une tournee. ");
			calculerTourneeButton.setDisable(false);

		}

	}

	public void initialiseListView() {

		if (!listview.getItems().isEmpty()) {
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

			// recherche par id
			DropShadow b = new DropShadow();
			for (Node n : livraisons.getChildren()) {
				Shape s = (Shape) n;
				s.setEffect(null);
				if (n.getId() == l.getNoeud().GetIdNoeud()) {
					s.setEffect(b);
				}
			}
		});
	}

	public void chargerTournee(ActionEvent event) {
		undoButton.setVisible(true);
		redoButton.setVisible(true);
		tournee = new Tournee(demande.getEntrepotLivraison(), demande.getLivraisons(), plan);
		tourneePane.getChildren().clear();
		List<Noeud> listeTournee = tournee.calculTournee();
		VueTroncon.drawTournee(listeTournee, tourneePane);

		console.setText("Vous pouvez maintenant modifier la tournee ou generer une feuille de route. ");
		genererFeuilleRouteButton.setDisable(false);
		genererFeuilleRouteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					feuilleDeRoute = new FeuilleDeRoute(listeTournee, plan, tournee);
					// MainControlleur.feuilleDeRoute =
					// System.out.println(feuille.toString());
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/feuilleDeRoute.fxml"));
					Parent root = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.setScene(new Scene(root));
					stage.show();
				} catch (Exception e) {

				}

			}

		});

		reInitialiseListView(tournee.getenchainementNoeudAVisiterAvecInfos());

		ajoutLivraisonBoutton.setVisible(true);
		ajoutLivraisonBoutton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	livraisonPane.getChildren().clear();
	        		VueNoeud.drawClikableNoeud(plan, livraisonPane,MainControlleur.this);
	            	//VueNoeud.drawClikableNoeudOfTournee(tournee, livraisonPane, MainControlleur.this);
	        		//VueDemandeLivraison.drawDemandeLivraison(plan, demande, livraisonPane,livraisonsVue);
	        		livraisonPane.getChildren().add(livraisons);
	                ajoutBouttonAnchorPane.setVisible(true);
	                console.setText("Vous entrez en mode ajout de livraison : "
	                		+ "\n- Commencez par renseigner la dur�e de l'enlevement et de la livraison"
	                		+ "\n- Cliquer sur un noeud pour sp�cifier le lieu du pick-up "
	                		+ "\n- Cliquer sur un noeud pour sp�cifier le noeud avant le pick-up "
	                		+ "\n- Cliquer sur un noeud pour sp�cifier le noeud avant le delivery ");
	            }
	        });
		annulerAjoutBoutton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				isPickUpAdded = false;
				isNoeudBeforePickUpAdded = false;
				livraisonPane.getChildren().clear();
				VueDemandeLivraison.drawDemandeLivraison(plan, demande, livraisonPane, livraisonsVue);

				ajoutBouttonAnchorPane.setVisible(false);
				console.setText("Vous pouvez maintenant modifier la tourn�e ou g�n�rer une feuille de route. ");
			}
		});

		saveButtonAjoutLivraison.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!((estUnEntier(dureeEnlevementTextField.getText()))
						&& (estUnEntier(dureeLivraisonTextField.getText())))) {
					console.setText("La duree de l'enlevement et de livraison doivent �tre des entiers");
					return;
				} else if (noeudPickUp == null) {
					console.setText("Veuillez cliquer sur le noeud representant le lieu du pick-up");
					return;
				} else if (noeudBeforePickUp == null) {
					console.setText("Veuillez cliquer sur le noeud avant le pick-up");
					return;
				} else if (noeudDelivery == null) {
					console.setText("Veuillez cliquer sur le noeud representant le lieu du delivery");
					return;
				} else if (noeudBeforeDelivery == null) {
					console.setText("Veuillez cliquer sur le noeud avant le delivery");
					return;
				}

	private void reset() {
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

	private boolean estUnEntier(String chaine) {
		try {
			Integer.parseInt(chaine);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public void reInitialiseListView(List<Triplet<Noeud, Livraison, Boolean>> liste) {
		listview.getItems().clear();

		// List< Triplet<Noeud, Livraison, Boolean>> liste =
		// tournee.getenchainementNoeudAVisiterAvecInfos();

		ObservableList<LivraisonDisplay> observable = FXCollections.observableArrayList();

		List<LivraisonDisplay> temp = new ArrayList<LivraisonDisplay>();

		int i = 0;
		for (Triplet<Noeud, Livraison, Boolean> e : liste) {
			if (i != 0) {
				Color c = Color.WHITE;
				for (LivraisonDisplay l : livraisonsVue) {
					if (l.getNoeud().GetIdNoeud() == e.getFirst().GetIdNoeud()) {
						c = l.getColor();
					}
				}
				System.out.println(e.getFirst());
				LivraisonDisplay livraisonDisplay = new LivraisonDisplay(e.getFirst(), e.getThird(), c);
				temp.add(livraisonDisplay);
			}
			i++;
		}
		livraisonsVue.clear();

		for (LivraisonDisplay l : temp) {
			// MAJ de la liste qu'on utilise
			livraisonsVue.add(l);
			observable.add(l);
		}

		listview.setItems(observable);
		listview.setCellFactory(livraisonListView -> new LivraisonListViewCell());

	}

	public void supprimerLivraison(ActionEvent event) {
		if (livraisonsVue.size() > 2) {
			LivraisonDisplay l = (LivraisonDisplay) listview.getSelectionModel().getSelectedItem();
			Livraison liv = new Livraison(l.getNoeud(), l.getNoeud(), 0, 0);
			for (Livraison livraison : demande.getLivraisons()) {
				if (l.getIsPickup() && livraison.getNoeudEnlevement().GetIdNoeud() == l.getNoeud().GetIdNoeud()) {
					liv = livraison;
					break;
				} else if (!l.getIsPickup()
						&& livraison.getNoeudLivraison().GetIdNoeud() == l.getNoeud().GetIdNoeud()) {
					liv = livraison;
					break;
				}
			}

			CommandeSuppressionLivraison cde = new CommandeSuppressionLivraison(this, liv, tournee);

			VueDemandeLivraison.removeLivraisonTextuellement(l, livraisonsVue);

			initialiseListView();

			VueDemandeLivraison.removeLivraisonGraphiquement(livraisons, l.getColor());
			// VueTroncon.drawTournee(tournee.recalculTourneeApresSupression(),
			// tourneePane);

		} else {
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
