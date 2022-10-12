package jmb.logic;

import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.IOException;

public class StringsReader {

    private Ini strings;
    //TODO Cambiare gestione dei file STRINGS e supportedLanguages per permettere dinamicità ed estendibilità
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

    protected static boolean isLanguageRightToLeft(String language) {
        boolean out = false;
        try {
            Ini ini = new Ini(StringsReader.class.getResource("supportedLanguages.ini"));
            Profile.Section rightToLeftLanguages = ini.get("RightToLeft");
            String[] rTLArray = rightToLeftLanguages.getAll("language", String[].class);
            for (int i = 0; i<rTLArray.length && !out; i++) {
                if (language.equals(rTLArray[i])) {
                    out = true;
                }
            }
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

