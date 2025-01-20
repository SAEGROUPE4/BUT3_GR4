package com.iut.banque.test.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.iut.banque.modele.Client;
import com.iut.banque.modele.CompteSansDecouvert;
import org.junit.Before;
import org.junit.Test;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.modele.Utilisateur;
import com.iut.banque.modele.Client;

public class TestsUtilisateur {

    private Client utilisateur;

    @Before
    public void setUp() throws IllegalFormatException {
        utilisateur = new Client("Veneroso", "Thomas", "123 rue grande Metz", true, "a.b1","clientpass3","1322456789");
    }

    @Test
    public void testGetNom() {
        assertEquals("Veneroso", utilisateur.getNom());
    }
    @Test
    public void testGetPrenom() {
        assertEquals("Thomas", utilisateur.getPrenom());
    }
    @Test
    public void testGetAdresse() {
        assertEquals("123 rue grande Metz", utilisateur.getAdresse());
    }
    @Test
    public void testGetMale() {
        assertEquals(true, utilisateur.isMale());
    }
    @Test
    public void testGetUsrId() {
        assertEquals("a.b1", utilisateur.getUserId());
    }
    @Test
    public void testGetUsrPwd() {
        assertEquals("clientpass3", utilisateur.getUserPwd());
    }



}
