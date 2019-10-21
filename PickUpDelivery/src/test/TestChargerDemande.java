package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modele.DataContainer;
import modele.Plan;

public class TestChargerDemande {

	private DataContainer dataContainer = new DataContainer();

	@Test
	public void lireXML() {
		try {
			dataContainer.chargerPlan(".//src/modele/demandeGrand7.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Plan plan = dataContainer.GetPlan();
		int nombreNoeuds = plan.getNoeuds().size();
		int nombreTroncons = plan.getTroncons().size();
		assertEquals(3736, nombreNoeuds);
		assertEquals(7811, nombreTroncons);
		System.out.println(plan.toString());
	}

}