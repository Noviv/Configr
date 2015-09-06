# Configr [![Build Status](https://travis-ci.org/Noviv/Configr.svg?branch=master)](https://travis-ci.org/Noviv/Configr)
Small library that makes interfacing with configuration files easy.


## Use
1. Download Configr-1.0-SNAPSHOT.jar from the target/ folder.
2. Add library to classpath/library folder.

## Examples
##### Read File
<code>ConfigrReadContext r = new ConfigrReadContext("src/test/java/resources/config.cfgr");//read file</code><br>
<code>ConfigrFile file = r.getConfigrFile();//get file</code><br>
<code>file.printAllSettings();//print all settings from file</code><br>

##### Import File
<code>ConfigrImportContext i = new ConfigrImportContext("src/test/java/resources/config.cfgr");</code><br>
<code>for (ConfigrFile f : i.getImportedFiles()) {</code><br>
<code>  f.printAllSettings();</code><br>
<code>}</code>

##### Write File
<code>ConfigrFile f = new ConfigrFile("Test", "src/test/java/resources/config.cfgr");</code><br>
<code>f.set("working", true);</code><br>
<code>f.writeIfNecessary();</code><br>
