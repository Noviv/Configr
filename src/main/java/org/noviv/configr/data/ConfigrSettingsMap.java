package org.noviv.configr.data;

import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Map of all settings and their data types.
 */
public class ConfigrSettingsMap {

    private HashMap<String, Object> settingsMap;
    private HashMap<String, ConfigrDataType> typeMap;

    /**
     * Create a new map.
     */
    public ConfigrSettingsMap() {
        settingsMap = new HashMap<>();
        typeMap = new HashMap<>();
    }

    /**
     * Put a data type in the map with the default value.
     *
     * @param key Setting.
     * @param type Data type.
     */
    public void put(String key, ConfigrDataType type) {
        put(key, type.getDefault(), type);
    }

    /**
     * Put a setting in the map.
     *
     * @param key Setting.
     * @param value Value.
     * @param type Data type.
     */
    public void put(String key, Object value, ConfigrDataType type) {
        settingsMap.put(key, value);
        typeMap.put(key, type);
    }

    /**
     * Get all settings in map.
     *
     * @return Set of all settings.
     */
    public Set<String> getSettings() {
        return settingsMap.keySet();
    }

    /**
     * Get the value of a specific setting.
     *
     * @param key Setting.
     * @return Value as an object.
     */
    public Object getSetting(String key) {
        return settingsMap.get(key);
    }

    /**
     * Get the data type of a setting.
     *
     * @param key Setting.
     * @return Data type.
     */
    public ConfigrDataType getType(String key) {
        return typeMap.get(key);
    }

    /**
     * Get the size of the map.
     *
     * @return Size.
     */
    public int size() {
        return settingsMap.size();
    }

    /**
     * Clear the map.
     */
    public void clear() {
        settingsMap.clear();
        typeMap.clear();
    }
}
