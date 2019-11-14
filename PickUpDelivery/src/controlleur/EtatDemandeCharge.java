package controlleur;

import java.io.File;//
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modele.FeuilleDeRoute;
import modele.Livraison;
import modele.Noeud;
import modele.Tournee;
import vue.LivraisonDisplay;
import vue.LivraisonListViewCell;
import vue.MainControlleur;
import vue.VueDemandeLivraison;
import vue.VueNoeud;
import vue.VueTroncon;
import vue.VueUtils;

public class EtatDemandeCharge  extends EtatInit{
	
	@Override
	public void chargerPlan(Controleur c, MainControlleur f) {
		File selectedFile = selectFileXML();
		if (selectedFile != null) {
			
			try {
				c.getDataContainer().chargerPlan(selectedFile.getAbsolutePath());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			c.setPlan(c.getDataContainer().GetPlan());
			
			c.getFenetre().paneMap.getChildren().clear();
			c.getFenetre().livraisonPane.getChildren().clear();
			c.getFenetre().tourneePane.getChildren().clear();
			
			VueUtils.initalisationDonnees(c.getPlan(),c.getFenetre().paneMap);

			VueTroncon.drawTroncons(c.getPlan(), c.getFenetre().paneMap);
			c.getFenetre().console.setText("Charger une demande de livraison. ");
			c.getFenetre().chargerDemandeButton.setDisable(false);
			
			c.setEtatCourant(c.etatPlanCharge);
		}
	}

	@Override
	public void chargerDemandeLivraison(Controleur c, MainControlleur f) {
		File selectedFile = selectFileXML();
		if (selectedFile != null) {
			System.out.println(selectedFile.getName());

			try {
				Boolean success = f.dataContainer.chargerDemandeLivraison(selectedFile.getAbsolutePath());
				if (!success) {
					f.console.setText("Echec du chargement des livraisons avec ce fichier ");
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			f.demande = f.dataContainer.GetDemandeLivraison();

			f.livraisonPane.getChildren().clear();
			f.tourneePane.getChildren().clear();
			f.livraisonsVue.clear();
			f.livraisons = VueDemandeLivraison.drawDemandeLivraison(f.plan, f.demande, f.livraisonPane, f.livraisonsVue);
			
			f.initialiseListView();
			f.console.setText("Charger une tournee. ");
			f.calculerTourneeButton.setDisable(false);	
			
			c.setEtatCourant(c.etatDemandeCharge);
		}
	}
	
	@Override
	public void calculerTournee(Controleur c, MainControlleur f) {
		
		f.undoButton.setVisible(true);
		f.redoButton.setVisible(true);
		f.tournee = new Tournee(f.demande.getEntrepotLivraison(),f.demande.getLivraisons(),f.plan);
		f.tourneePane.getChildren().clear();
		List<Noeud> listeTournee=f.tournee.calculTournee();
		VueTroncon.drawTournee(listeTournee, f.tourneePane);
		
		f.console.setText("Vous pouvez maintenant modifier la tournee ou generer une feuille de route. ");
		f.genererFeuilleRouteButton.setDisable(false);
		f.genererFeuilleRouteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					f.feuilleDeRoute = new FeuilleDeRoute(listeTournee, f.plan, f.tournee);
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

		f.reInitialiseListView(f.tournee.getenchainementNoeudAVisiterAvecInfos());
		
		f.ajoutLivraisonBoutton.setVisible(true);
		f.supprimerBoutton.setVisible(true);
		f.modifierEmplacementNoeud.setVisible(true);
		f.ajoutLivraisonBoutton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	f.livraisonPane.getChildren().clear();
	        		VueNoeud.drawClikableNoeud(f.plan, f.livraisonPane,f);
	            	//VueNoeud.drawClikableNoeudOfTournee(tournee, livraisonPane, MainControlleur.this);
	        		//VueDemandeLivraison.drawDemandeLivraison(plan, demande, livraisonPane,livraisonsVue);
	        		f.livraisonPane.getChildren().add(f.livraisons);
	                f.ajoutBouttonAnchorPane.setVisible(true);
	                f.console.setText("Vous entrez en mode ajout de livraison : "
	                		+ "\n- Commencez par renseigner la dureee de l'enlevement et de la livraison"
	                		+ "\n- Cliquer sur un noeud pour specifier le lieu du pick-up "
	                		+ "\n- Cliquer sur un noeud pour specifier le noeud avant le pick-up "
	                		+ "\n- Cliquer sur un noeud pour specifier le noeud avant le delivery ");
	            }
	        });
		f.annulerAjoutBoutton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	f.reset();
	            }
	        });
		
		f.saveButtonAjoutLivraison.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	if(!((f.estUnEntier(f.dureeEnlevementTextField.getText()))&& (f.estUnEntier(f.dureeLivraisonTextField.getText())))){
	            		f.console.setText("La dur�e de l'enlevement et de livraison doivent etre des entiers");
	            		return;
	            	} else if(f.noeudPickUp == null){
	            		f.console.setText("Veuillez cliquer sur le noeud representant le lieu du pick-up");
	            		return;
	            	} else if(f.noeudBeforePickUp == null){
	            		f.console.setText("Veuillez cliquer sur le noeud avant le pick-up");
	            		return;
	            	} else if(f.noeudDelivery == null){
	            		f.console.setText("Veuillez cliquer sur le noeud representant le lieu du delivery");
	            		return;
	            	} else if(f.noeudBeforeDelivery == null){
	            		f.console.setText("Veuillez cliquer sur le noeud avant le delivery");
	            		return;
	            	}
	            	
	            	Livraison new_livraison = new Livraison(f.noeudPickUp,f.noeudDelivery,Integer.valueOf(f.dureeEnlevementTextField.getText()),Integer.valueOf(f.dureeLivraisonTextField.getText()));
	            	//demande.AjouterLivraison(new_livraison);
	            	
	            	System.out.println(f.noeudBeforeDelivery);
	            	System.out.println(f.noeudBeforePickUp);
	            	System.out.println(f.noeudPickUp);
	            	System.out.println(f.noeudDelivery);
	            	
	            	// TODO : RECALCUL TOURNEE
	            	CommandeAjoutLivraison commande = new CommandeAjoutLivraison(f,f.noeudBeforePickUp,f.noeudBeforeDelivery,new_livraison,f.tournee);
	            	f.listeDeCommandes.ajoute(commande);
	            	
	            	
	            	f.reset();
	            }
	        });
		
		c.setEtatCourant(c.etatTourneeModifiee);
		
	}

	public void remplirObservable(List<LivraisonDisplay> livraisonsVue, ObservableList<LivraisonDisplay> observable) {
		for(LivraisonDisplay l : livraisonsVue) {
			observable.add(l);
		}
	}
	
	
	public File selectFileXML() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		File selectedFile = fc.showOpenDialog(null);

		return selectedFile;

	}

	@Override
	public void ajouterLivraison(Controleur controleur, MainControlleur fenetre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void supprimerLivraison(Controleur controleur, MainControlleur fenetre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifierOrdreLivraison(Controleur controleur, MainControlleur fenetre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifierNoeudLivraison(Controleur controleur, MainControlleur fenetre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifierTournee(Controleur controleur, MainControlleur fenetre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validerTournee(Controleur controleur, MainControlleur fenetre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void consulterTournee(Controleur controleur, MainControlleur fenetre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void genererFeuilleDeRoute(Controleur c, MainControlleur f) {
		// TODO Auto-generated method stub
		
	}
	
	
}
