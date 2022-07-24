package jmb.logic;

import org.ini4j.Ini;

import java.io.IOException;

public class StringsReader {

    private Ini strings;
    private static final String SECTION = "Strings";

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

