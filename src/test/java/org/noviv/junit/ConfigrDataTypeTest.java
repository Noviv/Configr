package org.noviv.junit;

import org.junit.Test;
import static org.junit.Assert.*;
import org.noviv.configr.data.ConfigrDataType;

/**
 *
 * @author Matthew
 */
public class ConfigrDataTypeTest {

    /**
     * Test of values method, of class ConfigrDataType.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        ConfigrDataType[] expResult = null;
        ConfigrDataType[] result = ConfigrDataType.values();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of valueOf method, of class ConfigrDataType.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "";
        ConfigrDataType expResult = null;
        ConfigrDataType result = ConfigrDataType.valueOf(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class ConfigrDataType.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        ConfigrDataType instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDefault method, of class ConfigrDataType.
     */
    @Test
    public void testGetDefault() {
        System.out.println("getDefault");
        ConfigrDataType instance = null;
        Object expResult = null;
        Object result = instance.getDefault();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
