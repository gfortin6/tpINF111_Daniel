/**
 * Offre des fonctions utilitaires communes au projet.
 * 
 * @author pbelisle
 * @version mars 2009
 *
 */
public class UtilitaireFonction {

	/**
	 * Retourne une nombre aléatoire dans un intervalle entier donné.
	 * 
	 * @param min La plus petite valeur possible.
	 * @param max La plus grande valeur possible.
	 * 
	 * @return Un nombre entre min et max (inclusivement).
	 */
	public static int alea(int min, int max){
		
		
		// On reçoit une valeur entre 0 et 1 qu'on déplace entre min et max.
		return (int) Math.round(Math.random()* (max - min) + min);
	}	

}
