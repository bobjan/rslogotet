package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.MovementRecord;
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

public class MovementDB extends DBClass {
    private static final String insCmd = "INSERT INTO movement (tip,stolova,filename) VALUES (?,?,?)";
    private static final String selCmd = "SELECT movementid,tip,stolova,filename FROM movement WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE movement SET " +
            "movementid = ?," +
            "tip = ?," +
            "stolova = ?," +
            "filename = ? WHERE 1 = 1";
    private static final String delCmd = "DELETE FROM  movement WHERE 1 = 2";

    public MovementDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public void insert(DBRecord ent) throws DBRecordException {
        MovementRecord movement = (MovementRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setString(1, movement.getTip());
            stmt.setInt(2, movement.getStolova());
            stmt.setString(3, movement.getFilename());
            stmt.executeUpdate();
            stmt.close();
            movement.setMovementid(getAutoincrementKey());
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public MovementRecord getById(int id) {
        MovementRecord movement = new MovementRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + id);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, movement);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return movement;
    }

    public Vector getCollection(Criteria crit) {
        MovementRecord movement;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movement = new MovementRecord();
                mapRecordToObject(rs, movement);
                movement.setInDB(true);
                coll.add(movement);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        MovementRecord movement = (MovementRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setString(1, movement.getTip());
            stmt.setInt(2, movement.getStolova());
            stmt.setString(3, movement.getFilename());
            stmt.setInt(4, movement.getMovementid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        MovementRecord movement = (MovementRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, movement.getMovementid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, MovementRecord movement) throws SQLException {
        int movementid = rs.getInt(1);
        movement.setMovementid(movementid);
        String tip = rs.getString(2);
        movement.setTip(tip);
        int stolova = rs.getInt(3);
        movement.setStolova(stolova);
        String filename = rs.getString(4);
        movement.setFilename(filename);
        movement.setInDB(true);
    }
}