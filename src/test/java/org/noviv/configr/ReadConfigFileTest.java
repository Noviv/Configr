package org.noviv.configr;

import org.noviv.configr.io.ConfigrReadContext;

public class ReadConfigFileTest {

    public static void main(String[] args) throws Exception {
        ConfigrReadContext r = new ConfigrReadContext("src/test/java/resources/config.cfgr");
        ConfigrFile file = r.getConfigrFile();
        file.printAllSettings();
    }
}
