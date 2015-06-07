package rs.logotet.bridge.test;

import rs.logotet.bridge.model.db.SesijaDB;
import rs.logotet.bridge.model.dbrecord.SesijaRecord;
import rs.logotet.bridge.model.Seansa;
import rs.logotet.bridge.model.Tournament;
import rs.logotet.bridge.model.masterpoeni.BJRating;
import rs.logotet.bridge.test.*;
import rs.logotet.helper.Criteria;
import rs.logotet.helper.db.DBStartException;
import rs.logotet.util.BJDatum;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

/**
 * kreirano:
 * Date: Nov 19, 2008
 * Time: 8:25:19 PM
 */
public class DoRating {
    public static void main(String[] args) throws IOException, DBStartException {
        rs.logotet.bridge.test.StartDB.start();
        Tournament tur = new Tournament(new BJDatum());
        SesijaRecord sesRecord = new SesijaRecord();
        SesijaDB sesijaDB = (SesijaDB) sesRecord.getDBClass();
        Criteria crit = new Criteria();
//        crit.addOgranicenje(new FieldCriteria("","obracun",FieldCriteria.IS_EQUAL,Bridge.BUTTLER0));
        crit.addSort("datum");
        System.out.println("reading sesijaDB ");
        Vector sveSesije = sesijaDB.getCollection(crit);
        Vector seanse = new Vector();
        Enumeration enm = sveSesije.elements();
        System.out.println("enumerating sveSesije ");
        while (enm.hasMoreElements()) {
            sesRecord = (SesijaRecord) enm.nextElement();
            Seansa seansa = new Seansa(tur);
            seanse.add(seansa);
        }

        enm = seanse.elements();
        System.out.println("enumerating seansa ");
        while (enm.hasMoreElements()) {
            BJRating bjRating = new BJRating((Seansa) enm.nextElement());
            bjRating.preracunaj();
        }

    }
}
