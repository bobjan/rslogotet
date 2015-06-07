package rs.logotet.bridge.dataimport.novisad;

import rs.logotet.bridge.dataimport.MatchpointParser;

/**
 * User: jankovicb
 * Date: Sep 18, 2008
 */
public class NSMatchpointParser extends MatchpointParser {
    protected boolean pronadjiVrednosti(String ulaz) {
        try {
            plasman = (int) Double.parseDouble(ulaz.substring(0, 3).trim());
        } catch (NumberFormatException nfe) {
            plasman = 0;
        }

//        st.nextToken();
        prvoIme = ulaz.substring(4, 26).trim();
        drugoIme = ulaz.substring(29, 51).trim();
        procenat = Double.parseDouble(ulaz.substring(63, 68).trim());
//        System.out.println(ulaz);
//        System.out.println(prvoIme + "-" + drugoIme + " " + procenat);

        return true;
    }
}
