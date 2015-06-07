package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class RatingRecord extends DBRecord {
    private int playerid;
    private int godina;
    private int broj;
    private double startpoints;
    private double points;
    private int rank;

    public RatingRecord() {
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPlayerid() {
        return playerid;
    }

    public void setPlayerid(int playerid) {
        this.playerid = playerid;
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

    public double getStartpoints() {
        return startpoints;
    }

    public void setStartpoints(double startpoints) {
        this.startpoints = startpoints;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.RATING);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLRating makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            RatingRecord obj = (RatingRecord) ent;
            playerid = obj.getPlayerid();
            godina = obj.getGodina();
            broj = obj.getBroj();
            startpoints = obj.getStartpoints();
            points = obj.getPoints();
            rank = obj.getRank();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        RatingRecord obj = (RatingRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nRating:");
        sb.append("\n\tplayerid : " + playerid);
        sb.append("\n\tgodina : " + godina);
        sb.append("\n\tbroj : " + broj);
        sb.append("\n\tstartpoints : " + startpoints);
        sb.append("\n\tpoints : " + points);
        sb.append("\n\trank : " + rank);
        return sb.toString();
    }
}