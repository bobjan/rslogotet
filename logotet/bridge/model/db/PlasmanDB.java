package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.PlasmanRecord;
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

public class PlasmanDB extends DBClass {
    private static final String insCmd = "INSERT INTO plasman (teamid,mpscore,mpprocenat,mpadjust,imp,impadjust,vp,vpadjust) VALUES (?,?,?,?,?,?,?,?)";
    private static final String selCmd = "SELECT plasmanid,teamid,mpscore,mpprocenat,mpadjust,imp,impadjust,vp,vpadjust FROM plasman WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE plasman SET " +
            "teamid = ?," +
            "mpscore = ?," +
            "mpprocenat = ?," +
            "mpadjust = ?," +
            "imp = ?," +
            "impadjust = ?," +
            "vp = ?," +
            "vpadjust = ? WHERE plasmanid = ?";
    private static final String delCmd = "DELETE FROM  plasman WHERE plasmanid = ?";

    public PlasmanDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        PlasmanRecord plasman = (PlasmanRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, plasman.getTeamid());
            stmt.setDouble(2, plasman.getScoreMatchpoints());
            stmt.setDouble(3, plasman.getProcenatMatchpoints());
            stmt.setDouble(4, plasman.getAdjustMatchpoints());
            stmt.setDouble(5, plasman.getPointsIMP());
            stmt.setDouble(6, plasman.getAdjustIMP());
            stmt.setDouble(7, plasman.getVictoryPoints());
            stmt.setDouble(8, plasman.getAdjustVictoryPoints());
            stmt.executeUpdate();
            stmt.close();
            plasman.setPlasmanid(getAutoincrementKey());
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public PlasmanRecord getBy(TeamRecord id) {
        if (id == null)
            return null;
        PlasmanRecord plasman = new PlasmanRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND teamid = ?");
            stmt.setInt(1, id.getTeamid());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, plasman);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return plasman;
    }

    public PlasmanRecord getById(int id) {
        PlasmanRecord plasman = new PlasmanRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND plasmanid = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, plasman);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return plasman;
    }

    public Vector getCollection(Criteria crit) {
        PlasmanRecord plasman;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                plasman = new PlasmanRecord();
                mapRecordToObject(rs, plasman);
                plasman.setInDB(true);
                coll.add(plasman);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        PlasmanRecord plasman = (PlasmanRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setInt(1, plasman.getTeamid());
            stmt.setDouble(2, plasman.getScoreMatchpoints());
            stmt.setDouble(3, plasman.getProcenatMatchpoints());
            stmt.setDouble(4, plasman.getAdjustMatchpoints());
            stmt.setDouble(5, plasman.getPointsIMP());
            stmt.setDouble(6, plasman.getAdjustIMP());
            stmt.setDouble(7, plasman.getVictoryPoints());
            stmt.setDouble(8, plasman.getAdjustVictoryPoints());

            stmt.setInt(8, plasman.getPlasmanid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        PlasmanRecord plasman = (PlasmanRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, plasman.getPlasmanid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }


    private void mapRecordToObject(ResultSet rs, PlasmanRecord plasman) throws SQLException {
        plasman.setPlasmanid(rs.getInt(1));
        plasman.setTeamid(rs.getInt(2));
        plasman.setScoreMatchpoints(rs.getDouble(3));
        plasman.setProcenatMatchpoints(rs.getDouble(4));
        plasman.setAdjustMatchpoints(rs.getDouble(5));
        plasman.setPointsIMP(rs.getDouble(6));
        plasman.setAdjustIMP(rs.getDouble(7));
        plasman.setVictoryPoints(rs.getDouble(8));
        plasman.setAdjustVictoryPoints(rs.getDouble(9));
        plasman.setInDB(true);
    }
}