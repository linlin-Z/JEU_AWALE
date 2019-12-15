package descartes.awele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Awele extends AbstractMancala {

	// Constantes propres à la version

	public static final int NB_GRAINES_TOTAL = 48;

	public static final int NB_JOUEURS_TOTAL = 2;

	public static final int NB_TROUS_PAR_JOUEUR = 6;

	public Awele(ArrayList<Joueur> listeJoueurs) {

		super(listeJoueurs);
		this.setNbGrainesTotal(Awele.NB_GRAINES_TOTAL);
		this.setNbJoueursTotal(Awele.NB_JOUEURS_TOTAL);
		this.setNbTrousJoueur(Awele.NB_TROUS_PAR_JOUEUR);
	}

	@Override
	public boolean estPartiePrete() throws Exception {

		if (this.getListeJoueurs().size() != this.getNbJoueursTotal()) {
			throw new Exception("Nb de joueurs est incorrect, attendu: " + this.getNbJoueursTotal() + " réel: "
					+ this.getListeJoueurs().size());
		}
		if (this.getJoueurEnCours() == null) {
			throw new Exception("Premier joueur non déterminé");
		}
		if (!this.estTerrainPret()) {
			throw new Exception("Terrain non prêt ou invalide");
		}
		return true;
	}

	@Override
	public boolean estCoupValide(int n) throws Exception {

		// System.out.println("estCoupValide: " + n);

		// Le nombre doit être dans l'intervalle du possible
		if (n < 0 || n > this.getNbTrousTotal()) {
			throw new Exception("Veuillez saisir un trou autorisé");
		}
		
		// et faire partie des trous utilisables par le joueur
		final ArrayList<Integer> listeTrousJoueur = this.getListeTrouJoueur(this.getPosJoueurEnCours());		
		if (!listeTrousJoueur.contains(new Integer(n))) {
			throw new Exception("Ce trou n'est pas utilisable par le joueur: " + n);
		}
		
		// le trou ne doit pas être vide
		if (this.getContenuTrou(n) == 0) {
			throw new Exception("Ce trou est vide: " + n);
		}

		// System.out.println("le coup est valide: le trou " + n + " contient " + this.getContenuTrou(n) + " graines");
		return true;
	}

	@Override
	public int joueCoup(int numTrouJoue) throws Exception {

		// le trou de départ est maintenant vide
		int nbGrainesASemer = this.getContenuTrou(numTrouJoue);
		this.setContenuTrou(numTrouJoue, 0);

		// on sème depuis le trou suivant jusqu'à ne plus avoir de graines
		int trouEnCours = numTrouJoue;
		do {
			trouEnCours = this.getTrouSuivant(trouEnCours);
			// on saute le trou de départ
			if (trouEnCours != numTrouJoue) {
				this.ajouteContenuTrou(trouEnCours, 1);
				nbGrainesASemer--;
			}

		} while (nbGrainesASemer > 0);
		// System.out.println("on a semé " + numTrouJoue + " jusqu'en " + trouEnCours);

		return calculeGains(numTrouJoue, trouEnCours);
	}

	/**
	 * Vérifie si le trou rempli les conditions pour être pris (liste autorisee + remplissage)
	 * @param numeroTrou
	 * @param trousAutorises
	 * @return
	 */
	public boolean estTrouPrenable(int numeroTrou, ArrayList<Integer> trousAutorises) {
		if (!trousAutorises.contains(numeroTrou)) {
			return false;
		}
		if (this.getContenuTrou(numeroTrou) == 2 || this.getContenuTrou(numeroTrou) == 3)
			return true;
		return false;
	}

	/**
	 * Fonction de calcul des gains, vide les trous gagnés
	 * 
	 * @param trouDepart
	 * @param trouArrivee
	 * @return nombre de graines gagnees
	 */
	private int calculeGains(int trouDepart, int trouArrivee) {

		System.out.println("TODO le gain de graines à coder n'est pas fini");

		// Si le trou d'arrivée contient 1 ou plus de 3 graines : gains = 0
		if (this.getContenuTrou(trouArrivee) == 1 || this.getContenuTrou(trouArrivee) > 3) {
			return 0;
		}

		// Si le trou d'arrivée n'est pas chez l'adversaire : gains = 0
		int posJoueurAdverse = this.getPosJoueurEnCours() == 0 ? 1 : 0;
		ArrayList<Integer> trousAdverses = this.getListeTrouJoueur(posJoueurAdverse);
		if (!trousAdverses.contains(new Integer(trouArrivee))) {
			return 0;
		}

		// Tant qu'il reste des graines dans la rangée de l'adversaire, on peut prendre (sans vider sa rangée)
		HashSet<Integer> trousPrenables = new HashSet<>();
		trousPrenables.add(trouArrivee);

		System.out.println(this.getJoueurEnCours().getNom() + " trouDepart: " + trouDepart + " trouArrivee: " + trouArrivee);
//		int trouMin = trousAdverses.get(0);
//		int trouMax = trousAdverses.get(trousAdverses.size() - 1);
//		System.out.println(this.getJoueurEnCours().getNom() + " peut prendre dans " + trouMin + " - " + trouMax);

		// on analyse les trous précédents
		int trouChecked = trouArrivee;
		while (this.estTrouPrenable(trouChecked, trousAdverses)) {
			trouChecked = this.getTrouPrecedent(trouChecked);
			System.out.println("préc: trou" + trouChecked + " = " + this.getContenuTrou(trouChecked));
			// Si le trou contient le bon nombre de graines on prend sinon on s'arrête
			if (this.estTrouPrenable(trouChecked, trousAdverses)) {
				trousPrenables.add(trouChecked);
			} else {
				break;
			}
		}

		// on analyse les trous suivants
		trouChecked = trouArrivee;
		while (this.estTrouPrenable(trouChecked, trousAdverses)) {
			trouChecked = this.getTrouSuivant(trouChecked);
			System.out.println("suiv: trou " + trouChecked + " = " + this.getContenuTrou(trouChecked));
			// Si le trou contient le bon nombre de graines on prend sinon on s'arrête
			if (this.estTrouPrenable(trouChecked, trousAdverses)) {
				trousPrenables.add(trouChecked);
			} else {
				break;
			}
		}

		int gains = 0;
		for (Integer numTrou : trousPrenables) {
			System.out.println("trou potentiellement prenable: " + numTrou);
			gains += this.getContenuTrou(numTrou);
			this.setContenuTrou(numTrou, 0);
		}
		this.getJoueurEnCours().addScore(gains);

//		Par contre, si le trou contenait déjà 1 ou 2 graines (et en contient ainsi 2 ou 3), et se trouve sur la rangée de l'adversaire, il prend :
//		• les graines du trou, y compris celle qu'il apporte;
//		• les graines des trous contigus, avant et après, contenant 2 ou 3 graines; il arrête sa prise, dans chaque sens, dès qu'il rencontre un trou vide, ou contenant une seule graine, ou plus de 3.
//		Au cours de ces prises, il est interdit de vider totalement la rangée de l'adversaire. Un trou au moins doit rester plein.

		return gains;

	}

	@Override
	public boolean estPartieFinie() {

		System.out.println("estPartieFinie pas codé");
		// Pas assez de graines restantes
		if (this.getNbGrainesRestantes() < 6) {
			return true;
		}
		return false;
	}

	@Override
	public String afficherTerrain() {

		// on utilise un StringBuilder (comme dans TD 4) pour éviter de créer une nouvelle chaîne
		// à chaque concaténation : "un" + "deux" + "trois" => 3 chaînes en mémoire ("un", "undeux", "undeuxtrois")
		final StringBuilder carteTerrain = new StringBuilder();

		final LinkedHashMap<Joueur, ArrayList<Integer>> listeTrousParJoueur = this.getListeTrousParJoueur();

		// Le premier joueur a les derniers trous. Pas logique...

		// On garde un compteur pour savoir dans quel sens dessiner le terrain
		int compteur = 0;
		for (Entry<Joueur, ArrayList<Integer>> coupleJoueurTrous : listeTrousParJoueur.entrySet()) {
			Joueur joueur = coupleJoueurTrous.getKey();
			ArrayList<Integer> trous = coupleJoueurTrous.getValue();
			// System.out.println(joueur.getNom() + " / " + trous);

			StringBuilder ligneJoueur = new StringBuilder();
			StringBuilder ligneTrous = new StringBuilder();

			ligneJoueur.append(joueur.getNom() + "\t");
			ligneTrous.append("\t");

			// Ordre décroissant : joueur puis trous
			// NORD...12...11...10....9....8....7
			// ......[X]...[X]..[X]..[X]..[X]..[X]
			if (compteur == 0) {
				Collections.reverse(trous);
			}
			// Ordre croissant : trous puis joueur
			// .....[X]..[X]..[X]..[X]..[X]..[X]
			// SUD ..1....2....3....4....5....6

			for (Integer numTrou : trous) {

				ligneJoueur.append(numTrou + "\t");
				ligneTrous.append("[");
				ligneTrous.append(this.getContenuTrou(numTrou));
				ligneTrous.append("]\t");
			}

			if (compteur == 0) {
				carteTerrain.append(ligneJoueur.toString());
				carteTerrain.append("\n");
				carteTerrain.append(ligneTrous.toString());
				carteTerrain.append("\n");
			} else {
				carteTerrain.append(ligneTrous.toString());
				carteTerrain.append("\n");
				carteTerrain.append(ligneJoueur.toString());
				carteTerrain.append("\n");
			}
			compteur++;
		}
		carteTerrain.append(MancalaUtil.renvoieScores(this.getListeJoueurs()));
		carteTerrain.append("\n");
		return carteTerrain.toString();
	}

}
