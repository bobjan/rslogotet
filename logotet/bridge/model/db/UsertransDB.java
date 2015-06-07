package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.UsertransRecord;
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

public class UsertransDB extends DBClass {
    private static final String insCmd = "INSERT INTO usertrans (klubid,datum,amount,status) VALUES (?,?,?,?)";
    private static final String selCmd = "SELECT usertransid,klubid,datum,amount,status FROM usertrans WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE usertrans SET " +
            "klubid = ?," +
            "datum = ?," +
            "amount = ?," +
            "status = ? WHERE usertransid = ?";
    private static final String delCmd = "DELETE FROM  usertrans WHERE usertransid = ?";

    public UsertransDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        UsertransRecord usertrans = (UsertransRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, usertrans.getKlubid());
            stmt.setDate(2, usertrans.getDatum().getSQLDate());
            stmt.setDouble(3, usertrans.getAmount());
            stmt.setInt(4, usertrans.getStatus());
            stmt.executeUpdate();
            stmt.close();
            usertrans.setUsertransid(getAutoincrementKey());
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public UsertransRecord getById(int id) {
        UsertransRecord usertrans = new UsertransRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND usertransid = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, usertrans);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return usertrans;
    }

    public Vector getCollection(Criteria crit) {
        UsertransRecord usertrans;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                usertrans = new UsertransRecord();
                mapRecordToObject(rs, usertrans);
                usertrans.setInDB(true);
                coll.add(usertrans);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        UsertransRecord usertrans = (UsertransRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setInt(1, usertrans.getKlubid());
            stmt.setDate(2, usertrans.getDatum().getSQLDate());
            stmt.setDouble(3, usertrans.getAmount());
            stmt.setInt(4, usertrans.getStatus());
            stmt.setInt(5, usertrans.getUsertransid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        UsertransRecord usertrans = (UsertransRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, usertrans.getUsertransid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, UsertransRecord usertrans) throws SQLException {
        usertrans.setUsertransid(rs.getInt(1));
        usertrans.setKlubid(rs.getInt(2));
        usertrans.setDatum(new BJDatum(rs.getDate(3)));
        usertrans.setAmount(rs.getDouble(4));
        usertrans.setStatus(rs.getInt(5));
        usertrans.setInDB(true);
    }
}