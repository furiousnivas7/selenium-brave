package com.example;

import java.nio.file.Files;
import java.nio.file.Path;

/** Finds the Brave browser executable for the current operating system. */
public final class BravePath {

    private BravePath() {
    }

    public static String find() {
        // An explicit override always wins
        String override = System.getenv("BRAVE_PATH");
        if (override != null && !override.isBlank()) {
            return override;
        }

        String os = System.getProperty("os.name").toLowerCase();
        String path;

        if (os.contains("win")) {
            path = System.getenv("LOCALAPPDATA")
                    + "\\BraveSoftware\\Brave-Browser"
                    + "\\Application\\brave.exe";
        } else if (os.contains("mac")) {
            path = "/Applications/Brave Browser.app"
                    + "/Contents/MacOS/Brave Browser";
        } else {
            path = "/usr/bin/brave-browser";
        }

        if (!Files.isExecutable(Path.of(path))) {
            throw new IllegalStateException(
                "Brave not found at: " + path
                + " (install Brave or set BRAVE_PATH)");
        }
        return path;
    }
}
