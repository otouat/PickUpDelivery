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
			Tournee tournee = new Tournee(dataContainer.GetDemandeLivraison().getEntrepotLivraison(),
					dataContainer.GetDemandeLivraison().getLivraisons(), dataContainer.GetPlan());
			List<Noeud> listTournee = tournee.calculTournee();

			String[] listeDirection = new String[9];
			String[] listeNomRue = new String[9];
			double[] listeLongueur = new double[9];
			String[] listeToDo = new String[9];
			FeuilleDeRoute feuilleDeRoute = new FeuilleDeRoute(listTournee, dataContainer.GetPlan(), tournee);
			for (int i = 0; i < 6; i++) {
				listeDirection[i] = feuilleDeRoute.getInstructions().get(i).getDirection();
				listeNomRue[i] = feuilleDeRoute.getInstructions().get(i).getNomRueCourant();
				listeLongueur[i] = feuilleDeRoute.getInstructions().get(i).getLongueur();
				listeToDo[i] = feuilleDeRoute.getInstructions().get(i).getToDo();
			}
			/**
			 * Test calculer direction
			 */
			assertEquals(listeDirection[0].contains("droite"), true);
			assertEquals(listeDirection[1].contains("gauche"), true);
			assertEquals(listeDirection[2].contains("droite"), true);
			assertEquals(listeDirection[3].contains("droite"), true);
			assertEquals(listeDirection[4].contains("droite"), true);

			/**
			 * Test récupérer l'info
			 */
			assertEquals(listeNomRue[0], "Rue 1-3");
			assertEquals(listeNomRue[3], "Rue 5-6");
			assertEquals(listeNomRue[5], "Rue 2-1");

			assertEquals(listeLongueur[0], 30.0, 0.000001);
			assertEquals(listeLongueur[4], 30.0, 0.000001);
			assertEquals(listeLongueur[5], 20.0, 0.000001);

			assertEquals(listeToDo[0], "recuperer un colis");
			assertEquals(listeToDo[3], "livrer un colis");
			assertEquals(listeToDo[4], "livrer un colis");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}