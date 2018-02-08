/*
 * Utilitaire qui contient des SP pour gérer des tableaux de cartes dans le cadre 
 * du tp1INF111H18  (voir énoncé fourni.)
 * 
  * Auteur: Pierre Bélisle
 * Version : copyright H2018
 * Révision : Frédérick Simard et Simon Pichette 
*/
public class UtilitaireTableauCartes {

	/*
	 * Les cartes se suivent si elles sont de la même couleur et que :
	 * 
	 * Si le paramètre "visible" est à true.
	 * 
	 * - il y a une différence de 1 entre les deux cartes (8-9 9-8).
	 * 
	 * Sinon - le numéro de la 2ième carte est exactement un de plus que la 1ière.
	 * 
	 * L'AS ne suit pas le ROI
	 * 
	 * ***On s'en sert pour
	 */
	public static boolean cartesSeSuivent(Carte c1, Carte c2, boolean reversible) {

		boolean couleurPareil = c1.couleur == c2.couleur;

		// L'opérateur ternaire c'est qu'on vérifie la suite des deux côtés ou non.
		return couleurPareil && ((reversible) ? Math.abs(c1.numero - c2.numero) == 1 : c1.numero + 1 == c2.numero);
	}

	/**
	 * Cette méthode indique si toutes les cartes ont été tournées par
	 * l'utilisateur. Si tel est le cas, cela indique que la partie est terminé et
	 * que le joueur a gagné
	 * 
	 * @param cartesAffichees
	 * @return partieTerminée
	 */
	public static boolean toutesLesCartesSontTournee(Carte[] cartesAffichees) {

		boolean partieFini = false;
		int nombreDeCartesNull = 52;
		//Valider le nombre de cartes retournées
		for (int pos = 0; pos < cartesAffichees.length; pos++) {
			if (cartesAffichees[pos] == null)
				nombreDeCartesNull--;
		}
		if (nombreDeCartesNull == 0) {
			partieFini = true;
		}
		return partieFini;
	}

	/**
	 * Cette méthode permet de changer le champ .visible de toutes les cartesdu
	 * tableau de cartes
	 * 
	 * @param cartes
	 * @param visibilite
	 */
	public static void modifierVisibilite(Carte[] cartes, boolean visibilite) {

		for (int i = 0; i < cartes.length; i++) {
			cartes[i].visible = visibilite;
		}

	}

	/**
	 * Cette méthode permet une copie profonde du tableau de cartes
	 * 
	 * @param jeuNeuf
	 * @return copieJeu
	 */
	public static Carte[] copieDuJeu(Carte[] jeuNeuf) {

		Carte[] copieJeu = new Carte[jeuNeuf.length];

		for (int pos = 0; pos < jeuNeuf.length; pos++) {
			// jeuNeuf[pos].visible = true;
			copieJeu[pos] = jeuNeuf[pos];
		}
		return copieJeu;
	}

	/**
	 * brassage par position aléatoire. Mélange dans un tableau qui dit si la case
	 * est occupée ou non. Une fois toutes les cases occupées, les données du
	 * tableau sont copiées dans le tableau
	 * 
	 * @param cartes
	 */
	public static void brasserCartesRandom(Carte[] cartes) {
		Carte[] temp = new Carte[cartes.length];

		int posTemp;

		for (int pos = 0; pos < cartes.length; pos++) {
			boolean carteValide = false;
			while (!carteValide) {

				posTemp = UtilitaireFonction.alea(0, 51);
				if (temp[posTemp] == null) {

					temp[posTemp] = cartes[pos];
					carteValide = true;
				}
			}
		}

		for (int i = 0; i < cartes.length; i++) {
			cartes[i] = temp[i];
		}
	}

	/**
	 * Méthode de brassage qui subdivice les cartes entre 6 et 8 paquets on met un
	 * paquet dans un autre jusqu'à ce qu'il ne reste plus qu'un paquet
	 * 
	 * @param cartes
	 */
	public static void brasserCartesRandomPaquet(Carte[] cartes) {

		/**
		 * des variables nécessaires pour generer le nombre de paquets
		 */
		int validitePaquets6To10 = cartes.length;
		int compteur = 0;
		boolean ajoutPaquets = true;
		int paquetActuel;
		int[] taillePaquets = new int[Constantes.NB_CARTES];

		while (!ajoutPaquets) {

			// garde en mémoire temporelle la taille du paquet
			paquetActuel = UtilitaireFonction.alea(6, 8);

			/**
			 * Met la valeur du paquet dans un tableau pour ne pas perdre la taille du
			 * paquet précedent ou ça écrase la valeur d'un tableau qui represente le n_ième
			 * paquet
			 */
			taillePaquets[compteur] = paquetActuel;

			// reduit le nombre des cartes disponibles pour les
			// prochains paquets
			validitePaquets6To10 -= paquetActuel;

			/**
			 * Si le nombre des cartes couvertes par les paquets depassent le nombre des
			 * cartes du paquet, on réset les compteurs pour recommencer la boucle
			 */
			if (validitePaquets6To10 < 0) {
				compteur = 0;
				validitePaquets6To10 = cartes.length;
			}
			/**
			 * Le taille des paquets couvrent la totalité des cartes. On peut sortir de la
			 * boucle
			 */
			else if (validitePaquets6To10 == 0) {
				ajoutPaquets = false;
			}
		}
		// Tableau 2D qui contient les autres paquets
		Carte[][] paquetsCartes = new Carte[9][Constantes.NB_CARTES];

		// Copie le jeu de cartes dans le paquet qui suit les autres
		for (int j = 0; j < cartes.length; j++) {
			paquetsCartes[compteur + 1][j] = cartes[j];
		}

	}

	public static void brasserCartes(Carte[] cartes) {
		// Genère un nombre alléatoire pour brasser les cartes
		int nombreDeBrassages = UtilitaireFonction.alea(0, 1000);

		Carte[] tableau1;
		Carte[] tableau2;
		// Algorithme pour brasser
		for (int i = 0; i < nombreDeBrassages; i++) {

			// Mélange le paquet initial
			cartes = melanger(cartes);

			int taillePaquet1 = UtilitaireFonction.alea(20, 30);
			int taillePaquet2 = Constantes.NB_CARTES - taillePaquet1;

			tableau1 = new Carte[taillePaquet1];
			tableau2 = new Carte[taillePaquet2];
		}
	}

	/**
	 * Met les cartes de la fin et les mets au début en les déplaçant un certain
	 * nombre de fois
	 * 
	 * @param cartes
	 * @param nombreDeBrassages
	 * @return
	 */
	private static Carte[] melanger(Carte[] cartes) {

		int nombreDeBrassages = UtilitaireFonction.alea(0, 1000);
		int cartesDeplacer;
		// Carte de fin qui est copié pour le passer au début à chaque fin de
		// déplacement
		Carte temp = cartes[0];

		// Le nombre des fois à répéter
		for (int i = 0; i < nombreDeBrassages; i++) {
			cartesDeplacer = UtilitaireFonction.alea(3, 10);

			// nombre de déplacement à faire aléatoire
			for (int k = 0; k < cartesDeplacer; k++) {

				// Déplacement de toutes les cartes
				for (int j = (cartes.length - 1); j > 0; j--) {

					// Retient la dernière carte pour la copier au début
					// lors de la fin de la boucle
					if (j == (cartes.length - 1)) {
						temp = cartes[j];

					} else
						cartes[j] = cartes[j - 1];

				}
				// Carte de la fin vers le début
				cartes[0] = temp;
			}

		}
		return cartes;
	}
}
