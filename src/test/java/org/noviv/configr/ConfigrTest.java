package org.noviv.configr;

import org.noviv.configr.io.ConfigrImportContext;

public class ConfigrTest {

    public static void main(String[] args) throws Exception {
        System.out.println(Configr.validateCheckHead("Configr 1.0.0a"));
        ConfigrImportContext context = new ConfigrImportContext("src/test/java/org/noviv/configr/config.cfgr");
        for (ConfigrFile f : context.getImportedFiles()) {
            f.printAll();
        }
    }
}
