# Configr [![Build Status](https://travis-ci.org/Noviv/Configr.svg?branch=master)](https://travis-ci.org/Noviv/Configr)
Small library that makes interfacing with configuration files easy. Can read config files, write new config files, and import non-standard system/program config files.

## Use
1. Download Configr-1.0-SNAPSHOT.jar from the target/ folder.
2. Add library to classpath/library folder.

## Examples
##### Read File
```java
//create read context on file to read
ConfigrReadContext r = new ConfigrReadContext("src/test/java/resources/config.cfgr");
//get the ConfigrFile from the read context
ConfigrFile file = r.getConfigrFile();
//print all settings
file.printAllSettings();
```
##### Import File
```java
//create import context on file to import
ConfigrImportContext i = new ConfigrImportContext("src/test/java/resources/config.cfgr");
//print all imported ConfirFiles
for (ConfigrFile f : i.getImportedFiles()) {
  f.printAllSettings();
}
```

##### Write File
```java
//create new ConfigrFile
ConfigrFile f = new ConfigrFile("Test", "src/test/java/resources/config.cfgr");
//add a setting
f.set("working", true);
//write the file
f.writeIfNecessary();
```

## Branches
<code>master</code> - the most recent updates<br>
<code>build</code> - the stable build

## To do
[X] Add import capability<br>
[X] Allow for non-standard config mapping (space instead of '=')<br>
[&nbsp;&nbsp;] Add static reference for all classes (easy access)<br>
