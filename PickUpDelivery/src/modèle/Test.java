package mod�le;

public class Test {
	public static void main(String[] args) {
		DataContainer dataContainer = new DataContainer();
		dataContainer.chargerPlan("./src/mod�le/moyenPlan.xml");
		Plan plan = dataContainer.GetPlan();
		plan.getInfo();
		
		dataContainer.chargerDemandeLivraison(".//src/mod�le/demandeMoyen5.xml");
		DemandeLivraison demandeLivraison= dataContainer.GetDemandeLivraison();
		demandeLivraison.getInfo();
		
	}
}