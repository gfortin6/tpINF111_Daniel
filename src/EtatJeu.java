/*
 * Durant la partie, les différents sous-programmes doivent maintenir ces 
 * attributs qui donnent l'état du jeu.
 * 
 * Auteur: Pierre Bélisle
 * Version : copyright H2018
 * Révision : Frédérick Simard et Simon Pichette 
 */
public class EtatJeu{
	
    	// Mis à true si l'utilisateur clique sur le bouton pour quitter.
		public boolean partieTerminee;
		
		// Permet de retenir entre les sous-programme s'il y a une séquence de 
		// cartes sélectionnées par l'utilisateur (plus de 2 cartes).
		public boolean ilYaSequence;

		// On retient toutes les positions de cartes qui font partie d'une 
		// séquence.
		public int[] tabSequence;
		public int longueurSequence;
		
		// Le pointage depuis le démarrage du jeu.
		public int pointage;
		
		
		// Le nombre de fois que le joueur peut demander de voir la prochaine 
		// carte de la s�quence.
		public int nbIndices =  Constantes.NB_INDICE_DEPART;
		

}
	
