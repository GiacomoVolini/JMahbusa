package jmb.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Utilities {

    public static void createApplicationFolders(String appDirectory) {
        try {
            Files.createDirectories(Path.of(appDirectory + "/leaderboard"));
            Files.createDirectories(Path.of(appDirectory + "/saves"));
            Files.createDirectories(Path.of(appDirectory + "/settings"));
            Files.createDirectories(Path.of(appDirectory + "/languages/strings"));
            Files.createDirectories(Path.of(appDirectory + "/languages/flags"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void placeLanguageFiles(String appDirectory) {
        try {
            Path supportedPath = Path.of(appDirectory, "languages","languages.ini");
            if (!Files.exists(supportedPath)) {
                Files.copy(Objects.requireNonNull(Utilities.class.getResourceAsStream("languages.ini")), supportedPath);
                for (String lang : StringsReader.getSupportedLanguages()) {
                    Path langPath = Path.of(appDirectory , "languages","strings","STRINGS_" + lang + ".ini");
                    Path flagPath = Path.of(appDirectory , "languages","flags","flag_" + lang + ".png");
                    if (!Files.exists(langPath))
                        Files.copy(Objects.requireNonNull(Utilities.class.getResourceAsStream("STRINGS_"+lang+".ini")), langPath);
                    if (!Files.exists(flagPath))
                        Files.copy(Objects.requireNonNull(Utilities.class.getResourceAsStream("flags/flag_"+lang+".png")), flagPath);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static boolean isParsableAsInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }
}
