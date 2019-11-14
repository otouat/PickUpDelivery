package controlleur;

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

public class EtatAjouterLivraison  extends EtatInit {

	@Override
	public void ajouterLivraison(Controleur c, MainControlleur f) {
		//Traitement plus recalcul //
		
		
		c.setEtatCourant(c.etatTourneeModifiee);
	}
	
	@Override
	public void undo(Controleur controleur, ListeDeCommandes listeDeCommandes, MainControlleur fenetre) {
		controleur.setEtatCourant(controleur.etatTourneeModifiee);
		//fenetre.setModeModificationTournee();
	}
}
