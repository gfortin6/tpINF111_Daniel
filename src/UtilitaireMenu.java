/*
 * Classe qui contient les SP pour gÈrer les boutons d'options
 * de menu montrÈs dans le GUI.
 * 
 * S'il y a ajout de bouton dans le module Constantes, il faut modifier cette 
 * classe et y ajouter le comportement dÈsirÈ.
 * 
 * Auteur: Pierre BÈlisle
 * Version : copyright H2018
 * RÈvision : FrÈdÈrick Simard et Simon Pichette 
 *
 */
public class UtilitaireMenu {
	
	
	/*
	 * VÈrifie quelle option de menu a ÈtÈ choisie et dÈmarre la bonne
	 * fonctionnalitÈ.
	 * 
	 * @param jeuNeuf Un jeu pas brasssÈ
	 * @param cartes  Le jeu de cartes affichÈes
	 * @param gui La fenÍtre qui  montre les cartes et les boutons
	 * @param stats Les statistiqwues ‡ maintenir
	 * @param etatJeu Les dÈtails sur le jeu.
	 * @return true si l'utilisateur a cliquÈ sur le bouton pour quitter.
	 */
	public static boolean gererMenu(Carte[] jeuNeuf, 
			Carte[] cartes, 
			GrilleGui gui, 
			Stats stats,
			EtatJeu etatJeu){

		/*
		 * StratÈgie : Agit simplement comme distributeur de t‚che selon 
		 *             l'option du menu choisie par l'utlisateur dand le gui.
		 *                  
		 *             On a crÈÈ un sous-programme pour chaque situation
		 *             mÍme si c'Ètait possible de rÈutiliser en une seule ligne
		 *             de code (voir: montrer_stats).
		 *                  
		 *             Doit Ítre modifiÈ si on ajoute des options de menu dans 
		 *             le tableau-constante TAB_OPTIONS_MENU.
		 */
		
		boolean partieTerminee = false;
		
		String reponse = gui.getOptionMenuClique();
		
		// Selon les versions de Java, switch-case sur des String ne 
		// fonctionne pas toujours.
		if (reponse. equals(Constantes.TAB_OPTIONS_MENU[Constantes.NOUVELLE_PARTIE])) {

			UtilitaireJeu.initialiserJeu(jeuNeuf, cartes,gui, etatJeu);
		}
		
		else if(reponse.equals(Constantes.TAB_OPTIONS_MENU[Constantes.CARTE])) {

			UtilitaireJeu.montrerLesCartes(cartes, gui, etatJeu);
				
		}

		else if(reponse.equals(Constantes.TAB_OPTIONS_MENU[Constantes.INDICE])) {

				
			UtilitaireJeu.montrerIndices(cartes, gui, etatJeu);
		}

		else if(reponse.equals(Constantes.TAB_OPTIONS_MENU[Constantes.STATS])) {

//			UtilitaireStats.montrerStats(stats);
		}
		
		else if(reponse.equals(Constantes.TAB_OPTIONS_MENU[Constantes.QUITTER])) {

			partieTerminee = true;
		}
		
		return partieTerminee;
	}
}
