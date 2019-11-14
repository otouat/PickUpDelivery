package controlleur;

import java.util.List;

import modele.Livraison;//
import modele.Noeud;
import modele.Tournee;
import vue.MainControlleur;
import vue.VueTroncon;

public class CommandeModifierNoeudLivraison implements Commande {

	private Tournee tournee;
	private Noeud nouveauNoeud;
	private Noeud ancienNoeud;
	private Integer rangNoeudAChanger;
	private MainControlleur fenetre;


	
	
	public CommandeModifierNoeudLivraison(Tournee tournee, Noeud nouveauNoeud, Noeud ancienNoeud, Integer rangNoeudAChanger,
			MainControlleur fenetre) {
		super();
		this.tournee = tournee;
		this.nouveauNoeud = nouveauNoeud;
		this.rangNoeudAChanger = rangNoeudAChanger;
		this.fenetre = fenetre;
		this.ancienNoeud = ancienNoeud;
		
	}

	@Override
	public void doCommande() {
		// TODO Auto-generated method stub
		List<Noeud> listeNoeuds = tournee.recalculTourneeApresChangementNoeud(nouveauNoeud, rangNoeudAChanger);
		fenetre.reInitialiseListView(tournee.getenchainementNoeudAVisiterAvecInfos());
		VueTroncon.drawTournee(listeNoeuds, fenetre.tourneePane);
	}

	@Override
	public void undoCommande() {
		// TODO Auto-generated method stub
		List<Noeud> listeNoeuds = tournee.recalculTourneeApresChangementNoeud(ancienNoeud, rangNoeudAChanger);
		fenetre.reInitialiseListView(tournee.getenchainementNoeudAVisiterAvecInfos());
		VueTroncon.drawTournee(listeNoeuds, fenetre.tourneePane);
	}

}
