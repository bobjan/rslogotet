package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class TeamRecord extends DBRecord {
    private int teamid;
    private String naziv;
    private int teamnumber;
    private int sesijaid;
    private int sectionnumber;
    private String tip;

    public TeamRecord() {
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getTeamid() {
        return teamid;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTeamnumber() {
        return teamnumber;
    }

    public void setTeamnumber(int teamnumber) {
        this.teamnumber = teamnumber;
    }

    public int getSesijaid() {
        return sesijaid;
    }

    public void setSesijaid(int sesijaid) {
        this.sesijaid = sesijaid;
    }

    public int getSectionnumber() {
        return sectionnumber;
    }

    public void setSectionnumber(int sectionnumber) {
        this.sectionnumber = sectionnumber;
    }

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.TEAM);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLTeam makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            TeamRecord obj = (TeamRecord) ent;
            teamid = obj.getTeamid();
            naziv = obj.getNaziv();
            teamnumber = obj.getTeamnumber();
            sesijaid = obj.getSesijaid();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        TeamRecord obj = (TeamRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nTeam:");
        sb.append("\n\tteamid : " + teamid);
        sb.append("\n\tnaziv : " + naziv);
        sb.append("\n\tteamnumber : " + teamnumber);
        sb.append("\n\tturnirid : " + sesijaid);
        return sb.toString();
    }
}