package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TSP1 extends TemplateTSP {

	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int[][] cout, int[] duree, HashMap<Integer, Integer> precedence) {
		return new IteratorSeq(nonVus, sommetCrt, precedence);
	}

	@Override
	protected int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		return 0;
	}
}
