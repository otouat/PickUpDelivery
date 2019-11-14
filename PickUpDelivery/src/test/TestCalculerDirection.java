package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import modele.DataContainer;
import modele.FeuilleDeRoute;
import modele.Noeud;
import modele.Tournee;

public class TestCalculerDirection {
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

			FeuilleDeRoute feuilleDeRoute = new FeuilleDeRoute(listTournee, dataContainer.GetPlan(), tournee);
			for (int i = 0; i < 6; i++) {
				listeDirection[i] = feuilleDeRoute.getInstructions().get(i).getDirection();
			}
			System.out.println(listeDirection[0]);
			assertEquals(listeDirection[0].contains("droite"), true);
			assertEquals(listeDirection[1].contains("gauche"), true);
			assertEquals(listeDirection[2].contains("droite"), true);
			assertEquals(listeDirection[3].contains("droite"), true);
			assertEquals(listeDirection[4].contains("droite"), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}