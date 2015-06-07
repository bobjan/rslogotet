package rs.logotet.bridge.dataimport.onedummysession;

import rs.logotet.helper.parser.Parser;
import rs.logotet.helper.parser.ParserListener;

import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: jankovicb
 * Date: May 13, 2009
 * Time: 11:55:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class DummyParser implements Parser {
    private ParserListener parserListener;


    private int boardNumber;
    private int northNumber;
    private String contract;
    private String decl;
    private int tricksMade;


    public void parse(String s) throws ParseException {
        if(s.trim().length() == 0)
            return;
        if(s.substring(0,1).equals("#"))
        return;
        StringTokenizer st = new StringTokenizer(s, "\t");
        try {
            boardNumber = Integer.parseInt(st.nextToken().trim());
            northNumber = Integer.parseInt(st.nextToken().trim());
            contract = st.nextToken().trim();
            decl = st.nextToken().trim();
            String tmp = st.nextToken().trim();
            if (tmp.equals("="))
                tricksMade = 0;
            else {
                if (tmp.substring(0, 1).equals("-"))
                    tricksMade = Integer.parseInt(tmp.substring(1)) * (-1);
                else
                    tricksMade = Integer.parseInt(tmp.substring(1));
            }
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage() + "\t" + boardNumber + "-" + northNumber + "-" +
                    contract + "-" + decl + "-" + tricksMade);
        } catch (NoSuchElementException nse) {
        }
        if (parserListener != null)
            parserListener.gotovo(this);
    }

    public void setListener(ParserListener pl) {
        parserListener = pl;
    }

    public int getBoardNumber() {
        return boardNumber;
    }

    public int getNorthNumber() {
        return northNumber;
    }

    public String getContract() {
        return contract;
    }

    public String getDecl() {
        return decl;
    }

    public int getTricksMade() {
        return tricksMade;
    }

    public int getDenomination() {
        try {
            return Integer.parseInt(contract.substring(0, 1));
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public int getSuit() {
        String boje[] = {"C", "D", "H", "S", "NT"};
        for (int i = 0; i < boje.length; i++) {
            int ima = contract.toUpperCase().indexOf(boje[i]);
            if (ima > 0)
                return i;
        }
        return -1;
    }

    public int getDouble() {
        int ima = contract.toUpperCase().indexOf("XX");
        if (ima > 0)
            return 2;
        ima = contract.toUpperCase().indexOf("X");
        if (ima > 0)
            return 1;
        return 0;
    }

}
