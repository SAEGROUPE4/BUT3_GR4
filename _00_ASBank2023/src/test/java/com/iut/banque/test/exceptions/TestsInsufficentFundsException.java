package com.iut.banque.test.exceptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.iut.banque.exceptions.InsufficientFundsException;

/**
 * Tests pour la classe InsufficientFundsException
 */
public class TestsInsufficentFundsException {

    /**
     * Test du constructeur par défaut
     */
    @Test
    public void testDefaultConstructor() {
        InsufficientFundsException exception = new InsufficientFundsException();
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
        String message = "Fonds insuffisants pour effectuer l'opération";
        InsufficientFundsException exception = new InsufficientFundsException(message);
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
        InsufficientFundsException exception = new InsufficientFundsException(cause);
        assertEquals("La cause de l'exception est incorrecte", cause, exception.getCause());
        assertEquals("Le message de l'exception est incorrect", cause.toString(), exception.getMessage());
    }

    /**
     * Test du constructeur avec un message et une cause
     */
    @Test
    public void testConstructorWithMessageAndCause() {
        String message = "Fonds insuffisants pour effectuer l'opération";
        Throwable cause = new IllegalArgumentException("Illegal argument");
        InsufficientFundsException exception = new InsufficientFundsException(message, cause);
        assertEquals("Le message de l'exception est incorrect", message, exception.getMessage());
        assertEquals("La cause de l'exception est incorrecte", cause, exception.getCause());
    }

    /**
     * Test du constructeur avec tous les paramètres
     */
    @Test
    public void testConstructorWithAllParameters() {
        String message = "Fonds insuffisants pour effectuer l'opération";
        Throwable cause = new IllegalArgumentException("Illegal argument");
        boolean enableSuppression = true;
        boolean writableStackTrace = false;

        InsufficientFundsException exception = new InsufficientFundsException(message, cause, enableSuppression, writableStackTrace);

        assertEquals("Le message de l'exception est incorrect", message, exception.getMessage());
        assertEquals("La cause de l'exception est incorrecte", cause, exception.getCause());
    }
}
