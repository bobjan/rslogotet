package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.SectionRecord;
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

public class SectionDB extends DBClass {
    private static final String insCmd = "INSERT INTO section (sesijaid, sectionnumber) VALUES (?,?)";
    private static final String selCmd = "SELECT sesijaid, sectionnumber FROM secion WHERE 1 = 1 ";
    private static final String delCmd = "DELETE FROM section WHERE  sesijaid = ? AND sectionnumber = ?";

    public SectionDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        SectionRecord section = (SectionRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, section.getSesijaid());
            stmt.setInt(2, section.getSectionnumber());
            stmt.executeUpdate();
            stmt.close();
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public SectionRecord getById(int tid, int pid) {
        SectionRecord section = new SectionRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND teamid = ? AND playerid = ?");
            stmt.setInt(1, tid);
            stmt.setInt(2, pid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, section);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return section;
    }

    public Vector getCollection(Criteria crit) {
        SectionRecord section;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                section = new SectionRecord();
                mapRecordToObject(rs, section);
                section.setInDB(true);
                coll.add(section);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
    }

    public void delete(DBRecord ent) throws DBRecordException {
        SectionRecord section = (SectionRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, section.getSesijaid());
            stmt.setInt(2, section.getSectionnumber());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, SectionRecord section) throws SQLException {
        section.setSesijaid(rs.getInt(1));
        section.setSectionnumber(rs.getInt(2));
        section.setInDB(true);
    }
}