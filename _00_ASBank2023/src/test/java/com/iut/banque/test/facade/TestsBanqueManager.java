package com.iut.banque.test.facade;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.exceptions.TechnicalException;
import com.iut.banque.facade.BanqueManager;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

//@RunWith indique à JUnit de prendre le class runner de Spirng
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration permet de charger le context utilisé pendant les tests.
// Par défault (si aucun argument n'est précisé), cherche le fichier
/// src/com/iut/banque/test/TestsDaoHibernate-context.xml
@ContextConfiguration("/test/resources/TestsBanqueManager-context.xml")
@Transactional("transactionManager")
public class TestsBanqueManager {

	@Autowired
	private BanqueManager bm;

	// Tests de par rapport à l'ajout d'un client
	@Test
	public void TestCreationDunClient() {
		try {
			bm.loadAllClients();
			bm.createClient("t.test1", "password", "test1nom", "test1prenom", "test town", true, "4242424242");
		} catch (IllegalOperationException e) {
			e.printStackTrace();
			fail("IllegalOperationException récupérée : " + e.getStackTrace());
		} catch (Exception te) {
			te.printStackTrace();
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestCreationDunClientAvecDeuxNumerosDeCompteIdentiques() {
		try {
			bm.loadAllClients();
			bm.createClient("t.test1", "password", "test1nom", "test1prenom", "test town", true, "0101010101");
			fail();
		} catch (IllegalOperationException e) {
		} catch (Exception te) {
			te.printStackTrace();
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	// Tests par rapport à la suppression de comptes
	@Test
	public void TestSuppressionDunCompteAvecDecouvertAvecSoldeZero() {
		try {

			bm.deleteAccount(bm.getAccountById("CADV000000"));
		} catch (IllegalOperationException e) {
			e.printStackTrace();
			fail("IllegalOperationException récupérée : " + e.getStackTrace());
		} catch (Exception te) {
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDunCompteAvecDecouvertAvecSoldeDifferentDeZero() {
		try {
			bm.deleteAccount(bm.getAccountById("CADNV00000"));
			fail("Une IllegalOperationException aurait dû être récupérée");
		} catch (IllegalOperationException e) {
		} catch (Exception te) {
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDunCompteSansDecouvertAvecSoldeZero() {
		try {
			bm.deleteAccount(bm.getAccountById("CSDV000000"));
		} catch (IllegalOperationException e) {
			e.printStackTrace();
			fail("IllegalOperationException récupérée : " + e.getStackTrace());
		} catch (Exception te) {
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDunCompteSansDecouvertAvecSoldeDifferentDeZero() {
		try {
			bm.deleteAccount(bm.getAccountById("CSDNV00000"));
			fail("Une IllegalOperationException aurait dû être récupérée");
		} catch (IllegalOperationException e) {
		} catch (Exception te) {
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	// Tests en rapport avec la suppression d'utilisateurs
	@Test
	public void TestSuppressionDunUtilisateurSansCompte() {
		try {
			bm.loadAllClients();
			bm.deleteUser(bm.getUserById("g.pasdecompte"));
		} catch (IllegalOperationException e) {
			e.printStackTrace();
			fail("IllegalOperationException récupérée : " + e.getStackTrace());
		} catch (Exception te) {
			te.printStackTrace();
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDuDernierManagerDeLaBaseDeDonnees() {
		bm.loadAllGestionnaires();
		try {
			bm.deleteUser(bm.getUserById("admin"));
			fail("Une IllegalOperationException aurait dû être récupérée");
		} catch (IllegalOperationException e) {
		} catch (Exception te) {
			te.printStackTrace();
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDunClientAvecComptesDeSoldeZero() {
		try {
			bm.loadAllClients();
			bm.deleteUser(bm.getUserById("g.descomptesvides"));
			if (bm.getAccountById("KL4589219196") != null || bm.getAccountById("KO7845154956") != null) {
				fail("Les comptes de l'utilisateur sont encore présents dans la base de données");
			}
		} catch (IllegalOperationException e) {
			e.printStackTrace();
			fail("IllegalOperationException récupérée : " + e.getStackTrace());
		} catch (Exception te) {
			te.printStackTrace();
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDunClientAvecUnCompteDeSoldePositif() {
		try {
			bm.deleteUser(bm.getUserById("j.doe1"));
			fail("Une IllegalOperationException aurait dû être récupérée");
		} catch (IllegalOperationException e) {
		} catch (Exception te) {
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDunClientAvecUnCompteAvecDecouvertDeSoldeNegatif() {
		try {
			bm.deleteUser(bm.getUserById("j.doe1"));
			fail("Une IllegalOperationException aurait dû être récupérée");
		} catch (IllegalOperationException e) {
		} catch (Exception te) {
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void testCrediter() {
		Compte compte = bm.getAccountById("SA1011011011");
		if (compte == null) {
			fail("Le compte n'existe pas.");
		}

		double soldeInitial = compte.getSolde();
		double montant = 100.0;

		try {
			bm.crediter(compte, montant);

			assertEquals("Le compte n'a pas été crédité correctement.", soldeInitial + montant, compte.getSolde(), 0.001);
		} catch (IllegalFormatException e) {
			fail("Aucune exception ne devrait être levée pour un montant valide.");
		}
	}

	@Test
	public void testDebiter() {
		Compte compte = bm.getAccountById("SA1011011011");
		if (compte == null) {
			fail("Le compte n'existe pas.");
		}

		double soldeInitial = compte.getSolde();
		double montant = 2.0;

		try {
			bm.debiter(compte, montant);

			assertEquals("Le compte n'a pas été debite correctement.", soldeInitial - montant, compte.getSolde(), 0.001);
		} catch (IllegalFormatException e) {
			fail("Aucune exception ne devrait être levée pour un montant valide.");
		} catch (InsufficientFundsException e) {
            throw new RuntimeException(e);
        }
    }

	@Test
	public void testChangeDecouvert() {

		CompteAvecDecouvert compte = (CompteAvecDecouvert) bm.getAccountById("AV1011011011");
		if (compte == null) {
			fail("Le compte avec découvert n'existe pas dans la base de données.");
		}

		double decouvertInitial = compte.getDecouvertAutorise();
		double nouveauDecouvert = decouvertInitial + 100.0;

		try {
			bm.changeDecouvert(compte, nouveauDecouvert);

			CompteAvecDecouvert updatedCompte = (CompteAvecDecouvert) bm.getAccountById("AV1011011011");
			if (updatedCompte == null) {
				fail("Le compte n'a pas été récupéré après mise à jour.");
			}

			assertEquals("Le découvert autorisé n'a pas été correctement mis à jour.", nouveauDecouvert, updatedCompte.getDecouvertAutorise(), 0.001);
		} catch (Exception e) {
			fail("Aucune exception ne devrait être levée : " + e.getMessage());
		}
	}

	@Test
	public void testCreateAccount() {

		Client client = (Client) bm.getUserById("c.exist");
		if (client == null) {
			fail("Le client n'existe pas dans la base de données.");
		}
		String numeroCompte = "NW2022010001";

		try {
			bm.createAccount(numeroCompte,client);
		} catch (TechnicalException | IllegalFormatException e) {
			fail("Aucune exception ne devrait être levée lors de la création du compte : " + e.getMessage());
		}
	}





}
