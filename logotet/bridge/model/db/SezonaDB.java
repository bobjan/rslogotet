package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.SezonaRecord;
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

public class SezonaDB extends DBClass {
    private static final String insCmd = "INSERT INTO sezona (godina,broj,startdate,enddate) VALUES (?,?,?,?)";
    private static final String selCmd = "SELECT godina,broj,startdate,enddate FROM sezona WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE sezona SET " +
            "startdate = ?," +
            "enddate = ? WHERE godina = ? AND broj = ?";
    private static final String delCmd = "DELETE FROM  sezona WHERE godina = ? AND broj = ?";

    public SezonaDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        SezonaRecord sezona = (SezonaRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, sezona.getGodina());
            stmt.setInt(2, sezona.getBroj());
            stmt.setDate(3, sezona.getStartdate().getSQLDate());
            stmt.setDate(4, sezona.getEnddate().getSQLDate());
            stmt.executeUpdate();
            stmt.close();
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public SezonaRecord getById(int y, int br) {
        SezonaRecord sezona = new SezonaRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND godina = ? AND broj = ?");
            stmt.setInt(1, y);
            stmt.setInt(2, br);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, sezona);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return sezona;
    }

    public Vector getCollection(Criteria crit) {
        SezonaRecord sezona;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sezona = new SezonaRecord();
                mapRecordToObject(rs, sezona);
                sezona.setInDB(true);
                coll.add(sezona);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        SezonaRecord sezona = (SezonaRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setDate(1, sezona.getStartdate().getSQLDate());
            stmt.setDate(2, sezona.getEnddate().getSQLDate());
            stmt.setInt(3, sezona.getGodina());
            stmt.setInt(4, sezona.getBroj());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        SezonaRecord sezona = (SezonaRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, sezona.getGodina());
            stmt.setInt(2, sezona.getBroj());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, SezonaRecord sezona) throws SQLException {
        sezona.setGodina(rs.getInt(1));
        sezona.setBroj(rs.getInt(2));
        sezona.setStartdate(new BJDatum(rs.getDate(3)));
        sezona.setEnddate(new BJDatum(rs.getDate(4)));
        sezona.setInDB(true);
    }
}