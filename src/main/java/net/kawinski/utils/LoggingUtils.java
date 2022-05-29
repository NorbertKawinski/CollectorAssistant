package net.kawinski.utils;

import org.slf4j.Logger;

import java.io.OutputStream;
import java.io.PrintStream;

public class LoggingUtils {

    public static void redirectStdoutToLogger(final Logger logger) {
        // JBoss has similar functionality which redirects stdout to logging facility.
        // But it has a single flaw:
        // Problemem jest to, ze Hibernate printuje wiadomosci z \r\n na koncu (expected behavior with calling println() on Windows)
        // JBoss wychwytuje i ucina \n na koncu (zamiast \r\n) i przekazuje do loggera.
        // Wszystko jest OK, jezeli pattern jest %m%n bo wtedy koniec wiadomosci po akcji JBossa jest jest \r\r\n. Razem z \n idzie flush.
        // Gorzej, gdy za %m jest cos jeszcze, np %l.
        // Wtedy po \r jest dalej jakas wiadomosc, ktora nadpisuje poczatek i sie printuje tylko to co jest po %m
        // I zamiast:
        // - System.out.println("Hello, I'm a message\rwhich get cut in half");
        // Efektywnie printuje sie tak, jakby bylo:
        // - System.out.println("which get cut in half");
        // This class fixes this issue.
        System.setOut(new PrintStream(new OutputStream() {
            private final StringBuilder lineBuilder = new StringBuilder();

            @Override
            public void write(final int b) {
                final char ch = (char)b;
                if(ch == '\n') {
                    final String outRaw = lineBuilder.toString();
                    final String out = outRaw.trim();
                    lineBuilder.setLength(0);
                    logger.info("stdout: {}", out);
                } else {
                    lineBuilder.append(ch);
                }
            }
        }));
    }

}
