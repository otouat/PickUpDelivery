package vue;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import modele.Livraison;
import modele.Troncon;

public class Data {
	 @FXML
	    private Pane paneItem;
	    @FXML
	    private Label title_label;
	    @FXML
	    private Text text1;
	    @FXML
	    private Circle shapeCircle;
	    
	    
	    @FXML
	    private Label title_label2;
	    @FXML
	    private Text text2;
	    @FXML
	    private Rectangle rectangleShape;
	    
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
	    		adresse += tronconList.get(i).GetNomRue() + " // " + "\n";
	    	}
	    	
	    	
	    	title_label.setText( " Pickup id n°" + idNoeud );
	    	text1.setText(adresse);
	        shapeCircle.setFill(Color.rgb(255, 0, 0));
	        
	        
	        
	        
	        String idNoeud2 = livraison.getNoeudLivraison().GetIdNoeud();
	    	
	    	String adresse2 ="";
	    	List<Troncon> tronconList2 = livraison.getNoeudLivraison().GetTronconsDepuisLeNoeud();
	    	
	    	for(int i=0;i<tronconList2.size();i++) {
	    		adresse2 += tronconList2.get(i).GetNomRue() + " // "+ "\n";
	    	}
	    	
	    	
	    	title_label2.setText( " Delivery id n°" + idNoeud2 );
	    	text2.setText(adresse2);
	        rectangleShape.setFill(Color.rgb(255, 0, 0));
	    }

	    public Pane getPane()
	    {
	        return paneItem;
	    }
}
