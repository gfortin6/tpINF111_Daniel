import javax.swing.JOptionPane;

/*
 * Classe qui simule les règles du jeu décrit dans l'énoncé tp1H18Inf111.
 * 
 * Auteur: Pierre Bélisle
 * Version : copyright H2018
 * Révision : Frédérick Simard et Simon Pichette 
 */
public class UtilitaireJeu {

	/*
	 * Localise le code qui initialise le jeu de cartes au départ en brassant les 
	 * cartes.  Affiche le jeu avant de la cacher.
	 */
	public static void initialiserJeu(Carte[] jeuNeuf,
			Carte[] cartes, 
			GrilleGui gui,
			EtatJeu etatJeu) {
		
		
		etatJeu.longueurSequence = 0;
		etatJeu.nbIndices = Constantes.NB_INDICE_DEPART;

		// Choisir la méthode de brassage.
		brasserCartesSelonChoix(cartes); // à activer quand cette fonction sera écrite

		// On affiche le jeu neuf.
		UtilitaireGrilleGui.afficherCartes(gui, jeuNeuf); // à activer quand cette fonction sera écrite

		// On montre ce message qu'une fois par partie.
		JOptionPane.showMessageDialog(null, 
				"Vous avez quelques secondes pour mémoriser les cartes");

		// On mets les cartes non visible avant de réafficher..
		UtilitaireTableauCartes.modifierVisibilite(cartes, false);

		montrerLesCartes(cartes, gui, etatJeu);
		

	}

	/*
	 * S'occupe des appels nécessaires pour s'occuper de la carte qui a été cliquée.
	 */
	public static void effectuerUnTour(Carte[] cartes, 
			GrilleGui gui, 
			Stats stats, 
			EtatJeu etatJeu) {

		/*
		 * On affiche entre chaque traitement car la visibilité de la carte peut avoir été
		 * modifiée.
		 */

		// La position de la grille où l'utilisateur a cliqué.
		Coordonnee position = gui.getPosition();

		// On obtient la corespondance de 2D à 1D.
		int posColonne = position.ligne * gui.getNbColonnes() + position.colonne;

		// Si valide, la carte est mise dans le tableau de séquence de EtatJeu.
		UtilitaireJeu.retenirCarteUtilisateurSiValide(cartes, 
				posColonne, 
				stats,
				etatJeu);

		UtilitaireGrilleGui.afficherCartes(gui, cartes);

		// On traite les autres cas.
		UtilitaireJeu.gererSequence(cartes, 
				posColonne,
				stats,
				etatJeu);

		UtilitaireGrilleGui.afficherCartes(gui, cartes);
	}	

	/*
	 * Selon la posColonne, on met la carte visible et on la retient dans le tableau
	 * qui retient les séquences dans etatJeu.  
	 * 
	 * Si la carte est déjà visible, un message d'avis est donné.  
	 * 
	 * Dans tous les cas, le nombre d'essais actuel est incrémenté.
	 * 
	 * @param cartes
	 * @param gui
	 * @param stats
	 * @param etatJeu
	 * 
	 */
	public static void retenirCarteUtilisateurSiValide(Carte[] cartes, 
			int posColonne, 
			Stats stats,
			EtatJeu etatJeu) {

		// L'utilisateur clique sur une carte déjà montrée.
		if (cartes[posColonne].visible) {

			JOptionPane.showMessageDialog(null, "Vous avez perdu un coup");

		}

		// Si le clique est sur une carte tournée (valide).
		else{			

			// On retourne la carte.
			cartes[posColonne].visible = true;

			// On retient la position de la carte pour vérifier les séquences.
			etatJeu.tabSequence[etatJeu.longueurSequence] = posColonne;			

		}

		// Il y a eu essai, valide ou non.
		stats.nbEssaieActuel++;
	}

	/**
	 * 
	 * @param cartes
	 * @param gui
	 * @param stats
	 * @param etatJeu
	 */
	public static void gererSequence(Carte[] cartes, 
			int posColonne, 
			Stats stats, 
			EtatJeu etatJeu) {

		/* Si la longueur de la séquence est de 0 c'est la première carte d'une séquence.

		    Est-ce que les cartes se suivent ?  On regarde la carte cliquée et la
		   dernière de la séquence.  Il est important d'affecter le champ booléen 
		    pour l'état du jeu.
		*/
		
		// Pour le premier tour.
		if(etatJeu.longueurSequence == 0) {

			etatJeu.longueurSequence++;						
		}

		// Si ce n'est pas la première carte.
		else{

			// On obtient l'indice de la dernière carte de la séquence (On est certain qu'il
			// y en a une).
			int indiceDernierCarte = etatJeu.tabSequence[etatJeu.longueurSequence-1];

			// On retient si les deux cartes se suivent de la même couleur.
			// Sauf le premier tour, on regarde la carte actuelle avec la dernière carte.
			etatJeu.ilYaSequence = 
					UtilitaireTableauCartes.cartesSeSuivent(cartes[posColonne] ,
							cartes[indiceDernierCarte], true);

			if(etatJeu.ilYaSequence) {				

				etatJeu.longueurSequence++;	
			}

			else{

				JOptionPane.showMessageDialog(null,"Bris de séquence");

				cartes[posColonne].visible = false;

				// Si la séquence est brisée à la deuxième carte, il faut 
				// retourner la première.
				if(etatJeu.longueurSequence == 1) {
					cartes[etatJeu.tabSequence[0] ].visible = false;		
				}

				etatJeu.longueurSequence = 0;
			}			
		}		

		// On retient toujours la plus grande séquence.
		if(etatJeu.longueurSequence > stats.grandeSequence) {

			stats.grandeSequence = etatJeu.longueurSequence;
		}

	}			
	/*
	 * Montre la prochaine carte de la dernière séquence montrée par l'utilisateur.  
	 * S'il n'y a pas de séquence, on montre une carte cachée au hasard.
	 */
	public static void montrerIndices(Carte[] cartes, GrilleGui gui, EtatJeu etatJeu) {

		// S'il n'y a pas eu de séquence, on montre une carte cachée au hasard.
		if(etatJeu.longueurSequence == 0) {
			montrerProchaineCarte(cartes, gui, etatJeu);
		}

		else {
			int numCarte = etatJeu.tabSequence[etatJeu.longueurSequence- 1];

			// On parcourt la grille à la recherche de la carte qui suit la dernière
			// de la séquence.  On le fait dans un for mais on arrête dès que la carte
			// a été montrée et recachée.
			for(int i = 0; i < cartes.length; i++) {

				// 6-7 n'est pas comme 7-6 qui n'est pas bon.
				if(UtilitaireTableauCartes
						.cartesSeSuivent(cartes[numCarte], cartes[i], false)){

					montrerUneCarte(cartes, i, gui, Constantes.TEMPS_INDICE);

					return;					
				}
			}
		}
	}

	/*
	 * Montre la première plus petite carte d'une séquence.
	 * 
	 * Cherche pour un as.  S'ils sont tous montrés, cherche pour un 2, etc.
	 */
	private static void montrerProchaineCarte(Carte[] cartes, 
			GrilleGui gui, 
			EtatJeu etatJeu) {

		// On parcourt la grille à la recherche de la carte qui suit la dernière
		// de la séquence.  On le fait dans un for mais on arrête dès que la carte
		// a été montrée et recachée.
		for(int i = 0; i < Constantes.CARTES_PAR_SORTES; i++) {

			for(int j = 0; j < cartes.length; j++) {

				if(cartes[j].numero == i + 1 && !cartes[j].visible){

					montrerUneCarte(cartes, j, gui, Constantes.TEMPS_VISIONNEMENT/2);

					return;
				}
			}
		}

	}

	/*
	 * On met visible la carte du jeu dont on a reçu l'indice, dans la grille.  Cela
	 * l'espace du temps donné en millisecondes.
	 */
	private static void montrerUneCarte(Carte[] cartes, int laquelle,
			GrilleGui gui,
			int temps) {

		cartes[laquelle].visible = true;

		UtilitaireGrilleGui.afficherCartes(gui, cartes);

		gui.pause(temps);

		cartes[laquelle].visible = false;

		UtilitaireGrilleGui.afficherCartes(gui, cartes);
	}


	
	/*
	 * Demande à l'utilisateur la méthode de brassage à utiliser.
	 * 
	 */
	private static void brasserCartesSelonChoix(Carte[] cartes) {
		
		String[] options = {"Méthode aléatoire",
				"Méthode en paquets",
				"Méthode carte brassée"};
		
		String reponse = (String) JOptionPane.showInputDialog(null, 
				"Sélectionnez la méthode de brassage des cartes", 
				"Méthode de brassage", 
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				options, 
				0);
		
		if(reponse.equals(options[Constantes.METHODE_ALEA])){
			
			UtilitaireTableauCartes.brasserCartesRandom(cartes);
		}
		else if(reponse.equals(options[Constantes.METHODE_BRASSER])){
			
			UtilitaireTableauCartes.brasserCartes(cartes);
			
		}
		
		// options[Constantes.METHODE_PAQUETS]
		// (Laisser  à la fin si on ajoute des méthodes de brassage).
		else {
			
			UtilitaireTableauCartes.brasserCartesRandomPaquet(cartes);			
		}
		

		
	}

	/**
	 * Montre les cartes et ensuite remettre le jeu dans son état initial.
	 * 
	 * @param cartes
	 * @param gui
	 * @param etatJeu 
	 */
	public static void montrerLesCartes(Carte[] cartes, 
			GrilleGui gui, 
			EtatJeu etatJeu) {

		/*
		 * Stratégie : On veut montrer toutes les cartes.  S'il y a des cartes qui sont 
		 * déjà tournée, elles doivent le rester quand on recache les cartes à nouveau.
		 * 
		 * - On se fait une copie du jeu avec le champ visible à true qu'on affiche.
		 * 
		 * - On laisse le temps de voir, on  met les cartes de la copie invisible qu'on 
		 *    affiche.
		 *    
		 * - On réaffiche le jeu de cartes original pour voir les cartes qui tournées.
		 */
		
		// La copie des cartes qu'on rend visible avant d'afficher.
		Carte[] tmp = UtilitaireTableauCartes.copieDuJeu(cartes);

		UtilitaireGrilleGui.afficherCartes(gui, tmp);

		// On laisse le temps de voir un peu les cartes.
		gui.pause(Constantes.TEMPS_VISIONNEMENT);

		// On mets les cartes non visible avant de réafficher..
		UtilitaireTableauCartes.modifierVisibilite(tmp, false);
 
		// Revient à effacer l'écran.
		UtilitaireGrilleGui.afficherCartes(gui, tmp);

		// On réaffiche le jeu original.
		UtilitaireGrilleGui.afficherCartes(gui, cartes);	
	}
}
