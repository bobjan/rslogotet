package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.PlayerRecord;
import rs.logotet.helper.Criteria;
import rs.logotet.helper.DBRecordException;
import rs.logotet.helper.FieldCriteria;
import rs.logotet.helper.db.ConnectionPoolManager;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class PlayerDB extends DBClass {
    private static final String insCmd = "INSERT INTO player (ime,prezime,rating,titula,sifra) VALUES (?,?,?,?,?)";
    private static final String selCmd = "SELECT playerid, ime,prezime,rating,titula,sifra FROM player WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE player SET " +
            "ime = ?," +
            "prezime = ?," +
            "rating = ?," +
            "titula = ?," +
            "sifra = ? WHERE playerid = ?";
    private static final String delCmd = "DELETE FROM  player WHERE playerid = ?";

    public PlayerDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        PlayerRecord player = (PlayerRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setString(1, player.getIme());
            stmt.setString(2, player.getPrezime());
            stmt.setDouble(3, player.getRating());
            stmt.setInt(4, player.getTitula());
            stmt.setString(5, player.getSifra());
            stmt.executeUpdate();
            stmt.close();
            player.setPlayerid(getAutoincrementKey());
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public PlayerRecord findBy(String ime, String prezime) {
        Criteria crit = new Criteria();
        crit.addOgranicenje(new FieldCriteria("", "ime", FieldCriteria.IS_EQUAL, ime));
        crit.addOgranicenje(new FieldCriteria("", "prezime", FieldCriteria.IS_EQUAL, prezime));
        Vector vec = getCollection(crit);
        if (vec.size() == 0)
            return null;
        return (PlayerRecord) vec.elementAt(0);
    }


    public PlayerRecord getById(int id) {
        PlayerRecord player = new PlayerRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND playerid = ? ");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, player);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return player;
    }

    public Vector getCollection(Criteria crit) {
        PlayerRecord player;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                player = new PlayerRecord();
                mapRecordToObject(rs, player);
                player.setInDB(true);
                coll.add(player);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        PlayerRecord player = (PlayerRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setString(1, player.getIme());
            stmt.setString(2, player.getPrezime());
            stmt.setDouble(3, player.getRating());
            stmt.setInt(4, player.getTitula());
            stmt.setString(5, player.getSifra());
            stmt.setInt(6, player.getPlayerid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        PlayerRecord player = (PlayerRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, player.getPlayerid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, PlayerRecord player) throws SQLException {
        player.setPlayerid(rs.getInt(1));
        player.setIme(rs.getString(2));
        player.setPrezime(rs.getString(3));
        player.setRating(rs.getDouble(4));
        player.setTitula(rs.getInt(5));
        player.setSifra(rs.getString(6));
        player.setInDB(true);
    }
}