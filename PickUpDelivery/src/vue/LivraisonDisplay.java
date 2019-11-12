package vue;

import javafx.scene.paint.Color;
import modele.Livraison;
import modele.Noeud;

public class LivraisonDisplay {
	private Noeud noeud;
	private Boolean isPickup;
	private Color color;
	
	public LivraisonDisplay(Noeud noeud, Boolean isPickup, Color color) {
		this.noeud = noeud;
		this.isPickup = isPickup;
		this.color = color;
	}
	
	public Noeud getNoeud() {
		return noeud;
	}

	public Boolean getIsPickup() {
		return isPickup;
	}
	
	public Color getColor() {
		return color;
	}
	
}
