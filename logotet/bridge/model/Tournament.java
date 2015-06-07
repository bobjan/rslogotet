package rs.logotet.bridge.model;

import rs.logotet.bridge.model.*;
import rs.logotet.bridge.model.db.SesijaDB;
import rs.logotet.bridge.model.dbrecord.SesijaRecord;
import rs.logotet.bridge.model.dbrecord.TurnirRecord;
import rs.logotet.helper.Criteria;
import rs.logotet.helper.DBRecordException;
import rs.logotet.helper.FieldCriteria;
import rs.logotet.util.BJDatum;

import java.util.Enumeration;
import java.util.Vector;

/**
 * wrapper around TurnirRecord
 */
public class Tournament {

    private TurnirRecord turnirRecord  = null;
    private BJDatum datum;
    private int tipTurnira; // 0 - individualni; 1 - parski; 2 - timski
    // kada su senase  npr.parske; a konsolidacija plasmana se vrsi na ind. nivou
    // znaco moze turnir da bude ind. a seanse parske
    // ako je turnir parski onda nema promene parova medju seansama
    // ako je turnir timski onda nema promene igraca medju seansama

    private Vector seanse;


    public Tournament(TurnirRecord turRecord) {
        this(turRecord.getDatum(),turRecord.getTipObracuna());
        turnirRecord = turRecord;
        fillSeanse();
    }

    public Tournament(BJDatum datum, int tipTurnira) {
        if(turnirRecord == null)
            turnirRecord = new TurnirRecord();
        this.datum = datum;
        this.tipTurnira = tipTurnira;
        seanse = new Vector();
    }

    public Tournament(BJDatum datum) {
        this(datum, 1);
    }

    private void fillSeanse() {
        SesijaRecord sesijaRecord = new SesijaRecord();
        SesijaDB sesijaDB = (SesijaDB) sesijaRecord.getDBClass();
        Criteria crit = new Criteria();
        crit.addOgranicenje(new FieldCriteria("", "turnirid", FieldCriteria.IS_EQUAL, turnirRecord.getTurnirid()));
        Vector vec = sesijaDB.getCollection(crit);
        Enumeration enm = vec.elements();
        while (enm.hasMoreElements()) {
            SesijaRecord sr = (SesijaRecord) enm.nextElement();
            addSeansa(new rs.logotet.bridge.model.Seansa(this, sr));
        }
    }

    public int getTurnirid() {
        return turnirRecord.getTurnirid();
    }

    public void setTurnir(TurnirRecord turnir) {
        turnirRecord = turnir;
    }

    public int getScoringType() {
        return turnirRecord.getTipObracuna();
    }

    public void setScoringType(int scoringType) {
        turnirRecord.setTipObracuna(scoringType);
    }

    public BJDatum getDatum() {
        return turnirRecord.getDatum();
    }

    public void setDatum(BJDatum datum) {
        turnirRecord.setDatum(datum);
    }

    public int getTipTurnira() {
        return turnirRecord.getTipObracuna();//@todo pvp pogledati
    }

    public void setTipTurnira(int tipTurnira) {
        this.tipTurnira = tipTurnira;
    }

    public Vector getSeanse() {
        return seanse;
    }

    public void addSeansa(rs.logotet.bridge.model.Seansa s) {
        if (!seanse.contains(s)) {
            s.setTurnir(this);
            seanse.add(s);
        }
    }

    public void createSeansa(){
        seanse.add(new rs.logotet.bridge.model.Seansa(this));
    }

    public void makePersistent() {
        try {
            turnirRecord.makePersistent();
            Enumeration enm = seanse.elements();
            while (enm.hasMoreElements()) {
                rs.logotet.bridge.model.Seansa obj = (rs.logotet.bridge.model.Seansa) enm.nextElement();
                obj.makePersistent();
            }
        } catch (DBRecordException e) {
        }
    }
}
