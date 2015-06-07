package rs.logotet.bridge.dataimport;

import rs.logotet.helper.parser.Parser;
import rs.logotet.helper.parser.ParserListener;

/**
 * User: jankovicb
 * Date: Sep 18, 2008
 */
public abstract class IMPParser implements Parser {
    protected ParserListener parserListener;

    protected String prvoIme;
    protected String drugoIme;
    protected int imp;
    protected int plasman;

    public void parse(String ulaz) {
        pronadjiVrednosti(ulaz);
        if (parserListener != null)
            parserListener.gotovo(this);
    }

    abstract protected boolean pronadjiVrednosti(String ulaz);

    public void setListener(ParserListener pl) {
        parserListener = pl;
    }

    public String getPrvoIme() {
        return prvoIme;
    }

    public String getDrugoIme() {
        return drugoIme;
    }

    public int getImp() {
        return imp;
    }

    public int getPlasman() {
        return plasman;
    }
}
