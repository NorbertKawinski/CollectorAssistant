package net.kawinski.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Random utils that shouldn't make it to production
 */
@Deprecated // Debug only
public class DebugUtils {

    public static void writeToTempFile(final String prefix, final String suffix, final byte[] bytes) {
        final File tmpFile = createTempFile(prefix, suffix);
        write(tmpFile.toPath(), bytes);
    }

    public static File createTempFile(final String prefix, final String suffix) {
        try {
            return File.createTempFile(prefix, suffix);
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void write(final String path, final byte[] bytes) {
        write(Paths.get(path), bytes);
    }

    public static void write(final Path path, final byte[] bytes) {
        try {
            Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
