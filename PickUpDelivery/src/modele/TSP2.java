package modele;

import java.util.ArrayList;
import java.util.Iterator;

public class TSP2 extends TSP1{
	
	@Override
	protected int bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		Integer somme=0;
		Integer minCoutNdVoisin=Integer.MAX_VALUE;
		
		
		for(Integer i : nonVus) {
			if(cout[sommetCourant][i]<minCoutNdVoisin) {
				minCoutNdVoisin=cout[sommetCourant][i];
			}
		}
		somme+=minCoutNdVoisin; // ajout du cout du chemin sommetCourant vers le noeud le plus proche
		for(Integer i : nonVus) {
			Integer min=cout[i][0];
			for(Integer j : nonVus) {
				if( (cout[i][j]<min) && (i!=j)) {
					min=cout[i][j];
				}
			somme+=min+duree[i];
			}
		}
		
		return somme; 
		//retourne la borne inferieur du cout des permutation passant
		//par tous les noeuds non vus et terminant par l'entrepot
	}
}
