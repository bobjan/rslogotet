package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class TeammemberRecord extends DBRecord {
    private int teamid;
    private int playerid;
    private String pozicija;

    public TeammemberRecord() {
    }

    public int getTeamid() {
        return teamid;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    public int getPlayerid() {
        return playerid;
    }

    public void setPlayerid(int playerid) {
        this.playerid = playerid;
    }

    public String getPozicija() {
        return pozicija;
    }

    public void setPozicija(String pozicija) {
        this.pozicija = pozicija;
    }

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.TEAMMEMBER);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLTeammember makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            TeammemberRecord obj = (TeammemberRecord) ent;
            teamid = obj.getTeamid();
            playerid = obj.getPlayerid();
            pozicija = obj.getPozicija();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        TeammemberRecord obj = (TeammemberRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nTeammember:");
        sb.append("\n\tteamid : " + teamid);
        sb.append("\n\tplayerid : " + playerid);
        sb.append("\n\tpozicija : " + pozicija);
        return sb.toString();
    }
}