package com.iut.banque.test.dao;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import com.iut.banque.interfaces.IDao;
import com.iut.banque.modele.Gestionnaire;

public class TestDatabaseConnection {

	private IDao daoMock;

	@Before
	public void setUp() {
		daoMock = mock(IDao.class);
	}

	@Test
	public void testSimulatedDatabase() {
		try {
			Gestionnaire adminUser = new Gestionnaire("Admin", "User", "1 rue des Admins", true, "adminUser", "securepassword");
			when(daoMock.getUserById("adminUser")).thenReturn(adminUser);
			Gestionnaire result = (Gestionnaire) daoMock.getUserById("adminUser");
			assertNotNull(result);
			assertEquals("adminUser", result.getUserId());
			assertEquals("Admin", result.getNom());
			assertEquals("User", result.getPrenom());
			verify(daoMock, times(1)).getUserById("adminUser");

		} catch (Exception e) {
			fail("Exception rencontrée -> " + e.getMessage());
		}
	}
}
