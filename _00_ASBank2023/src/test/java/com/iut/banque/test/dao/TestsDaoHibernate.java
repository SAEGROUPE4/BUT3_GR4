package com.iut.banque.test.dao;

import com.iut.banque.dao.DaoHibernate;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.TechnicalException;
import com.iut.banque.modele.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.Assert.*;
/**
 * Class de test pour la DAO.
 * 
 * L'annotation @Rollback n'est pas nécéssaire partout car par défaut elle est
 * true pour les méthodes de test.
 */
// @RunWith indique à JUnit de prendre le class runner de Spirng
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration permet de charger le context utilisé pendant les tests.
// Par défault (si aucun argument n'est précisé), cherche le fichier
// TestsDaoHibernate-context.xml dans le même dosssier que la classe
@ContextConfiguration("/test/resources/TestsDaoHibernate-context.xml")
@Transactional("transactionManager")
public class TestsDaoHibernate {

	// Indique que c'est un champ à injecter automatiquement. Le bean est choisi
	// en fonction du type.
	@Autowired
	private DaoHibernate daoHibernate;

	@Test
	public void testGetAccountByIdExist() {
		Compte account = daoHibernate.getAccountById("IO1010010001");
		if (account == null) {
			fail("Le compte ne doit pas être null.");
		} else if (!"IO1010010001".equals(account.getNumeroCompte()) && !"j.doe2".equals(account.getOwner().getUserId())
				&& !"IO1010010001".equals(account.getNumeroCompte())) {
			fail("Les informations du compte ne correspondent pas à celles de la BDD.");
		}
	}

	@Test
	public void testGetAccountByIdDoesntExist() {
		Compte account = daoHibernate.getAccountById("IO1111111111");
		if (account != null) {
			fail("Le compte n'aurait pas du être renvoyé.");
		}
	}

	@Test
	public void testGetCompteSansDecouvert() {
		assertTrue(daoHibernate.getAccountById("SA1011011011") instanceof CompteSansDecouvert);
	}

	@Test
	public void testGetCompteAvecDecouvert() {
		assertTrue(daoHibernate.getAccountById("AV1011011011") instanceof CompteAvecDecouvert);
	}

	@Test
	public void testCreateCompteAvecDecouvert() {
		Client client = (Client) daoHibernate.getUserById("c.exist");
		String id = "NW1010010001";
		try {
			Compte compte = daoHibernate.createCompteAvecDecouvert(0, id, 100, client);
			assertEquals(0, compte.getSolde(), 0.001);
			assertEquals(id, compte.getNumeroCompte());
			assertEquals("c.exist", compte.getOwner().getUserId());
			assertEquals(100, ((CompteAvecDecouvert) compte).getDecouvertAutorise(), 0.001);
			assertTrue(compte instanceof CompteAvecDecouvert);
		} catch (TechnicalException | IllegalFormatException | IllegalOperationException e) {
			e.printStackTrace();
			fail("Le compte aurait du être créé.");
		}
	}

	@Test
	public void testCreateCompteAvecDecouvertExistingId() {
		Client client = (Client) daoHibernate.getUserById("c.exist");
		String id = "AV1011011011";
		try {
			Compte compte = daoHibernate.createCompteAvecDecouvert(0, id, 100, client);
			if (compte != null) {
				System.out.println(compte);
				fail("Le compte n'aurait pas du être créé.");
			}
		} catch (TechnicalException | IllegalFormatException | IllegalOperationException e) {
			assertTrue(e instanceof TechnicalException);
		}
	}

	@Test
	public void testCreateCompteSansDecouvert() {
		Client client = (Client) daoHibernate.getUserById("c.exist");
		String id = "NW1010010001";
		try {
			Compte compte = daoHibernate.createCompteSansDecouvert(0, id, client);
			assertEquals(0, compte.getSolde(), 0.001);
			assertEquals(id, compte.getNumeroCompte());
			assertEquals("c.exist", compte.getOwner().getUserId());
			assertTrue(compte instanceof CompteSansDecouvert);
		} catch (TechnicalException | IllegalFormatException e) {
			e.printStackTrace();
			fail("Le compte aurait du être crée.");
		}
	}

	@Test
	public void testCreateCompteSansDecouvertExistingId() {
		Client client = (Client) daoHibernate.getUserById("c.exist");
		String id = "SA1011011011";
		try {
			daoHibernate.createCompteSansDecouvert(0, id, client);
			fail("Le compte n'aurait pas du être créé.");
		} catch (TechnicalException | IllegalFormatException e) {
			assertTrue(e instanceof TechnicalException);
		}
	}

	@Test
	public void testDeleteAccountExist() {
		Compte account = daoHibernate.getAccountById("SA1011011011");
		if (account == null) {
			fail("Problème de récupération du compte.");
		}
		try {
			daoHibernate.deleteAccount(account);
		} catch (TechnicalException e) {
			fail("Le compte aurait du être supprimé.");
		}
		account = daoHibernate.getAccountById("SA1011011011");
		if (account != null) {
			fail("Le compte n'a pas été supprimé.");
		}
	}

	@Test
	public void testGetUserByIdExist() {
		Utilisateur user = daoHibernate.getUserById("c.exist");
		if (user == null) {
			fail("Le compte n'aurait pas du être null.");
		} else if (!"TEST NOM".equals(user.getNom()) && !"TEST PRENOM".equals(user.getPrenom())
				&& !"TEST ADRESSE".equals(user.getAdresse()) && !"TEST PASS".equals(user.getUserPwd())
				&& !"c.exist".equals(user.getUserId())) {
			fail("Les informations de l'utilisateur ne correspondent pas à celle de la BDD.");
		}
	}

	@Test
	public void testGetUserByIdDoesntExist() {
		Utilisateur user = daoHibernate.getUserById("c.doesntexist");
		if (user != null) {
			fail("L'utilisateur n'aurait pas du être renvoyé.");
		}
	}

	@Test
	public void testGetAccountsByUserIdExist() {
		Map<String, Compte> accounts = daoHibernate.getAccountsByClientId("g.descomptes");
		if (accounts == null) {
			fail("Ce client devrait avoir des comptes.");
		} else if (!daoHibernate.getAccountById("SA1011011011").equals(accounts.get("SA1011011011"))
				&& !daoHibernate.getAccountById("AV1011011011").equals(accounts.get("AV1011011011"))) {
			fail("Les mauvais comptes ont été chargés.");
		}
	}

	@Test
	public void testGetClientById() {
		assertTrue(daoHibernate.getUserById("c.exist") instanceof Client);
	}

	@Test
	public void testGetgestionnaireById() {
		assertTrue(daoHibernate.getUserById("admin") instanceof Gestionnaire);
	}

	@Test
	public void testGetAccountsByUserIdDoesntExist() {
		Map<String, Compte> accounts = daoHibernate.getAccountsByClientId("c.doesntexit");
		if (accounts != null) {
			fail("Les comptes de cette utilisateur inexistant n'aurait pas du être renvoyés.");
		}
	}

	@Test
	public void testGetAccountsByUserIdNoAccount() {
		Map<String, Compte> accounts = daoHibernate.getAccountsByClientId("c.exist");
		if (accounts.size() != 0) {
			fail("Ce client ne devrait pas avoir de compte.");
		}
	}

	@Test
	public void testCreateUser() {
		try {
			try {
				daoHibernate.createUser("NOM", "PRENOM", "ADRESSE", true, "c.new1", "PASS", false, "5544554455");
			} catch (IllegalArgumentException e) {
				fail("Il ne devrait pas y avoir d'exception ici");
			} catch (IllegalFormatException e) {
				fail("Il ne devrait pas y avoir d'exception ici");
			}
			Utilisateur user = daoHibernate.getUserById("c.new1");
			assertEquals("NOM", user.getNom());
			assertEquals("PRENOM", user.getPrenom());
			assertEquals("ADRESSE", user.getAdresse());
			assertEquals("c.new1", user.getUserId());
			assertEquals("PASS", user.getUserPwd());
			assertTrue(user.isMale());
		} catch (TechnicalException he) {
			fail("L'utilisateur aurait du être créé.");
		}
	}

	@Test
	public void testCreateUserExistingId() {
		try {
			try {
				daoHibernate.createUser("NOM", "PRENOM", "ADRESSE", true, "c.exist", "PASS", false, "9898989898");
			} catch (IllegalArgumentException e) {
				fail("Il ne devrait pas y avoir d'exception ici");
				e.printStackTrace();
			} catch (IllegalFormatException e) {
				fail("Il ne devrait pas y avoir d'exception ici");

			}
			fail("Une TechnicalException aurait d'être lançée ici.");
		} catch (TechnicalException e) {
			assertTrue(e instanceof TechnicalException);
		}
	}

	@Test
	public void testCreateGestionnaire() {
		try {
			try {
				daoHibernate.createUser("NOM", "PRENOM", "ADRESSE", true, "g.new", "PASS", true, "9898989898");
			} catch (IllegalArgumentException | IllegalFormatException e) {
				fail("Il ne devrait pas y avoir d'exception ici");
				e.printStackTrace();
			}
			Utilisateur gestionnaire = daoHibernate.getUserById("g.new");
			if (!(gestionnaire instanceof Gestionnaire)) {
				fail("Cet utilisateur devrait être un gestionnaire.");
			}
		} catch (TechnicalException e) {
			fail("Il ne devrait pas y avoir d'exception ici.");
		}
	}

	@Test
	public void testCreateClient() {
		try {
			try {
				daoHibernate.createUser("NOM", "PRENOM", "ADRESSE", true, "c.new1", "PASS", false, "9898989898");
			} catch (IllegalArgumentException | IllegalFormatException e) {
				fail("Il ne devrait pas y avoir d'exception ici");
				e.printStackTrace();
			}
			Utilisateur client = daoHibernate.getUserById("c.new1");
			if (!(client instanceof Client)) {
				fail("Cet utilisateur devrait être un client.");
			}
			assertEquals("9898989898", ((Client) client).getNumeroClient());
		} catch (TechnicalException e) {
			fail("Il ne devrait pas y avoir d'exception ici.");
		}
	}

	@Test
	public void testDeleteUser() {
		Utilisateur user = daoHibernate.getUserById("c.exist");
		if (user == null) {
			fail("Problème de récupération de ;'utilisateur.");
		}
		try {
			daoHibernate.deleteUser(user);
		} catch (TechnicalException e) {
			fail("L'utilisateur aurait du être supprimé.");
		}
		user = daoHibernate.getUserById("c.exist");
		if (user != null) {
			fail("L'utilisateur n'a pas été supprimé.");
		}
	}

	@Test
	public void testIsUserAllowedUser() {
		assertTrue(daoHibernate.isUserAllowed("c.exist", "TEST PASS"));
	}

	@Test
	public void testIsUserAllowedWrongPassword() {
		assertEquals(false, daoHibernate.isUserAllowed("c.exist", "WRONG PASS"));
	}

	@Test
	public void testIsUserAllowedUserDoesntExist() {
		assertEquals(false, daoHibernate.isUserAllowed("c.doesntexist", "TEST PASS"));
	}

	@Test
	public void testIsNullPassword() {
		assertEquals(false, daoHibernate.isUserAllowed("c.exist", null));
	}

	@Test
	public void testIsUserAllowedNullLogin() {
		assertEquals(false, daoHibernate.isUserAllowed(null, "TEST PASS"));
	}

	@Test
	public void testIsUserAllowedEmptyPassword() {
		assertEquals(false, daoHibernate.isUserAllowed("c.exist", ""));
	}

	@Test
	public void testIsUserAllowedEmptyTrimmedLogin() {
		assertEquals(false, daoHibernate.isUserAllowed("", "TEST PASS"));
		assertEquals(false, daoHibernate.isUserAllowed(" ", "TEST PASS"));
		assertEquals(false, daoHibernate.isUserAllowed("  ", "TEST PASS"));
		assertEquals(false, daoHibernate.isUserAllowed("   ", "TEST PASS"));
		assertEquals(false, daoHibernate.isUserAllowed("    ", "TEST PASS"));
	}

	@Test
	public void testUpdateAccountWithCrediter() {
		Compte account = daoHibernate.getAccountById("SA1011011011");
		if (account == null) {
			fail("Le compte n'existe pas dans la base de données.");
		}
		double soldeActuel = account.getSolde();

		double montantACrediter = 100.0;

		try {
			account.crediter(montantACrediter);
		} catch (IllegalFormatException e) {
			fail("Aucune exception ne devrait être levée pour un montant positif.");
		}

		daoHibernate.updateAccount(account);

		Compte updatedAccount = daoHibernate.getAccountById("SA1011011011");
		if (updatedAccount == null) {
			fail("Le compte n'a pas été récupéré après mise à jour.");
		}

		assertEquals(
				"Le solde du compte n'a pas été correctement mis à jour.",
				soldeActuel + montantACrediter,
				updatedAccount.getSolde(),
				0.001
		);
	}


	@Test
	public void testUpdateUser() {
		Client client = (Client) daoHibernate.getUserById("c.exist");

		if (client == null) {
			fail("L'utilisateur n'existe pas dans la base de données.");
		}

		String nouvelAdresse = "Nouvelle Adresse";
		String nouveauNom = "Nouveau Nom";
		client.setAdresse(nouvelAdresse);
		client.setNom(nouveauNom);

		daoHibernate.updateUser(client);

		Client updatedClient = (Client) daoHibernate.getUserById("c.exist");
		if (updatedClient == null) {
			fail("L'utilisateur n'a pas été récupéré après mise à jour.");
		}

		assertEquals("L'adresse de l'utilisateur n'a pas été correctement mise à jour.", nouvelAdresse, updatedClient.getAdresse());
		assertEquals("Le nom de l'utilisateur n'a pas été correctement mis à jour.", nouveauNom, updatedClient.getNom());

	}

	@Test
	public void testDisconnect() { // la methode disconnect doit etre implemente
		try {
			daoHibernate.disconnect();
			assertTrue("La méthode disconnect a été appelée avec succès.", true);
		} catch (Exception e) {
			fail("La méthode disconnect ne devrait pas lever d'exception.");
		}
	}



}
