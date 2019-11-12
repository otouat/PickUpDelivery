package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import modele.DataContainer;
import modele.FeuilleDeRoute;
import modele.Noeud;
import modele.Tournee;

public class TestGenererFeuilleDeRoute {
	@Test
	public void calculerDirection() {
		DataContainer dataContainer = new DataContainer();
		try {
			dataContainer.chargerPlan("./src/test/miniPlan.xml");
			dataContainer.chargerDemandeLivraison("./src/test/miniDemande1.xml");
			/*
			 * System.out.println(dataContainer.GetPlan().toString());
			 * System.out.println(dataContainer.GetDemandeLivraison().toString());
			 */
			Tournee tournee = new Tournee(dataContainer.GetDemandeLivraison().getEntrepotLivraison(),
					dataContainer.GetDemandeLivraison().getLivraisons(), dataContainer.GetPlan());
			List<Noeud> listTournee = tournee.calculTournee();

			String[] listeDirection = new String[9];
			String[] listeNomRue = new String[9];
			double[] listeLongueur = new double[9];
			String[] listeToDo = new String[9];
			FeuilleDeRoute feuilleDeRoute = new FeuilleDeRoute(listTournee, dataContainer.GetPlan(), tournee);
			for (int i = 0; i < 9; i++) {
				listeDirection[i] = feuilleDeRoute.getInstructions().get(i).getDirection();
				listeNomRue[i] = feuilleDeRoute.getInstructions().get(i).getNomRueCourant();
				listeLongueur[i] = feuilleDeRoute.getInstructions().get(i).getLongueur();
				listeToDo[i] = feuilleDeRoute.getInstructions().get(i).getToDo();
			}

			assertEquals(listeDirection[0], "tourner à gauche");
			assertEquals(listeDirection[1], "tourner à droite");
			assertEquals(listeDirection[2], "tourner à droite");
			assertEquals(listeDirection[3], "tourner à droite");
			assertEquals(listeDirection[4], "tourner à droite");
			assertEquals(listeDirection[5], "tourner à gauche");
			assertEquals(listeDirection[6], "tourner à gauche");
			assertEquals(listeDirection[7], "retourner");
			assertEquals(listeDirection[8], "");

			assertEquals(listeNomRue[0], "Rue 1-2");
			assertEquals(listeNomRue[3], "Rue 5-6");
			assertEquals(listeNomRue[5], "Rue 4-3");

			assertEquals(listeLongueur[0], 20.0, 0.000001);
			assertEquals(listeLongueur[4], 20.0, 0.000001);
			assertEquals(listeLongueur[6], 30.0, 0.000001);

			assertEquals(listeToDo[2], "récupérer un colis");
			assertEquals(listeToDo[3], "livrer un colis");
			assertEquals(listeToDo[5], "récupérer un colis");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}