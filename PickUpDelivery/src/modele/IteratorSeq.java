package modele;

import java.util.Collection;
import java.util.Iterator;

public class IteratorSeq implements Iterator<Integer> {

	private Integer[] candidats;
	private int nbCandidats;
	private Integer[][] precedence;

	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * @param nonVus
	 * @param sommetCrt
	 */
	public IteratorSeq(Collection<Integer> nonVus, int sommetCrt){
		this.candidats = new Integer[nonVus.size()];
		nbCandidats = 0;
		for (Integer s : nonVus){
			if(!nonVus.contains(precedence[s][1])) {
				candidats[nbCandidats++] = s;
			}
		}
	}
	
	@Override
	public boolean hasNext() {
		return nbCandidats > 0;
	}

	@Override
	public Integer next() {
		return candidats[--nbCandidats];
	}

	@Override
	public void remove() {}

}
