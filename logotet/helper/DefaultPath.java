package rs.logotet.helper;

import java.util.prefs.Preferences;

/**
 *
 */
public class DefaultPath {
    private final String MYROOT = "logotet";        // radi upisivanja u registry bazu

    private Preferences bazni;
    private Preferences root;
    private Preferences mainNode;

    public DefaultPath(String mykey) {
        bazni = Preferences.systemRoot();
        root = bazni.node(MYROOT);
        mainNode = root.node(mykey);
    }

    public String getValue() {
        String value = mainNode.get("putanja",System.getProperty("home"));
        return value;
    }

    public void setValue(String val) {
        mainNode.put("putanja",val);
    }
}
