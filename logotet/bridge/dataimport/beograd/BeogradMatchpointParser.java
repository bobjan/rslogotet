package rs.logotet.bridge.dataimport.beograd;

import rs.logotet.bridge.dataimport.MatchpointParser;

import java.util.StringTokenizer;

/**
 * User: jankovicb
 * Date: Sep 18, 2008
 */
public class BeogradMatchpointParser extends MatchpointParser {
    protected boolean pronadjiVrednosti(String ulaz) {
        StringTokenizer st = new StringTokenizer(ulaz.trim(), "\t");
        try {
            plasman = (int) Double.parseDouble(st.nextToken());
        } catch (NumberFormatException nfe) {
            plasman = (int) Double.parseDouble(st.nextToken());
        }

//        st.nextToken();
        prvoIme = st.nextToken();
        drugoIme = st.nextToken();
        st.nextToken();
        st.nextToken();
        st.nextToken();
        String proc = st.nextToken();
        procenat = Double.parseDouble(proc.replace(',', '.'));
//        System.out.println(ulaz);
//        System.out.println(prvoIme + "-" + drugoIme + " " + procenat);

        return true;
    }

}
