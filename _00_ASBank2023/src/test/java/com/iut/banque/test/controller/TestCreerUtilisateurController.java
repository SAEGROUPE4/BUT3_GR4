package com.iut.banque.test.controller;

import com.iut.banque.controller.CreerUtilisateur;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.TechnicalException;
import com.iut.banque.facade.BanqueFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestCreerUtilisateurController {

    @Mock
    private BanqueFacade banqueFacade;

    @InjectMocks
    private CreerUtilisateur creerUtilisateur;

    @Before
    public void setUp() {
        banqueFacade = mock(BanqueFacade.class);
        creerUtilisateur = new CreerUtilisateur(banqueFacade); // Utilisation du constructeur alternatif
        creerUtilisateur.setUserId("user123");
        creerUtilisateur.setUserPwd("password");
        creerUtilisateur.setNom("Doe");
        creerUtilisateur.setPrenom("John");
        creerUtilisateur.setAdresse("123 rue Test");
        creerUtilisateur.setMale(true);
        creerUtilisateur.setClient(true);
        creerUtilisateur.setNumClient("C123");
    }

    @Test
    public void testCreationUtilisateur_Success_Client() throws IllegalFormatException, TechnicalException, IllegalOperationException {
        doNothing().when(banqueFacade).createClient(anyString(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyString());
        String result = creerUtilisateur.creationUtilisateur();
        assertEquals("SUCCESS", result);
        assertEquals("Le nouvel utilisateur avec le user id 'user123' a bien été crée.", creerUtilisateur.getMessage());
    }

    @Test
    public void testCreationUtilisateur_Success_Manager() throws IllegalFormatException, TechnicalException{
        creerUtilisateur.setClient(false);
        doNothing().when(banqueFacade).createManager(anyString(), anyString(), anyString(), anyString(), anyString(), anyBoolean());
        String result = creerUtilisateur.creationUtilisateur();
        assertEquals("SUCCESS", result);
        assertEquals("Le nouvel utilisateur avec le user id 'user123' a bien été crée.", creerUtilisateur.getMessage());
    }

    @Test
    public void testCreationUtilisateur_Failure_IllegalOperationException() throws IllegalFormatException, TechnicalException, IllegalOperationException{
        doThrow(new IllegalOperationException()).when(banqueFacade).createClient(anyString(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyString());
        String result = creerUtilisateur.creationUtilisateur();
        assertEquals("ERROR", result);
        assertEquals("L'identifiant à déjà été assigné à un autre utilisateur de la banque.", creerUtilisateur.getMessage());
    }

    @Test
    public void testCreationUtilisateur_Failure_TechnicalException() throws IllegalFormatException, TechnicalException, IllegalOperationException{
        doThrow(new TechnicalException()).when(banqueFacade).createClient(anyString(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyString());
        String result = creerUtilisateur.creationUtilisateur();
        assertEquals("ERROR", result);
        assertEquals("Le numéro de client est déjà assigné à un autre client.", creerUtilisateur.getMessage());
    }

    @Test
    public void testCreationUtilisateur_Failure_IllegalArgumentException() throws IllegalFormatException, TechnicalException, IllegalOperationException{
        doThrow(new IllegalArgumentException()).when(banqueFacade).createClient(anyString(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyString());
        String result = creerUtilisateur.creationUtilisateur();
        assertEquals("ERROR", result);
        assertEquals("Le format de l'identifiant est incorrect.", creerUtilisateur.getMessage());
    }

    @Test
    public void testCreationUtilisateur_Failure_IllegalFormatException() throws IllegalFormatException, TechnicalException, IllegalOperationException{
        doThrow(new IllegalFormatException()).when(banqueFacade).createClient(anyString(), anyString(), anyString(), anyString(), anyString(), anyBoolean(), anyString());
        String result = creerUtilisateur.creationUtilisateur();
        assertEquals("ERROR", result);
        assertEquals("Format du numéro de client incorrect.", creerUtilisateur.getMessage());
    }

    @Test
    public void testGettersAndSetters() {
        creerUtilisateur.setUserId("newUser");
        assertEquals("newUser", creerUtilisateur.getUserId());

        creerUtilisateur.setNom("NewName");
        assertEquals("NewName", creerUtilisateur.getNom());

        creerUtilisateur.setPrenom("NewPrenom");
        assertEquals("NewPrenom", creerUtilisateur.getPrenom());

        creerUtilisateur.setAdresse("New Address");
        assertEquals("New Address", creerUtilisateur.getAdresse());

        creerUtilisateur.setUserPwd("NewPassword");
        assertEquals("NewPassword", creerUtilisateur.getUserPwd());

        creerUtilisateur.setMale(false);
        assertFalse(creerUtilisateur.isMale());

        creerUtilisateur.setClient(false);
        assertFalse(creerUtilisateur.isClient());

        creerUtilisateur.setNumClient("NewNumClient");
        assertEquals("NewNumClient", creerUtilisateur.getNumClient());

        creerUtilisateur.setMessage("New Message");
        assertEquals("New Message", creerUtilisateur.getMessage());

        creerUtilisateur.setResult("NEW_RESULT");
        assertEquals("NEW_RESULT", creerUtilisateur.getResult());
    }

    @Test
    public void testSetBanque() {
        BanqueFacade newBanqueFacade = mock(BanqueFacade.class);
        creerUtilisateur.setBanque(newBanqueFacade);
        assertSame(newBanqueFacade, creerUtilisateur.getBanque());
    }
}
