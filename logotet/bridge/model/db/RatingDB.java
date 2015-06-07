package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.RatingRecord;
import rs.logotet.helper.Criteria;
import rs.logotet.helper.DBRecordException;
import rs.logotet.helper.db.ConnectionPoolManager;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class RatingDB extends DBClass {
    private static final String insCmd = "INSERT INTO rating (playerid,godina,broj,startpoints,points,rank) VALUES (?,?,?,?,?,?)";
    private static final String selCmd = "SELECT playerid,godina,broj,startpoints,points,rank FROM rating WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE rating SET " +
            "startpoints = ?," +
            "points = ?," +
            "rank = ? WHERE playedid = ? AND godina = ? AND broj = ?";
    private static final String delCmd = "DELETE FROM  rating WHERE playedid = ? AND godina = ? AND broj = ?";

    public RatingDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        RatingRecord rating = (RatingRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, rating.getPlayerid());
            stmt.setInt(2, rating.getGodina());
            stmt.setInt(3, rating.getBroj());
            stmt.setDouble(4, rating.getStartpoints());
            stmt.setDouble(5, rating.getPoints());
            stmt.setInt(6, rating.getRank());
            stmt.executeUpdate();
            stmt.close();
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public RatingRecord getById(int pid, int y, int br) {
        RatingRecord rating = new RatingRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND playedid = ? AND godina = ? AND broj = ?");
            stmt.setInt(1, pid);
            stmt.setInt(1, y);
            stmt.setInt(1, br);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, rating);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return rating;
    }

    public Vector getCollection(Criteria crit) {
        RatingRecord rating;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rating = new RatingRecord();
                mapRecordToObject(rs, rating);
                rating.setInDB(true);
                coll.add(rating);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        RatingRecord rating = (RatingRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setDouble(1, rating.getStartpoints());
            stmt.setDouble(2, rating.getPoints());
            stmt.setInt(3, rating.getRank());
            stmt.setInt(4, rating.getPlayerid());
            stmt.setInt(5, rating.getGodina());
            stmt.setInt(6, rating.getBroj());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        RatingRecord rating = (RatingRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, rating.getPlayerid());
            stmt.setInt(2, rating.getGodina());
            stmt.setInt(3, rating.getBroj());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, RatingRecord rating) throws SQLException {
        rating.setPlayerid(rs.getInt(1));
        rating.setGodina(rs.getInt(2));
        rating.setBroj(rs.getInt(3));
        rating.setStartpoints(rs.getDouble(4));
        rating.setPoints(rs.getInt(5));
        rating.setRank(rs.getInt(6));
        rating.setInDB(true);
    }
}