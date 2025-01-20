package com.iut.banque.test.facade;

import com.iut.banque.dao.DaoHibernate;
import com.iut.banque.facade.LoginManager;
import com.iut.banque.modele.Client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.iut.banque.constants.LoginConstants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test/resources/TestsDaoHibernate-context.xml")
@Transactional("transactionManager")
public class TestsLoginManager {

    @Autowired
    private DaoHibernate daoHibernate;


    private LoginManager loginManager;

    @Before
    public void setUp() {
        loginManager = new LoginManager();
    }

    @Test
    public void testSetCurrentUserAndGetUser() {
        Client client = (Client) daoHibernate.getUserById("c.exist");
        if (client == null) {
            fail("Le client avec l'ID 'c.exist' n'existe pas dans la base de données.");
        }

        loginManager.setCurrentUser(client);

        if(loginManager.getConnectedUser() == null){
            fail("get connected user est null");
        }
        else if (loginManager.getConnectedUser() != client) {
            fail("L'utilisateur connecté ne correspond pas à l'utilisateur défini.");
        }
    }

    @Test
    public void testLogout() {
        Client client = (Client) daoHibernate.getUserById("c.exist");
        if (client == null) {
            fail("Le client avec l'ID 'c.exist' n'existe pas dans la base de données.");
        }

        loginManager.setCurrentUser(client);

        if(loginManager.getConnectedUser() == null){
            fail("get connected user est null");
        }
        else if (loginManager.getConnectedUser() != client) {
            fail("L'utilisateur connecté ne correspond pas à l'utilisateur défini.");
        }


        loginManager.setDao(daoHibernate);

        loginManager.logout();

        if(loginManager.getConnectedUser() != null){
            fail("le user ne s'est pas deconecte");
        }
    }

    @Test
    public void testTryLoginUserConnected() {

        loginManager.setDao(daoHibernate);
        loginManager.logout();

        int result = loginManager.tryLogin("j.doe2","toto");

        assertEquals("erreur try login.",
                LoginConstants.USER_IS_CONNECTED, result);

    }

    @Test
    public void testTryLoginManagerConnected() {

        loginManager.setDao(daoHibernate);
        loginManager.logout();

        int result = loginManager.tryLogin("admin","adminpass");

        assertEquals("erreur try login.",
                LoginConstants.MANAGER_IS_CONNECTED, result);

    }

    @Test
    public void testTryLoginFailed() {

        loginManager.setDao(daoHibernate);
        loginManager.logout();

        int result = loginManager.tryLogin("aaaa","jfjfjf");

        assertEquals("erreur try login.",
                LoginConstants.LOGIN_FAILED, result);

    }



}
