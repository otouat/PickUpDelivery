package controlleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;




public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("../vue/Main.fxml"));
		
		Scene scene = new Scene(root);
		

		primaryStage.setTitle("Pickup Delivery - Acceuil");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
}