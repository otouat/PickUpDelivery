package controlleur;
import java.io.File;//

import javafx.stage.FileChooser;
import modele.DataContainer;
import modele.Livraison;
import modele.Plan;
import vue.MainControlleur;
import vue.VueTroncon;
import vue.VueUtils;

public class EtatInit implements Etat {

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
	
	private File selectFileXML() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		File selectedFile = fc.showOpenDialog(null);
		
		return selectedFile;
		
	}

	@Override
	public void chargerDemandeLivraison(Controleur c, MainControlleur f) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void calculerTournee(Controleur c, MainControlleur f) {
		// TODO Auto-generated method stub
		
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
