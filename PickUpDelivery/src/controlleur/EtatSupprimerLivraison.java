package controlleur;

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
	public void undo(Controleur controleur, ListeDeCommandes listeDeCommandes, MainControlleur fenetre) {
		controleur.setEtatCourant(controleur.etatTourneeModifiee);
		//fenetre.setModeModificationTournee();
	}
	
}
