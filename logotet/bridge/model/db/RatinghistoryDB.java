package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.RatinghistoryRecord;
import rs.logotet.helper.Criteria;
import rs.logotet.helper.DBRecordException;
import rs.logotet.helper.db.ConnectionPoolManager;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJDatum;
import rs.logotet.util.BJTraceLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class RatinghistoryDB extends DBClass {
    private static final String insCmd = "INSERT INTO ratinghistory (playerid,datum,rating) VALUES (?,?,?)";
    private static final String selCmd = "SELECT playerid,datum,rating FROM ratinghistory WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE ratinghistory SET " +
            "rating = ? WHERE playerid = ? AND datum = ?";
    private static final String delCmd = "DELETE FROM  ratinghistory WHERE playerid = ? AND datum = ?";

    public RatinghistoryDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public void insert(DBRecord ent) throws DBRecordException {
        RatinghistoryRecord ratinghistory = (RatinghistoryRecord) ent;
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, ratinghistory.getPlayerid());
            stmt.setDate(2, ratinghistory.getDatum().getSQLDate());
            stmt.setDouble(3, ratinghistory.getRating());
            stmt.executeUpdate();
            stmt.close();
            ent.setInDB(true);
        } catch (SQLException sqle) {
            update(ent);
//            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public RatinghistoryRecord getById(int pid, BJDatum dat) {
        RatinghistoryRecord ratinghistory = new RatinghistoryRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND playerid = ? AND datum = ?");
            stmt.setInt(1, pid);
            stmt.setDate(1, dat.getSQLDate());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, ratinghistory);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return ratinghistory;
    }

    public Vector getCollection(Criteria crit) {
        RatinghistoryRecord ratinghistory;
        if (crit == null)
            crit = new Criteria();
        Vector coll = new Vector();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ratinghistory = new RatinghistoryRecord();
                mapRecordToObject(rs, ratinghistory);
                ratinghistory.setInDB(true);
                coll.add(ratinghistory);
            }
            stmt.close();
        } catch (SQLException sqle) {
//            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        RatinghistoryRecord ratinghistory = (RatinghistoryRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setDouble(1, ratinghistory.getRating());
            stmt.setInt(2, ratinghistory.getPlayerid());
            stmt.setDate(3, ratinghistory.getDatum().getSQLDate());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        RatinghistoryRecord ratinghistory = (RatinghistoryRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, ratinghistory.getPlayerid());
            stmt.setDate(2, ratinghistory.getDatum().getSQLDate());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, RatinghistoryRecord ratinghistory) throws SQLException {
        ratinghistory.setPlayerid(rs.getInt(1));
        ratinghistory.setDatum(new BJDatum(rs.getDate(2)));
        ratinghistory.setRating(rs.getDouble(3));
        ratinghistory.setInDB(true);
    }
}