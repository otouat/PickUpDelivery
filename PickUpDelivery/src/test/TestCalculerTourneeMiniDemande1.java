package test;

import java.util.List;

import modele.DataContainer;
import modele.FeuilleDeRoute;
import modele.Noeud;
import modele.Tournee;

public class TestCalculerTourneeMiniDemande1 {

	public static void main(String[] args) {
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

			/*
			 * Noeud noeud1 = dataContainer.GetPlan().getNoeuds().get("1"); Noeud noeud2 =
			 * dataContainer.GetPlan().getNoeuds().get("2"); Noeud noeud3 =
			 * dataContainer.GetPlan().getNoeuds().get("3"); Noeud noeud4 =
			 * dataContainer.GetPlan().getNoeuds().get("4"); Noeud noeud5 =
			 * dataContainer.GetPlan().getNoeuds().get("5"); Noeud noeud6 =
			 * dataContainer.GetPlan().getNoeuds().get("6"); double angle126 =
			 * Angle2(noeud2, noeud1, noeud6); double angle135 = Angle2(noeud3, noeud1,
			 * noeud5); for (int i = 0; i < listTournee.size() - 2; i++) { String direction
			 * = calculerDirection(listTournee.get(i + 1), listTournee.get(i),
			 * listTournee.get(i + 2)); System.out.println(listTournee.get(i).GetIdNoeud() +
			 * listTournee.get(i + 1).GetIdNoeud() + listTournee.get(i + 2).GetIdNoeud() +
			 * direction);
			 * 
			 * }
			 */
			System.out.println(tournee.getEnchainementNoeudAVisiter());
			System.out.println(tournee.getNoeudAVisiter());

			FeuilleDeRoute feuilleDeRoute = new FeuilleDeRoute(listTournee, dataContainer.GetPlan(), tournee);
			System.out.println(feuilleDeRoute.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}