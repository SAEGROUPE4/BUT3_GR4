package com.iut.banque.test.controller;

import com.iut.banque.controller.DetailCompteEdit;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class TestDetailCompteEditController {

    private DetailCompteEdit detailCompteEdit;

    @Mock
    private BanqueFacade mockBanque;

    @Mock
    private CompteAvecDecouvert mockCompteAvecDecouvert;

    @Mock
    private Compte mockCompte;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        detailCompteEdit = new DetailCompteEdit(mockBanque);
    }

    @Test
    public void testSetAndGetDecouvertAutorise() {
        detailCompteEdit.setDecouvertAutorise("100.0");
        assertEquals("100.0", detailCompteEdit.getDecouvertAutorise());
    }


    @Test
    public void testChangementDecouvert_Error_NotCompteAvecDecouvert() {
        detailCompteEdit.setCompte(mockCompte);
        detailCompteEdit.setDecouvertAutorise("200.0");
        String result = detailCompteEdit.changementDecouvert();
        assertEquals("ERROR", result);
    }

    @Test
    public void testChangementDecouvert_Error_NumberFormatException() {
        detailCompteEdit.setCompte(mockCompteAvecDecouvert);
        detailCompteEdit.setDecouvertAutorise("invalid");
        String result = detailCompteEdit.changementDecouvert();
        assertEquals("ERROR", result);
    }





}
