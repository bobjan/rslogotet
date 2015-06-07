package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class TeammatchRecord extends DBRecord {
    private int matchid;
    private int hometeamid;
    private int awayteamid;
    private int round;
    private double impscore;
    private double vpscore;
    private double impadj;
    private double vpadj;

    public TeammatchRecord() {
    }

    public int getMatchid() {
        return matchid;
    }

    public void setMatchid(int matchid) {
        this.matchid = matchid;
    }

    public int getHometeamid() {
        return hometeamid;
    }

    public void setHometeamid(int hometeamid) {
        this.hometeamid = hometeamid;
    }

    public int getAwayteamid() {
        return awayteamid;
    }

    public void setAwayteamid(int awayteamid) {
        this.awayteamid = awayteamid;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public double getImpscore() {
        return impscore;
    }

    public void setImpscore(double impscore) {
        this.impscore = impscore;
    }

    public double getVpscore() {
        return vpscore;
    }

    public void setVpscore(double vpscore) {
        this.vpscore = vpscore;
    }

    public double getImpadj() {
        return impadj;
    }

    public void setImpadj(double impadj) {
        this.impadj = impadj;
    }

    public double getVpadj() {
        return vpadj;
    }

    public void setVpadj(double vpadj) {
        this.vpadj = vpadj;
    }

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.TEAMMATCH);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLKlub makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            TeammatchRecord obj = (TeammatchRecord) ent;

        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        TeammatchRecord obj = (TeammatchRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nKlub:");

        return sb.toString();
    }
}