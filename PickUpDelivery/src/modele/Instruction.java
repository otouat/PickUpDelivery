/**
 * Une instruction contient le nom de rue ainsi que la longueur à suivre à partir d'un noeud,
 * puis une direction à suivre quand il arrive au noeud prochain. Elle contient la chose à faire
 * (récupérer ou livrer un colis) si nécessaire.
 *  @author Tianming
 */

package modele;

public class Instruction {
	private String nomRueCourant;
	private String nomRueSuivant;
	private double longueur;
	private String direction;
	private String toDo;
	private Noeud noeudCourant;
	private Noeud noeudSuivant;

	/**
	 * Constructeur d'une instruction sans contenant une chose à faire. Il calcule
	 * le nom de rue ainsi que la longueur à suivre à partir d'un noeud, puis une
	 * direction à suivre quand il arrive au noeud prochain.
	 * 
	 * @param noeudCourant : noeud de départ de cette instruction
	 * @param noeudSuivant : noeud d'arrivée de cette instruction
	 * @param noeudApres   : le noeud suivant de noeudSuivant dans la tournée
	 *                     calculée
	 * @param plan         : plan chargé
	 */
	public Instruction(Noeud noeudCourant, Noeud noeudSuivant, Noeud noeudApres, Plan plan) {
		Troncon tronconCourant = plan.ChercherTronconDepuisDeuxNoeuds(noeudCourant.GetIdNoeud(),
				noeudSuivant.GetIdNoeud());
		this.nomRueCourant = tronconCourant.GetNomRue();
		this.longueur = tronconCourant.GetLongueur();
		this.noeudCourant = noeudCourant;
		this.noeudSuivant = noeudSuivant;
		Troncon tronconSuivant = plan.ChercherTronconDepuisDeuxNoeuds(noeudSuivant.GetIdNoeud(),
				noeudApres.GetIdNoeud());
		this.nomRueSuivant = tronconSuivant.GetNomRue();
		this.direction = calculerDirection(noeudSuivant, noeudCourant, noeudApres);

	}

	/**
	 * Constructeur d'une instruction qui contenant une chose (récupérer ou livrer
	 * un colis) à faire. Il calcule le nom de rue ainsi que la longueur à suivre à
	 * partir d'un noeud, puis la chose à faire et la direction à suivre
	 * 
	 * @param noeudCourant : noeud de départ de cette instruction
	 * @param noeudSuivant : noeud d'arrivée de cette instruction
	 * @param plan         : plan chargé
	 * @param toDo         : la chose à faire quand on arrive au noeudSuivant
	 * @param direction    : la direction à suivre après noeudSuivant
	 */
	public Instruction(Noeud noeudCourant, Noeud noeudSuivant, Plan plan, String toDO, String direction) {
		Troncon tronconCourant = plan.ChercherTronconDepuisDeuxNoeuds(noeudCourant.GetIdNoeud(),
				noeudSuivant.GetIdNoeud());
		this.noeudCourant = noeudCourant;
		this.noeudSuivant = noeudSuivant;
		this.nomRueCourant = tronconCourant.GetNomRue();
		this.longueur = tronconCourant.GetLongueur();
		this.direction = direction;
		if (toDO.contentEquals("recuperer")) {
			this.toDo = "recuperer un colis";
		} else if (toDO.contentEquals("livrer")) {
			this.toDo = "livrer un colis";
		} else if (toDO.contentEquals("termine")) {
			this.toDo = "termine";
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

	public String getToDo() {
		return toDo;
	}

	public void setToDo(String toDo) {
		this.toDo = toDo;
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

	/**
	 * Cette méthode calcule la direction d'un troncon à l'autre. Elle calcule
	 * d'abord l'angle entre ces deux troncons puis calcule la direction à partir de
	 * l'angle calculée
	 * 
	 * @param cen    : le noeud commune de deux troncons
	 * @param noeud1 : le noeud de départ
	 * @param noeud2 : le noeud d'arrivée
	 * @return : la direction calculée
	 */
	public String calculerDirection(Noeud cen, Noeud noeud1, Noeud noeud2) {
		if (this.nomRueCourant.contentEquals(this.nomRueSuivant)) {
			return "aller tout droit";
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
		if (angle == 180) {
			return "aller tout droit";
		} else if (angle == 0) {
			return "retourner";
		} else if (angle > 0) {
			return "tourner a� gauche";
		} else {
			return "tourner a� droite";
		}
	}

	public String toString() {
		if (toDo == null) {
			return "Suivre " + this.nomRueCourant + ", marcher " + (int)this.longueur + " metres, " + "puis "
					+ this.direction + " et atteindre " + this.nomRueSuivant + "\n"; //
		} else {
			if (toDo.contentEquals("termine")) {
				return "Suivre " + this.nomRueCourant + ", marcher " + (int)this.longueur + " metres, " + "puis "
						+ "atteindre noeud" + noeudSuivant.GetIdNoeud()
						+ " et arriver a� l'entrepot.\n Tournee est terminee";
			} else {
				return "Suivre " + this.nomRueCourant + ", marcher " + (int)this.longueur + " metres, " + "puis "
						+ "atteindre noeud" + noeudSuivant.GetIdNoeud() + " et " + this.toDo + ", puis "
						+ this.direction+ "\n" ;
			}
		}
	}

}