package vue;

import java.net.URL;
import java.util.ResourceBundle;

import controlleur.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import modele.FeuilleDeRoute;
import modele.Tournee;

public class FeuilleDeRouteControlleur implements Initializable  {
	@FXML private Text feuilleDeRoute;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//feuilleDeRoute.setText(MainControlleur.feuilleDeRoute.toString());
		feuilleDeRoute.setText(MainControlleur.feuilleDeRoute.toString());
		System.out.println(MainControlleur.feuilleDeRoute);
		
	}
	
}
