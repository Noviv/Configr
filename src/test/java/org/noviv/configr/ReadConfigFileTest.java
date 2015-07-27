package org.noviv.configr;

public class ReadConfigFileTest {

    public static void main(String[] args) throws Exception {
        ConfigrFile config = new ConfigrFile("src/test/java/resources/config.cfgr");
        config.set("working", true);
    }
}
