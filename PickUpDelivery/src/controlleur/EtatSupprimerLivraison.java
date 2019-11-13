package controlleur; //

import vue.MainControlleur;
import vue.MainControlleur;
import java.io.File;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import modele.Livraison;
import vue.LivraisonDisplay;
import vue.LivraisonListViewCell;
import vue.MainControlleur;
import vue.VueDemandeLivraison;
import vue.VueTroncon;
import vue.VueUtils;


public class EtatSupprimerLivraison extends EtatInit {
	
	@Override
	public void supprimerLivraison(Controleur c, MainControlleur f) {
		if(c.getFenetre().livraisonsVue.size()>2) {
			LivraisonDisplay l = (LivraisonDisplay) c.getFenetre().listview.getSelectionModel().getSelectedItem();
			
			VueDemandeLivraison.removeLivraisonTextuellement(l,c.getFenetre().livraisonsVue);
			
			c.getFenetre().initialiseListView();
			
			VueDemandeLivraison.removeLivraisonGraphiquement(c.getFenetre().livraisons, l.getColor());
			//VueTroncon.drawTournee(tournee.recalculTourneeApresSupression(), tourneePane);
		
			}else {
				c.getFenetre().console.setText("Vous ne pouvez pas supprimer toutes les livraisons. ");
			}
		c.setEtatCourant(c.etatTourneeModifiee);
	}
	
	@Override
	public void validerTournee(Controleur c, MainControlleur f) {
		c.setEtatCourant(c.etatTourneeModifiee);

	}
	private File selectFileXML() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		File selectedFile = fc.showOpenDialog(null);
		
		return selectedFile;
		
	}
	
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
			//VueNoeud.drawClikableNoeud(c.getPlan(), c.getFenetre().paneMap);
			c.getFenetre().console.setText("Charger une demande de livraison. ");
			c.getFenetre().chargerDemandeButton.setDisable(false);
			
			c.setEtatCourant(c.etatPlanCharge);
		}
	}

	@Override
	public void chargerDemandeLivraison(Controleur c, MainControlleur f) {
		File selectedFile = selectFileXML();
		if (selectedFile != null) {
			
			try {
				c.getDataContainer().chargerDemandeLivraison(selectedFile.getAbsolutePath());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.setDemandeLivraison(c.getDataContainer().GetDemandeLivraison());
			
			c.getFenetre().livraisonPane.getChildren().clear();
			c.getFenetre().tourneePane.getChildren().clear();
			//VueDemandeLivraison.drawDemandeLivraison(c.getPlan(), c.getDemandeLivraison(), c.getFenetre().livraisonPane);
			
			initialiseListView(c,f);
			c.getFenetre().console.setText("Charger une tournee. ");
			c.getFenetre().calculerTourneeButton.setDisable(false);
			
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
