package descartes.awele;

/**
 * Interface décrivant les méthodes de chaque version de mancala utilisables depuis l'application
 *
 */
public interface IMancala {

	/**
	 * méthode pour initialiser la partie
	 * 
	 */
	public void preparePartie() throws Exception;

	/**
	 * Envoie si la partie est prête (nb de joueurs OK + terrain prêt + pas d'erreur)
	 * 
	 * @return
	 */
	public boolean estPartiePrete() throws Exception;

	/**
	 * Donne le nombre de joueurs du jeu
	 * 
	 * @return
	 */
	public int getNbJoueursTotal();

	/**
	 * Détermine si le coup joué depuis le trou n est valide
	 * 
	 * @param n trou de départ
	 * @return coup valide ?
	 * @throws Exception le trou n'existe pas ou n'appartient pas au joueur
	 */
	public boolean estCoupValide(int n) throws Exception;

	/**
	 * Joue le coup depuis la position n et renvoie les gains
	 * 
	 * @param n numéro du trou joué
	 * @return nombre de graines gagnées
	 * @throws Exception le trou n'existe pas ou n'appartient pas au joueur
	 */
	public int joueCoup(int n) throws Exception;

	/**
	 * détermine si la partie est finie
	 * 
	 * @return
	 */
	boolean estPartieFinie();

	/**
	 * Finit de distribuer les graines entre joueurs
	 */
	public void calculeScores();

	/**
	 * Affiche l'état en cours du terrain
	 * 
	 * @return terrain affiché propre à chaque version
	 */
	public String afficherTerrain();

	/**
	 * Renvoie le joueur dont c'est le tour
	 * 
	 * @return Renvoie le joueur dont c'est le tour
	 */
	public Joueur getJoueurEnCours();

	/**
	 * Passe au joueur suivant
	 */
	public void passeJoueurSuivant();

}
