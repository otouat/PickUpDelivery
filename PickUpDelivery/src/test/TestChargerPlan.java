package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modele.DataContainer;
import modele.Noeud;
import modele.Plan;
import modele.Troncon;

public class TestChargerPlan {

	private DataContainer dataContainer = new DataContainer();

	@Test
	public void lireXML() {
		boolean resultatBon = false;
		boolean resultatMT = true;
		boolean resultatMN = true;
		try {
			// Charger un bon xml
			resultatBon = dataContainer.chargerPlan("./src/modele/grandPlan.xml");
			// Charger un mauvais plan qui manque troncon
			resultatMT = dataContainer.chargerPlan("./src/test/planManqueTroncon.xml");
			// Charger un mauvais plan qui manque noeud
			resultatMN = dataContainer.chargerPlan("./src/test/planManqueNoeud.xml");
			// Charger un mauvais plan qui contient un troncon dont noeud d'origine
			// introuvable
			resultatMN = dataContainer.chargerPlan("./src/test/planAvecTronconIncorrect.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Plan plan = dataContainer.GetPlan();
		int nombreNoeuds = plan.getNoeuds().size();
		int nombreTroncons = plan.getTroncons().size();
		assertEquals(true, resultatBon);
		// Les résultats de charger les deux mauvais xmls doivent être false
		assertEquals(false, resultatMT);
		assertEquals(false, resultatMN);

		// DataContainer doit garder le bon résultat généré par grandPlan.xml
		// On teste le nombre de noeuds et nombre de troncons
		assertEquals(3736, nombreNoeuds);
		assertEquals(7811, nombreTroncons);

		// On teste deux noeuds
		Noeud noeud1 = plan.getNoeuds().get("25327121");
		double latitudeNoeud1 = noeud1.GetLatitude();
		double longitudeNoeud1 = noeud1.GetLongitude();
		assertEquals(45.771732, latitudeNoeud1, 0.000001);
		assertEquals(4.855157, longitudeNoeud1, 0.000001);

		Noeud noeud2 = plan.getNoeuds().get("26470094");
		double latitudeNoeud2 = noeud2.GetLatitude();
		double longitudeNoeud2 = noeud2.GetLongitude();
		assertEquals(45.746674, latitudeNoeud2, 0.000001);
		assertEquals(4.8928156, longitudeNoeud2, 0.000001);

		// On teste deux troncons
		Troncon troncon1 = plan.getTroncons().get(10);
		String nomTroncon1 = troncon1.GetNomRue();
		double longueurTroncon1 = troncon1.GetLongueur();
		assertEquals("Route de Genas", nomTroncon1);
		assertEquals(89.326126, longueurTroncon1, 0.000001);

		Troncon troncon2 = plan.getTroncons().get(100);
		String nomTroncon2 = troncon2.GetNomRue();
		double longueurTroncon2 = troncon2.GetLongueur();
		assertEquals("Rue Nungesser et Coli", nomTroncon2);
		assertEquals(37.43976, longueurTroncon2, 0.000001);

		// On teste le nombre de troncons depuis un noeud choisi
		int nombreDeTronconDepuisNoeud1 = plan.CalculerNombreDeTronconDepuisNoeud("25327121");
		assertEquals(2, nombreDeTronconDepuisNoeud1);

		int nombreDeTronconDepuisNoeud2 = plan.CalculerNombreDeTronconDepuisNoeud("26470094");
		assertEquals(3, nombreDeTronconDepuisNoeud2);

		// On assure qu'on trouve le bon troncon depuis deux noeuds choisis
		Troncon troncon3 = plan.ChercherTronconDepuisDeuxNoeuds("25310887", "25327147");
		assertEquals("Rue Garibaldi", troncon3.GetNomRue());
		assertEquals(82.975, troncon3.GetLongueur(), 0.000001);

		Troncon troncon4 = plan.ChercherTronconDepuisDeuxNoeuds("25310873", "26035015");
		assertEquals("Rue Cuvier", troncon4.GetNomRue());
		assertEquals(88.460655, troncon4.GetLongueur(), 0.000001);

		// On teste latitude et longitude maximale, minimale ainsi que leur écart
		double latmin = plan.CalculMinLatitude();
		double latmax = plan.CalculMaxLatitude();
		double ecartLat = plan.CalculEcartLatitude();

		double longmin = plan.CalculMinLongitude();
		double longmax = plan.CalculMaxLongitude();
		double ecartlong = plan.CalculEcartLongitude();

		assertEquals(45.727352, latmin, 0.000001);
		assertEquals(45.780518, latmax, 0.000001);
		assertEquals(0.05316599999999738, ecartLat, 0.000001);

		assertEquals(4.8314376, longmin, 0.000001);
		assertEquals(4.9075384, longmax, 0.000001);
		assertEquals(0.07610079999999986, ecartlong, 0.000001);
	}

}