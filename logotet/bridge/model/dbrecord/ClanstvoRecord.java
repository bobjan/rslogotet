package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJDatum;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class ClanstvoRecord extends DBRecord {
    private int playerid;
    private int klubid;
    private BJDatum datum;

    public ClanstvoRecord() {
    }

    public int getPlayerid() {
        return playerid;
    }

    public void setPlayerid(int playerid) {
        this.playerid = playerid;
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

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.CLANSTVO);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLClanstvo makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            ClanstvoRecord obj = (ClanstvoRecord) ent;
            playerid = obj.getPlayerid();
            klubid = obj.getKlubid();
            datum = obj.getDatum();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        ClanstvoRecord obj = (ClanstvoRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nClanstvo:");
        sb.append("\n\tplayerid : " + playerid);
        sb.append("\n\tklubid : " + klubid);
        sb.append("\n\tdatum : " + datum);
        return sb.toString();
    }
}