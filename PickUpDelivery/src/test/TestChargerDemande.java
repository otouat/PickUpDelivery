package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modele.DataContainer;
import modele.DemandeLivraison;

public class TestChargerDemande {

	private DataContainer dataContainer = new DataContainer();

	@Test
	public void lireXML() {
		boolean resultatME = true;
		boolean resultatML = true;
		boolean resultatMV = true;
		try {
			dataContainer.chargerPlan("./src/modele/grandPlan.xml");
			// Charger un bon xml
			dataContainer.chargerDemandeLivraison("./src/modele/demandeGrand7.xml");
			// Charger un mauvais xml qui manque entrepot
			resultatME = dataContainer.chargerDemandeLivraison("./src/test/demandeManqueEntrepot.xml");
			// Charger un mauvais xml qui manque livrasion
			resultatML = dataContainer.chargerDemandeLivraison("./src/test/demandeManqueLivraison.xml");
			// Charger un  xml qui n'existe pas
			resultatMV = dataContainer.chargerDemandeLivraison("./src/test/demandeLivraisonInexistante.xml");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Les resultats de chargement trois mauvais xmls doivent etre false
		assertEquals(false, resultatME);
		assertEquals(false, resultatML);
		assertEquals(false, resultatMV);

		// DataContainer doit garder le bon resultat genere par demandeGrand7.xml
		DemandeLivraison demande = dataContainer.GetDemandeLivraison();
		assertEquals(7, demande.getLivraisons().size());

		System.out.println(demande.toString());
	}

}