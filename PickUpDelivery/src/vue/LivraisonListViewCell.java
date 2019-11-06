package vue;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import modele.*;

public class LivraisonListViewCell extends ListCell<Livraison>{
	  @Override
	    public void updateItem(Livraison livraison, boolean empty)
	    {
	        super.updateItem(livraison,empty);
	        if(livraison != null)
	        {
	            Data data = new Data();
	            data.setInfo(livraison);
	            setGraphic(data.getBox());
	        }
	    }
}
