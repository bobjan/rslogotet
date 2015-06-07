package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.TeammatchRecord;
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

public class TeammatchDB extends DBClass {
    private static final String insCmd = "INSERT INTO teammatch (round,hometeamid,awayteamid,impscore,impadj,vpscore,vpadj) VALUES (?,?,?,?,?,?,?)";
    private static final String selCmd = "SELECT round,matchid,hometeamid,awayteamid,impscore,impadj,vpscore,vpadj FROM teammatch WHERE 1 ";
    private static final String updCmd = "UPDATE teammatch SET " +
            "round = ?," +
            "hometeamid = ?," +
            "awayteamid = ?," +
            "impscore = ?," +
            "impadj = ?," +
            "vpscore = ?," +
            "vpadj = ? WHERE matchid = ? ";
    private static final String delCmd = "DELETE FROM  teammatch WHERE matchid = ? ";

    public TeammatchDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public void insert(DBRecord ent) throws DBRecordException {
        TeammatchRecord match = (TeammatchRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, match.getRound());
            stmt.setInt(2, match.getHometeamid());
            stmt.setInt(3, match.getAwayteamid());
            stmt.setDouble(4, match.getImpscore());
            stmt.setDouble(5, match.getImpadj());
            stmt.setDouble(6, match.getVpscore());
            stmt.setDouble(7, match.getVpadj());
            stmt.executeUpdate();
            stmt.close();
            match.setMatchid(getAutoincrementKey());
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public TeammatchRecord getById(int id) {
        TeammatchRecord match = new TeammatchRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND matchid = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, match);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return match;
    }

    public Vector getCollection(Criteria crit) {
        TeammatchRecord match;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                match = new TeammatchRecord();
                mapRecordToObject(rs, match);
                match.setInDB(true);
                coll.add(match);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!! MATCH-DB " + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        TeammatchRecord match = (TeammatchRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setInt(1, match.getRound());
            stmt.setInt(2, match.getHometeamid());
            stmt.setInt(3, match.getAwayteamid());
            stmt.setDouble(4, match.getImpscore());
            stmt.setDouble(5, match.getImpadj());
            stmt.setDouble(6, match.getVpscore());
            stmt.setDouble(7, match.getVpadj());
            stmt.setInt(8, match.getMatchid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        TeammatchRecord match = (TeammatchRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, match.getMatchid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, TeammatchRecord match) throws SQLException {
        match.setMatchid(rs.getInt(1));
        match.setRound(rs.getInt(2));
        match.setHometeamid(rs.getInt(3));
        match.setAwayteamid(rs.getInt(4));
        match.setImpscore(rs.getDouble(5));
        match.setImpadj(rs.getDouble(6));
        match.setVpscore(rs.getDouble(5));
        match.setVpadj(rs.getDouble(6));
        match.setInDB(true);
    }

    public DBRecord getById(String id) throws DBRecordException {
        try {
            return getById(Integer.parseInt(id));
        } catch (NumberFormatException nfe) {
            return null;  //---
        }
    }


}