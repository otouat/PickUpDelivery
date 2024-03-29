package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import modele.DataContainer;
import modele.FeuilleDeRoute;
import modele.Livraison;
import modele.Noeud;
import modele.Plan;
import modele.Tournee;

public class TestTourneeAjouterLivraison {
	@Test
	public void testRecalculerTournee() {
		DataContainer dataContainer = new DataContainer();
		try {
			dataContainer.chargerPlan("./src/test/miniPlan.xml");
			dataContainer.chargerDemandeLivraison("./src/test/miniDemande1.xml");

			Tournee tournee = new Tournee(dataContainer.GetDemandeLivraison().getEntrepotLivraison(),
					dataContainer.GetDemandeLivraison().getLivraisons(), dataContainer.GetPlan());
			List<Noeud> listTournee = tournee.calculTournee();
			System.out.println(listTournee.size());
			Livraison unLivraison = dataContainer.GetDemandeLivraison().getLivraisons().get(0);

			FeuilleDeRoute feuilleDeRoute = new FeuilleDeRoute(listTournee, dataContainer.GetPlan(), tournee);
			Plan plan = dataContainer.GetPlan();
			Livraison livraisonAAjouter = new Livraison(plan.getNoeuds().get("3"), plan.getNoeuds().get("4"), 0, 0);
			listTournee = tournee.recalculTourneeApresAjoutLivraison(livraisonAAjouter, plan.getNoeuds().get("5"),
					plan.getNoeuds().get("6"));
			System.out.println(listTournee);

			assertEquals(listTournee.get(0).GetIdNoeud(), "1");
			assertEquals(listTournee.get(1).GetIdNoeud(), "3");
			assertEquals(listTournee.get(2).GetIdNoeud(), "3");
			assertEquals(listTournee.get(3).GetIdNoeud(), "4");
			assertEquals(listTournee.get(4).GetIdNoeud(), "5");
			assertEquals(listTournee.get(5).GetIdNoeud(), "5");
			assertEquals(listTournee.get(6).GetIdNoeud(), "4");
			assertEquals(listTournee.get(7).GetIdNoeud(), "3");
			assertEquals(listTournee.get(8).GetIdNoeud(), "3");
			assertEquals(listTournee.get(9).GetIdNoeud(), "4");
			assertEquals(listTournee.get(10).GetIdNoeud(), "6");
			assertEquals(listTournee.get(11).GetIdNoeud(), "6");
			assertEquals(listTournee.get(12).GetIdNoeud(), "4");
			assertEquals(listTournee.get(13).GetIdNoeud(), "4");
			assertEquals(listTournee.get(14).GetIdNoeud(), "2");
			assertEquals(listTournee.get(15).GetIdNoeud(), "2");
			assertEquals(listTournee.get(16).GetIdNoeud(), "1");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}