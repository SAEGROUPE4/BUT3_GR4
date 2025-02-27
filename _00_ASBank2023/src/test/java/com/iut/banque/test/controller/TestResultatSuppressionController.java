package com.iut.banque.test.controller;

import static org.junit.Assert.*;

import com.iut.banque.controller.ResultatSuppression;
import org.junit.Before;
import org.junit.Test;

import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestResultatSuppressionController {

    private ResultatSuppression resultatSuppression;
    private Client mockClient;
    private Compte mockCompte;

    @Mock
    private Client client;

    @Mock
    private Compte compte;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        resultatSuppression = new ResultatSuppression();
    }
    @Test
    public void testSetAndGetCompte() {
        resultatSuppression.setCompte(mockCompte);
        assertEquals(mockCompte, resultatSuppression.getCompte());
    }

    @Test
    public void testSetAndGetClient() {
        resultatSuppression.setClient(mockClient);
        assertEquals(mockClient, resultatSuppression.getClient());
    }

    @Test
    public void testSetAndGetCompteInfo() {
        String compteInfo = "Compte de test";
        resultatSuppression.setCompteInfo(compteInfo);
        assertEquals(compteInfo, resultatSuppression.getCompteInfo());
    }

    @Test
    public void testSetAndGetUserInfo() {
        String userInfo = "Utilisateur test";
        resultatSuppression.setUserInfo(userInfo);
        assertEquals(userInfo, resultatSuppression.getUserInfo());
    }

    @Test
    public void testSetAndGetErrorMessage() {
        String errorMessage = "Erreur critique";
        resultatSuppression.setErrorMessage(errorMessage);
        assertTrue(resultatSuppression.isError());
        assertEquals(errorMessage, resultatSuppression.getErrorMessage());
    }

    @Test
    public void testSetErrorMessageToEmpty() {
        resultatSuppression.setErrorMessage("");
        assertFalse(resultatSuppression.isError());
        assertEquals("", resultatSuppression.getErrorMessage());
    }

    @Test
    public void testSetErrorMessageToNull() {
        resultatSuppression.setErrorMessage(null);
        assertFalse(resultatSuppression.isError());
        assertNull(resultatSuppression.getErrorMessage());
    }

    @Test
    public void testSetAndGetIsAccount() {
        resultatSuppression.setAccount(true);
        assertTrue(resultatSuppression.isAccount());

        resultatSuppression.setAccount(false);
        assertFalse(resultatSuppression.isAccount());
    }
}
