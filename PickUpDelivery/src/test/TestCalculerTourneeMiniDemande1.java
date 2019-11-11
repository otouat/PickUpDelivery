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

			FeuilleDeRoute feuilleDeRoute = new FeuilleDeRoute(
					dataContainer.GetDemandeLivraison().getEntrepotLivraison(), listTournee, dataContainer.GetPlan(),
					tournee);
			System.out.println(feuilleDeRoute.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String calculerDirection(Noeud cen, Noeud noeud1, Noeud noeud2) {
		double y1 = noeud1.GetLatitude() - cen.GetLatitude();
		double x1 = noeud1.GetLongitude() - cen.GetLongitude();
		double y2 = noeud2.GetLatitude() - cen.GetLatitude();
		double x2 = noeud2.GetLongitude() - cen.GetLongitude();
		double angle = Math.atan2(y1, x1) - Math.atan2(y2, x2);
		angle = 180 * angle / Math.PI;
		if (Math.abs(angle) > 180) {
			if (angle < 0) {
				angle = 360 + angle;
			} else {
				angle = angle - 360;
			}
		}
		if (angle == 0) {
			return "tout droit";
		} else if (angle == 180) {
			return "retourner";
		} else if (angle > 0) {
			return "gauche";
		} else {
			return "droite";
		}

	}

	public static double Angle2(Noeud cen, Noeud first, Noeud second) {
		double y1 = first.GetLatitude() - cen.GetLatitude();
		double x1 = first.GetLongitude() - cen.GetLongitude();
		double y2 = second.GetLatitude() - cen.GetLatitude();
		double x2 = second.GetLongitude() - cen.GetLongitude();
		double angle = Math.atan2(y1, x1) - Math.atan2(y2, x2);
		angle = 180 * angle / Math.PI;
		if (Math.abs(angle) > 180) {
			if (angle < 0) {
				angle = 360 + angle;
			} else {
				angle = angle - 360;
			}
		}
		return angle;
	}

}