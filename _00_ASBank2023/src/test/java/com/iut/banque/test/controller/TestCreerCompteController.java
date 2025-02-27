package com.iut.banque.test.controller;

import com.iut.banque.controller.CreerCompte;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.TechnicalException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestCreerCompteController {

    @Mock
    private BanqueFacade banqueFacade;

    @InjectMocks
    private CreerCompte creerCompte;

    private Client client;
    private Compte compte;

    @Before
    public void setUp() {
        client = mock(Client.class);
        compte = mock(Compte.class);
    }

    @Test
    public void testCreationCompteSuccess() throws TechnicalException, IllegalFormatException{
        String numeroCompte = "12345";
        doNothing().when(banqueFacade).createAccount(numeroCompte, client);
        when(banqueFacade.getCompte(numeroCompte)).thenReturn(compte);

        creerCompte.setNumeroCompte(numeroCompte);
        creerCompte.setClient(client);
        creerCompte.setAvecDecouvert(false);

        String result = creerCompte.creationCompte();

        assertEquals("SUCCESS", result);
        verify(banqueFacade, times(1)).createAccount(numeroCompte, client);  // Vérifier que la méthode a été appelée une fois
    }

    @Test
    public void testCreationCompteAlreadyExists() throws TechnicalException, IllegalFormatException {
        String numeroCompte = "12345";
        doThrow(new TechnicalException()).when(banqueFacade).createAccount(numeroCompte, client);
        creerCompte.setNumeroCompte(numeroCompte);
        creerCompte.setClient(client);

        String result = creerCompte.creationCompte();

        assertEquals("NONUNIQUEID", result);
    }

    @Test
    public void testCreationCompteInvalidFormat() throws TechnicalException, IllegalFormatException {
        String numeroCompte = "abc";
        doThrow(new IllegalFormatException()).when(banqueFacade).createAccount(numeroCompte, client);
        creerCompte.setNumeroCompte(numeroCompte);
        creerCompte.setClient(client);
        String result = creerCompte.creationCompte();
        assertEquals("INVALIDFORMAT", result);
    }

    @Test
    public void testCreationCompteWithOverdraft() throws TechnicalException, IllegalFormatException, IllegalOperationException {
        String numeroCompte = "789";
        double decouvertAutorise = 1000.0;
        doNothing().when(banqueFacade).createAccount(numeroCompte, client, decouvertAutorise);
        when(banqueFacade.getCompte(numeroCompte)).thenReturn(compte);
        creerCompte.setNumeroCompte(numeroCompte);
        creerCompte.setClient(client);
        creerCompte.setAvecDecouvert(true);
        creerCompte.setDecouvertAutorise(decouvertAutorise);
        String result = creerCompte.creationCompte();
        assertEquals("SUCCESS", result);
        verify(banqueFacade, times(1)).createAccount(numeroCompte, client, decouvertAutorise);
    }

    @Test
    public void testSetMessage() {
        creerCompte.setMessage("NONUNIQUEID");
        assertEquals("Ce numéro de compte existe déjà !", creerCompte.getMessage());

        creerCompte.setMessage("INVALIDFORMAT");
        assertEquals("Ce numéro de compte n'est pas dans un format valide !", creerCompte.getMessage());

        creerCompte.setCompte(compte);
        when(compte.getNumeroCompte()).thenReturn("12345");
        creerCompte.setMessage("SUCCESS");
        assertEquals("Le compte 12345 a bien été créé.", creerCompte.getMessage());
    }

    @Test
    public void testGettersAndSetters() {
        creerCompte.setNumeroCompte("56789");
        assertEquals("56789", creerCompte.getNumeroCompte());
        creerCompte.setAvecDecouvert(true);
        assertTrue(creerCompte.isAvecDecouvert());
        creerCompte.setDecouvertAutorise(500.0);
        assertEquals(500.0, creerCompte.getDecouvertAutorise(), 0.01);

        creerCompte.setClient(client);
        assertEquals(client, creerCompte.getClient());

        creerCompte.setCompte(compte);
        assertEquals(compte, creerCompte.getCompte());

        creerCompte.setError(true);
        assertTrue(creerCompte.isError());

        creerCompte.setResult(true);
        assertTrue(creerCompte.isResult());
    }



    @Test
    public void testCreationCompteWithOverdraftIllegalOperation() throws TechnicalException, IllegalFormatException, IllegalOperationException {
        String numeroCompte = "789";
        double decouvertAutorise = 200.0;

        doThrow(new IllegalOperationException()).when(banqueFacade).createAccount(numeroCompte, client, decouvertAutorise);

        creerCompte.setNumeroCompte(numeroCompte);
        creerCompte.setClient(client);
        creerCompte.setAvecDecouvert(true);
        creerCompte.setDecouvertAutorise(decouvertAutorise);

        String result = creerCompte.creationCompte();

        assertEquals("SUCCESS", result); // On s'attend à "SUCCESS" car IllegalOperationException est juste loguée
    }




}
