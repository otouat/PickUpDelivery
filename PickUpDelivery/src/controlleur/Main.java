package controlleur;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;




public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("../vue/Main.fxml"));
		
		Scene scene = new Scene(root);
		

		primaryStage.setTitle("Pickup Delivery - Acceuil");
		primaryStage.setScene(scene);
		//primaryStage.setResizable(false);
		primaryStage.show();
		
	}
}