package rs.logotet.bridge.dataimport.onedummysession;

import rs.logotet.bridge.dataimport.onedummysession.*;
import rs.logotet.bridge.model.Seansa;
import rs.logotet.helper.parser.Parser;
import rs.logotet.helper.parser.ParserListener;

/**
 * Created by IntelliJ IDEA.
 * User: jankovicb
 * Date: May 13, 2009
 * Time: 12:04:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class DummyListener implements ParserListener {
    private Seansa seansa;

    public DummyListener() {
        seansa = null;
    }

    public void gotovo(Parser parser) {
        rs.logotet.bridge.dataimport.onedummysession.DummyParser dp = (rs.logotet.bridge.dataimport.onedummysession.DummyParser) parser;
        System.out.println(dp.getBoardNumber() + "__" + dp.getNorthNumber() + "__" + dp.getContract() +
                "__" + dp.getDecl() + "__ " + dp.getTricksMade());

        seansa.addPlayedContract(dp.getBoardNumber(), dp.getNorthNumber(),
                getEW(dp.getBoardNumber(), dp.getNorthNumber()), dp.getDenomination(),
                dp.getSuit(), dp.getDouble(), dp.getDecl(), dp.getTricksMade());

    }

    public void setSeansa(Seansa seansa) {
        this.seansa = seansa;
    }

    private int getEW(int broard, int NS) {
        return NS;
    }
}
