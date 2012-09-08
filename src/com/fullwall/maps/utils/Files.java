package com.fullwall.maps.utils;

import java.io.File;
import java.io.IOException;

public class Files {
    private Files() {
    }

    public static void createFile(File file) {
        try {
            file.getParentFile().mkdirs();
            if (!file.exists())
                file.createNewFile();
        } catch (IOException ex) {
            Messaging.log("Unable to create file: " + file.getName() + ".");
        }
    }
}
