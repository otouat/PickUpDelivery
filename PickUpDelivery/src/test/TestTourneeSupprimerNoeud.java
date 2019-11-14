package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import modele.DataContainer;
import modele.FeuilleDeRoute;
import modele.Noeud;
import modele.Tournee;

public class TestTourneeSupprimerNoeud {
	@Test
	public void testRecalculerTournee() {
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
			System.out.println(listTournee.size());

			for (int i = 0; i < listTournee.size(); i++) {
				System.out.println(listTournee.get(i).toString());
			}

			FeuilleDeRoute feuilleDeRoute = new FeuilleDeRoute(listTournee, dataContainer.GetPlan(), tournee);
			System.out.println(feuilleDeRoute.toString());
			assertEquals(listTournee.get(0).GetIdNoeud(), "1");
			assertEquals(listTournee.get(1).GetIdNoeud(), "3");
			assertEquals(listTournee.get(2).GetIdNoeud(), "3");
			assertEquals(listTournee.get(3).GetIdNoeud(), "4");
			assertEquals(listTournee.get(4).GetIdNoeud(), "5");
			assertEquals(listTournee.get(5).GetIdNoeud(), "5");
			assertEquals(listTournee.get(6).GetIdNoeud(), "6");
			assertEquals(listTournee.get(7).GetIdNoeud(), "6");
			assertEquals(listTournee.get(8).GetIdNoeud(), "2");
			assertEquals(listTournee.get(9).GetIdNoeud(), "2");
			assertEquals(listTournee.get(10).GetIdNoeud(), "1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}