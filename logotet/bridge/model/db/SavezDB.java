package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.SavezRecord;
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

public class SavezDB extends DBClass {
    private static final String insCmd = "INSERT INTO savez (naziv,email,kontakt) VALUES (?,?,?)";
    private static final String selCmd = "SELECT savezid,naziv,email,kontakt FROM savez WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE savez SET " +
            "naziv = ?," +
            "email = ?," +
            "kontakt = ? WHERE savezid = ?";
    private static final String delCmd = "DELETE FROM  savez WHERE savezid = ?";

    public SavezDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        SavezRecord savez = (SavezRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setString(1, savez.getNaziv());
            stmt.setString(2, savez.getEmail());
            stmt.setString(3, savez.getKontakt());
            stmt.executeUpdate();
            stmt.close();
            savez.setSavezid(getAutoincrementKey());
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public SavezRecord getById(int id) {
        SavezRecord savez = new SavezRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND savezid = ? ");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, savez);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return savez;
    }

    public Vector getCollection(Criteria crit) {
        SavezRecord savez;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                savez = new SavezRecord();
                mapRecordToObject(rs, savez);
                savez.setInDB(true);
                coll.add(savez);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        SavezRecord savez = (SavezRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setString(1, savez.getNaziv());
            stmt.setString(2, savez.getEmail());
            stmt.setString(3, savez.getKontakt());
            stmt.setInt(4, savez.getSavezid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        SavezRecord savez = (SavezRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, savez.getSavezid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, SavezRecord savez) throws SQLException {
        int savezid = rs.getInt(1);
        savez.setSavezid(savezid);
        String naziv = rs.getString(2);
        savez.setNaziv(naziv);
        String email = rs.getString(3);
        savez.setEmail(email);
        String kontakt = rs.getString(4);
        savez.setKontakt(kontakt);
        savez.setInDB(true);
    }
}