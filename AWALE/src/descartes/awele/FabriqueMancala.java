package descartes.awele;

import java.util.ArrayList;

public class FabriqueMancala {


	private FabriqueMancala() {

		// Pas besoin de constructeur
	}


	/**
	 * 
	 * @param version
	 * @param joueurs
	 */
	static public IMancala getMancala(final String version, final ArrayList<String> nomsJoueurs) throws Exception{

		final ArrayList<Joueur> listeJoueurs = new ArrayList<>();
		for (String nom : nomsJoueurs) {
			listeJoueurs.add(new Joueur(nom));
		}
		if ("awele".equalsIgnoreCase(version)) {
			return new Awele(listeJoueurs);
		}
		throw new Exception("version non reconnue: " + version);
	}
}
