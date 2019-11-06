package vue;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import modele.Livraison;
import modele.Troncon;

public class Data {
	 @FXML
	    private HBox hBox;
	    @FXML
	    private Label label2;
	    @FXML
	    private Circle shapeCircle;
	    
	    public Data()
	    {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml//listCellItem.fxml"));
	        fxmlLoader.setController(this);
	        
	        try
	        {
	            fxmlLoader.load();
	        }
	        catch (IOException e)
	        {
	            throw new RuntimeException(e);
	        }
	    }
	    
	    public void setInfo(Livraison livraison)
	    {
	    	String idNoeud = livraison.getNoeudEnlevement().GetIdNoeud();
	    	
	    	String adresse ="";
	    	List<Troncon> tronconList = livraison.getNoeudEnlevement().GetTronconsDepuisLeNoeud();
	    	
	    	for(int i=0;i<tronconList.size();i++) {
	    		adresse += tronconList.get(i).GetNomRue() + " // ";
	    	}
	    	
	    	
	        label2.setText(idNoeud + " " +adresse);
	        shapeCircle.setFill(Color.rgb(255, 0, 0));
	    }

	    public HBox getBox()
	    {
	        return hBox;
	    }
}
