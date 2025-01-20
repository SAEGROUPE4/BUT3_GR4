package com.iut.banque.test.facade;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.exceptions.TechnicalException;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.facade.BanqueManager;
import com.iut.banque.facade.LoginManager;
import com.iut.banque.interfaces.IDao;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.Utilisateur;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test/resources/TestsBanqueFacade-context.xml")
@Transactional("transactionManager")
public class TestsBanqueFacade {

    @Autowired
    private BanqueFacade banqueFacade;

    @Autowired
    private BanqueManager banqueManager;

    @Autowired
    private LoginManager loginManager;

    @Autowired
    private IDao dao;



    @Test
    public void testGetConnectedUser() {
        Utilisateur utilisateur = new Utilisateur("name", "firstname", "adress", false, "test", "test") {
        };
        loginManager.setCurrentUser(utilisateur);

        Utilisateur connectedUser = banqueFacade.getConnectedUser();

        assertNotNull("L'utilisateur connecté ne doit pas être null", connectedUser);
        assertEquals("L'utilisateur connecté n'est pas celui attendu", utilisateur, connectedUser);
    }
    @Test
    public void testTryLogin() {
        String userId = "testUser";
        String userPwd = "password123";
        int result = banqueFacade.tryLogin(userId, userPwd);


        assertEquals("Le résultat de la tentative de login n'est pas correct", -1, result);
    }


    @Test
    public void testCrediter() {
        try {
            Compte compte = banqueManager.getAccountById("SA1011011011");
            if (compte == null) {
                fail("Le compte n'existe pas.");
            }

            double soldeInitial = compte.getSolde();
            double montant = 100.0;

            banqueFacade.crediter(compte, montant);
            assertEquals("Le compte n'a pas été crédité correctement.", soldeInitial + montant, compte.getSolde(), 0.001);
        } catch (IllegalFormatException e) {
            fail("Aucune exception ne devrait être levée pour un montant valide.");
        }
    }

    // Test for debiting an account
    @Test
    public void testDebiter() {
        try {
            Compte compte = banqueManager.getAccountById("SA1011011011");
            if (compte == null) {
                fail("Le compte n'existe pas.");
            }

            double soldeInitial = compte.getSolde();
            double montant = 2.0;

            banqueFacade.debiter(compte, montant);
            assertEquals("Le compte n'a pas été débité correctement.", soldeInitial - montant, compte.getSolde(), 0.001);
        } catch (IllegalFormatException | InsufficientFundsException e) {
            fail("Aucune exception ne devrait être levée pour un montant valide.");
        }
    }

    // Test for creating a client
    @Test
    public void testCreateClient() {
        try {
            Client client = (Client) banqueManager.getUserById("c.exist");
            if (client == null) {
                fail("Le client n'existe pas dans la base de données.");
            }
            String numeroClient = "4242424242";
            banqueFacade.createClient("newUser", "newPwd", "newNom", "newPrenom", "newAdresse", true, numeroClient);
        } catch (Exception e) {
            fail("Aucune exception ne devrait être levée lors de la création du client: " + e.getMessage());
        }
    }

    // Test for creating a manager
    @Test
    public void testCreateManager() {
        try {
            banqueFacade.createManager("1234567891", "tusaisdeja", "managernom", "prenom", "adresse", true);
        } catch (TechnicalException | IllegalFormatException e) {
            fail("Aucune exception ne devrait être levée lors de la création du manager : " + e.getMessage());
        }
    }

    @Test
    public void testCreateAccount() {
        try {
            Client client = (Client) banqueManager.getUserById("c.exist");
            if (client == null) {
                fail("Le client n'existe pas dans la base de données.");
            }
            String numeroCompte = "NW2022010001";
            banqueFacade.createAccount(numeroCompte, client);
        } catch (TechnicalException | IllegalFormatException e) {
            fail("Aucune exception ne devrait être levée lors de la création du compte : " + e.getMessage());
        }
    }

}
