package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.ClanstvoRecord;
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

public class ClanstvoDB extends DBClass {
    private static final String insCmd = "INSERT INTO clanstvo (playerid,klubid,datum) VALUES (?,?,?)";
    private static final String selCmd = "SELECT playerid,klubid,datum FROM clanstvo WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE clanstvo SET " +
            "datum = ? WHERE playerid = ? AND klubid = ? ";
    private static final String delCmd = "DELETE FROM  clanstvo WHERE playerid = ? AND klubid = ?";

    public ClanstvoDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        ClanstvoRecord clanstvo = (ClanstvoRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, clanstvo.getPlayerid());
            stmt.setInt(2, clanstvo.getKlubid());
            stmt.setDate(3, clanstvo.getDatum().getSQLDate());
            stmt.executeUpdate();
            stmt.close();
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public ClanstvoRecord getById(int pid, int kid) {
        ClanstvoRecord clanstvo = new ClanstvoRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND playerid = ? AND klubid = ?");
            stmt.setInt(1, pid);
            stmt.setInt(2, kid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, clanstvo);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return clanstvo;
    }

    public Vector getCollection(Criteria crit) {
        ClanstvoRecord clanstvo;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clanstvo = new ClanstvoRecord();
                mapRecordToObject(rs, clanstvo);
                clanstvo.setInDB(true);
                coll.add(clanstvo);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        ClanstvoRecord clanstvo = (ClanstvoRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setInt(1, clanstvo.getPlayerid());
            stmt.setInt(2, clanstvo.getKlubid());
            stmt.setDate(3, clanstvo.getDatum().getSQLDate());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        ClanstvoRecord clanstvo = (ClanstvoRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
//            stmt.setInt(1, clanstvo.getDBUniqueKey());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, ClanstvoRecord clanstvo) throws SQLException {
        clanstvo.setPlayerid(rs.getInt(1));
        clanstvo.setKlubid(rs.getInt(2));
        clanstvo.setDatum(new BJDatum(rs.getDate(3)));
        clanstvo.setInDB(true);
    }
}