package jmb.logic;

import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.IOException;

public class StringsReader {

    private Ini strings;
    private static final String SECTION = "Strings";
    protected static String[] getSupportedLanguages() {
        String[] out = null;
        try {
            Ini ini = new Ini(StringsReader.class.getResource("supportedLanguages.ini"));
            Profile.Section section = ini.get("Languages");
            out = section.getAll("supportedLanguage", String[].class);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return out;
    }

    public StringsReader(String language) {
        try {
            String name = "STRINGS_"+language+".ini";
            strings = new Ini(this.getClass().getResource(name));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    protected String get(String key) {
        return strings.get(SECTION, key);
    }


}

