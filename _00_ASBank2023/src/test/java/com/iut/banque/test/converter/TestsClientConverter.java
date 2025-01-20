package com.iut.banque.test.converter;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import com.iut.banque.exceptions.IllegalFormatException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import com.opensymphony.xwork2.conversion.TypeConversionException;
import com.iut.banque.converter.ClientConverter;
import com.iut.banque.interfaces.IDao;
import com.iut.banque.modele.Client;
import java.util.Map;
import java.util.Arrays;

public class TestsClientConverter {

    private IDao daoMock;
    private ClientConverter clientConverter;

    /** 
     * Utilisation de Mockito pour créer faire des tests avec le dao
     */

    @Before
    public void setUp() {
        daoMock = mock(IDao.class);
        clientConverter = new ClientConverter(daoMock);
    }

    @Test
    public void testConvertFromString() {
        try {
            Client mockClient = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
            when(daoMock.getUserById("j.doe1")).thenReturn(mockClient);
            Client result = (Client) clientConverter.convertFromString(null, new String[]{"j.doe1"}, Client.class);
            assertNotNull(result);
            assertEquals("Doe John (1234567890)", result.getIdentity());
        } catch (Exception e) {
            fail("Exception rencontrée -> " + Arrays.toString(e.getStackTrace()));
        }
    }
    

    @Test(expected = TypeConversionException.class)
    public void testConvertFromString_ClientNotFound() {

        String clientId = "j.doe1";
        when(daoMock.getUserById(clientId)).thenReturn(null);

        clientConverter.convertFromString(null, new String[]{clientId}, Client.class);
    }

    @Test
    public void testConvertToString() {
        try {
            Client client = new Client("John", "Doe", "20 rue Bouvier", true, "j.doe1", "password", "1234567890");
            String result = clientConverter.convertToString(null, client);
           
            assertEquals("Doe John (1234567890)", result);
        } catch (Exception e) {
            fail("Exception rencontrée -> " + Arrays.toString(e.getStackTrace()));
        }
    }
    
}
