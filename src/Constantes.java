/*
 * Contient les déclarations de constantes globales pour le tp1 inf111 H18
 * (voir énoncé fourni).
 *
 * Auteur: Pierre Bélisle
 * Version : copyright H2018
 * Révision : Frédérick Simard et Simon Pichette 
 */
public class Constantes {
	
	 // Permettrait de changer le nombre de cartes et de sortes.
	 public static final int NB_CARTES = 52;
	 public static final int NB_SORTES = 4;	 
	 
 	public static final int CARTES_PAR_SORTES = NB_CARTES / NB_SORTES;
 	
 	// Pour la lisibilité.
 	public static final int AS = 1;
 	public static final int ROI = 13;
 	 
	// Pour les cartes en bas de VALET.
 	public static final int NB_DIGITS = 9;
 	 
 	// Sorte.values() pour obtenir les 4 constantes dans un tableau.
 	public  static enum Sorte {COEUR, CARREAU, TREFLE,  PIQUE};
 	
	//Nécessaire à l'affichage des options du menu présenté dans le GUI.
	public static final String[] TAB_OPTIONS_MENU = {"Nouvelle partie",
			"Voir cartes",
			"Prochaine carte",		 
			"Statistiques", 
			"Quitter"};

	// Position dans le tableau des options du menu pour les méthodes 
    // de brassage de cartes.
	public static final int METHODE_ALEA = 0;		
	public static final int METHODE_PAQUETS = 1;		
	public static final int METHODE_BRASSER = 2;		

	
	//Position dans le tableau des options du menu de boutons.
	public static final int NOUVELLE_PARTIE = 0;
	public static final int CARTE = 1;
	public static final int INDICE= 2;
	public static final int STATS = 3;
	public static final int QUITTER= 4;
	
	// En millisecondes.
	public static final int TEMPS_VISIONNEMENT = 3000;
	public static final int TEMPS_INDICE = 200;
		
    // Le nombre de fois que le joueur peut demander de voir la prochaine carte. 
	public static final int NB_INDICE_DEPART = 5;
}