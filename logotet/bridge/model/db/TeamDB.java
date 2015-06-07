package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.TeamRecord;
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

public class TeamDB extends DBClass {
    private static final String insCmd = "INSERT INTO team (sesijaid,sectionnumber,naziv,teamnumber,tip) VALUES (?,?,?,?,?)";
    private static final String selCmd = "SELECT teamid,sesijaid,sectionnumber,naziv,teamnumber,tip FROM team WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE team SET " +
            "sesijaid = ?, " +
            "sectionnumber = ?," +
            "naziv = ?," +
            "teamnumber = ?," +
            "tip = ? WHERE 1 = 1";
    private static final String delCmd = "DELETE FROM  team WHERE teamid = ?";

    public TeamDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        TeamRecord team = (TeamRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, team.getSesijaid());
            stmt.setInt(2, team.getSectionnumber());
            stmt.setString(3, team.getNaziv());
            stmt.setInt(4, team.getTeamnumber());
            stmt.setString(5, team.getTip());
            stmt.executeUpdate();
            stmt.close();
            team.setTeamid(getAutoincrementKey());
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public TeamRecord getById(int id) {
        TeamRecord team = new TeamRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND teamid = ? ");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, team);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return team;
    }

    public Vector getCollection(Criteria crit) {
        TeamRecord team;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                team = new TeamRecord();
                mapRecordToObject(rs, team);
                team.setInDB(true);
                coll.add(team);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        TeamRecord team = (TeamRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setInt(1, team.getSesijaid());
            stmt.setInt(2, team.getSectionnumber());
            stmt.setString(3, team.getNaziv());
            stmt.setInt(4, team.getTeamnumber());
            stmt.setString(5, team.getTip());
            stmt.setInt(6, team.getTeamid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        TeamRecord team = (TeamRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, team.getTeamid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, TeamRecord team) throws SQLException {
        team.setTeamid(rs.getInt(1));
        team.setSesijaid(rs.getInt(2));
        team.setSectionnumber(rs.getInt(3));
        team.setNaziv(rs.getString(4));
        team.setTeamnumber(rs.getInt(5));
        team.setTip(rs.getString(6));
        team.setInDB(true);
    }
}