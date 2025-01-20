package com.iut.banque.test.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.iut.banque.exceptions.IllegalFormatException;
import org.junit.Before;
import org.junit.Test;

import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.CompteSansDecouvert;

import java.util.HashMap;
import java.util.Map;

public class TestsClient {
	private Client utilisateur;
	private CompteSansDecouvert compte;
	private Map<String, Compte> testAccounts;

	@Before
	public void setUp() throws IllegalFormatException {
		utilisateur = new Client("Veneroso", "Thomas", "123 rue grande Metz", true, "a.b1","clientpass3","1322456789");
		compte = new CompteSansDecouvert("FR0123456689", 100, new Client());

		testAccounts = new HashMap<>();
		testAccounts.put(compte.getNumeroCompte(), compte);
	}
	/**
	 * Tests successifs de la méthode de vérification du format de numéro de
	 * client
	 */
	@Test
	public void testMethodeCheckFormatUserIdClientCorrect() {
		String strClient = "a.utilisateur928";
		if (!Client.checkFormatUserIdClient(strClient)) {
			fail("String " + strClient + " refusé dans le test");
		}
	}

	@Test
	public void testMethodeCheckFormatUserIdClientCommencantParUnChiffre() {
		String strClient = "32a.abc1";
		if (Client.checkFormatUserIdClient(strClient)) {
			fail("String " + strClient + " validé dans le test");
		}
	}

	@Test
	public void testMethodeCheckFormatUserIdClientCommencantParPlusieursLettres() {
		String strClient = "aaa.abc1";
		if (Client.checkFormatUserIdClient(strClient)) {
			fail("String " + strClient + " validé dans le test");
		}
	}

	@Test
	public void testMethodeCheckFormatUserIdClientSansPointSeparateur() {
		String strClient = "abc1";
		if (Client.checkFormatUserIdClient(strClient)) {
			fail("String " + strClient + " validé dans le test");
		}
	}

	@Test
	public void testMethodeCheckFormatUserIdClientChaineVide() {
		String strClient = "";
		if (Client.checkFormatUserIdClient(strClient)) {
			fail("String " + strClient + " validé dans le test");
		}
	}

	@Test
	public void testMethodeCheckFormatUserIdClientSansLettresApresLePointSeparateur() {
		String strClient = "a.138";
		if (Client.checkFormatUserIdClient(strClient)) {
			fail("String " + strClient + " validé dans le test");
		}
	}

	@Test
	public void testMethodeCheckFormatUserIdClientAvecUneSeuleLettreApresLePointSeparateur() {
		String strClient = "a.a1";
		if (!Client.checkFormatUserIdClient(strClient)) {
			fail("String " + strClient + " refusé dans le test");
		}
	}

	@Test
	public void testMethodeCheckFormatUserIdClientAvecCaractereSpecial() {
		String strClient = "a.bcdé1";
		if (Client.checkFormatUserIdClient(strClient)) {
			fail("String " + strClient + " validé dans le test");
		}
	}

	@Test
	public void testMethodeCheckFormatUserIdClientAvecTrailingZeros() {
		String strClient = "a.abc01";
		if (Client.checkFormatUserIdClient(strClient)) {
			fail("String " + strClient + " validé dans le test");
		}
	}

	@Test
	public void testMethodeCheckFormatUserIdClientAvecPlusieursPointsSeparateurs() {
		String strClient = "a.ab.c1";
		if (Client.checkFormatUserIdClient(strClient)) {
			fail("String " + strClient + " validé dans le test");
		}
	}

	/**
	 * Tests successifs de la méthode de vérification du format du numéro de
	 * client
	 */
	@Test
	public void testMethodeCheckFormatNumeroClientCorrect() {
		String strClient = "1234567890";
		if (!Client.checkFormatNumeroClient(strClient)) {
			fail("String " + strClient + " refusé dans le test");
		}
	}

	@Test
	public void testMethodeCheckFormatNumeroClientAvecLettre() {
		String strClient = "12a456789";
		if (Client.checkFormatNumeroClient(strClient)) {
			fail("String " + strClient + " validé dans le test");
		}
	}

	@Test
	public void testMethodeCheckFormatNumeroClientAvecCaractereSpecial() {
		String strClient = "12#456789";
		if (Client.checkFormatNumeroClient(strClient)) {
			fail("String " + strClient + " validé dans le test");
		}
	}

	@Test
	public void testMethodeCheckFormatNumeroClientAvecMoinsDeNeufChiffres() {
		String strClient = "12345678";
		if (Client.checkFormatNumeroClient(strClient)) {
			fail("String " + strClient + " validé dans le test");
		}
	}

	@Test
	public void testMethodeCheckFormatNumeroClientAvecPlusDeDixChiffres() {
		String strClient = "12345678901";
		if (Client.checkFormatNumeroClient(strClient)) {
			fail("String " + strClient + " validé dans le test");
		}
	}

	/**
	 * Tests de la méthode possedeComptesADecouvert
	 */
	@Test
	public void testMethodePossedeComptesADecouvertPourClientAvecQueDesComptesSansDecouvert() {
		try {
			Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
			c.addAccount(new CompteSansDecouvert("FR1234567890", 42, c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 0, c));
			if (c.possedeComptesADecouvert()) {
				fail("La méthode aurait du renvoyer faux");
			}
		} catch (Exception e) {
			fail("Exception récupérée -> " + e.getStackTrace().toString());
		}
	}

	@Test
	public void testMethodePossedeComptesADecouvertPourClientSansComptes() {
		try {
			Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
			if (c.possedeComptesADecouvert()) {
				fail("La méthode aurait du renvoyer faux");
			}
		} catch (Exception e) {
			fail("Exception récupérée -> " + e.getStackTrace().toString());
		}
	}

	@Test
	public void testMethodePossedeComptesADecouvertPourClientAvecUnCompteADecouvertParmisPlusieursTypesDeComptes() {
		try {
			Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
			c.addAccount(new CompteSansDecouvert("FR1234567890", 42, c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 0, c));
			c.addAccount(new CompteAvecDecouvert("FR1234567892", -42, 100, c));
			c.addAccount(new CompteAvecDecouvert("FR1234567893", 1000, 100, c));
			if (!c.possedeComptesADecouvert()) {
				fail("La méthode aurait du renvoyer vrai");
			}
		} catch (Exception e) {
			fail("Exception récupérée -> " + e.getStackTrace().toString());
		}
	}

	@Test
	public void testMethodePossedeComptesADecouvertPourClientAvecPlusieursComptesADecouvertParmisPlusieursTypesDeComptes() {
		try {
			Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
			c.addAccount(new CompteSansDecouvert("FR1234567890", 42, c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 0, c));
			c.addAccount(new CompteAvecDecouvert("FR1234567892", -42, 100, c));
			c.addAccount(new CompteAvecDecouvert("FR1234567893", 1000, 100, c));
			c.addAccount(new CompteAvecDecouvert("FR1234567893", -4242, 5000, c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 1000.01, c));
			if (!c.possedeComptesADecouvert()) {
				fail("La méthode aurait du renvoyer vrai");
			}
		} catch (Exception e) {
			fail("Exception récupérée -> " + e.getStackTrace().toString());
		}
	}

	@Test
	public void testMethodePossedeComptesADecouvertPourClientAvecUnUniqueCompteADecouvert() {
		try {
			Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
			c.addAccount(new CompteAvecDecouvert("FR1234567892", -42, 100, c));
			if (!c.possedeComptesADecouvert()) {
				fail("La méthode aurait du renvoyer vrai");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception récupérée -> " + e.getStackTrace().toString());
		}
	}
	
	//Tests pour la méthode getCompteAvecSoldeNonNul()

	@Test
	public void testMethodeGetCompteAvecSoldeNonNulAvecDeuxComptesAvecSoldeNul(){
		try {
			Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
			c.addAccount(new CompteAvecDecouvert("FR1234567890",0,42,c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 0, c));
			if (c.getComptesAvecSoldeNonNul().size()!=0){
				fail("La méthode a renvoyé un ou plusieurs comptes aveec un solde nul");
			}
		} catch (Exception e) {
			fail("Exception récupérée -> " + e.getStackTrace().toString());
		}
	}
	@Test
	public void testMethodeGetCompteAvecSoldeNonNulAvecUnCompteSansDecouvertAvecSoldeNonNul(){
		try {
			Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
			c.addAccount(new CompteAvecDecouvert("FR1234567890",0,42,c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 1, c));
			if (c.getComptesAvecSoldeNonNul().get("FR1234567891")==null){
				fail("La méthode n'a pas renvoyé dans le map le compte avec solde non nul");
			}
		} catch (Exception e) {
			fail("Exception récupérée -> " + e.getStackTrace().toString());
		}
	}
	@Test
	public void testMethodeGetCompteAvecSoldeNonNulAvecUnCompteAvecDecouvertAvecSoldeNonNul(){
		try {
			Client c = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
			c.addAccount(new CompteAvecDecouvert("FR1234567890",1,42,c));
			c.addAccount(new CompteSansDecouvert("FR1234567891", 0, c));
			if (c.getComptesAvecSoldeNonNul().get("FR1234567890")==null){
				fail("La méthode n'a pas renvoyé dans le map le compte avec solde non nul");
			}
		} catch (Exception e) {
			fail("Exception récupérée -> " + e.getStackTrace().toString());
		}
	}

	@Test
	public void testSetAccounts(){

	}

	@Test
	public void testSetUserIdSuccess() throws IllegalFormatException{
		utilisateur.setUserId("b.a2");
		assertEquals("b.a2", utilisateur.getUserId());
	}


	@Test
	public void testSetNumeroClientSuccess() throws IllegalFormatException{
		utilisateur.setNumeroClient("1234566789");
		assertEquals("1234566789", utilisateur.getNumeroClient());
	}



	@Test
	public void testToString() {
		String expected = "Client [userId=a.b1, nom=Veneroso, prenom=Thomas, adresse=123 rue grande Metz, male=true, userPwd=clientpass3, numeroClient=1322456789, accounts=0]";
		assertEquals(expected, utilisateur.toString());
	}

	@Test
	public void testGetIdentity(){
		String expected = "Thomas Veneroso (1322456789)";
		assertEquals(expected, utilisateur.getIdentity());
	}

	@Test
	public void testSetAccount() {
		utilisateur.setAccounts(testAccounts);

		Map<String, Compte> accounts = utilisateur.getAccounts();

		assertEquals(1, accounts.size());
		assertEquals(compte, accounts.get("FR0123456689"));
	}

}
