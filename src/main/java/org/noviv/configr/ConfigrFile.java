package org.noviv.configr;

import java.io.File;
import java.util.Set;
import org.noviv.configr.data.ConfigrDataType;
import org.noviv.configr.data.ConfigrSettingsMap;
import org.noviv.configr.exceptions.ConfigrIOException;
import org.noviv.configr.io.ConfigrWriteContext;

/**
 * Main class of the Configr library. Holds all interfaces with the Configr
 * library.
 */
public class ConfigrFile {

    private File file;

    private ConfigrSettingsMap configs;
    private boolean configsChanged;

    private String configName;

    /**
     *
     * @param file
     * @param name
     */
    public ConfigrFile(File file, String name) {
        this(file.getAbsolutePath(), name);
    }

    /**
     *
     * @param path
     * @param name
     */
    public ConfigrFile(String path, String name) {
        file = new File(path);
        configName = name;
        configs = new ConfigrSettingsMap();
        configsChanged = false;
        try {
            file.createNewFile();
        } catch (Exception e) {
            throw new ConfigrIOException("Could not create new " + Configr.getName() + " file: " + e.getMessage());
        }
    }

    /**
     *
     * @param key
     * @param valueType
     */
    public void set(String key, ConfigrDataType valueType) {
        configs.put(key, valueType);
    }

    /**
     *
     * @param key
     * @param value
     */
    public void set(String key, double value) {
        set(key, value, ConfigrDataType.DOUBLE);
    }

    /**
     *
     * @param key
     * @param value
     */
    public void set(String key, int value) {
        set(key, value, ConfigrDataType.INTEGER);
    }

    /**
     *
     * @param key
     * @param value
     */
    public void set(String key, boolean value) {
        set(key, value, ConfigrDataType.BOOLEAN);
    }

    /**
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        set(key, value, ConfigrDataType.STRING);
    }

    /**
     *
     * @param key
     * @param value
     * @param type
     */
    public void set(String key, Object value, ConfigrDataType type) {
        configs.put(key, value, type);
        configsChanged = true;
    }

    /**
     *
     * @param newSettings
     */
    public void setAll(ConfigrSettingsMap newSettings) {
        configs = newSettings;
        configsChanged = true;
    }

    /**
     * Write the file only if values have been changed.
     */
    public void writeIfNecessary() {
        write(false);
    }

    /**
     * Write the file if forced or if necessary.
     *
     * @param force Write the file regardless.
     */
    public void write(boolean force) {
        if (configsChanged || force) {
            ConfigrWriteContext write = new ConfigrWriteContext();
            write.head(configName);
            for (String s : configs.getSettings()) {
                write.buffer(s, configs.getSetting(s).toString());
            }
            write.flush(this);
            write = null;
        }
    }

    /**
     * Print all settings, values, and data types to the console.
     */
    public void printAllSettings() {
        for (String setting : configs.getSettings()) {
            System.out.println(setting + "=" + configs.getSetting(setting) + "(" + configs.getType(setting) + ")");
        }
    }

    /**
     *
     * @return
     */
    public Set<String> getSettings() {
        return configs.getSettings();
    }

    /**
     *
     * @param key
     * @return
     */
    public Object getSetting(String key) {
        return configs.getSetting(key);
    }

    /**
     *
     * @param key
     * @return
     */
    public ConfigrDataType getSettingType(String key) {
        return configs.getType(key);
    }

    /**
     *
     * @return
     */
    public String getName() {
        return configName;
    }

    /**
     *
     * @return
     */
    public boolean exists() {
        return file.exists();
    }

    /**
     *
     * @return
     */
    public File toFile() {
        return file;
    }
}
