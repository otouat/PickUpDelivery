package test;

import org.junit.Test;

import modele.DataContainer;
import modele.DemandeLivraison;

public class TestChargerDemande {

	private DataContainer dataContainer = new DataContainer();

	@Test
	public void lireXML() {
		try {
			dataContainer.chargerPlan("./src/modele/demandeGrand7.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		DemandeLivraison demande = dataContainer.GetDemandeLivraison();
		System.out.println(demande.toString());
	}

}