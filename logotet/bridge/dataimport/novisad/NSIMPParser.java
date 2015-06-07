package rs.logotet.bridge.dataimport.novisad;

import rs.logotet.bridge.dataimport.IMPParser;

/**
 * User: jankovicb
 * Date: Sep 18, 2008
 */
public class NSIMPParser extends IMPParser {
    protected boolean pronadjiVrednosti(String ulaz) {
        try {
            plasman = (int) Double.parseDouble(ulaz.substring(0, 3).trim());
        } catch (NumberFormatException nfe) {
            plasman = 0;
        }
        prvoIme = ulaz.substring(4, 27).trim();
        drugoIme = ulaz.substring(29, 50).trim();
        imp = (int) Double.parseDouble(ulaz.substring(56, 63).trim());
        return true;
    }
}
