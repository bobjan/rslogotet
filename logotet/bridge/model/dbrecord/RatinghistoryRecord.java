package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJDatum;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class RatinghistoryRecord extends DBRecord {
    private int playerid;
    private BJDatum datum;
    private double rating;

    public RatinghistoryRecord() {
    }

    public int getPlayerid() {
        return playerid;
    }

    public void setPlayerid(int playerid) {
        this.playerid = playerid;
    }

    public BJDatum getDatum() {
        return datum;
    }

    public void setDatum(BJDatum datum) {
        this.datum = datum;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getId() {
        return playerid + "-" + datum.getStringDate();
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.RATINGHISTORY);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLRatinghistory makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            RatinghistoryRecord obj = (RatinghistoryRecord) ent;
            playerid = obj.getPlayerid();
            datum = obj.getDatum();
            rating = obj.getRating();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        RatinghistoryRecord obj = (RatinghistoryRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nRatinghistory:");
        sb.append("\n\tplayerid : " + playerid);
        sb.append("\n\tdatum : " + datum);
        sb.append("\n\trating : " + rating);
        return sb.toString();
    }
}