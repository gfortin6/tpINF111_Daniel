import javax.swing.JOptionPane;

/*
Le jeu memSuite est un nom inventé dans le cadre du tp1 INF111 H18.  C'est un
jeu de mémoire pour retrouver des suites de cartes cachées (voir énoncé fourni).

La règle du jeu est simple.  Des cartes sont brassées et disposées à l'envers à 
l'utilisateur en N lignes et M colonnes!  L'utilisateur doit sélectionner sans
 erreur des suites de cartes de même sorte.

Le jeu retient les statistiques sur la plus grande séquence obtenue 
(1, 2, 3, ..., n cartes consécutives), le nombre d'essais dans la partie,
le nombre de réussites depuis le début de la partie et le nombre d'essais en
moyenne.

Les régles sont les suivantes :
	- Une carte est retournée.  Impossible d'annuler.
	- Tant qu'il y a une suite (1,2,3 ou 3,4,5, ou 10,V,D,R ...) les cartes 
	  restent dévoilées.  Le jeu se termine lorque toutes les cartes sont 
	  dévoilées.
	- Il faut un minimum de 2 cartes pour une suite.
	- Une carte seule de la mauvaise couleur compte pour un essai.
	- Une suite compte pour un essai.
	- Il n'y a pas de suivante au ROI et L'AS vient avant 2.


Le code ne dépasse pas 80 colonnes.
Remarquez l'aération du code (l'espace entre les lignes).

 * Auteur: Pierre Bélisle
 * Version : copyright H2018
 * Révision : Frédérick Simard et Simon Pichette 
 *
 */

public class DemarrerMemSuite {

	/*
	 * Stratégie globale : On utilise les SP des différents modules pour obtenir
	 * une grille GUI dont on se sert pour afficher les cartes du jeu. 
	 * 
	 * C'est ici qu'on initialise le jeu et qu'on gère la boucle principale qui 
	 * se termine si l'utilisateur quitte.  S'il réussit, un message de 
	 * félicitations est donné et certaines statistiques sont initialisées.
	 * 
	 * Pour gèrer les clics, il n'y a que 2 possibilités. Un clic sur un 
	 * bouton de menu ou sur une carte. 
	 */
	public static void main(String[] args) {
			
		// Création de l'interface graphique qui permet de voir les cartes 
		// et de jouer.
		GrilleGui gui = new GrilleGui(Constantes.NB_SORTES, 
				Constantes.NB_CARTES/Constantes.NB_SORTES);

		// Les statistique à maintenir.
		Stats stats = new Stats();		

		// Il faut afficher un jeu neuf avant chaque nouvelle partie.  Pour ne 
		// pas avoir à réouvrir les fichiers à chaque fois, on garde le jeu 
		// neuf et on travaille sur une copie.
		Carte[] jeuNeuf = UtilitaireSysteme.obtenirJeuCartesNeuf();
		Carte[] cartesAffichees = UtilitaireTableauCartes.copieDuJeu(jeuNeuf);
		

		// Retient l'état du jeu.
		EtatJeu etatJeu = new EtatJeu();
		
		// Création du tableau de séquences avec le maximum possible. 
		etatJeu.tabSequence =  new int[Constantes.CARTES_PAR_SORTES];

		// Boucle qui se termine si l'utilisateur quitte 
		// en cliquant sur X ou le bouton quitter.
		while(!etatJeu.partieTerminee){

			// à faire entre chaque partie.
			etatJeu.ilYaSequence = true;
			etatJeu.longueurSequence = 0;
			stats.nbEssaieActuel = 0;			


			// Brasse les cartes, affiche le jeu et le cache ensuite.
			UtilitaireJeu.initialiserJeu(jeuNeuf, cartesAffichees, gui, etatJeu);

			/*
			 * Tant que toutes les cartes ne sont pas tournées ou que 
			 * l'utilisateur n'a  pas quitté, on continue.
			 * 
			 * Se lit aussi : Tantque toutes les cartes ne sont pas toutes 
			 *                tournées et que la partie n'est pas terminée.
			 * 
			 */
			while(!(UtilitaireTableauCartes
							.toutesLesCartesSontTournee(cartesAffichees)  || 
											etatJeu.partieTerminee)) {

				// Procédure locale.
				gererClic(jeuNeuf, cartesAffichees, gui, stats, etatJeu);
			}	
			
			// Si la boucle s'est terminée, c'est parce que toutes les cartes 
			// sont tournées ou que l'utilisateur a annulé.  S'il n'a pas 
			// annulé, on le félicite et on se prépare à la prochaine partie.
			if(!etatJeu.partieTerminee) {

				JOptionPane.showMessageDialog(null, 
						"Bravo, vous avez " + 
								++ stats.nbReussites + " réussite(s)");

//				UtilitaireStats.ajusterStatsNouvellePartie(stats);
				}			
		}
		
		// Ferme le GUI et termine l'application.
		System.exit(0);
}
	/*
	 * Permet à l'utilisateur de cliquer sur une carte ou sur un des 
	 * boutons du menu.
	 */
	private static void gererClic(Carte[] jeuNeuf, 
			Carte[] cartes, GrilleGui gui,
			Stats stats, 
			EtatJeu etatJeu) {

		etatJeu.partieTerminee = false;

		// Laisse le temps à l'utilisateur de cliquer (important).
		gui.pause(1);

		// On effectue un tour s'il y a eu un clic  sur une case.
		// Une seule carte est traitée à chaque tour.
		if(gui.caseEstCliquee()){

			UtilitaireJeu.effectuerUnTour(cartes, gui, stats, etatJeu);
		}

		//L'utilisateur a cliqué sur un des boutons d'option, on g�re le menu.
		else if(gui.optionMenuEstCliquee()) {

			etatJeu.partieTerminee =
				UtilitaireMenu.gererMenu(jeuNeuf, cartes, gui, stats, etatJeu);
		}
	}	
}