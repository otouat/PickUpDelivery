package modele;

public class Pair<Integer extends Comparable<Integer>, Noeud> implements Comparable<Pair<Integer, Noeud>> {
	Integer integer;
	Noeud noeud;

	public Pair(Integer integer, Noeud noeud) {
		this.integer = integer;
		this.noeud = noeud;
	}

	public Integer getInteger() {
		return integer;
	}

	public Noeud getNoeud() {
		return noeud;
	}

	public void setNoeud(Noeud noeud) {
		this.noeud = noeud;
	}

	public void setInteger(Integer integer) {
		this.integer = integer;
	}

	@Override
	public int compareTo(Pair<Integer, Noeud> pair) {
		int compare = this.getInteger().compareTo(pair.getInteger());
		return compare;

	}

}
