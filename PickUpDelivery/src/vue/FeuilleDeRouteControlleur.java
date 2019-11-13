package vue;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class FeuilleDeRouteControlleur implements Initializable {
	@FXML
	private Text feuilleDeRoute;

	@Override
	/**
	 * Cette méthode permet d'afficher la feuille de route
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {

		// feuilleDeRoute.setText(MainControlleur.feuilleDeRoute.toString());
		feuilleDeRoute.setText(MainControlleur.feuilleDeRoute.toString());
		System.out.println(MainControlleur.feuilleDeRoute);

	}

}
