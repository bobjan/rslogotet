package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJDatum;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class UsertransRecord extends DBRecord {
    private int usertransid;		// AUTOINCREMENT
    private int klubid;
    private BJDatum datum;
    private double amount;
    private int status;

    public UsertransRecord() {
    }

    public int getUsertransid() {
        return usertransid;
    }

    public void setUsertransid(int usertransid) {
        this.usertransid = usertransid;
    }

    public int getKlubid() {
        return klubid;
    }

    public void setKlubid(int klubid) {
        this.klubid = klubid;
    }

    public BJDatum getDatum() {
        return datum;
    }

    public void setDatum(BJDatum datum) {
        this.datum = datum;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.USERTRANS);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLUsertrans makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            UsertransRecord obj = (UsertransRecord) ent;
            usertransid = obj.getUsertransid();
            klubid = obj.getKlubid();
            datum = obj.getDatum();
            amount = obj.getAmount();
            status = obj.getStatus();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        UsertransRecord obj = (UsertransRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nUsertrans:");
        sb.append("\n\tusertransid : " + usertransid);
        sb.append("\n\tklubid : " + klubid);
        sb.append("\n\tdatum : " + datum);
        sb.append("\n\tamount : " + amount);
        sb.append("\n\tstatus : " + status);
        return sb.toString();
    }
}