package vue;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
	public BorderPane paneMap;
	@FXML
	public AnchorPane tourneePane;
	@FXML
	public AnchorPane livraisonPane;
	@FXML
	public TextArea console;
	@FXML
	public ListView listview;

	@FXML
	public Button ajoutLivraisonBoutton;
	@FXML
	public AnchorPane ajoutBouttonAnchorPane;
	@FXML
	public TextField dureeEnlevementTextField;
	@FXML
	public TextField dureeLivraisonTextField;
	@FXML
	public Button saveButtonAjoutLivraison;
	@FXML
	public Button annulerAjoutBoutton;
	public static Noeud noeudPickUp;
	public static Noeud noeudBeforePickUp;
	public static Noeud noeudDelivery;
	public static Noeud noeudBeforeDelivery;
	public static Boolean isPickUpAdded = false;
	public static Boolean isNoeudBeforePickUpAdded = false;

	public static FeuilleDeRoute feuilleDeRoute;
	public DemandeLivraison demande;
	public Plan plan;
	public DataContainer dataContainer = new DataContainer();
	public Tournee tournee;

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
		}

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
			// VueNoeud.drawClikableNoeud(plan, livraisonPane);
			VueDemandeLivraison.drawDemandeLivraison(plan, demande, livraisonPane);

			initialiseListView();
			console.setText("Charger une tourn�. ");
			calculerTourneeButton.setDisable(false);

		}

	}

	public void initialiseListView() {
		ObservableList<LivraisonDisplay> observable = FXCollections.observableArrayList();

		List<Livraison> livraisonList = demande.getLivraisons();
		for (int i = 0; i < livraisonList.size(); i++) {
			LivraisonDisplay livraisonDisplay1 = new LivraisonDisplay(livraisonList.get(i), true,
					VueDemandeLivraison.couleurs.get(i));
			LivraisonDisplay livraisonDisplay2 = new LivraisonDisplay(livraisonList.get(i), false,
					VueDemandeLivraison.couleurs.get(i));
			observable.add(livraisonDisplay1);
			observable.add(livraisonDisplay2);
		}

		listview.setItems(observable);
		listview.setCellFactory(livraisonListView -> new LivraisonListViewCell());
	}

	public void chargerTournee(ActionEvent event) {

		// tournee = new Tournee();

		tournee = new Tournee(demande.getEntrepotLivraison(), demande.getLivraisons(), plan);
		tourneePane.getChildren().clear();
		List<Noeud> listeTournee = tournee.calculTournee();
		VueTroncon.drawTournee(listeTournee, tourneePane);

		console.setText("Vous pouvez maintenant modifier la tourn�e ou g�n�rer une feuille de route. ");
		genererFeuilleRouteButton.setDisable(false);
		genererFeuilleRouteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					feuilleDeRoute = new FeuilleDeRoute(listeTournee, plan, tournee);
					// MainControlleur.feuilleDeRoute =
					//System.out.println(feuille.toString());
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/feuilleDeRoute.fxml"));
					Parent root = (Parent) fxmlLoader.load();
					Stage stage = new Stage();
					stage.setScene(new Scene(root));
					stage.show();
				} catch (Exception e) {

				}

			}

		});

		reInitialiseListView();

		ajoutLivraisonBoutton.setVisible(true);
		ajoutLivraisonBoutton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				livraisonPane.getChildren().clear();
				VueNoeud.drawClikableNoeud(plan, livraisonPane, MainControlleur.this);
				// VueNoeud.drawClikableNoeudOfTournee(tournee, livraisonPane,
				// MainControlleur.this);
				VueDemandeLivraison.drawDemandeLivraison(plan, demande, livraisonPane);

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
				VueDemandeLivraison.drawDemandeLivraison(plan, demande, livraisonPane);

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

				Livraison new_livraison = new Livraison(noeudPickUp, noeudDelivery,
						Integer.valueOf(dureeEnlevementTextField.getText()),
						Integer.valueOf(dureeLivraisonTextField.getText()));
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
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public void reInitialiseListView() {
		listview.getItems().clear();

		HashMap<Integer, Triplet<Noeud, Livraison, Boolean>> hashMAp = tournee.getNoeudAVisiter();

		ObservableList<LivraisonDisplay> observable = FXCollections.observableArrayList();

		int i = 0;
		for (Entry<Integer, Triplet<Noeud, Livraison, Boolean>> e : hashMAp.entrySet()) {
			if (i != 0) {
				LivraisonDisplay livraisonDisplay = new LivraisonDisplay(e.getValue().getSecond(),
						e.getValue().getThird(), VueDemandeLivraison.couleurs.get(1));
				observable.add(livraisonDisplay);
			}
			i++;
		}

		listview.setItems(observable);
		listview.setCellFactory(livraisonListView -> new LivraisonListViewCell());

	}

}
