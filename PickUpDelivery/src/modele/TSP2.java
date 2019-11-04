package modele;

import java.util.ArrayList;
import java.util.Iterator;

public class TSP2 extends TSP1{
	
	@Override
	protected int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		return 0;
	}
}
