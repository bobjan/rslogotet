package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.SesijaRecord;
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
import java.sql.Types;
import java.util.Vector;

public class SesijaDB extends DBClass {
    private static final String insCmd = "INSERT INTO sesija (movementid,turnirid,datum,oznakasesije,tip,flagresult,obracun) VALUES (?,?,?,?,?,?,?)";
    private static final String selCmd = "SELECT sesijaid,movementid,turnirid,datum,oznakasesije,tip,flagresult,obracun FROM sesija WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE sesija SET " +
            "movementid = ?," +
            "turnirid = ?," +
            "datum = ?," +
            "oznakasesije = ?," +
            "tip = ?" +
            "flagresult = ?," +
            "obracun = ? WHERE sesijaid = ?";
    private static final String delCmd = "DELETE FROM  sesija WHERE sesijaid = ?";

    public SesijaDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        SesijaRecord sesija = (SesijaRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            if (sesija.getMovementid() == 0)
                stmt.setNull(1, Types.INTEGER);
            else
                stmt.setInt(1, sesija.getMovementid());
            stmt.setInt(2, sesija.getTurnirid());
            stmt.setDate(3, sesija.getDatum().getSQLDate());
            stmt.setInt(4, sesija.getOznakasesije());
            stmt.setInt(5, sesija.getTip());
            stmt.setInt(6, sesija.getFlagresult());
            stmt.setInt(7, sesija.getObracun());
            stmt.executeUpdate();
            stmt.close();
            sesija.setSesijaid(getAutoincrementKey());
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public SesijaRecord getById(int id) {
        SesijaRecord sesija = new SesijaRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND sesijaid = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, sesija);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return sesija;
    }

    public Vector getCollection(Criteria crit) {
        SesijaRecord sesija;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sesija = new SesijaRecord();
                mapRecordToObject(rs, sesija);
                sesija.setInDB(true);
                coll.add(sesija);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        SesijaRecord sesija = (SesijaRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            if (sesija.getMovementid() == 0)
                stmt.setNull(1, Types.INTEGER);
            else
                stmt.setInt(1, sesija.getMovementid());
            stmt.setInt(2, sesija.getTurnirid());
            stmt.setDate(3, sesija.getDatum().getSQLDate());
            stmt.setInt(4, sesija.getOznakasesije());
            stmt.setInt(5, sesija.getTip());
            stmt.setInt(6, sesija.getFlagresult());
            stmt.setInt(7, sesija.getObracun());
            stmt.setInt(8, sesija.getSesijaid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        SesijaRecord sesija = (SesijaRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, sesija.getSesijaid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, SesijaRecord sesija) throws SQLException {
        sesija.setSesijaid(rs.getInt(1));
        sesija.setMovementid(rs.getInt(2));
        sesija.setTurnirid(rs.getInt(3));
        sesija.setDatum(new BJDatum(rs.getDate(4)));
        sesija.setOznakasesije(rs.getInt(5));
        sesija.setTip(rs.getInt(6));
        sesija.setFlagresult(rs.getInt(7));
        sesija.setObracun(rs.getInt(8));
        sesija.setInDB(true);
    }
}