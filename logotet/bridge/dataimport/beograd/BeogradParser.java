package rs.logotet.bridge.dataimport.beograd;

import rs.logotet.bridge.dataimport.IMPParser;
import rs.logotet.bridge.dataimport.MatchpointParser;
import rs.logotet.bridge.dataimport.beograd.BeogradIMPParser;
import rs.logotet.bridge.dataimport.beograd.BeogradMatchpointParser;
import rs.logotet.bridge.test.StartDB;
import rs.logotet.bridge.test.parsing.BridgeFolderManipulator;
import rs.logotet.helper.db.DBStartException;

import java.io.IOException;

/**
 * kreirano:
 * Date: Nov 8, 2008
 * Time: 12:00:00 PM
 */
public class BeogradParser {
    private static String rootFolder = "E://Transfers";
    private MatchpointParser mParser;
    private IMPParser impParser;

    public BeogradParser() {
        mParser = new BeogradMatchpointParser();
        impParser = new BeogradIMPParser();
    }

    public void odradiSve() {
        odradi("AS", false);
        odradi("DaSilva", false);
        odradi("Dummy", false);
        odradi("Ostalo", false);
        odradi("AS/IMP", true);
        odradi("DaSilva/IMP", true);
        odradi("Dummy/IMP", true);
        odradi("Ostalo/IMP", true);
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
        BeogradParser bp = new BeogradParser();
        bp.odradiSve();
    }

}
