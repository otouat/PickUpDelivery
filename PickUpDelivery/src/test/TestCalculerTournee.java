package test;

import java.util.List;

import modele.DataContainer;
import modele.Noeud;
import modele.Tournee;

public class TestCalculerTournee {

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
			/*
			 * for (int i = 0; i < listTournee.size(); i++) {
			 * System.out.println(listTournee.get(i).toString()); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}