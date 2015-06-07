package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.bridge.model.db.PlayerDB;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class PlayerRecord extends DBRecord {
    private int playerid;		// AUTOINCREMENT
    private String ime;
    private String prezime;
    private double rating;
    private int titula;
    private String sifra;

    public PlayerRecord() {
    }

    public PlayerRecord(int pid) {
        PlayerDB pdb = (PlayerDB) getDBClass();
        PlayerRecord pr = pdb.getById(pid);
        if (pr != null) {
            copyFrom(pr);
        }
    }

    public int getPlayerid() {
        return playerid;
    }

    public void setPlayerid(int playerid) {
        this.playerid = playerid;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getFullName() {
        return getPrezime() + " " + getIme();
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTitula() {
        return titula;
    }

    public void setTitula(int titula) {
        this.titula = titula;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getId() {
        return "" + playerid;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.PLAYER);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLPlayer makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            PlayerRecord obj = (PlayerRecord) ent;
            playerid = obj.getPlayerid();
            ime = obj.getIme();
            prezime = obj.getPrezime();
            rating = obj.getRating();
            titula = obj.getTitula();
            inDB = obj.isInDB();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        PlayerRecord obj = (PlayerRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nPlayer:");
        sb.append("\n\tplayerid : " + playerid);
        sb.append("\n\time : " + ime);
        sb.append("\n\tprezime : " + prezime);
        sb.append("\n\trating : " + rating);
        sb.append("\n\ttitula : " + titula);
        return sb.toString();
    }
}