package controlleur; //

import modele.Tournee;
import vue.MainControlleur;
import vue.VueTroncon;

public class EtatTourneeCalculee extends EtatInit {
	
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
    public void genererFeuilleDeRoute(Controleur c, MainControlleur f) {
		// Traitement
		c.setEtatCourant(c.etatFeuilleDeRouteEditee);
	}
	
}
