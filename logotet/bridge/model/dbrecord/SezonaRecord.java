package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJDatum;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class SezonaRecord extends DBRecord {
    private int godina;
    private int broj;
    private BJDatum startdate;
    private BJDatum enddate;

    public SezonaRecord() {
        startdate = new BJDatum();
        enddate = new BJDatum();
    }

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public BJDatum getStartdate() {
        return startdate;
    }

    public void setStartdate(BJDatum startdate) {
        this.startdate = startdate;
    }

    public BJDatum getEnddate() {
        return enddate;
    }

    public void setEnddate(BJDatum enddate) {
        this.enddate = enddate;
    }

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.SEZONA);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLSezona makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            SezonaRecord obj = (SezonaRecord) ent;
            godina = obj.getGodina();
            broj = obj.getBroj();
            startdate = obj.getStartdate();
            enddate = obj.getEnddate();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        SezonaRecord obj = (SezonaRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nSezona:");
        sb.append("\n\tgodina : " + godina);
        sb.append("\n\tbroj : " + broj);
        sb.append("\n\tstartdate : " + startdate);
        sb.append("\n\tenddate : " + enddate);
        return sb.toString();
    }
}