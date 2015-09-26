package org.noviv.configr;

import java.io.File;
import java.util.Set;
import org.noviv.configr.data.ConfigrDataType;
import org.noviv.configr.data.ConfigrSettingsMap;
import org.noviv.configr.exceptions.ConfigrIOException;
import org.noviv.configr.exceptions.ConfigrValidationException;
import org.noviv.configr.io.ConfigrWriteContext;

/**
 * Main class of the Configr library. Holds all interfaces with the Configr library.
 */
public class ConfigrFile {

    private File file;

    private ConfigrSettingsMap configs;
    private boolean configsChanged;

    private String configName;
    private boolean autoWrite;

    private ConfigrWriteContext writeContext;

    /**
     * Create a new ConfigrFile.
     *
     * @param name Config name.
     */
    public ConfigrFile(String name) {
        this(name, null);
    }

    /**
     * Create a new ConfigrFile and assign a file.
     *
     * @param name Config name.
     * @param filePath Path to file.
     */
    public ConfigrFile(String name, String filePath) {
        configName = name;
        configs = new ConfigrSettingsMap();
        configsChanged = true;
        autoWrite = false;
        ConfigrFile.this.setWriteFile(filePath);
    }

    /**
     * Set whether or not the config file will automatically be written.
     *
     * @param autoWrite_ True means auto write is on.
     */
    public void setAutoWrite(boolean autoWrite_) {
        if (file == null && autoWrite_) {
            throw new ConfigrValidationException("Cannot enable autowriting without assigning a file.");
        }
        autoWrite = autoWrite_;
    }

    /**
     * Assign ConfigrFile object to a file. File will be created if it doesn't exist.
     *
     * @param filePath Path of file.
     */
    public void setWriteFile(String filePath) {
        if (filePath != null) {
            setWriteFile(new File(filePath));
        }
    }

    /**
     * Assign ConfigrFile object to a file. File will be created if it doesn't exist.
     *
     * @param file_ File.
     */
    public void setWriteFile(File file_) {
        file = file_;
        try {
            file.createNewFile();
        } catch (Exception e) {
            throw new ConfigrIOException("Could not create config file: " + e.getMessage());
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

    private void set(String key, Object value, ConfigrDataType type) {
        configs.put(key, value, type);
        configsChanged = true;
        if (autoWrite) {
            write(true);
        }
    }

    /**
     * Change all values in current ConfigrFile to map values.
     *
     * @param newSettings New settings.
     */
    public void setAll(ConfigrSettingsMap newSettings) {
        configs = newSettings;
        configsChanged = true;
        if (autoWrite) {
            write(true);
        }
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
     * Print name and all settings, values, and data types to the console.
     */
    public void printAll() {
        System.out.println(configName);
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
     * @return Config name.
     */
    public String getName() {
        return configName;
    }

    /**
     * Get the name of the assigned file.
     *
     * @return File name.
     */
    public String getFileName() {
        if (file == null) {
            throw new ConfigrValidationException("Cannot convert to File, config file not assigned yet.");
        }
        return file.getName();
    }

    /**
     * Get the absolute path of the assigned file.
     *
     * @return Absolute file path.
     */
    public String getAbsolutePath() {
        if (file == null) {
            throw new ConfigrValidationException("Cannot convert to File, config file not assigned yet.");
        }
        return file.getAbsolutePath();
    }

    /**
     * Check whether or not Java has write access to the assigned file.
     *
     * @return True means that Java has write access.
     */
    public boolean canWrite() {
        if (file == null) {
            throw new ConfigrValidationException("Cannot convert to File, config file not assigned yet.");
        }
        return file.canWrite();
    }

    /**
     * Convert ConfigrFile to File.
     *
     * @return File.
     */
    public File toFile() {
        if (file == null) {
            throw new ConfigrValidationException("Cannot convert to File, config file not assigned yet.");
        }
        return file;
    }
}
