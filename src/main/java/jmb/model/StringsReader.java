package jmb.model;

import org.ini4j.Ini;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

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

