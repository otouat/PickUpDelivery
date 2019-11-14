package controlleur;

import java.io.File;//
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import modele.Livraison;
import modele.Tournee;
import vue.LivraisonDisplay;
import vue.LivraisonListViewCell;
import vue.MainControlleur;
import vue.VueDemandeLivraison;
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
	
	
}
