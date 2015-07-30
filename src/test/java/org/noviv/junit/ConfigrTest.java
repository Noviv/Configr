package org.noviv.junit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.noviv.configr.Configr;

public class ConfigrTest {

    public ConfigrTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class Configr.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = "Configr";
        String result = Configr.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getVersion method, of class Configr.
     */
    @Test
    public void testGetVersion() {
        System.out.println("getVersion");
        String expResult = "1.0.0a";
        String result = Configr.getVersion();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCheckHead method, of class Configr.
     */
    @Test
    public void testGetCheckHead() {
        System.out.println("getCheckHead");
        String expResult = "Configr 1.0.0a";
        String result = Configr.getCheckHead();
        assertEquals(expResult, result);
    }

}
