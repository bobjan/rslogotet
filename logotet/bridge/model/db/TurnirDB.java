package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.TurnirRecord;
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

public class TurnirDB extends DBClass {
    private static final String insCmd = "INSERT INTO turnir (datum,status,tipobracuna,naziv,finished) VALUES (?,?,?,?,?)";
    private static final String selCmd = "SELECT turnirid, datum,status,tipobracuna,naziv,finished FROM turnir WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE turnir SET " +
            "datum = ?," +
            "status = ?, " +
            "tipobracuna = ?," +
            "naziv = ?," +
            "finished = ? WHERE turnirid = ?";
    private static final String delCmd = "DELETE FROM  turnir WHERE turnirid = ?";

    public TurnirDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        TurnirRecord turnir = (TurnirRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setDate(1, turnir.getDatum().getSQLDate());
            stmt.setInt(2, turnir.getStatus());
            stmt.setInt(3, turnir.getTipObracuna());
            stmt.setString(4, turnir.getNaziv());
            stmt.setInt(5, turnir.isFinished() ? 1 : 0);
            stmt.executeUpdate();
            stmt.close();
            turnir.setTurnirid(getAutoincrementKey());
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public TurnirRecord getById(int id) {
        TurnirRecord turnir = new TurnirRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND turnirid = ? ");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, turnir);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return turnir;
    }

    public Vector getCollection(Criteria crit) {
        TurnirRecord turnir;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                turnir = new TurnirRecord();
                mapRecordToObject(rs, turnir);
                turnir.setInDB(true);
                coll.add(turnir);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        TurnirRecord turnir = (TurnirRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setDate(1, turnir.getDatum().getSQLDate());
            stmt.setInt(2, turnir.getStatus());
            stmt.setInt(3, turnir.getTipObracuna());
            stmt.setString(4, turnir.getNaziv());
            stmt.setInt(5, turnir.isFinished() ? 1 : 0);
            stmt.setInt(6, turnir.getTurnirid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        TurnirRecord turnir = (TurnirRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, turnir.getTurnirid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, TurnirRecord turnir) throws SQLException {
        turnir.setTurnirid(rs.getInt(1));
        turnir.setDatum(new BJDatum(rs.getDate(2)));
        turnir.setStatus(rs.getInt(3));
        turnir.setTipObracuna(rs.getInt(4));
        turnir.setNaziv(rs.getString(5));
        turnir.setFinished(rs.getInt(6) == 1);
        turnir.setInDB(true);
    }
}