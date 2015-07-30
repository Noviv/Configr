package org.noviv.configr.debugging;

import java.io.PrintStream;
import java.sql.Timestamp;

public class Debug {

    private static PrintStream out;

    private Debug() {
    }

    public static void init() {
        init(System.out);
    }

    public static void init(PrintStream out_) {
        out = out_;
    }

    public static void log(String msg) {
        log(msg, Level.STATUS);
    }

    public static void log(String msg, Level level) {
        if (out != null) {
            out.println("Configr " + timestamp() + " [" + level.name + "]: " + msg);
        }
    }

    private static String timestamp() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    public enum Level {

        STATUS("STATUS"),
        ERROR("ERROR");

        private String name;

        Level(String name_) {
            name = name_;
        }
    }
}
