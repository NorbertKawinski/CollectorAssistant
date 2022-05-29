package net.kawinski.collecting.startup.helpers;

import net.kawinski.utils.java.JavaUtils;

import java.util.List;

public class StartupResources {

    private static final List<String> catImageNames = JavaUtils.readCaResourceAsLines("uploads/sample/cats.txt");

    public static String getRandomCatImagePathForUpload() {
        return "sample/cats/" + JavaUtils.randomElement(catImageNames);
    }

}
