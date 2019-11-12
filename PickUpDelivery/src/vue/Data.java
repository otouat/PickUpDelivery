package vue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
	   // @FXML
	   // private Circle shapeCircle;
	    @FXML
	    private Pane paneShape;
	    
	    
	    
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
	    
	    public void setInfo(LivraisonDisplay livraisonDisplay)
	    {	
	    		if(livraisonDisplay.getIsPickup()) {
	    			String idNoeud = livraisonDisplay.getNoeud().GetIdNoeud();
		    		String adresse ="";
			    	List<Troncon> tronconList = livraisonDisplay.getNoeud().GetTronconsDepuisLeNoeud();
			    	List<String> adressesList = new ArrayList<>();
			    	for(int i=0;i<tronconList.size();i++) {
			    		if((!adressesList.contains(tronconList.get(i).GetNomRue())) && adressesList.size()<2) {
			    			adresse += tronconList.get(i).GetNomRue() + " // " + "\n";
			    			adressesList.add(tronconList.get(i).GetNomRue());
			    		}
			    		
			    	}
			    	
			    	title_label.setText( " Pickup id n°" + idNoeud );
			    	title_label.setTextFill(livraisonDisplay.getColor());
			    	text1.setText(adresse);
			    	// shapeCircle.setFill(VueDemandeLivraison.couleurs.get(row%15));
			    	Circle circle = new Circle(9);
			    	circle.setCenterX(9);
			    	circle.setFill(livraisonDisplay.getColor());
			    	paneShape.getChildren().add(circle);
	    		} else {
	    			String idNoeud = livraisonDisplay.getNoeud().GetIdNoeud();
		    		
		    		String adresse ="";
			    	List<Troncon> tronconList = livraisonDisplay.getNoeud().GetTronconsDepuisLeNoeud();
			    	List<String> adressesList = new ArrayList<>();
			    	for(int i=0;i<tronconList.size();i++) {
			    		if((!adressesList.contains(tronconList.get(i).GetNomRue())) && adressesList.size()<2) {
			    			adresse += tronconList.get(i).GetNomRue() + " // " + "\n";
			    			adressesList.add(tronconList.get(i).GetNomRue());
			    		}
			    		
			    	}
			    	
			    	title_label.setText( " Delivery id n°" + idNoeud );
			    	title_label.setTextFill(livraisonDisplay.getColor());
			    	text1.setText(adresse);
			    	// shapeCircle.setFill(VueDemandeLivraison.couleurs.get(row%15));
			    	Rectangle rectangle = new Rectangle(16,16);
			    	rectangle.setX(2);
			    	rectangle.setY(-3);
			    	rectangle.setFill(livraisonDisplay.getColor());
			    	paneShape.getChildren().add(rectangle);
	    		}
	    	
	    		
		    	
	    	
	    }

	    public Pane getPane()
	    {
	        return paneItem;
	    }
}
