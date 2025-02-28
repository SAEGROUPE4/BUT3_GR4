package com.iut.banque.test.constants;

import com.iut.banque.constants.LoginConstants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class TestLoginConstants {

    @Test
    public void testLoginConstantsValues() {
        assertEquals(1, LoginConstants.USER_IS_CONNECTED);
        assertEquals(2, LoginConstants.MANAGER_IS_CONNECTED);
        assertEquals(LoginConstants.LOGIN_FAILED, -1);
        assertEquals(LoginConstants.ERROR, -2);
    }
}
