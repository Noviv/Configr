package org.noviv.configr;

import org.noviv.configr.io.ConfigrImportContext;

public class ConfigrTest {

    public static void main(String[] args) throws Exception {
        ConfigrImportContext c = new ConfigrImportContext("src/test/java/resources/config.cfgr");
        c.printAll();
    }
}
