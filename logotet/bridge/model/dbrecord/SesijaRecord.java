package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJDatum;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class SesijaRecord extends DBRecord {
    private int sesijaid;
    private int movementid;
    private int turnirid;

    private BJDatum datum;
    private int oznakasesije;
    private int tip;

    private int flagresult;
    private int obracun;

    public SesijaRecord() {
    }

    public int getTurnirid() {
        return turnirid;
    }

    public void setTurnirid(int turnirid) {
        this.turnirid = turnirid;
    }

    public int getOznakasesije() {
        return oznakasesije;
    }

    public void setOznakasesije(int oznakasesije) {
        this.oznakasesije = oznakasesije;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public int getSesijaid() {
        return sesijaid;
    }

    public void setSesijaid(int sesijaid) {
        this.sesijaid = sesijaid;
    }

    public int getMovementid() {
        return movementid;
    }

    public void setMovementid(int movementid) {
        this.movementid = movementid;
    }

    public BJDatum getDatum() {
        return datum;
    }

    public void setDatum(BJDatum datum) {
        this.datum = datum;
    }

    public int getFlagresult() {
        return flagresult;
    }

    public void setFlagresult(int flagresult) {
        this.flagresult = flagresult;
    }

    public int getObracun() {
        return obracun;
    }

    public void setObracun(int obracun) {
        this.obracun = obracun;
    }

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.SESIJA);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLSesija makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            SesijaRecord obj = (SesijaRecord) ent;
            turnirid = obj.getTurnirid();
            oznakasesije = obj.getOznakasesije();
            tip = obj.getTip();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        SesijaRecord obj = (SesijaRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nSesija:");
        sb.append("\n\tturnirid : " + turnirid);
        sb.append("\n\toznakasesije : " + oznakasesije);
        sb.append("\n\ttip : " + tip);
        return sb.toString();
    }


}