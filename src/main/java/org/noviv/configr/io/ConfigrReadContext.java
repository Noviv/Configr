package org.noviv.configr.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.noviv.configr.Configr;
import org.noviv.configr.ConfigrFile;
import org.noviv.configr.data.ConfigrDataType;
import org.noviv.configr.data.ConfigrSettingsMap;
import org.noviv.configr.exceptions.ConfigrBufferException;
import org.noviv.configr.exceptions.ConfigrValidationException;

/**
 * The context in which a file is read into a ConfigrFile object. Must be
 * refreshed when a file changed, and must be written when data is changed in
 * the object.
 */
public class ConfigrReadContext {
    
    private String inputFilePath;
    
    private String nameBuffer;
    private ConfigrFile cFile;
    
    private ConfigrSettingsMap settings;

    /**
     * Create a new read context.
     *
     * @param path Path to target file.
     * @throws FileNotFoundException Thrown the file cannot be found/read by the
     * JVM.
     */
    public ConfigrReadContext(String path) throws FileNotFoundException {
        this(new File(path));
    }

    /**
     * Refresh the context and reload settings. Only necessary if file changes
     * after read context is initialized.
     *
     * @return ConfigrFile with new settings.
     */
    public ConfigrFile refresh() {
        try {
            process();
        } catch (Exception e) {
            throw new ConfigrBufferException("Configr read buffer could not be refreshed: " + e.getMessage());
        }
        return cFile;
    }

    /**
     * Create a new read context.
     *
     * @param target The target file.
     * @throws FileNotFoundException Thrown if the file cannot be found/read by
     * the JVM.
     */
    public ConfigrReadContext(File target) throws FileNotFoundException {
        inputFilePath = target.getAbsolutePath();
        settings = new ConfigrSettingsMap();
        try {
            process();
        } catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                throw new FileNotFoundException(e.getMessage());
            } else {
                throw new ConfigrBufferException("Configr read buffer could not be initialized: " + e.getMessage());
            }
        }
    }
    
    private void process() throws IOException {
        File f = new File(inputFilePath);
        if (!f.getName().substring(f.getName().indexOf(".")).equals(".cfgr")) {
            throw new ConfigrValidationException("Invalid file extension: " + f.getName().substring(f.getName().indexOf(".")));
        }
        BufferedReader br = new BufferedReader(new FileReader(f));
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        
        if (!Configr.validateCheckHead(lines.get(0))) {
            throw new ConfigrValidationException("Invalid Configr check head: " + lines.get(0) + " (Should be: " + Configr.getCheckHead() + ")");
        }
        nameBuffer = lines.get(1).replace("[", "").replace("]", "");
        
        cFile = new ConfigrFile(nameBuffer, inputFilePath);
        for (int i = 2; i < lines.size(); i++) {
            String key = lines.get(i).substring(0, lines.get(i).indexOf("="));
            String value = lines.get(i).substring(lines.get(i).indexOf("=") + 1);
            if (value.contains("true") || value.contains("false")) {
                settings.put(key, value, ConfigrDataType.BOOLEAN);
            } else if (value.matches("^-?\\d+$")) {
                settings.put(key, value, ConfigrDataType.INTEGER);
            } else if (value.matches("-?\\d+(\\.\\d+)?")) {
                settings.put(key, value, ConfigrDataType.DOUBLE);
            } else {
                settings.put(key, value, ConfigrDataType.STRING);
            }
        }
        cFile.setAll(settings);
    }

    /**
     * Get the ConfigrFile from the most recent refresh.
     *
     * @return ConfigrFile with new settings.
     */
    public ConfigrFile getConfigrFile() {
        return cFile;
    }
}
