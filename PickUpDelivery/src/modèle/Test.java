package modèle;

public class Test {
	public static void main(String[] args) {
		DataContainer dataContainer = new DataContainer();
		dataContainer.chargerPlan("./src/modèle/moyenPlan.xml");
		Plan plan = dataContainer.GetPlan();
		plan.getInfo();
		
		dataContainer.chargerDemandeLivraison(".//src/modèle/demandeMoyen5.xml");
		DemandeLivraison demandeLivraison= dataContainer.GetDemandeLivraison();
		demandeLivraison.getInfo();
		
	}
}