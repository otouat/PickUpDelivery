package controlleur;

import java.io.File;

import javafx.stage.FileChooser;
import modele.Tournee;
import vue.MainControlleur;
import vue.VueTroncon;
import vue.VueUtils;

public class EtatFeuilleDeRouteEditee extends EtatInit {
	
	public void modifierTournee(Controleur c, MainControlleur f) {
		
		c.setEtatCourant(c.etatTourneeModifiee); //
	}
	
	@Override
	public void calculerTournee(Controleur c, MainControlleur f) {
		
		c.setEtatCourant(c.etatTourneeCalculee);
		
	}
	
	public void quitterApplication(Controleur c, MainControlleur f) {}
	
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
			
			c.setEtatCourant(c.etatDemandeCharge);
		}

		
		
	}
	
	
	
	private File selectFileXML() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		File selectedFile = fc.showOpenDialog(null);
		
		return selectedFile;
		
	}
	
}
