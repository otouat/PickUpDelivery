package modele;

public class Instruction {
	private String nomRueCourant;
	private String nomRueSuivant;
	private double longueur;
	private String direction;
	private String toDo;
	// private String aFaire;

	public Instruction(Noeud noeudCourant, Noeud noeudSuivant, Noeud noeudApres, Plan plan) {
		Troncon tronconCourant = plan.ChercherTronconDepuisDeuxNoeuds(noeudCourant.GetIdNoeud(),
				noeudSuivant.GetIdNoeud());

		this.nomRueCourant = tronconCourant.GetNomRue();
		this.longueur = tronconCourant.GetLongueur();

		Troncon tronconSuivant = plan.ChercherTronconDepuisDeuxNoeuds(noeudSuivant.GetIdNoeud(),
				noeudApres.GetIdNoeud());
		this.nomRueSuivant = tronconSuivant.GetNomRue();
		this.direction = calculerDirection(noeudSuivant, noeudCourant, noeudApres);
	}

	public Instruction(Noeud noeudCourant, Noeud noeudSuivant, Plan plan, boolean typeNoeud) {
		Troncon tronconCourant = plan.ChercherTronconDepuisDeuxNoeuds(noeudCourant.GetIdNoeud(),
				noeudSuivant.GetIdNoeud());

		this.nomRueCourant = tronconCourant.GetNomRue();
		this.longueur = tronconCourant.GetLongueur();
		if (typeNoeud == true) {
			toDo = "récupérer un colis";
		} else {
			toDo = "livrer un colis";
		}
	}

	public String getNomRueCourant() {
		return nomRueCourant;
	}

	public void setNomRueCourant(String nomRueCourant) {
		this.nomRueCourant = nomRueCourant;
	}

	public String getNomRueSuivant() {
		return nomRueSuivant;
	}

	public void setNomRueSuivant(String nomRueSuivant) {
		this.nomRueSuivant = nomRueSuivant;
	}

	public double getLongueur() {
		return longueur;
	}

	public void setLongueur(double longueur) {
		this.longueur = longueur;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String calculerDirection(Noeud cen, Noeud noeud1, Noeud noeud2) {
		if (this.nomRueCourant.contentEquals(this.nomRueSuivant)) {
			return "tout droit";
		}
		double y1 = noeud1.GetLatitude() - cen.GetLatitude();
		double x1 = noeud1.GetLongitude() - cen.GetLongitude();
		double y2 = noeud2.GetLatitude() - cen.GetLatitude();
		double x2 = noeud2.GetLongitude() - cen.GetLongitude();
		double angle = Math.atan2(y1, x1) - Math.atan2(y2, x2);
		angle = 180 * angle / Math.PI;
		if (Math.abs(angle) > 180) {
			if (angle < 0) {
				angle = 360 + angle;
			} else {
				angle = angle - 360;
			}
		}
		if (angle == 0) {
			return "aller tout droit";
		} else if (angle == 180) {
			return "retourner";
		} else if (angle > 0) {
			return "tourner à gauche";
		} else {
			return "tourner à droite";
		}
	}

	public String toString() {
		if (toDo == null) {
			return "Suivre " + this.nomRueCourant + ", marcher " + this.longueur + " mettres, " + "puis "
					+ this.direction + ", pour atteindre " + this.nomRueSuivant;
		} else {
			return "Suivre " + this.nomRueCourant + ", marcher " + this.longueur + " mettres, " + "puis " + this.toDo;
		}
	}

}