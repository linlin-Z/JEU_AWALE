package descartes.awele;

import java.util.ArrayList;
import java.util.Random;

/**
 * Contient des fonctions utiles pour les variantes de mancala :
 * - fonctions autonomes ne dépendant pas de l'état du jeu 
 * - fonctions d'affichage des messages de l'application
 * Les variantes du jeu renvoient des valeurs, cette classe sert à une uniformisation des mises en forme
 * 
 * C'est plus facile pour
 * - tester si indépendant de la version du jeu
 * - faire évoluer si stocké à un seul endroit
 * - récupérer de simples valeurs et afficher un message différent selon l'utilisateur (langue ?)
 * 
 * Abstract oblige à changer dans tous les enfants en héritant, une classe à part permet d'appeler ou pas une fonction
 */
public class MancalaUtil {

	/**
	 * Tire un nombre au hasard entre 1 et n
	 * @param listeJoueurs
	 * @return
	 * @throws Exception
	 */
	static public int tireNbHasard(int nbMax){
		Random rand = new Random();
		return rand.nextInt(nbMax);
	}

	/**
	 * Renvoie la liste des scores de tous les joueurs
	 * 
	 * @return liste des scores des joueurs
	 */
	static public String renvoieScores(ArrayList<Joueur> listeJoueurs) {
		final StringBuilder scores = new StringBuilder();
		String prefix = "";
		for (Joueur j : listeJoueurs) {
			scores.append(prefix);
			scores.append(j.getNom() + " a " + j.getScore() + " bille(s)");
			prefix = "\n";
		}
		return scores.toString();
	}

	/**
	 * Renvoie le meilleur joueur de la liste
	 * 
	 * @return
	 */
	static public Joueur renvoieMeilleurJoueur(ArrayList<Joueur> listeJoueurs) {
		System.out.println("TODO : vérifier si pas pertinent de renvoyer une liste pour les égalités");
		// on initialise avec le premier joueur pour éviter de provoquer une erreur dans
		// la comparaison des scores
		Joueur meilleurJoueur = listeJoueurs.get(0);
		for (Joueur j : listeJoueurs) {
			if (j.getScore() > meilleurJoueur.getScore()) {
				meilleurJoueur = j;
			}
		}
		return meilleurJoueur;
	}

}
