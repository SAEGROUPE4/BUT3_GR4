package com.iut.banque.test.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.CompteAvecDecouvert;

public class TestsCompteAvecDecouvert {

	private CompteAvecDecouvert compte;

	@Before
	public void setUp() throws IllegalFormatException, IllegalOperationException {
		compte = new CompteAvecDecouvert("FR0123456789", 100, 100, new Client());
	}

	@Test
	public void testGetClassNameAvecDecouvert() {
		assertEquals("CompteAvecDecouvert", compte.getClass().getSimpleName());
	}

    @Test
	public void testCrediterCompteMontantNegatif() throws InsufficientFundsException, IllegalFormatException {
		compte.debiter(100);
	}

	@Test
	public void testDebiterCompteAvecDecouvertValeurPossible() throws IllegalFormatException, InsufficientFundsException {
		compte.debiter(150);
		assertEquals(-50.0, compte.getSolde(), 0.0001);
	}

	@Test(expected = InsufficientFundsException.class)
	public void testDebiterCompteAvecDecouvertValeurImpossible() throws IllegalFormatException, InsufficientFundsException {
		compte.debiter(250);
	}

	@Test
	public void testSetDecouvertAutorise() throws IllegalFormatException, IllegalOperationException {
		compte.setDecouverAutorise(200);
		assertEquals(200, compte.getDecouvertAutorise(), 0.001);
	}

	@Test(expected = IllegalFormatException.class)
	public void testSetDecouvertAutoriseNegative() throws IllegalFormatException, IllegalOperationException {
		compte.setDecouverAutorise(-50);
	}

	@Test(expected = IllegalOperationException.class)
	public void testSetDecouvertAutoriseIncompatible() throws IllegalFormatException, IllegalOperationException, InsufficientFundsException {
		compte.debiter(150); // Set balance to -50
		compte.setDecouverAutorise(30);
	}
}