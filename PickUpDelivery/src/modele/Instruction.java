package modele;

public class Instruction {
	private String nomRue;
	private double longueur;
	private String direction;

	public Instruction(String nomRue, String direction) {
		this.nomRue = nomRue;
		this.direction = direction;
	}
}