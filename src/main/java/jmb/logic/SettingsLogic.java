package jmb.logic;

import org.ini4j.Ini;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static jmb.logic.Logic.logic;


public class SettingsLogic {
    private final static int MIN_RESOLUTION_WIDTH = 640;
    private final static int MIN_RESOLUTION_HEIGHT = 480;

    private static final String[] LEFT_PRESET = { "#000000",  "#ad1111", "#ffde3a", "#c0c0c0", "#6495ed"};
    private static final String[] RIGHT_PRESET = { "#090957", "#53960f", "#c21123", "#daa505", "#fffb00"};
    private Ini current;

    private Path currentPath;

    public SettingsLogic() {
        try {
            String settingsDir = logic.getAppDirectory() + "/settings";
            currentPath = Path.of (settingsDir.concat("/Current.ini"));
            if (!Files.exists(currentPath)) {
                Files.createFile(currentPath);
                getDefaultSettings();
            }
            current = new Ini (currentPath.toFile());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected void getDefaultSettings() {
        try {
            Ini defaults = new Ini(this.getClass().getResource("Defaults.ini"));
            defaults.store(currentPath.toFile());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected void restoreCurrent() {
        try {
            current.load(currentPath.toFile());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected void resetDefaultSettings() {
        getDefaultSettings();
        restoreCurrent();
    }

    protected void applySettingsChanges() {
        try {
            current.store();
            logic.initializeStringsReader();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    protected static void flagTutorialPlayed() {
        try {
            String fileLocation = logic.getAppDirectory() + "/settings/played.tut";
            Path filePath = Path.of(fileLocation);
            if (!Files.exists(filePath))
                Files.createFile(filePath);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected static boolean shouldPlayTutorial() {
        String fileLocation = logic.getAppDirectory() + "/settings/played.tut";
        Path filePath = Path.of (fileLocation);
        return !Files.exists(filePath);
    }

    protected void setSetting (String section, String setting, Object value) {
        current.put(section, setting, value);
    }

    protected <T> T getSetting (String section, String setting, Class<T> whichClass) {
        return current.get(section, setting, whichClass);
    }

    protected String getSetting (boolean leftPreset, int presetEnum) {
        String out;
        if (leftPreset)
            out = LEFT_PRESET[presetEnum];
        else out = RIGHT_PRESET[presetEnum];
        return out;
    }
}
