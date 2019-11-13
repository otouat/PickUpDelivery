package controlleur;

import java.util.List;

import modele.Livraison;//
import modele.Noeud;
import modele.Tournee;
import vue.MainControlleur;
import vue.VueTroncon;

public class CommandeModifierNoeudLivraison implements Commande {

	private Livraison livraison;
	private Tournee tournee;
	private Noeud noeudChange;
	private Noeud noeudActuel;
	private int rangNoeudAChanger;
	private MainControlleur fenetre;
	

	
	public CommandeModifierNoeudLivraison(Livraison livraison, Tournee tournee, Noeud noeudChange, Noeud noeudActuel,
			int rangNoeudAChanger, MainControlleur fenetre) {
		super();
		this.livraison = livraison;
		this.tournee = tournee;
		this.noeudChange = noeudChange;
		this.noeudActuel = noeudActuel;
		this.rangNoeudAChanger = rangNoeudAChanger;
		this.fenetre = fenetre;
	}

	@Override
	public void doCommande() {
		// TODO Auto-generated method stub
		List<Noeud> listeNoeuds = tournee.recalculTourneeApresChangementNoeud(noeudChange,rangNoeudAChanger);
		fenetre.reInitialiseListView(tournee.getenchainementNoeudAVisiterAvecInfos());
		VueTroncon.drawTournee(listeNoeuds, fenetre.tourneePane);
	}

	@Override
	public void undoCommande() {
		// TODO Auto-generated method stub
		List<Noeud> listeNoeuds = tournee.recalculTourneeApresChangementNoeud(noeudActuel,rangNoeudAChanger);
		fenetre.reInitialiseListView(tournee.getenchainementNoeudAVisiterAvecInfos());
		VueTroncon.drawTournee(listeNoeuds, fenetre.tourneePane);
	}

}
