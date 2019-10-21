package modele;

public class Test {
	public static void main(String[] args) throws Exception {
		DataContainer dataContainer = new DataContainer();
		dataContainer.chargerPlan("./src/modele/grandPlan.xml");
		Plan plan = dataContainer.GetPlan();
		plan.getInfo();
		
		
		

		dataContainer.chargerDemandeLivraison(".//src/modele/demandeGrand7.xml");
		DemandeLivraison demandeLivraison = dataContainer.GetDemandeLivraison();
		demandeLivraison.getInfo();
		
		plan.CalculMinLatitude();
		plan.CalculMaxLatitude();
		plan.CalculMinLongitude();
		plan.CalculMaxLongitude();

		plan.CalculEcartLatitude();
		plan.CalculEcartLongitude();
		
		plan.AfficheNoeudsAdjacents();//Affiche pour chaque noeud du plan ses noeuds adjacents
		
		
		
	
	}
	
	
}
