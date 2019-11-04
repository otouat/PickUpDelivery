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
		try {
			// Charger le plan
			dataContainer.chargerPlan("./src/modele/grandPlan.xml");
			// Charger un bon xml de demande
			dataContainer.chargerDemandeLivraison("./src/modele/demandeGrand7.xml");
			// Charger un mauvais xml qui manque entrepot
			resultatME = dataContainer.chargerDemandeLivraison("./src/test/demandeManqueEntrepot.xml");
			// Charger un mauvais xml qui manque livrasion
			resultatML = dataContainer.chargerDemandeLivraison("./src/test/demandeManqueLivraison.xml");
			// Charger une mauvaise demande dont l'entrepot n'est pas dans le plan
			resultatML = dataContainer.chargerDemandeLivraison("./src/test/demandeAvecEntrepotIncorrect.xml");
			// Charger une mauvaise demande dont noeud d'enlevement d'un livraison n'est pas dans le plan
			resultatML = dataContainer.chargerDemandeLivraison("./src/test/demandeAvecLivraisonIncorrect.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Les résultats de charger les deux mauvais xmls doivent être false
		assertEquals(false, resultatME);
		assertEquals(false, resultatML);

		// DataContainer doit garder le bon résultat généré par demandeGrand7.xml
		DemandeLivraison demande = dataContainer.GetDemandeLivraison();
		assertEquals(7, demande.getLivraisons().size());

		System.out.println(demande.toString());
	}

}