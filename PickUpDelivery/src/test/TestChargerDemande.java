package test;

import org.junit.Test;

import modele.DataContainer;
import modele.DemandeLivraison;

public class TestChargerDemande {

	private DataContainer dataContainer = new DataContainer();

	@Test
	public void lireXML() {
		try {
			dataContainer.chargerPlan("./src/modele/grandPlan.xml");
			dataContainer.chargerDemandeLivraison("./src/modele/demandeGrand7.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		DemandeLivraison demande = dataContainer.GetDemandeLivraison();
		// demande.getInfo();
		System.out.println(demande.toString());
	}

}