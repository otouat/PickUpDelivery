package controlleur;
import java.io.File;//

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
