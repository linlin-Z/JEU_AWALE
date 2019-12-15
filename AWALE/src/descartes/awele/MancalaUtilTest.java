package descartes.awele;


import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;



public class MancalaUtilTest {


	public static ArrayList<Joueur> listeVide;


	public static ArrayList<Joueur> listeJoueurs;


	public static ArrayList<Joueur> listeJoueursOntJoue;


	public static Joueur jAvecMauvaisScore;


	public static Joueur jAvecMeilleurScore;


	/**
	 * Exécuté une seule fois avant le premier test
	 */
	@BeforeClass
	public static void setUpBeforeClass() {

		listeVide = new ArrayList<>();
		listeJoueurs = new ArrayList<>();
		listeJoueursOntJoue = new ArrayList<>();
	}


	/**
	 * Exécuté avant chaque test
	 */
	@BeforeEach
	public static void initialiseVariablesTest() {

		listeVide = new ArrayList<>();

		listeJoueurs = new ArrayList<>();
		listeJoueurs.add(new Joueur("NORD"));
		listeJoueurs.add(new Joueur("SUD"));

		jAvecMauvaisScore = new Joueur("MAUVAIS");
		jAvecMauvaisScore.setScore(7);
		jAvecMeilleurScore = new Joueur("TOP");
		jAvecMeilleurScore.setScore(21);

		listeJoueursOntJoue = new ArrayList<>();
		listeJoueursOntJoue.add(jAvecMauvaisScore);
		listeJoueursOntJoue.add(jAvecMeilleurScore);
	}


	@Test
	public void testRenvoieScores() {

		assertEquals("", MancalaUtil.renvoieScores(listeVide), "aucun score si pas de joueur");
		assertFalse("lignes de scores", "".equals(MancalaUtil.renvoieScores(listeVide)));
	}


	@Test
	public void testRenvoieMeilleurJoueur() {

		assertEquals(jAvecMeilleurScore, MancalaUtil.renvoieMeilleurJoueur(listeJoueursOntJoue));
	}


	// @Test
	// public void testRenvoieAQuiDeJouer() {
	//
	// fail("Not yet implemented");
	// }
	//
	//
	// @Test
	// public void testAfficheTrousAutorises() {
	//
	// fail("Not yet implemented");
	// }
	//
	//
	// @Test
	// public void testIntArrayToString() {
	//
	// fail("Not yet implemented");
	// }

}
