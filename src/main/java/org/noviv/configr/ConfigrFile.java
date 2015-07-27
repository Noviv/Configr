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
     * Create a new ConfigrFile from a File object without a name. Config file
     * will be created if it does not exist.
     *
     * @param file File.
     */
    public ConfigrFile(File file) {
        this(file.getAbsolutePath(), "");
    }

    /**
     * Create a new ConfigrFile from a File object. Config file will be created
     * if it does not exist.
     *
     * @param file File.
     * @param name Config name.
     */
    public ConfigrFile(File file, String name) {
        this(file.getAbsolutePath(), name);
    }

    /**
     * Create a new ConfigrFile from a file path. Config file will be created if
     * it does not exist.
     *
     * @param path File path.
     * @param name Config name.
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
     * Set a setting to its default value.
     *
     * @param key Setting.
     * @param valueType Data type.
     */
    public void set(String key, ConfigrDataType valueType) {
        configs.put(key, valueType);
    }

    /**
     * Set a setting to a double.
     *
     * @param key Setting.
     * @param value Setting value.
     */
    public void set(String key, double value) {
        set(key, value, ConfigrDataType.DOUBLE);
    }

    /**
     * Set a setting to an int.
     *
     * @param key Setting.
     * @param value Setting value.
     */
    public void set(String key, int value) {
        set(key, value, ConfigrDataType.INTEGER);
    }

    /**
     * Set a setting to a boolean.
     *
     * @param key Setting.
     * @param value Setting value.
     */
    public void set(String key, boolean value) {
        set(key, value, ConfigrDataType.BOOLEAN);
    }

    /**
     * Set a setting to a string.
     *
     * @param key Setting.
     * @param value Setting value.
     */
    public void set(String key, String value) {
        set(key, value, ConfigrDataType.STRING);
    }

    /**
     * Set setting.
     *
     * @param key Setting.
     * @param value Value.
     * @param type Data type.
     */
    public void set(String key, Object value, ConfigrDataType type) {
        configs.put(key, value, type);
        configsChanged = true;
    }

    /**
     * Change all values in current ConfigrFile to map values.
     *
     * @param newSettings New settings.
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
     * Get all settings in ConfigrFile.
     *
     * @return Settings.
     */
    public Set<String> getSettings() {
        return configs.getSettings();
    }

    /**
     * Get the value of a setting.
     *
     * @param key Setting.
     * @return Value.
     */
    public Object getSetting(String key) {
        return configs.getSetting(key);
    }

    /**
     * Get data type of a setting.
     *
     * @param key Setting.
     * @return Data type.
     */
    public ConfigrDataType getSettingType(String key) {
        return configs.getType(key);
    }

    /**
     * Get config name.
     *
     * @return Name.
     */
    public String getName() {
        return configName;
    }

    /**
     * Convert ConfigrFile to File.
     *
     * @return File.
     */
    public File toFile() {
        return file;
    }
}
