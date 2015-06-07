package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.BoardRecord;
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

public class BoardDB extends DBClass {
    private static final String insCmd = "INSERT INTO board (sesijaid,boardnumber,southhand,northhand,easthand,westhand) VALUES (?,?,?,?,?,?)";
    private static final String selCmd = "SELECT sesijaid,boardnumber,southhand,northhand,easthand,westhand FROM board WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE board SET " +
            "southhand = ?," +
            "northhand = ?," +
            "easthand = ?," +
            "westhand = ? WHERE sesijaid = ? AND boardnumber = ?";
    private static final String delCmd = "DELETE FROM  board WHERE sesijaid = ? AND boardnumber = ?";

    public BoardDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public void insert(DBRecord ent) throws DBRecordException {
        BoardRecord board = (BoardRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, board.getSesijaid());
            stmt.setInt(2, board.getBoardNumber());
            stmt.setString(3, board.getSouthhand());
            stmt.setString(4, board.getNorthhand());
            stmt.setString(5, board.getEasthand());
            stmt.setString(6, board.getWesthand());
            stmt.executeUpdate();
            stmt.close();
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public BoardRecord getById(int id) {
        BoardRecord board = new BoardRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + id);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, board);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return board;
    }

    public Vector getCollection(Criteria crit) {
        BoardRecord board;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                board = new BoardRecord();
                mapRecordToObject(rs, board);
                board.setInDB(true);
                coll.add(board);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        BoardRecord board = (BoardRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setString(1, board.getSouthhand());
            stmt.setString(2, board.getNorthhand());
            stmt.setString(3, board.getEasthand());
            stmt.setString(4, board.getWesthand());
            stmt.setInt(5, board.getSesijaid());
            stmt.setInt(6, board.getBoardNumber());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        BoardRecord board = (BoardRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, board.getSesijaid());
            stmt.setInt(2, board.getBoardNumber());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, BoardRecord board) throws SQLException {
        board.setSesijaid(rs.getInt(1));
        int boardnumber = rs.getInt(2);
        board.setBoardNumber(boardnumber);
        String southhand = rs.getString(3);
        board.setSouthhand(southhand);
        String northhand = rs.getString(4);
        board.setNorthhand(northhand);
        String easthand = rs.getString(5);
        board.setEasthand(easthand);
        String westhand = rs.getString(6);
        board.setWesthand(westhand);
        board.setInDB(true);
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }


}