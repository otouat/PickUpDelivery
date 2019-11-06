package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modele.DataContainer;
import modele.DemandeLivraison;
import modele.Livraison;

public class TestChargerDemande {

	private DataContainer dataContainer = new DataContainer();

	@Test
	public void lireXML() {
		boolean resultatBon = false;
		boolean resultatME = true;
		boolean resultatML = true;
		try {
			// Charger le plan
			dataContainer.chargerPlan("./src/modele/grandPlan.xml");
			// Charger un bon xml de demande
			resultatBon = dataContainer.chargerDemandeLivraison("./src/modele/demandeGrand7.xml");
			// Charger un mauvais xml qui manque entrepot
			resultatME = dataContainer.chargerDemandeLivraison("./src/test/demandeManqueEntrepot.xml");
			// Charger un mauvais xml qui manque livrasion
			resultatML = dataContainer.chargerDemandeLivraison("./src/test/demandeManqueLivraison.xml");
			// Charger une mauvaise demande dont l'entrepot n'est pas dans le plan
			resultatML = dataContainer.chargerDemandeLivraison("./src/test/demandeAvecEntrepotIncorrect.xml");
			// Charger une mauvaise demande dont noeud d'enlevement d'un livraison n'est pas
			// dans le plan
			resultatML = dataContainer.chargerDemandeLivraison("./src/test/demandeAvecLivraisonIncorrect.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Le résultat de charger le bon xml doit être true
		assertEquals(true, resultatBon);
		// Les résultats de charger les deux mauvais xmls doivent être false
		assertEquals(false, resultatME);
		assertEquals(false, resultatML);

		// DataContainer doit garder le bon résultat généré par demandeGrand7.xml
		DemandeLivraison demande = dataContainer.GetDemandeLivraison();

		// On teste le nombre de livraisons
		assertEquals(7, demande.getLivraisons().size());

		// On teste l'entrepot
		assertEquals("8:0:0", demande.getEntrepotLivraison().getHeureDepart());
		assertEquals("25610888", demande.getEntrepotLivraison().GetIdNoeud());

		// On teste deux livraisons
		Livraison livraison1 = demande.getLivraisons().get(0);
		assertEquals("1362781062", livraison1.getNoeudEnlevement().GetIdNoeud());
		assertEquals(("27359745"), livraison1.getNoeudLivraison().GetIdNoeud());
		assertEquals(540, livraison1.getDureeEnlevement());
		assertEquals(540, livraison1.getDureeLivraison());

		Livraison livraison2 = demande.getLivraisons().get(5);
		assertEquals("26155372", livraison2.getNoeudEnlevement().GetIdNoeud());
		assertEquals(("26463669"), livraison2.getNoeudLivraison().GetIdNoeud());
		assertEquals(420, livraison2.getDureeEnlevement());
		assertEquals(360, livraison2.getDureeLivraison());
	}

}