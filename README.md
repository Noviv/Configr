# Configr [![Build Status](https://travis-ci.org/Noviv/Configr.svg?branch=master)](https://travis-ci.org/Noviv/Configr)
Small library that makes interfacing with configuration files easy.


## Use
1. Download Configr-1.0-SNAPSHOT.jar from the target/ folder.
2. Add library to classpath/library folder.

## Examples
##### Read File
```java
ConfigrReadContext r = new ConfigrReadContext("src/test/java/resources/config.cfgr");//read file
ConfigrFile file = r.getConfigrFile();//get file
file.printAllSettings();//print all settings from file
```
##### Import File
```java
ConfigrImportContext i = new ConfigrImportContext("src/test/java/resources/config.cfgr");
for (ConfigrFile f : i.getImportedFiles()) {
  f.printAllSettings();
}
```

##### Write File
```java
ConfigrFile f = new ConfigrFile("Test", "src/test/java/resources/config.cfgr");
f.set("working", true);
f.writeIfNecessary();
```
