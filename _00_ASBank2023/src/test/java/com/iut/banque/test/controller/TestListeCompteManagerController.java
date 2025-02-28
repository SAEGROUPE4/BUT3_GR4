package com.iut.banque.test.controller;

import com.iut.banque.controller.ListeCompteManager;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.TechnicalException;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestListeCompteManagerController {

    @InjectMocks
    private ListeCompteManager listeCompteManager;

    @Mock
    private BanqueFacade banque;

    @Mock
    private Client client;

    @Mock
    private Compte compte;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        listeCompteManager = new ListeCompteManager(banque);
    }

    @Test
    public void testGetAllClients() {
        Map<String, Client> clientsMock = new HashMap<>();
        clientsMock.put("123", client);

        when(banque.getAllClients()).thenReturn(clientsMock);

        // Exécution de la méthode c'est cette ligne qui manquait
        Map<String, Client> clients = listeCompteManager.getAllClients();

        assertNotNull(clients);
        assertTrue(clients.containsKey("123"));

        verify(banque).loadClients();

        verify(banque).getAllClients();
    }


    @Test
    public void testDeleteUser_Success() throws IllegalOperationException, TechnicalException {
        when(client.getIdentity()).thenReturn("client123");
        doNothing().when(banque).deleteUser(client);
        listeCompteManager.setClient(client);
        String result = listeCompteManager.deleteUser();
        assertEquals("SUCCESS", result);
        verify(banque).deleteUser(client);
    }



    @Test
    public void testDeleteUser_TechnicalException() throws IllegalOperationException, TechnicalException{
        when(client.getIdentity()).thenReturn("client123");
        doThrow(new TechnicalException()).when(banque).deleteUser(client);
        listeCompteManager.setClient(client);
        String result = listeCompteManager.deleteUser();
        assertEquals("ERROR", result);
        verify(banque).deleteUser(client);
    }

    @Test
    public void testDeleteUser_IllegalOperationException() throws IllegalOperationException, TechnicalException{
        when(client.getIdentity()).thenReturn("client123");
        doThrow(new IllegalOperationException()).when(banque).deleteUser(client);
        listeCompteManager.setClient(client);
        String result = listeCompteManager.deleteUser();
        assertEquals("NONEMPTYACCOUNT", result);
        verify(banque).deleteUser(client);
    }

    @Test
    public void testDeleteAccount_Success() throws IllegalOperationException, TechnicalException{
        when(compte.getNumeroCompte()).thenReturn("123456");
        doNothing().when(banque).deleteAccount(compte);

        listeCompteManager.setCompte(compte);
        String result = listeCompteManager.deleteAccount();
        assertEquals("SUCCESS", result);
        verify(banque).deleteAccount(compte);
    }

    @Test
    public void testDeleteAccount_IllegalOperationException() throws IllegalOperationException, TechnicalException{
        when(compte.getNumeroCompte()).thenReturn("123456");
        doThrow(new IllegalOperationException()).when(banque).deleteAccount(compte);
        listeCompteManager.setCompte(compte);
        String result = listeCompteManager.deleteAccount();
        assertEquals("NONEMPTYACCOUNT", result);
        verify(banque).deleteAccount(compte);
    }

    @Test
    public void testDeleteAccount_TechnicalException() throws IllegalOperationException, TechnicalException{
        when(compte.getNumeroCompte()).thenReturn("123456");
        doThrow(new TechnicalException()).when(banque).deleteAccount(compte);
        listeCompteManager.setCompte(compte);
        String result = listeCompteManager.deleteAccount();


        assertEquals("ERROR", result);
        verify(banque).deleteAccount(compte);
    }

    @Test
    public void testSettersAndGetters() {
        listeCompteManager.setaDecouvert(true);
        assertTrue(listeCompteManager.isaDecouvert());
        listeCompteManager.setCompte(compte);
        assertEquals(compte, listeCompteManager.getCompte());
        listeCompteManager.setClient(client);
        assertEquals(client, listeCompteManager.getClient());
    }

}
