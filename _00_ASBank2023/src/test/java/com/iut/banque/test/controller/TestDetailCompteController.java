package com.iut.banque.test.controller;

import com.iut.banque.controller.DetailCompte;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.Gestionnaire;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestDetailCompteController {

    @Mock
    private BanqueFacade banque;

    @Mock
    private Compte compte;

    @InjectMocks
    private DetailCompte detailCompte;

    @Before
    public void setUp() {
        detailCompte = new DetailCompte(banque);
    }

    @Test
    public void testGetErrorMessages() {
        detailCompte.setError("TECHNICAL");
        assertEquals("Erreur interne. Verifiez votre saisie puis réessayer. Contactez votre conseiller si le problème persiste.", detailCompte.getError());

        detailCompte.setError("BUSINESS");
        assertEquals("Fonds insuffisants.", detailCompte.getError());

        detailCompte.setError("NEGATIVEAMOUNT");
        assertEquals("Veuillez rentrer un montant positif.", detailCompte.getError());

        detailCompte.setError("NEGATIVEOVERDRAFT");
        assertEquals("Veuillez rentrer un découvert positif.", detailCompte.getError());

        detailCompte.setError("INCOMPATIBLEOVERDRAFT");
        assertEquals("Le nouveau découvert est incompatible avec le solde actuel.", detailCompte.getError());

        detailCompte.setError(null);
        assertEquals("", detailCompte.getError());
    }

    @Test
    public void testDebitSuccess() throws Exception {
        Client clientMock = mock(Client.class);

        Compte compteMock = mock(Compte.class);
        when(compteMock.getNumeroCompte()).thenReturn("123456");

        when(banque.getConnectedUser()).thenReturn(clientMock);
        Map<String, Compte> accountsMock = new HashMap<>();
        accountsMock.put("123456", compteMock);
        when(clientMock.getAccounts()).thenReturn(accountsMock);
        doNothing().when(banque).debiter(any(Compte.class), anyDouble());
        detailCompte.setMontant("100");
        detailCompte.setCompte(compteMock);

        assertEquals("SUCCESS", detailCompte.debit());

        verify(banque, times(1)).debiter(compteMock, 100.0);
    }


    @Test
    public void testDebitNumberFormatException() {
        detailCompte.setMontant("invalid_number");
        detailCompte.setCompte(compte);

        assertEquals("ERROR", detailCompte.debit());
    }

    @Test
    public void testCreditNumberFormatException() {
        detailCompte.setMontant("hsx");
        detailCompte.setCompte(compte);

        assertEquals("ERROR", detailCompte.credit());
    }

    @Test
    public void testGetCompteGestionnaire() {
        when(banque.getConnectedUser()).thenReturn(mock(Gestionnaire.class));
        detailCompte.setCompte(compte);

        assertEquals(compte, detailCompte.getCompte());
    }


    @Test
    public void testGetCompteClientOwnsAccount() {
        Client client = mock(Client.class);
        Map<String, Compte> accounts = new HashMap<>();
        accounts.put("1234", compte);
        when(client.getAccounts()).thenReturn(accounts);
        when(banque.getConnectedUser()).thenReturn(client);

        detailCompte.setCompte(compte);
        when(compte.getNumeroCompte()).thenReturn("1234");

        assertEquals(compte, detailCompte.getCompte());
    }


    @Test
    public void testGetCompteClientDoesNotOwnAccount() {
        Client client = mock(Client.class);
        Map<String, Compte> accounts = new HashMap<>();
        accounts.put("12345", compte);
        when(client.getAccounts()).thenReturn(accounts);
        when(banque.getConnectedUser()).thenReturn(client);

        detailCompte.setCompte(compte);
        when(compte.getNumeroCompte()).thenReturn("1234");
        assertNull(detailCompte.getCompte());
    }
    @Test
    public void testSetAndGetMontant() {
        detailCompte.setMontant("500");
        assertEquals("500", detailCompte.getMontant());
    }
}
