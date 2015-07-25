# Configr
Small library that makes interfacing with configuration files easy.


## Use
1. Download Configr-1.0-SNAPSHOT.jar from the target/ folder.
2. Add library to classpath/library folder.

## Example
<code>
ConfigrReadContext r = new ConfigrReadContext("src/test/java/resources/config.cfgr");//read file<br>
ConfigrFile file = r.getConfigrFile();//get file<br>
file.printAllSettings();//print all settings from file<br>
</code>
