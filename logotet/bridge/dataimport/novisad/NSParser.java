package rs.logotet.bridge.dataimport.novisad;

import rs.logotet.bridge.dataimport.IMPParser;
import rs.logotet.bridge.dataimport.MatchpointParser;
import rs.logotet.bridge.dataimport.novisad.NSIMPParser;
import rs.logotet.bridge.dataimport.novisad.NSMatchpointParser;
import rs.logotet.bridge.test.StartDB;
import rs.logotet.bridge.test.parsing.BridgeFolderManipulator;
import rs.logotet.helper.db.DBStartException;

import java.io.IOException;

/**
 * kreirano:
 * Date: Nov 8, 2008
 * Time: 12:00:00 PM
 */
public class NSParser {
    private static String rootFolder = "E://Transfers";
    private MatchpointParser mParser;
    private IMPParser impParser;

    public NSParser() {
        mParser = new NSMatchpointParser();
        impParser = new NSIMPParser();
    }

    public void odradiSve() {
        odradi("NS", false);
        odradi("NS/IMP", true);
    }

    public void odradi(String folder, boolean imp) {
        BridgeFolderManipulator fm = new BridgeFolderManipulator(rootFolder + "/" + folder, mParser);
        if (!fm.isOK())
            return;
        if (!imp) {
            fm.manipulate(mParser);
        } else {
            fm.manipulate(impParser);
        }
    }

    public static void main(String[] args) throws IOException, DBStartException {
        StartDB.start();
        NSParser bp = new NSParser();
        bp.odradiSve();
    }

}
