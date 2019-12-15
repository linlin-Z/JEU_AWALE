package descartes.awele;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * On stocke les principales propriétés et méthodes communes à la totalité des versions
 *
 */
abstract public class AbstractMancala implements IMancala {

	private int nbGrainesTotal = 0;

	private int nbJoueursTotal = 0;

	private int nbTrousJoueur = 0;

	private int[] terrain = new int[0];

	private ArrayList<Joueur> listeJoueurs = new ArrayList<>();

	// Stocke la position du joueur en cours (index dans la liste des joueurs)
	private int posJoueurEnCours = -1;

	/**
	 * @param listeJoueurs
	 */
	public AbstractMancala(ArrayList<Joueur> listeJoueurs) {

		// On conserve une copie de la liste plutôt que la liste elle-même
		// cf 03_complements-2x1.pdf p 14/53
		this.listeJoueurs = new ArrayList<Joueur>(listeJoueurs);
	}

	public ArrayList<Joueur> getListeJoueurs() {

		return this.listeJoueurs;
	}

	public int getNbGrainesTotal() {

		return nbGrainesTotal;
	}

	public void setNbGrainesTotal(int nbGrainesTotal) {

		this.nbGrainesTotal = nbGrainesTotal;
	}

	public int getNbJoueursTotal() {

		return nbJoueursTotal;
	}

	public void setNbJoueursTotal(int nbJoueursTotal) {

		this.nbJoueursTotal = nbJoueursTotal;
	}

	public int getNbTrousJoueur() {

		return nbTrousJoueur;
	}

	public void setNbTrousJoueur(int nbTrousJoueur) {

		this.nbTrousJoueur = nbTrousJoueur;
	}

	public int getNbTrousTotal() {

		return this.getNbJoueursTotal() * this.getNbTrousJoueur();
	}

	public int getPosJoueurEnCours() {

		return posJoueurEnCours;
	}

	/**
	 * Définit la position du prochain joueur pouvant jouer dans la liste des joueurs
	 * 
	 * @param posJoueurEnCours
	 */
	public void setPosJoueurEnCours(int posJoueurEnCours) {

		this.posJoueurEnCours = posJoueurEnCours;
	}

	@Override
	public Joueur getJoueurEnCours() {

		return this.getListeJoueurs().get(this.getPosJoueurEnCours());
	}

	protected void definitPremierJoueur() {

		// Tire le premier joueur
		final int indexPremierJoueur = MancalaUtil.tireNbHasard(this.getNbJoueursTotal());
		this.setPosJoueurEnCours(indexPremierJoueur);
	}

	public int[] getTerrain() {

		return terrain;
	}

	public void setTerrain(int[] terrain) {

		this.terrain = terrain;
	}

	protected boolean estTerrainPret() throws Exception {

		if (this.getTerrain().length == 0) {
			throw new Exception("pas de terrain");
		}
		if (this.getTerrain().length < (this.getNbJoueursTotal() * this.getNbTrousJoueur())) {
			throw new Exception("taille du terrain invalide");
		}
		return true;
	}

	protected void construitTerrain() {

		final int nbGrainesTrou = this.getNbGrainesTotal() / this.getNbTrousTotal();
		this.setTerrain(new int[this.getNbTrousTotal()]);

		for (int i = 1; i <= this.getNbTrousTotal(); ++i) {
			this.setContenuTrou(i, nbGrainesTrou);
		}
	}

	/**
	 * Renvoie le contenu d'un trou
	 * 
	 * @param numTrou numéro du trou
	 * @return
	 */
	protected int getContenuTrou(int numTrou) {
		assert (numTrou > 0);
		return this.getTerrain()[numTrou - 1];
	}

	/**
	 * Définit le contenu d'un trou
	 * 
	 * @param numTrou numéro du trou
	 * @return
	 */
	protected void setContenuTrou(int numTrou, int contenu) {
		assert (numTrou > 0);
		assert (contenu >= 0);
		this.getTerrain()[numTrou - 1] = contenu;
	}

	/**
	 * Définit le contenu d'un trou
	 * 
	 * @param numTrou numéro du trou
	 * @return
	 */
	protected void ajouteContenuTrou(int numTrou, int contenu) {
		assert (numTrou > 0);
		assert (contenu >= 0);
		this.getTerrain()[numTrou - 1] += contenu;
	}

	/**
	 * Donne le numéro du trou précédent numTrou (pour boucler)
	 * 
	 * @param numTrou
	 * @return
	 */
	protected int getTrouPrecedent(int numTrou) {
		assert(numTrou > 0);
		if (numTrou == 1) {
			return this.getNbTrousTotal();
		}
		return numTrou - 1;
	}

	/**
	 * Donne le numéro du trou suivant numTrou (pour boucler)
	 * 
	 * @param numTrou
	 * @return
	 */
	protected int getTrouSuivant(int numTrou) {
		assert(numTrou > 0);
		if (numTrou == this.getNbTrousTotal()) {
			return 1;
		}
		return numTrou + 1;
	}

	/**
	 * Renvoie le nombre total de graines sur le terrain
	 * 
	 * @return
	 */
	protected int getNbGrainesRestantes() {
		int restantes = 0;
		for (int i = 1; i < this.getTerrain().length; i++) {
			restantes += this.getContenuTrou(i);
		}
		return restantes;
	}

	@Override
	public void preparePartie() throws Exception {

		this.definitPremierJoueur();
		this.construitTerrain();

	}

	@Override
	public void passeJoueurSuivant() {

		if (posJoueurEnCours >= this.getNbJoueursTotal() - 1) {
			setPosJoueurEnCours(0);
		} else {
			setPosJoueurEnCours(posJoueurEnCours + 1);
		}

	}

	/**
	 * Renvoie la liste des trous autorisés pour chaque joueur
	 * 
	 * On utilise une LinkedHashMap car HashMap ne renvoie pas toujours dans le même ordre Une ArrayList ne pouvant
	 * contenir que des objets, on stocke des Integer
	 * 
	 * @return
	 */
	public LinkedHashMap<Joueur, ArrayList<Integer>> getListeTrousParJoueur() {

		final LinkedHashMap<Joueur, ArrayList<Integer>> listeTrousParJoueur = new LinkedHashMap<>();

		for (int i = 0; i < this.getListeJoueurs().size(); i++) {
			Joueur joueur = this.getListeJoueurs().get(i);

			ArrayList<Integer> listeTrousJoueur = this.getListeTrouJoueur(i);
			listeTrousParJoueur.put(joueur, listeTrousJoueur);
		}

		return listeTrousParJoueur;

	}

	/**
	 * Renvoie la liste des trous affectés à un joueur Le premier joueur a les derniers trous (SUD=1-6 ; NORD=7-12)
	 * 
	 * @param posJoueur index du joueur dans la liste
	 * @return
	 */
	public ArrayList<Integer> getListeTrouJoueur(int posJoueur) {

		// max = NB_MAX_TROUS - (NB_TROUS_PAR_JOUEUR * INDEX_JOUEUR_LISTE)
		// min = (max - NB_TROUS_PAR_JOUEUR) + 1;

		int dernierTrouJoueur = this.getNbTrousTotal() - (this.getNbTrousJoueur() * posJoueur);
		int premierTrouJoueur = (dernierTrouJoueur - this.getNbTrousJoueur()) + 1;
		// System.out.println(premierTrouJoueur + "-" + dernierTrouJoueur);
		ArrayList<Integer> listeTrousJoueur = new ArrayList<>();

		for (int numTrou = premierTrouJoueur; numTrou <= dernierTrouJoueur; ++numTrou) {
			listeTrousJoueur.add((Integer) numTrou);
		}
		return listeTrousJoueur;
	}

	@Override
	abstract public boolean estCoupValide(int n) throws Exception;

	@Override
	abstract public int joueCoup(int numTrouJoue) throws Exception;
	
	abstract public boolean estTrouPrenable(int numeroTrou, ArrayList<Integer> trousAutorises);

	@Override
	abstract public boolean estPartiePrete() throws Exception;

	@Override
	abstract public boolean estPartieFinie();

	@Override
	public void calculeScores() {
		System.out.println("calculeScores TODO finir de distribuer les graines");
		// TODO Auto-generated method stub

	}

}
