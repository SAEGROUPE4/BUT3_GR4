package com.iut.banque.test.controller;

import com.iut.banque.constants.LoginConstants;
import com.iut.banque.controller.Connect;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.Utilisateur;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestConnectController {

    @Mock
    private BanqueFacade banqueFacade;

    @InjectMocks
    private Connect connect;

    @Test
    public void testLogin_Success_User() {
        // Simuler le retour d'une connexion réussie pour un utilisateur
        when(banqueFacade.tryLogin("user123", "password")).thenReturn(LoginConstants.USER_IS_CONNECTED);

        connect.setUserCde("user123");
        connect.setUserPwd("password");

        String result = connect.login();

        assertEquals("SUCCESS", result);
    }

    @Test
    public void testLogin_Success_Manager() {
        // Simuler le retour d'une connexion réussie pour un manager
        when(banqueFacade.tryLogin("manager123", "password")).thenReturn(LoginConstants.MANAGER_IS_CONNECTED);

        connect.setUserCde("manager123");
        connect.setUserPwd("password");

        String result = connect.login();

        assertEquals("SUCCESSMANAGER", result);
    }

    @Test
    public void testLogin_Failure() {
        when(banqueFacade.tryLogin("user123", "wrongpassword")).thenReturn(LoginConstants.LOGIN_FAILED);
        connect.setUserCde("user123");
        connect.setUserPwd("wrongpassword");
        String result = connect.login();
        assertEquals("ERROR", result);
    }

    @Test
    public void testLogout() {
        doNothing().when(banqueFacade).logout();
        String result = connect.logout();
        assertEquals("SUCCESS", result);
        verify(banqueFacade, times(1)).logout();  // Vérifier que logout a été appelé une fois
    }

    @Test
    public void testGetConnectedUser() {
        // Simuler l'utilisateur connecté
        Utilisateur utilisateur = mock(Client.class);
        when(banqueFacade.getConnectedUser()).thenReturn(utilisateur);

        Utilisateur connectedUser = connect.getConnectedUser();

        assertEquals(utilisateur, connectedUser);
    }

    @Test
    public void testGetAccounts() {
        Client client = mock(Client.class);
        when(banqueFacade.getConnectedUser()).thenReturn(client);
        when(client.getAccounts()).thenReturn(null);  // Simuler l'absence de comptes

        Map<String, Compte> accounts = connect.getAccounts();

        assertEquals(null, accounts);
    }
}
