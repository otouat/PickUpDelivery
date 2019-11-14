package controlleur;

public interface Commande {
	/*
	 * Execute la commande this
	 */
	void doCommande();
	//
	/*
	 * Execute la commande invrse a this
	 */
	void undoCommande();
}



