package test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import modele.DataContainer;
import modele.Plan;

public class TestCalculLongLat {
	
	private DataContainer dataContainer = new DataContainer();
	
	@Test
	public void lireXML() {
		try {
			dataContainer.chargerPlan("./src/modele/grandPlan.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Plan plan = dataContainer.GetPlan();
		
		double latmin = plan.CalculMinLatitude();
		double latmax = plan.CalculMaxLatitude();
		double ecartLat = plan.CalculEcartLatitude();
		
		double longmin = plan.CalculMinLongitude();
		double longmax = plan.CalculMaxLongitude();
		double ecartlong= plan.CalculEcartLongitude();// test git
		
		assertEquals(45.727352,latmin,0.001);
		assertEquals(45.780518, latmax,0.001);
		assertEquals(0.05316599999999738, ecartLat,0.001);
		
		assertEquals(4.8314376, longmin,0.001);
		assertEquals(4.9075384, longmax,0.001);
		assertEquals(0.07610079999999986, ecartlong,0.001);
	
	}


}