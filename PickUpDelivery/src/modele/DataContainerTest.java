package modele;

import javax.annotation.Generated;

import org.junit.Test;

@Generated(value = "org.junit-tools-1.1.0")
public class DataContainerTest {

	private DataContainer createTestSubject() {
		return new DataContainer();
	}

	// @MethodRef(name = "chargerPlan", signature = "(QString;)Z")
	@Test
	public void testChargerPlan() throws Exception {
		DataContainer testSubject;
		String XMLPath = "";
		boolean result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.chargerPlan(XMLPath);
	}

	// @MethodRef(name = "chargerDemandeLivraison", signature = "(QString;)Z")
	@Test
	public void testChargerDemandeLivraison() throws Exception {
		DataContainer testSubject;
		String XMLPath = "";
		boolean result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.chargerDemandeLivraison(XMLPath);
	}
}