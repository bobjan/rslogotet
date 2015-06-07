package rs.logotet.bridge.dataimport.bridgemate;

import rs.logotet.bridge.dataimport.bridgemate.BridgemateParser;
import rs.logotet.helper.parser.Parser;
import rs.logotet.helper.parser.ParserListener;
import rs.logotet.helper.parser.ParsingReader;

import java.io.FileNotFoundException;
import java.text.ParseException;

public class ParserRunner implements ParserListener {
    private BridgemateParser bp;

    public ParserRunner(BridgemateParser bp) {
        this.bp = bp;
    }


    public void odradi(ParsingReader mdr) {
        bp.setListener(this);
        try {
            mdr.readAndParse(bp);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void gotovo(Parser parser) {
        System.out.println("--\t" + bp.toString());
    }

    public static void main(String[] args) throws FileNotFoundException {
        ParserRunner pr = new ParserRunner(new BridgemateParser());
        ParsingReader mdr = new ParsingReader("E:\\Razno\\TestData\\Data2.txt");
        pr.odradi(mdr);

    }

}
