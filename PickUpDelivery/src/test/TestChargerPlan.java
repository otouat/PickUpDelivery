package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modele.DataContainer;
import modele.Plan;

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
			// Charger un mauvais xml qui manque troncon
			resultatMT = dataContainer.chargerPlan("./src/test/planManqueTroncon.xml");
			// Charger un mauvais xml qui manque noeud
			resultatMN = dataContainer.chargerPlan("./src/test/planManqueNoeud.xml");
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
		assertEquals(3736, nombreNoeuds);
		assertEquals(7811, nombreTroncons);
		System.out.println(plan.toString());
	}

}