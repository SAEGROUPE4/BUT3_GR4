package com.iut.banque.test.converter;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import com.iut.banque.converter.AccountConverter;
import com.iut.banque.interfaces.IDao;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteSansDecouvert;
import com.opensymphony.xwork2.conversion.TypeConversionException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;

public class TestsAccountConverter {

    private IDao daoMock;
    private AccountConverter accountConverter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        daoMock = mock(IDao.class);
        accountConverter = new AccountConverter(daoMock);
    }

    @Test
    public void testConvertFromString() {
        try {
            Compte mockCompte = new CompteSansDecouvert("FR0123456789", 100, null);
            when(daoMock.getAccountById("FR0123456789")).thenReturn(mockCompte);
            Compte result = (Compte) accountConverter.convertFromString(null, new String[]{"FR0123456789"}, Compte.class);
            assertNotNull(result);
            assertEquals("FR0123456789", result.getNumeroCompte());
        } catch (Exception e) {
            fail("Exception encountered -> " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Test(expected = TypeConversionException.class)
    public void testConvertFromString_AccountNotFound() {
        String accountId = "FR0123456789";
        when(daoMock.getAccountById(accountId)).thenReturn(null);
        accountConverter.convertFromString(null, new String[]{accountId}, Compte.class);
    }

    @Test
    public void testConvertToString() {
        try {
            Compte compte = new CompteSansDecouvert("FR9876543210", 200, null);
            String result = accountConverter.convertToString(null, compte);
            assertEquals("FR9876543210", result);
        } catch (Exception e) {
            fail("Exception encountered -> " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Test
    public void testConvertToStringNull() {
        String result = accountConverter.convertToString(null, null);
        assertNull(result);
    }

    @Test
    public void testConvertToStringWithNullCompte() {
        Compte compte = null;
        String result = accountConverter.convertToString(null, compte);
        assertNull(result);
    }

    @Test
    public void testConvertFromStringWithNullValues() {
        try {
            Compte result = (Compte) accountConverter.convertFromString(null, new String[]{null}, Compte.class);
            assertNull(result);
        } catch (TypeConversionException e) {
            assertEquals("Impossible de convertir la chaine suivante : null", e.getMessage());
        }
    }
}