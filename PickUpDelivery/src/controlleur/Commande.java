package controlleur;

public interface Commande {
	/*
	 * Execute la commande this
	 */
	void doCde();
	
	/*
	 * Execute la commande invrse a this
	 */
	void undoCde();
}



