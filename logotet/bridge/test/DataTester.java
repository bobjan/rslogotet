package rs.logotet.bridge.test;

import rs.logotet.bridge.dataimport.bridgemate.BridgemateParser;
import rs.logotet.bridge.model.*;
import rs.logotet.bridge.base.Bridge;
import rs.logotet.bridge.test.*;
import rs.logotet.helper.db.DBStartException;
import rs.logotet.helper.parser.Parser;
import rs.logotet.helper.parser.ParserListener;
import rs.logotet.helper.parser.ParsingReader;
import rs.logotet.util.BJDatum;

import java.io.IOException;
import java.text.ParseException;

/**
 *
 */
public class DataTester implements ParserListener {
    Tournament turnir;
    Seansa seansa;
    private BridgemateParser bridgemateParser;

    public DataTester(Tournament turnir, Seansa seansa) {
        this.turnir = turnir;
        this.seansa = seansa;
        bridgemateParser = new BridgemateParser();
    }

    public void odradi(ParsingReader mdr) {
        bridgemateParser.setListener(this);
        try {
            mdr.readAndParse(bridgemateParser);
        } catch (ParseException e) {

        }
    }

    public static void main(String[] args) throws IOException, DBStartException {
        rs.logotet.bridge.test.StartDB.start();
        Tournament tur = new Tournament(new BJDatum());
        Seansa seansa = new Seansa(tur);
        Section section = new Section(seansa);
        DataTester dataTester = new DataTester(tur, seansa);
        ParsingReader mdr = new ParsingReader("E:\\AllSoftwareRoot\\AppData\\bridge\\Data2.txt");
        RandomPlayers rp = new RandomPlayers();
        BridgePlayer bp1;
        BridgePlayer bp2;
        BridgeTeam bt;
        for (int i = 0; i < 11; i++) {
            bp1 = new BridgePlayer(rp.getRandomIme(), rp.getRandomPrezime());
            bp2 = new BridgePlayer(rp.getRandomIme(), rp.getRandomPrezime());
            bt = new BridgeTeam();
            bt.add(bp1);
            bt.add(bp2);
            bt.setTakmicarskiBroj(i + 1);
            section.addTeam(bt, Bridge.NORTH_SOUTH);

            bp1 = new BridgePlayer(rp.getRandomIme(), rp.getRandomPrezime());
            bp2 = new BridgePlayer(rp.getRandomIme(), rp.getRandomPrezime());
            bt = new BridgeTeam();
            bt.add(bp1);
            bt.add(bp2);
            bt.setTakmicarskiBroj(i + 1);
            section.addTeam(bt, Bridge.EAST_WEST);
        }
        dataTester.odradi(mdr);
    }

    public void gotovo(Parser parser) {
        bridgemateParser.getContractDouble();
        seansa.addPlayedContract(bridgemateParser.getBoard(),
                bridgemateParser.getNSPlayer(), bridgemateParser.getEWPlayer(),
                bridgemateParser.getContractLevel(), bridgemateParser.getContractSuit(),
                bridgemateParser.getContractDouble(),
                bridgemateParser.getDecl(), bridgemateParser.getResult());
    }
}
