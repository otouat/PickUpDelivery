package modele;

public class Test {
	public static void main(String[] args) {
		DataContainer dataContainer = new DataContainer();
		dataContainer.chargerPlan("PickUpDelivery/src/modele/moyenPlan.xml");
		Plan plan = dataContainer.GetPlan();
		plan.getInfo();

		dataContainer.chargerDemandeLivraison("PickUpDelivery/src/modele/demandeMoyen5.xml");
		DemandeLivraison demandeLivraison = dataContainer.GetDemandeLivraison();
		demandeLivraison.getInfo();

	}
}