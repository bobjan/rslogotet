package rs.logotet.bridge.dataimport.bridgemate;

import rs.logotet.bridge.base.Bridge;
import rs.logotet.bridge.base.Bridge;
import rs.logotet.helper.parser.Parser;
import rs.logotet.helper.parser.ParserListener;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * User: jankovicb
 * Date: Aug 28, 2008
 */
public class BridgemateParser implements Parser {
    private String board;
    private String NSPlayer;
    private String EWPlayer;
    private String decl;
    private String contract;
    private String result;
    private ParserListener parserListener;

    public BridgemateParser() {
        parserListener = null;
    }

    public void parse(String ulaz) {
//        id; section;table;roundl;board;NS;EW;declN;declStrl;contract;result .....
        StringBuffer sb;
        StringTokenizer st = new StringTokenizer(ulaz, ";");
        st.nextToken();
        int section = Integer.parseInt(st.nextToken());
        if (section != 1)
            return;
        st.nextToken();
        st.nextToken();
        board = st.nextToken();
        NSPlayer = st.nextToken();
        EWPlayer = st.nextToken();
        st.nextToken();
        decl = removeQuotes(st.nextToken());
        contract = removeQuotes(st.nextToken());
        result = replaceEqual(removePlus(removeQuotes(st.nextToken())));
        if (parserListener != null)
            parserListener.gotovo(this);
    }

    public void setListener(ParserListener pl) {
        parserListener = pl;
    }


    private String removeQuotes(String s) {
        StringBuffer sb = new StringBuffer(s);
        while (sb.indexOf("\"") >= 0) {
            int idx = sb.indexOf("\"");
            sb.deleteCharAt(idx);
        }
        return sb.toString();
    }

    private String removePlus(String s) {
        StringBuffer sb = new StringBuffer(s);
        while (sb.indexOf("+") >= 0) {
            int idx = sb.indexOf("+");
            sb.deleteCharAt(idx);
        }
        return sb.toString();
    }

    private String replaceEqual(String s) {
        StringBuffer sb = new StringBuffer(s);
        while (sb.indexOf("=") >= 0) {
            int idx = sb.indexOf("=");
            sb.replace(idx, idx + 1, "0");
        }
        return sb.toString();
    }

    public int getBoard() {
        return Integer.parseInt(board.trim());
    }

    public int getNSPlayer() {
        return Integer.parseInt(NSPlayer.trim());
    }

    public int getEWPlayer() {
        return Integer.parseInt(EWPlayer.trim());
    }

    public String getDecl() {
        return decl;
    }

    public int getContractLevel() {
        StringTokenizer st = new StringTokenizer(contract, " ");
        return Integer.parseInt(st.nextToken().trim());
    }

    public int getContractSuit() {
        StringTokenizer st = new StringTokenizer(contract, " ");
        st.nextToken();
        return Bridge.getSuitInteger(st.nextToken());
    }

    public int getContractDouble() {
        try {
            StringTokenizer st = new StringTokenizer(contract, " ");
            st.nextToken();
            st.nextToken();
            String kontra = st.nextToken();
            if (kontra.toUpperCase().equals("X"))
                return Bridge.DOUBLED;
            if (kontra.toUpperCase().equals("XX"))
                return Bridge.REDOUBLED;
        } catch (NoSuchElementException nse) {
            return Bridge.NO_DOUBLE;
        }
        return Bridge.NO_DOUBLE;
    }

    public int getResult() {
        return Integer.parseInt(result.trim());
    }

    public ParserListener getParserListener() {
        return parserListener;
    }

    public String toString() {
        return board + "\t" + NSPlayer + "\t" + EWPlayer + "\t" + decl + "\t" + contract + "\t" + result.trim();
    }
}
