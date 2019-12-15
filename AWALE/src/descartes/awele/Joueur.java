package descartes.awele;

public class Joueur {


	private String nom;


	private int score;


	/**
	 * Crée un joueur
	 * 
	 * @param nom
	 *            nom du joueur
	 */
	public Joueur(String nom) {

		this.nom = nom;
	}


	public String getNom() {

		return nom;
	}


	public void setNom(String nom) {

		this.nom = nom;
	}


	public int getScore() {

		return score;
	}


	public void setScore(int score) {

		this.score = score;
	}


	/**
	 * Ajoute une valeur au score actuel du joueur
	 * 
	 * @param score
	 *            valeur à ajouter au joueur
	 */
	public void addScore(int score) {

		assert (score > 0);
		setScore(this.score + score);
	}

}
