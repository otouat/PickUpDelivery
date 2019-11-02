package modele;

public class Test {
	public static void main(String[] args) throws Exception {
		DataContainer dataContainer = new DataContainer();
		dataContainer.chargerPlan("C:/Users/Emmy/Documents/INSA/4IF/S1/PLD-Agile/fichiersXML2019/grandPlan.xml");
		Plan plan = dataContainer.GetPlan();
		System.out.println(plan.toString());

		dataContainer.chargerDemandeLivraison("./src/modele/demandeGrand7.xml");
		// dataContainer.chargerDemandeLivraison("./src/modele/demandeGrand7.xml");
		DemandeLivraison demandeLivraison = dataContainer.GetDemandeLivraison();
		System.out.println(demandeLivraison.toString());
		demandeLivraison.getInfo();
	
		/*
		 * plan.CalculMinLatitude(); plan.CalculMaxLatitude();
		 * plan.CalculMinLongitude(); plan.CalculMaxLongitude();
		 * 
		 * plan.CalculEcartLatitude(); plan.CalculEcartLongitude();
		 */
		
		plan.CalculMinLatitude();
		plan.CalculMaxLatitude();
		plan.CalculMinLongitude();
		plan.CalculMaxLongitude();

		plan.CalculEcartLatitude();
		plan.CalculEcartLongitude();

		// plan.AfficheTronconsDepuisNoeud();
		// Affiche pour chaque noeud du plan les troncons dont il est l'origine

	}

}
