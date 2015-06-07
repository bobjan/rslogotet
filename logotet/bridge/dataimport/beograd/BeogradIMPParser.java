package rs.logotet.bridge.dataimport.beograd;

import rs.logotet.bridge.dataimport.IMPParser;

import java.util.StringTokenizer;

/**
 * User: jankovicb
 * Date: Sep 18, 2008
 */
public class BeogradIMPParser extends IMPParser {

    protected boolean pronadjiVrednosti(String ulaz) {
        StringTokenizer st = new StringTokenizer(ulaz.trim(), "\t");
        try {
            plasman = (int) Double.parseDouble(st.nextToken());
        } catch (NumberFormatException nfe) {
            plasman = (int) Double.parseDouble(st.nextToken());
        }
        prvoIme = st.nextToken();
        drugoIme = st.nextToken();
        st.nextToken();
        st.nextToken();
        String impS = st.nextToken();
        if (impS.trim().length() == 0)
            impS = "0,0";
        imp = (int) Double.parseDouble(impS.replace(',', '.'));
        return true;
    }
}
