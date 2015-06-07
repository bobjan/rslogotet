package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJDatum;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class TurnirRecord extends DBRecord {
    private int turnirid;		// AUTOINCREMENT
    private BJDatum datum;
    private int tipObracuna;
    private int status;
    private String naziv;
    private boolean finished;

    public TurnirRecord() {
    }

    public int getTurnirid() {
        return turnirid;
    }

    public void setTurnirid(int turnirid) {
        this.turnirid = turnirid;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public BJDatum getDatum() {
        return datum;
    }

    public void setDatum(BJDatum datum) {
        this.datum = datum;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getTipObracuna() {
        return tipObracuna;
    }

    public void setTipObracuna(int tipObracuna) {
        this.tipObracuna = tipObracuna;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.TURNIR);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLTurnir makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            TurnirRecord obj = (TurnirRecord) ent;
            turnirid = obj.getTurnirid();
            datum = obj.getDatum();
            tipObracuna = obj.getTipObracuna();
            status = obj.getStatus();
            finished = obj.isFinished() ? true : false;
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        TurnirRecord obj = (TurnirRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nTurnir:");
        sb.append("\n\tturnirid : " + turnirid);
        sb.append("\n\tdatum : " + datum);
        sb.append("\n\tfinished : " + finished);
        sb.append("\n\tobracun : " + tipObracuna);
        sb.append("\n\tstatus : " + status);
        return sb.toString();
    }
}