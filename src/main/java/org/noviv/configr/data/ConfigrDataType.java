package org.noviv.configr.data;

/**
 * Data type of each setting.
 */
public enum ConfigrDataType {

    /**
     * String data type. Default "".
     */
    STRING("String", ""),
    /**
     * Boolean data type. Default false;
     */
    BOOLEAN("Boolean", false),
    /**
     * Integer data type. Default 0.
     */
    INTEGER("Integer", 0),
    /**
     * Double data type. Default 0.0.
     */
    DOUBLE("Double", 0.0),
    /**
     * Null value. Default null;
     */
    NULL("Null", null);

    private String name;
    private Object def;

    private ConfigrDataType(String name_, Object def_) {
        name = name_;
        def = def_;
    }

    /**
     * Get the name of the data type.
     *
     * @return Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the defualt value.
     *
     * @return Default value as an Object.
     */
    public Object getDefault() {
        return def;
    }
}
