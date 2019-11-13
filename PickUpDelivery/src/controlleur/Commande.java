/**
 * Interface Commande, qui contient une méthode do et une méthode undo.
 */

package controlleur;

public interface Commande {
	/**
	 * Execute la commande this
	 */
	void doCommande();

	/**
	 * Execute la commande invrse a this
	 */
	void undoCommande();
}
