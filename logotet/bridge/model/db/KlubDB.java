package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.KlubRecord;
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

public class KlubDB extends DBClass {
    private static final String insCmd = "INSERT INTO klub (savezid,naziv,adresa,telefon,email,kontakt,username,password,status) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String selCmd = "SELECT klubid, savezid,naziv,adresa,telefon,email,kontakt,username,password,status FROM klub WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE klub SET " +
            "savezid = ?," +
            "naziv = ?," +
            "adresa = ?," +
            "telefon = ?," +
            "email = ?," +
            "kontakt = ?," +
            "username = ?," +
            "password = ?," +
            "status = ? WHERE klubid = ?";
    private static final String delCmd = "DELETE FROM  klub WHERE klubid = ?";

    public KlubDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        KlubRecord klub = (KlubRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, klub.getSavezid());
            stmt.setString(2, klub.getNaziv());
            stmt.setString(3, klub.getAdresa());
            stmt.setString(4, klub.getTelefon());
            stmt.setString(5, klub.getEmail());
            stmt.setString(6, klub.getKontakt());
            stmt.setString(7, klub.getUsername());
            stmt.setString(8, klub.getPassword());
            stmt.setInt(9, klub.getStatus());
            stmt.executeUpdate();
            stmt.close();
            klub.setKlubid(getAutoincrementKey());
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public KlubRecord getById(int id) {
        KlubRecord klub = new KlubRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND klubid = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, klub);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return klub;
    }

    public Vector getCollection(Criteria crit) {
        KlubRecord klub;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                klub = new KlubRecord();
                mapRecordToObject(rs, klub);
                klub.setInDB(true);
                coll.add(klub);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        KlubRecord klub = (KlubRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setInt(1, klub.getSavezid());
            stmt.setString(2, klub.getNaziv());
            stmt.setString(3, klub.getAdresa());
            stmt.setString(4, klub.getTelefon());
            stmt.setString(5, klub.getEmail());
            stmt.setString(6, klub.getKontakt());
            stmt.setString(7, klub.getUsername());
            stmt.setString(8, klub.getPassword());
            stmt.setInt(9, klub.getStatus());
            stmt.setInt(10, klub.getKlubid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }


    public void delete(DBRecord ent) throws DBRecordException {
        KlubRecord klub = (KlubRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, klub.getKlubid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, KlubRecord klub) throws SQLException {
        int klubid = rs.getInt(1);
        klub.setKlubid(klubid);
        int savezid = rs.getInt(2);
        klub.setSavezid(savezid);
        String naziv = rs.getString(3);
        klub.setNaziv(naziv);
        String adresa = rs.getString(4);
        klub.setAdresa(adresa);
        String telefon = rs.getString(5);
        klub.setTelefon(telefon);
        String email = rs.getString(6);
        klub.setEmail(email);
        String kontakt = rs.getString(7);
        klub.setKontakt(kontakt);
        String username = rs.getString(8);
        klub.setUsername(username);
        String password = rs.getString(9);
        klub.setPassword(password);
        int status = rs.getInt(10);
        klub.setStatus(status);
        klub.setInDB(true);
    }
}