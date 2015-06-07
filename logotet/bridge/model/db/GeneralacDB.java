package rs.logotet.bridge.model.db;


import rs.logotet.bridge.model.dbrecord.GeneralacRecord;
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

public class GeneralacDB extends DBClass {
    private static final String insCmd = "INSERT INTO generalac (turnirid,teamid,poeni,brojigara,brojplasmana,usedpoints) VALUES (?,?,?,?,?,?)";
    private static final String selCmd = "SELECT turnirid,teamid,poeni,brojigara,brojplasmana,usedpoints FROM generalac WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE generalac SET " +
            "poeni = ?," +
            "brojigara = ?," +
            "brojplasmana = ?," +
            "usedpoints = ? WHERE turnirid = ? AND teamid = ?";
    private static final String delCmd = "DELETE FROM  generalac WHERE  turnirid = ? AND teamid = ?";

    public GeneralacDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public void insert(DBRecord ent) throws DBRecordException {
        GeneralacRecord generalac = (GeneralacRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, generalac.getTurnirid());
            stmt.setInt(2, generalac.getTeamid());
            stmt.setDouble(3, generalac.getPoeni());
            stmt.setInt(4, generalac.getBrojigara());
            stmt.setInt(5, generalac.getBrojplasmana());
            stmt.setDouble(6, generalac.getUsedpoints());
            stmt.executeUpdate();
            stmt.close();
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public GeneralacRecord getById(int id) {
        GeneralacRecord generalac = new GeneralacRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + id);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, generalac);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return generalac;
    }

    public Vector getCollection(Criteria crit) {
        GeneralacRecord generalac;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                generalac = new GeneralacRecord();
                mapRecordToObject(rs, generalac);
                generalac.setInDB(true);
                coll.add(generalac);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        GeneralacRecord generalac = (GeneralacRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setDouble(1, generalac.getPoeni());
            stmt.setInt(2, generalac.getBrojigara());
            stmt.setInt(3, generalac.getBrojplasmana());
            stmt.setDouble(4, generalac.getUsedpoints());
            stmt.setInt(5, generalac.getTurnirid());
            stmt.setInt(6, generalac.getTeamid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        GeneralacRecord generalac = (GeneralacRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, generalac.getTurnirid());
            stmt.setInt(2, generalac.getTeamid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, GeneralacRecord generalac) throws SQLException {
        int turnirid = rs.getInt(1);
        generalac.setTurnirid(turnirid);
        int teamid = rs.getInt(2);
        generalac.setTeamid(teamid);
        double poeni = rs.getDouble(3);
        generalac.setPoeni(poeni);
        int brojigara = rs.getInt(4);
        generalac.setBrojigara(brojigara);
        int brojplasmana = rs.getInt(5);
        generalac.setBrojplasmana(brojplasmana);
        double usedpoints = rs.getDouble(6);
        generalac.setUsedpoints(usedpoints);
        generalac.setInDB(true);
    }
}