package descartes.awele;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Cette application permet de jouer aux différentes versions de Mancala Pour le moment, seule la version awalé est
 * disponible
 * 
 * Vocabulaire à utiliser : trou (=case, cellule), terrain (=plateau), graines (=billes, pions), joueur
 *
 */
public class Appli {

	// Commandes de l'application

	private final static int COMMANDE_FIN_PARTIE = 0;

	public static void main(String[] args) {

		System.out.println("PENSER A VERIFIER QUE RANGEE ADVERSAIRE PAS VIDEE");
		System.out.println("DETECTER RANGEE VIDE ET PASSER TOUR JOUEUR");
		System.out.println("DETECTER AUTO FIN DE PARTIE");
		System.out.println("IMPLEMENTER VOTE POUR FIN DE PARTIE");
		
		// on utilise les arguments pour choisir la version et déterminer les noms des joueurs
		String version = "";
		ArrayList<String> nomJoueurs = new ArrayList<>();

		// Le choix de la variante, l’identité du joueur devant débuter la partie ainsi que tous les autres paramètres
		// de votre application doivent être reçus sur la ligne de commande
		// Du coup on ne tire plus au sort le premier joueur ?
		for (String arg : args) {
			// le premier argument est la version
			if (version == "") {
				version = arg;
			} else {
				nomJoueurs.add(arg);
			}
		}

		// On a la bonne version du jeu grâce à la fabrique, en cas d'erreur on arrête tout
		IMancala jeu = null;
		try {
			jeu = FabriqueMancala.getMancala(version, nomJoueurs);
		} catch (Exception e) {
			System.out.println("Erreur: " + e.getMessage());
			System.exit(-1);
		}

		// Prépare la partie
		try {
			jeu.preparePartie();
		} catch (Exception e) {
			System.out.println("Erreur: " + e);
			System.exit(-1);
		}

		// on teste si le jeu est prêt sinon on arrête tout
		try {
			if (!jeu.estPartiePrete()) {
				System.out.println("Erreur à la création du jeu: prérequis non respectés");
				System.exit(-1);
			}
		} catch (Exception e) {
			System.out.println("Erreur: " + e.getMessage());
			System.exit(-1);
		}

		Scanner sc = new Scanner(System.in);
		int commande;

		// Tant que la partie n'est pas finie on joue
		while (!jeu.estPartieFinie()) {

			do {

				// affiche le tableau, les joueurs..
				Appli.afficheTableau(jeu);

				// Verification saisie de l'utilisateur
				commande = sc.nextInt();
				// System.out.println("coupJoue: " + coupJoue);
			} while (!estCommandeValide(commande, jeu));

			if (Appli.estCommandeAppli(commande)) {
				// Déclencher la fin de partie
				System.out.println(Appli.COMMANDE_FIN_PARTIE + " pas codé");
				System.out.println("La saisie du numéro 0 doit permettre aux joueurs de signifier qu’ils considèrent la partie finie.");
				System.out.println("Lorsque la règle du jeu autorise une telle fin de partie (par décision commune des deux joueurs),");
				System.out.println("elle précise comme les graines restantes doivent être réparties entre eux.");
				System.out.println("Votre programme doit mettre à jour le score des joueurs en conséquence.");
			} else {
				try {
					int gains = jeu.joueCoup(commande);
					Appli.afficheGainsEventuels(jeu, gains);
					jeu.passeJoueurSuivant();
				} catch (Exception e) {
					System.out.println("Erreur: " + e.getMessage());
				}
			}
		}

		sc.close();

		jeu.calculeScores();
		// Afficher le gagnant + version : NORD gagne la partie d’Awalé.
		System.exit(-1);

	}

	private static void afficheTableau(IMancala jeu) {

		// Affiche le tableau, rendu propre à chaque version
		System.out.println(jeu.afficherTerrain());

		// Affiche le joueur dont c'est le tour
		System.out.println(renvoieAQuiDeJouer(jeu));
	}
	
	static public void afficheGainsEventuels(IMancala jeu, int gains) {
		if (gains > 0) {
			final StringBuilder phraseGains = new StringBuilder();
			phraseGains.append(jeu.getJoueurEnCours().getNom());
			phraseGains.append(" a ramassé ");
			phraseGains.append(gains);
			phraseGains.append(" bille(s).");
			System.out.println(phraseGains.toString());
		}
	}

	static public String renvoieAQuiDeJouer(IMancala jeu) {
		final StringBuilder aqui = new StringBuilder();
		aqui.append("A ");
		aqui.append(jeu.getJoueurEnCours().getNom());
		aqui.append(" de jouer.");
		return aqui.toString();
	}

	/**
	 * Est-ce commande interne à l'application ? (donc pas un coup jouable)
	 * 
	 * @param commande
	 * @return
	 */
	private static boolean estCommandeAppli(final int commande) {
		return (commande == COMMANDE_FIN_PARTIE);
	}

	/**
	 * envoie la liste des commandes utilisables dans l'application
	 * 
	 * @param commande numéro de la commande
	 * @param jeu      version du jeu en cours d'utilisation
	 * @return est-ce une commande valide ?
	 */
	private static boolean estCommandeValide(final int commande, IMancala jeu) {

		// System.out.println("commande jouée: " + commande);

		// Commandes propres à l'application (aide + règles + vote arrêt partie)
		if (Appli.estCommandeAppli(commande)) {
			return true;
		}
		// Commandes propres au jeu (numéro du trou à semer)
		try {
			if (jeu.estCoupValide(commande)) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Erreur: " + e.getMessage());
			return false;
		}
		return false;
	}

}
