package controlleur;

import java.io.File;//

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import vue.LivraisonDisplay;
import vue.LivraisonListViewCell;
import vue.MainControlleur;
import vue.VueDemandeLivraison;
import vue.VueNoeud;
import vue.VueTroncon;
import vue.VueUtils;

public class EtatPlanCharge extends EtatInit {
	
	private File selectFileXML() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		File selectedFile = fc.showOpenDialog(null);
		
		return selectedFile;
		
	}
	@Override
	public void chargerPlan(Controleur c, MainControlleur f) {
		File selectedFile = selectFileXML();
		f.resetVue();
		if (selectedFile != null) {
			
			System.out.println(selectedFile.getName());

			try {
				Boolean success = f.dataContainer.chargerPlan(selectedFile.getAbsolutePath());
				if (!success) {
					f.console.setText("Echec du chargement du plan avec ce fichier ");
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			f.plan = f.dataContainer.GetPlan();

			f.paneMap.getChildren().clear();
			f.livraisonPane.getChildren().clear();
			f.tourneePane.getChildren().clear();
			VueUtils.initalisationDonnees(f.plan, f.paneMap);

			VueTroncon.drawTroncons(f.plan, f.paneMap);

			f.console.setText("Charger une demande de livraison. ");
			f.chargerDemandeButton.setDisable(false);
			
			f.undoButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	f.listeDeCommandes.undo();
	            }
	        });
			
			f.redoButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	f.listeDeCommandes.redo();
	            }
	        });
		}
		}
		
	
	
	
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
	
	private void initialiseListView(Controleur c, MainControlleur f){
		ObservableList<LivraisonDisplay> observable = FXCollections.observableArrayList();
		
		/*List<Livraison> livraisonList = c.getDemandeLivraison().getLivraisons();
		for(int i=0;i<livraisonList.size();i++) {
			LivraisonDisplay livraisonDisplay1 = new LivraisonDisplay(livraisonList.get(i), true, VueDemandeLivraison.couleurs.get(i));
			LivraisonDisplay livraisonDisplay2 = new LivraisonDisplay(livraisonList.get(i), false, VueDemandeLivraison.couleurs.get(i));
			observable.add(livraisonDisplay1);
			observable.add(livraisonDisplay2);
		}*/

		
		c.getFenetre().listview.setItems(observable);
		c.getFenetre().listview.setCellFactory(livraisonListView -> new LivraisonListViewCell());
	}
	
	
	
}
