package net.kawinski.utils.java;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class JavaUtils {
    public static final Random random = new Random();
    public static final Object EMPTY_ARRAY = new Object[] {};

    /**
     * Find the root exception message.
     * This methods recursively ignores exceptions up to the root exception.
     * The interesting error message is usually at the root, while other exceptions are just wrappers.
     *
     * Please note that discarding other messages might discard some context information about the error that'd make fixing it easier.
     * This method is intended to be used for displaying short error information,
     * but the whole error should also be logged somewhere in case that context information is needed.
     *
     * But even in case of displaying short information, this method might return useless or even misleading information.
     * For example: "access denied" is pretty useless as an error-response to the "username" field in the registration form.
     */
    public static String getRootErrorMessage(final Throwable e) {
        String errorMessage = "An error occurred. No details are available, please contact administrator.";
        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        return errorMessage;
    }

    // Since Java9 we can just use inputStream.readlAllBytes()
    public static byte[] fromInputStream(final InputStream is) throws IOException {
        try(final ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            final byte[] readChunk = new byte[1024];
            while(true) {
                final int nRead = is.read(readChunk, 0, readChunk.length);
                if(nRead == -1)
                    break;
                buffer.write(readChunk, 0, nRead);
            }

            buffer.flush();
            return buffer.toByteArray();
        }
    }

    public static List<String> readCaResourceAsLines(final String path) {
        return Arrays.stream(readCaResourceAsString(path).split("\n"))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public static String readCaResourceAsString(final String path) {
        return new String(readCaResource(path), StandardCharsets.UTF_8);
    }

    public static byte[] readCaResource(final String path) {
        return readResource("net/kawinski/collecting/" + path);
    }

    public static byte[] readResource(final String path) {
        final ClassLoader classLoader = JavaUtils.class.getClassLoader();
        try(final InputStream inputStream = classLoader.getResourceAsStream(path)) {
            if(inputStream == null)
                throw new RuntimeException("Cannot read resource at '"+path+"'");
            return fromInputStream(inputStream);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Optional<String> getExtensionByStringHandling(final String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public static String getExtensionOrThrow(final String fileName) {
        return getExtensionByStringHandling(fileName)
                .orElseThrow(() -> new IllegalArgumentException("Cannot extract extension from file name '" + fileName + "'"));
    }

    public static <T> T randomElement(List<T> elements) {
        return elements.get(random.nextInt(elements.size()));
    }
}
