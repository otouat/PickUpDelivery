package modele;

public class Test {
	public static void main(String[] args) throws Exception {
		DataContainer dataContainer = new DataContainer();
		dataContainer.chargerPlan("./src/modele/grandPlan.xml");
		Plan plan = dataContainer.GetPlan();
		plan.getInfo();
		
		dataContainer.chargerDemandeLivraison("./src/modele/demaneGrand7.xml");
		DemandeLivraison demandeLivraison=dataContainer.GetDemandeLivraison();
		demandeLivraison.getInfo();
		
		
		plan.CalculMinLatitude();
		plan.CalculMaxLatitude();
		plan.CalculMinLongitude();
		plan.CalculMaxLongitude();

		plan.CalculEcartLatitude();
		plan.CalculEcartLongitude();
		
		plan.AfficheTronconsDepuisNoeud(); //Affiche pour chaque noeud du plan les troncons dont il est � l'origine
		
		
		
	
	}
	
	
}
