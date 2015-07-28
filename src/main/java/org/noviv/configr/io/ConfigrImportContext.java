package org.noviv.configr.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.noviv.configr.ConfigrFile;
import org.noviv.configr.data.ConfigrDataType;
import org.noviv.configr.data.ConfigrSettingsMap;
import org.noviv.configr.exceptions.ConfigrBufferException;
import org.noviv.configr.exceptions.ConfigrIOException;

public class ConfigrImportContext {

    private HashMap<String, ConfigrSettingsMap> maps;
    private ConfigrSettingsMap map;

    private ArrayList<ConfigrFile> importedConfigObjects;

    public ConfigrImportContext(String filePath) throws FileNotFoundException {
        this(new File(filePath));
    }

    public ConfigrImportContext(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new ConfigrBufferException("File selected to import does not exist.");
        }
        maps = new HashMap<>();
        importedConfigObjects = new ArrayList<>();
        run(file);
    }

    private void run(File file) throws FileNotFoundException {
        //read
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String> lines = new ArrayList<>();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new ConfigrIOException("Could not import file: " + e.getMessage());
        }

        //interpret
        line = null;
        String prevName = null;
        boolean multiple = false;
        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i);
            if (line.contains("[") && line.contains("]")) {
                if (map == null) {
                    map = new ConfigrSettingsMap();
                } else {
                    maps.put(prevName, map);
                    map.clear();
                }
                prevName = line.replace("[", "").replace("]", "");
            } else if (line.contains("=")) {
                String key = line.substring(0, line.indexOf("="));
                String value = line.substring(line.indexOf("=") + 1);
                if (value.contains("true") || value.contains("false")) {
                    map.put(key, value, ConfigrDataType.BOOLEAN);
                } else if (value.matches("^-?\\d+$")) {
                    map.put(key, value, ConfigrDataType.INTEGER);
                } else if (value.matches("-?\\d+(\\.\\d+)?")) {
                    map.put(key, value, ConfigrDataType.DOUBLE);
                } else {
                    map.put(key, value, ConfigrDataType.STRING);
                }
            }
        }
        maps.put(prevName, map);

        //convert
        ConfigrFile cFile;
        for (String s : maps.keySet()) {
            cFile = new ConfigrFile(s);
            cFile.setAll(maps.get(s));
            importedConfigObjects.add(cFile);
        }
    }

    public ArrayList<ConfigrFile> getImportedFiles() {
        return importedConfigObjects;
    }

    public void printAll() {
        for (ConfigrFile c : importedConfigObjects) {
            System.out.println("FILE");
            c.printAll();
        }
    }
}
