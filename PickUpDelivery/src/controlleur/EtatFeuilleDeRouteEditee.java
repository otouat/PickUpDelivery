package controlleur;

import modele.Tournee;
import vue.MainControlleur;
import vue.VueTroncon;

public class EtatFeuilleDeRouteEditee extends EtatInit {
	
	public void modifierTournee(Controleur c, MainControlleur f) {
		
		c.setEtatCourant(c.etatTourneeModifiee);
	}
	
	@Override
	public void calculerTournee(Controleur c, MainControlleur f) {
		
		c.setEtatCourant(c.etatTourneeCalculee);
		
	}
	
	public void quitterApplication(Controleur c, MainControlleur f) {}
}
