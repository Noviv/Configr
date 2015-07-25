package org.noviv.configr;

/**
 * Configr status class.
 */
public class Configr {

    private static final String LIBRARY_NAME = "Configr";

    private static final int VERSION_MAJOR = 1;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_REVISION = 0;

    private static final char BUILD_TYPE = 'a';

    private Configr() {
    }

    /**
     * Get the name of the Configr library.
     *
     * @return Name of Configr.
     */
    public static String getName() {
        return LIBRARY_NAME;
    }

    /**
     * Get the working version of Configr.
     *
     * @return Version of Configr.
     */
    public static String getVersion() {
        return String.valueOf(VERSION_MAJOR) + '.' + VERSION_MINOR + '.' + VERSION_REVISION + BUILD_TYPE;
    }

    /**
     * Get the check head of all Configr files. Must be present in order to
     * validate settings file.
     *
     * @return String containing value used as check head.
     */
    public static String getCheckHead() {
        return getName() + " " + getVersion();
    }
}
