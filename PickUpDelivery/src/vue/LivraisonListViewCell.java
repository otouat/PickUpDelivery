package vue;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import modele.*;

public class LivraisonListViewCell extends ListCell<LivraisonDisplay>{
	  @Override
	    public void updateItem(LivraisonDisplay livraisonDisplay, boolean empty)
	    {
	        super.updateItem(livraisonDisplay,empty);
	        if(livraisonDisplay != null)
	        {
	            Data data = new Data();
	            data.setInfo(livraisonDisplay);
	            setGraphic(data.getPane());
	        }
	    }
}
