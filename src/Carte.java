import javax.swing.Icon;

/*
 * Représente une carte à jouer.
 * 
 * Spécial : Contient l'image correspondante à afficher.
 * 
 * Auteur: Pierre Bélisle
 * Version : copyright H2018
 * Révision : Frédérick Simard et Simon Pichette 
 */
public class Carte {
	
	public int numero;  // Le numéro de carte de Constantes.AS à Constantes.ROI.
	
	public Constantes.Sorte couleur;  // COEUR À PIQUE.
	
	public Icon image; // L'image de la carte à montrer.
	
	public boolean visible = true; // Vrai si la carte est tournée vers le haut.

	// DEBUG
	public String toString() {
		return numero + image.toString() + couleur.toString();
	}
}
