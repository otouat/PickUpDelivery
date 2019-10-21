package modele;

public class Test {
	public static void main(String[] args) {
		DataContainer dataContainer = new DataContainer();
		dataContainer.chargerPlan("./src/modele/moyenPlan.xml");
		Plan plan = dataContainer.GetPlan();
		plan.getInfo();
		

		dataContainer.chargerDemandeLivraison(".//src/modele/demandeMoyen5.xml");
		DemandeLivraison demandeLivraison = dataContainer.GetDemandeLivraison();
		demandeLivraison.getInfo();
		
		plan.CalculMinLatitude();
		plan.CalculMaxLatitude();
		plan.CalculMinLongitude();
		plan.CalculMaxLongitude();

	}
}