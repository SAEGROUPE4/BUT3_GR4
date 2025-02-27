package com.iut.banque.test.modele;

import static org.junit.Assert.assertEquals;


import com.iut.banque.modele.Client;

import com.iut.banque.modele.Utilisateur;
import org.junit.Before;
import org.junit.Test;

import com.iut.banque.exceptions.IllegalFormatException;


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

    @Test
    public void testToStringUtilisateur() {
        Utilisateur user = new Utilisateur("Veneroso", "Thomas", "123 rue grande Metz", true, "a.b1", "clientpass3") {
        };
        String expected = "Utilisateur [userId=a.b1, nom=Veneroso, prenom=Thomas, adresse=123 rue grande Metz, male=true, userPwd=clientpass3]";
        assertEquals(expected, user.toString());
    }

    @Test
    public void testSetPrenom() {
        utilisateur.setPrenom("Jane");



        assertEquals("Jane", utilisateur.getPrenom());
    }

    @Test
    public void testSetNom() {
        utilisateur.setNom("Doe");

        assertEquals("Doe", utilisateur.getNom());
    }

    @Test
    public void testSetAdresse() {
        utilisateur.setAdresse("14 rue grande Metz");

        assertEquals("14 rue grande Metz", utilisateur.getAdresse());
    }

    @Test
    public void testSetPwd() {
        utilisateur.setUserPwd("test");


        assertEquals("test", utilisateur.getUserPwd());
    }

    @Test
    public void testSetMale() {
        utilisateur.setMale(false);

        assertEquals(false, utilisateur.isMale());
    }

    @Test
    public void testSetUserId() throws IllegalFormatException {
        utilisateur.setUserId("b.a2");

        assertEquals("b.a2", utilisateur.getUserId());
    }


}
