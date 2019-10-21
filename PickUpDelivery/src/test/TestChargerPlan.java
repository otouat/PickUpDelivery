package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modele.DataContainer;
import modele.Plan;

public class TestChargerPlan {

	private DataContainer dataContainer = new DataContainer();

	@Test
	public void lireXML() {
		try {
			dataContainer.chargerPlan("./src/modele/grandPlan.xml");
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