package vue;

import javafx.scene.paint.Color;
import modele.Livraison;
import modele.Noeud;

public class LivraisonDisplay {
	private Livraison livraison;
	private Boolean isPickup;
	private Color color;
	
	public LivraisonDisplay(Livraison livraison, Boolean isPickup, Color color) {
		this.livraison = livraison;
		this.isPickup = isPickup;
		this.color = color;
	}
	
	public Livraison getLivraison() {
		return livraison;
	}

	public Boolean getIsPickup() {
		return isPickup;
	}
	
	public Color getColor() {
		return color;
	}
}
