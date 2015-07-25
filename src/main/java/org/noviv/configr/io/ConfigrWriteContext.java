package org.noviv.configr.io;

import java.io.PrintWriter;
import org.noviv.configr.Configr;
import org.noviv.configr.ConfigrFile;
import org.noviv.configr.exceptions.ConfigrBufferException;

/**
 * The context in which a file is written to the system. Must only be flushed
 * once, or reset.
 */
public class ConfigrWriteContext {

    private String writeBuffer;
    private boolean flushed;

    /**
     * Create a new write context.
     */
    public ConfigrWriteContext() {
        writeBuffer = Configr.getCheckHead() + "\n";
        flushed = false;
    }

    /**
     * Set the head of the buffer as the name of the ConfigrFile.
     *
     * @param name Name of the ConfigrFile.
     */
    public void head(String name) {
        if (flushed) {
            throw new ConfigrBufferException("Buffer already flushed, cannot write.");
        }
        writeBuffer += "[" + name + "]\n";
    }

    /**
     * Buffer a setting.
     *
     * @param a Setting.
     * @param b Value.
     */
    public void buffer(String a, String b) {
        if (flushed) {
            throw new ConfigrBufferException("Buffer already flushed, cannot write.");
        }
        writeBuffer += a + "=" + b + "\n";
    }

    /**
     * Reset the context, allowing it to be flushed again.
     */
    public void reset() {
        writeBuffer = Configr.getCheckHead() + "\n";
        flushed = false;
    }

    /**
     * Flush to context buffer.
     *
     * @param file The target ConfigrFile to which the buffer should be flushed.
     */
    public void flush(ConfigrFile file) {
        flushed = true;
        PrintWriter pw;
        try {
            pw = new PrintWriter(file.toFile());
            pw.write(writeBuffer);
            pw.flush();
            pw.close();
        } catch (Exception ex) {
        }
    }
}
