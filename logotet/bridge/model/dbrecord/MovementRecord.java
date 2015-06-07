package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class MovementRecord extends DBRecord {
    private int movementid;
    private String tip;
    private int stolova;
    private String filename;

    public MovementRecord() {
    }

    public int getMovementid() {
        return movementid;
    }

    public void setMovementid(int movementid) {
        this.movementid = movementid;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getStolova() {
        return stolova;
    }

    public void setStolova(int stolova) {
        this.stolova = stolova;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.MOVEMENT);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLMovement makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            MovementRecord obj = (MovementRecord) ent;
            movementid = obj.getMovementid();
            tip = obj.getTip();
            stolova = obj.getStolova();
            filename = obj.getFilename();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        MovementRecord obj = (MovementRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nMovement:");
        sb.append("\n\tmovementid : " + movementid);
        sb.append("\n\ttip : " + tip);
        sb.append("\n\tstolova : " + stolova);
        sb.append("\n\tfilename : " + filename);
        return sb.toString();
    }
}