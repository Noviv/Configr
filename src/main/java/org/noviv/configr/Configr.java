package org.noviv.configr;

import java.io.FileNotFoundException;
import org.noviv.configr.io.ConfigrImportContext;

/**
 * Configr status class.
 */
public class Configr {

    private static final String LIBRARY_NAME = "Configr";

    private static final int VERSION_MAJOR = 1;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_REVISION = 0;

    private static final int VERSION_CHECK = 100 * VERSION_MAJOR
            + 10 * VERSION_MINOR
            + 1 * VERSION_REVISION;

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
     * Get the check-head of all Configr files. Must be present in order to
     * validate settings file.
     *
     * @return String containing value used as check-head.
     */
    public static String getCheckHead() {
        return getName() + " " + getVersion();
    }

    /**
     * Validate the check-head of a Configr file. Compatible with previous
     * versions.
     *
     * @param toValidate Check-head to validate.
     * @return Validated.
     */
    public static boolean validateCheckHead(String toValidate) {
        if (toValidate == null || toValidate.isEmpty() || !toValidate.contains(" ")) {
            return false;
        }
        String[] comps = toValidate.split(" ");
        if (!comps[0].equals(getName())) {
            return false;
        }
        int check = 100 * Integer.parseInt(comps[1].substring(0, 1))
                + 10 * Integer.parseInt(comps[1].substring(2, 3))
                + 1 * Integer.parseInt(comps[1].substring(4, 5));
        if (check > VERSION_CHECK) {
            return false;
        }
        return true;
    }

    /**
     * Get a setting from a file.
     *
     * @param filePath Path of file.
     * @param setting Setting.
     * @return First discovered setting value, or null if the setting is not
     * found.
     */
    public static String getSetting(String filePath, String setting) {
        ConfigrImportContext importContext;
        try {
            importContext = new ConfigrImportContext(filePath);
        } catch (FileNotFoundException e) {
            return null;
        }

        for (ConfigrFile f : importContext.getImportedFiles()) {
            if (f.getSetting(setting) != null) {
                return "" + f.getSetting(setting);
            }
        }

        return null;
    }
}
