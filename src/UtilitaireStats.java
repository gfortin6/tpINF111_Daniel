import javax.swing.JOptionPane;

/**
 * 
 * @author Daniel
 * @version Hiver 2018
 */

public class UtilitaireStats {
	
	public static void montrerStats (Stats stats) {
		JOptionPane.showMessageDialog(null, "Nombre d'essai(s) actuel: " + ++ stats.nbEssaieActuel + "cartes(s)");
		JOptionPane.showMessageDialog(null, "La plus grande séquence: " + ++ stats.grandeSequence + "cartes(s)");
		JOptionPane.showMessageDialog(null, "Nombre de réussites: " + ++ stats.nbReussites + "partie(s) consécutive(s)");
		JOptionPane.showMessageDialog(null, "Nombre d'essais(s) en moyenne par parties : " + ++ stats.nbEssaiesTotal );

	}
}
