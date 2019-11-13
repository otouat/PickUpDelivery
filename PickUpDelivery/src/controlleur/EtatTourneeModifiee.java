package controlleur; //

import java.io.File;

import javafx.stage.FileChooser;
import modele.Tournee;
import vue.MainControlleur;
import vue.VueTroncon;
import vue.VueUtils;

public class EtatTourneeModifiee extends EtatInit {
	
	@Override
	public void calculerTournee(Controleur c, MainControlleur f) {
		
		c.setTournee(new Tournee(c.getDemandeLivraison().getEntrepotLivraison(),c.getDemandeLivraison().getLivraisons(),c.getPlan()));
		
		c.getFenetre().tourneePane.getChildren().clear();
		VueTroncon.drawTournee(c.getTournee().calculTournee(), c.getFenetre().tourneePane);
		
		c.getFenetre().console.setText("Vous pouvez maintenant modifier la tournée ou générer une feuille de route. ");
		c.getFenetre().genererFeuilleRouteButton.setDisable(false);
		
		c.setEtatCourant(c.etatTourneeModifiee);
		
	}
	
	@Override
	public void ajouterLivraison(Controleur c, MainControlleur f) {
		c.setEtatCourant(c.etatAjouter);
	}

	@Override
	public void supprimerLivraison(Controleur c, MainControlleur f) {
		c.setEtatCourant(c.etatSupprimer);
	
	}

	@Override
	public void echangerLivraisons(Controleur controleur, MainControlleur f) {
		//controleur.setEtatCourant(controleur.etatIntervertirLivraisons1);
		
	}
	
	@Override
    public void genererFeuilleDeRoute(Controleur c, MainControlleur f) {
		// Traitement
		c.setEtatCourant(c.etatFeuilleDeRouteEditee);
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
