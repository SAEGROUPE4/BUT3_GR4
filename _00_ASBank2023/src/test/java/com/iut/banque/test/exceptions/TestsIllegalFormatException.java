package com.iut.banque.test.exceptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.iut.banque.exceptions.IllegalFormatException;

/**
 * Tests pour la classe IllegalFormatException
 */
public class TestsIllegalFormatException {

    /**
     * Test du constructeur par défaut
     */
    @Test
    public void testDefaultConstructor() {
        IllegalFormatException exception = new IllegalFormatException();
        if (exception.getMessage() != null) {
            fail("Le message de l'exception par défaut n'est pas null");
        }
        if (exception.getCause() != null) {
            fail("La cause de l'exception par défaut n'est pas null");
        }
    }

    /**
     * Test du constructeur avec un message
     */
    @Test
    public void testConstructorWithMessage() {
        String message = "Invalid format";
        IllegalFormatException exception = new IllegalFormatException(message);
        assertEquals("Le message de l'exception est incorrect", message, exception.getMessage());
        if (exception.getCause() != null) {
            fail("La cause de l'exception avec message n'est pas null");
        }
    }

    /**
     * Test du constructeur avec une cause
     */
    @Test
    public void testConstructorWithCause() {
        Throwable cause = new NullPointerException("Null value");
        IllegalFormatException exception = new IllegalFormatException(cause);
        assertEquals("La cause de l'exception est incorrecte", cause, exception.getCause());
        assertEquals("Le message de l'exception est incorrect", cause.toString(), exception.getMessage());
    }

    /**
     * Test du constructeur avec un message et une cause
     */
    @Test
    public void testConstructorWithMessageAndCause() {
        String message = "Invalid format";
        Throwable cause = new IllegalArgumentException("Illegal argument");
        IllegalFormatException exception = new IllegalFormatException(message, cause);
        assertEquals("Le message de l'exception est incorrect", message, exception.getMessage());
        assertEquals("La cause de l'exception est incorrecte", cause, exception.getCause());
    }

    /**
     * Test du constructeur avec tous les paramètres
     */
    @Test
    public void testConstructorWithAllParameters() {
        String message = "Invalid format";
        Throwable cause = new IllegalArgumentException("Illegal argument");
        boolean enableSuppression = true;
        boolean writableStackTrace = false;

        IllegalFormatException exception = new IllegalFormatException(message, cause, enableSuppression, writableStackTrace);

        assertEquals("Le message de l'exception est incorrect", message, exception.getMessage());
        assertEquals("La cause de l'exception est incorrecte", cause, exception.getCause());
    }
}
