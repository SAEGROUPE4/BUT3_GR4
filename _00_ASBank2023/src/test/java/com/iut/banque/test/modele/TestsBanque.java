package com.iut.banque.test.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.modele.Banque;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.CompteSansDecouvert;
import com.iut.banque.modele.Gestionnaire;

public class TestsBanque {

    private Banque banque;
    private Compte compte;
    private CompteAvecDecouvert compteAvecDecouvert;
    private Client client;
    private Gestionnaire gestionnaire;

    @Before
    public void setUp() throws IllegalFormatException, IllegalOperationException {
        banque = new Banque();
        client = new Client();
        gestionnaire = new Gestionnaire("Muller", "Lilyan", "123 Rue StMarie", true, "lmuller", "password");
        compte = new CompteSansDecouvert("FR0123456789", 100, client);
        compteAvecDecouvert = new CompteAvecDecouvert("FR9876543210", 100, 100, client);

        Map<String, Client> clients = new HashMap<>();
        clients.put("client1", client);
        banque.setClients(clients);

        Map<String, Gestionnaire> gestionnaires = new HashMap<>();
        gestionnaires.put("gestionnaire1", gestionnaire);
        banque.setGestionnaires(gestionnaires);

        Map<String, Compte> accounts = new HashMap<>();
        accounts.put("compte1", compte);
        accounts.put("compte2", compteAvecDecouvert);
        banque.setAccounts(accounts);
    }

    @Test
    public void testDebiter() {
        try {
            banque.debiter(compte, 50);
            assertEquals(50, compte.getSolde(), 0.001);
        } catch (InsufficientFundsException | IllegalFormatException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testCrediter() {
        try {
            banque.crediter(compte, 50);
            assertEquals(150, compte.getSolde(), 0.001);
        } catch (IllegalFormatException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testDeleteUser() {
        banque.deleteUser("client1");
        assertEquals(null, banque.getClients().get("client1"));
    }

    @Test
    public void testChangeDecouvert() {
        try {
            banque.changeDecouvert(compteAvecDecouvert, 200);
            assertEquals(200, compteAvecDecouvert.getDecouvertAutorise(), 0.001);
        } catch (IllegalFormatException | IllegalOperationException e) {
            fail("Exception should not have been thrown");
        }
    }
    @Test
    public void testGetAccounts(){
        assertEquals(2, banque.getAccounts().size());
    }
}