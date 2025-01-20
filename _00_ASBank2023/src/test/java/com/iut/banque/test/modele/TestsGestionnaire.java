package com.iut.banque.test.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.modele.Gestionnaire;

public class TestsGestionnaire {

    @Test
    public void testToString() throws IllegalFormatException {
        Gestionnaire gestionnaire = new Gestionnaire("Muller", "Lilyan", "123 StMarie", true, "lmuller", "password");
        String expected = "Gestionnaire [nom=Muller, prenom=Lilyan, adresse=123 StMarie, male=true, userId=lmuller, userPwd=password]";
        assertEquals(expected, gestionnaire.toString());
    }

    @Test
    public void testGestionnaireThrowError() {
        try {
            new Gestionnaire("Muller", "Lilyan", "123 StMarie", true, "", "password");
            fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("L'identifiant ne peux être vide.", e.getMessage());
        } catch (IllegalFormatException e) {
            fail("IllegalArgumentException should have been thrown, not IllegalFormatException");
        }
    }
}