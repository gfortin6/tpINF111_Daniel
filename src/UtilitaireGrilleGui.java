
public class UtilitaireGrilleGui {
	
	/**
	 * Cette méthode permet
	 * @param gui
	 * @param cartes
	 */
	public static void afficherCartes (GrilleGui gui, Carte[] cartes) {
		
		//regarde la position des cartes
		int pos=0;
		//Cette double 
		for (int i = 0; i < gui.getNbLignes(); i++) {
			
			for (int j = 0; j < gui.getNbColonnes(); j++) {
				
				
				if (cartes[pos].visible = true)
					gui.setImage(i, j, cartes[pos].image);
				
				else 
					gui.setImage(i, j, null);
				pos++;
			}
		}
	}
}
