package org.noviv.configr.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.noviv.configr.ConfigrFile;
import org.noviv.configr.data.ConfigrDataType;
import org.noviv.configr.data.ConfigrSettingsMap;
import org.noviv.configr.exceptions.ConfigrBufferException;
import org.noviv.configr.exceptions.ConfigrIOException;

/**
 * The context in which any configuration file is imported as an array of ConfigrFile objects. Must be refreshed when a file changed.
 */
public class ConfigrImportContext {

    private File file;
    private String regex;

    private ArrayList<String> linesBuffer;
    private ArrayList<String> nameBuffer;
    private ArrayList<ConfigrSettingsMap> configBuffer;
    private ConfigrSettingsMap nullBuffer;

    private ConfigrFile[] importedConfigObjects;
    private boolean nullBufferActive;

    /**
     * Create a new import context.
     *
     * @param filePath Path to target file.
     * @throws FileNotFoundException Thrown the file cannot be found/read by the JVM.
     */
    public ConfigrImportContext(String filePath) throws FileNotFoundException {
        this(new File(filePath));
    }

    /**
     * Create a new import context with the default regex '=';
     *
     * @param file_ File.
     * @throws FileNotFoundException Thrown the file cannot be found/read by the JVM.
     */
    public ConfigrImportContext(File file_) throws FileNotFoundException {
        this(file_, "=");
    }

    /**
     * Create a new import context with a custom regex.
     *
     * @param file_ File.
     * @param regex_ Custom regex.
     * @throws FileNotFoundException
     */
    public ConfigrImportContext(File file_, String regex_) throws FileNotFoundException {
        nullBufferActive = false;
        file = file_;
        if (!file.exists()) {
            throw new ConfigrBufferException("File selected to import does not exist.");
        }
        regex = regex_;
        if (regex == null || regex.isEmpty()) {
            throw new ConfigrBufferException("Custom regex " + regex + " is invalid.");
        }
        loadLineBuffer();
        loadImportBuffer();
    }

    /**
     * Refresh the context and reload settings. Only necessary if file changes after read context is initialized.
     *
     * @return The updated imported objects.
     */
    public ConfigrFile[] refresh() {
        try {
            loadLineBuffer();
            loadImportBuffer();
        } catch (Exception e) {
        }
        return getImportedFiles();
    }

    private void loadLineBuffer() throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        linesBuffer = new ArrayList<>();
        nullBuffer = new ConfigrSettingsMap();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                linesBuffer.add(line);
            }
        } catch (IOException e) {
            throw new ConfigrIOException("Could not import file: " + e.getMessage());
        }
    }

    private void loadImportBuffer() {
        nameBuffer = new ArrayList<>();
        configBuffer = new ArrayList<>();
        ConfigrSettingsMap currentConfig = null;
        for (String s : linesBuffer) {
            if (s.contains("[") && s.contains("]")) {
                if (currentConfig != null) {
                    configBuffer.add(currentConfig);
                }
                currentConfig = new ConfigrSettingsMap();
                nameBuffer.add(s.replace("[", "").replace("]", ""));
            } else if (s.contains("=")) {
                String key = s.substring(0, s.indexOf(regex));
                String value = s.substring(s.indexOf(regex) + 1);
                if (value.contains("true") || value.contains("false")) {
                    if (currentConfig != null) {
                        currentConfig.put(key, value, ConfigrDataType.BOOLEAN);
                    } else {
                        nullBuffer.put(key, value, ConfigrDataType.BOOLEAN);
                    }
                } else if (value.matches("^-?\\d+$")) {
                    if (currentConfig != null) {
                        currentConfig.put(key, value, ConfigrDataType.INTEGER);
                    } else {
                        nullBuffer.put(key, value, ConfigrDataType.INTEGER);
                    }
                } else if (value.matches("-?\\d+(\\.\\d+)?")) {
                    if (currentConfig != null) {
                        currentConfig.put(key, value, ConfigrDataType.DOUBLE);
                    } else {
                        nullBuffer.put(key, value, ConfigrDataType.DOUBLE);
                    }
                } else {
                    if (currentConfig != null) {
                        currentConfig.put(key, value, ConfigrDataType.STRING);
                    } else {
                        nullBuffer.put(key, value, ConfigrDataType.STRING);
                    }
                }
            } else if (s.length() > 0) {
                if (currentConfig != null) {
                    currentConfig.put(s, ConfigrDataType.NULL);
                } else {
                    nullBuffer.put(s, ConfigrDataType.NULL);
                }
            }
        }
        if (currentConfig != null) {
            configBuffer.add(currentConfig);
        }

        if (nameBuffer.size() != configBuffer.size()) {
            throw new ConfigrBufferException("Import buffer length mismatch: name/config.");
        }

        int add = 0;
        if (nullBuffer.size() > 0) {
            add = 1;
            nullBufferActive = true;
        }

        importedConfigObjects = new ConfigrFile[nameBuffer.size() + add];
        for (int i = 0; i < importedConfigObjects.length - add; i++) {
            importedConfigObjects[i] = new ConfigrFile(nameBuffer.get(i));
            importedConfigObjects[i].setAll(configBuffer.get(i));
        }

        if (add == 1) {
            importedConfigObjects[nameBuffer.size()] = new ConfigrFile("Null Config");
            importedConfigObjects[nameBuffer.size()].setAll(nullBuffer);
        }
    }

    /**
     * Get all imported ConfigrFile objects.
     *
     * @return Array of ConfigrFile objects.
     */
    public ConfigrFile[] getImportedFiles() {
        return importedConfigObjects;
    }

    /**
     * Check whether or not the null buffer was activated while importing.
     *
     * @return True means the null buffer is active.
     */
    public boolean isNullBufferActive() {
        return nullBufferActive;
    }
}
