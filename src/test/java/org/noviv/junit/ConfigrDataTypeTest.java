package org.noviv.junit;

import org.junit.Test;
import static org.junit.Assert.*;
import org.noviv.configr.data.ConfigrDataType;
import static org.noviv.configr.data.ConfigrDataType.*;

public class ConfigrDataTypeTest {

    /**
     * Test of values method, of class ConfigrDataType.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        ConfigrDataType[] expResult = {STRING, BOOLEAN, INTEGER, DOUBLE, NULL};
        ConfigrDataType[] result = ConfigrDataType.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getName method, of class ConfigrDataType.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String[] names = {"String", "Boolean", "Integer", "Double", "Null"};
        int index = 0;
        for (ConfigrDataType t : ConfigrDataType.values()) {
            assertEquals(t.getName(), names[index]);
            index++;
        }
    }

    /**
     * Test of getDefault method, of class ConfigrDataType.
     */
    @Test
    public void testGetDefault() {
        System.out.println("getDefault");
        Object[] defaults = {"", false, 0, 0.0, null};
        int index = 0;
        for (ConfigrDataType t : ConfigrDataType.values()) {
            assertEquals(t.getDefault(), defaults[index]);
            index++;
        }
    }

}
