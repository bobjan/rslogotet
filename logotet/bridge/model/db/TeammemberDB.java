package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.TeammemberRecord;
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

public class TeammemberDB extends DBClass {
    private static final String insCmd = "INSERT INTO teammember (teamid,playerid,pozicija) VALUES (?,?,?)";
    private static final String selCmd = "SELECT teamid,playerid,pozicija FROM teammember WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE teammember SET " +
            "pozicija = ? WHERE teamid = ? AND playerid = ?";
    private static final String delCmd = "DELETE FROM WHERE  teamid = ? AND playerid = ?";

    public TeammemberDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        TeammemberRecord teammember = (TeammemberRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, teammember.getTeamid());
            stmt.setInt(2, teammember.getPlayerid());
            stmt.setString(3, teammember.getPozicija());
            stmt.executeUpdate();
            stmt.close();
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public TeammemberRecord getById(int tid, int pid) {
        TeammemberRecord teammember = new TeammemberRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND teamid = ? AND playerid = ?");
            stmt.setInt(1, tid);
            stmt.setInt(2, pid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, teammember);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return teammember;
    }

    public Vector getCollection(Criteria crit) {
        TeammemberRecord teammember;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                teammember = new TeammemberRecord();
                mapRecordToObject(rs, teammember);
                teammember.setInDB(true);
                coll.add(teammember);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        TeammemberRecord teammember = (TeammemberRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setString(1, teammember.getPozicija());
            stmt.setInt(2, teammember.getTeamid());
            stmt.setInt(3, teammember.getPlayerid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        TeammemberRecord teammember = (TeammemberRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, teammember.getTeamid());
            stmt.setInt(2, teammember.getPlayerid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, TeammemberRecord teammember) throws SQLException {
        int teamid = rs.getInt(1);
        teammember.setTeamid(teamid);
        int playerid = rs.getInt(2);
        teammember.setPlayerid(playerid);
        String pozicija = rs.getString(3);
        teammember.setPozicija(pozicija);
        teammember.setInDB(true);
    }
}