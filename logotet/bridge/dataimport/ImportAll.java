package rs.logotet.bridge.dataimport;

import rs.logotet.bridge.dataimport.beograd.BeogradParser;
import rs.logotet.bridge.dataimport.novisad.NSParser;
import rs.logotet.bridge.test.StartDB;
import rs.logotet.helper.db.DBStartException;

import java.io.IOException;

/**
 * kreirano:
 * Date: Dec 2, 2008
 * Time: 6:30:33 PM
 */
public class ImportAll {
    public static void main(String[] args) throws IOException, DBStartException {
        StartDB.start();
        BeogradParser bp = new BeogradParser();
        bp.odradiSve();

        NSParser np = new NSParser();
        np.odradiSve();
    }
}
