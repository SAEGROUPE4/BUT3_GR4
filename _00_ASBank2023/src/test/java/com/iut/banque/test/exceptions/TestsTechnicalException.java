package com.iut.banque.test.exceptions;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.iut.banque.exceptions.TechnicalException;
import org.junit.Test;



public class TestsTechnicalException {


    @Test
    public void testDefaultConstructor() {
        try {
            TechnicalException exception = new TechnicalException();
            assertNull("Le message devrait être null", exception.getMessage());
            assertNull("La cause devrait être null", exception.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception inattendue : " + e.getMessage());
        }
    }

    @Test
    public void testConstructorWithMessage() {
        try {
            String message = "Erreur technique";
            TechnicalException exception = new TechnicalException(message);
            assertEquals("Le message ne correspond pas", message, exception.getMessage());
            assertNull("La cause devrait être null", exception.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception inattendue : " + e.getMessage());
        }
    }


    @Test
    public void testConstructorWithCause() {
        try {
            Throwable cause = new RuntimeException("Cause initiale");
            TechnicalException exception = new TechnicalException(cause);
            assertEquals("Le message ne correspond pas", "java.lang.RuntimeException: Cause initiale", exception.getMessage());
            assertEquals("La cause ne correspond pas", cause, exception.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception inattendue : " + e.getMessage());
        }
    }


    @Test
    public void testConstructorWithMessageAndCause() {
        try {
            String message = "Erreur technique";
            Throwable cause = new RuntimeException("Cause initiale");
            TechnicalException exception = new TechnicalException(message, cause);
            assertEquals("Le message ne correspond pas", message, exception.getMessage());
            assertEquals("La cause ne correspond pas", cause, exception.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception inattendue : " + e.getMessage());
        }
    }


    @Test
    public void testConstructorWithAllParameters() {
        try {
            String message = "Erreur technique";
            Throwable cause = new RuntimeException("Cause initiale");
            TechnicalException exception = new TechnicalException(message, cause, true, true);
            assertEquals("Le message ne correspond pas", message, exception.getMessage());
            assertEquals("La cause ne correspond pas", cause, exception.getCause());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception inattendue : " + e.getMessage());
        }
    }
}
