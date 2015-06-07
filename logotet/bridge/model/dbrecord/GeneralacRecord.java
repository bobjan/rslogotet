package rs.logotet.bridge.model.dbrecord;


import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class GeneralacRecord extends DBRecord {
    private int turnirid;
    private int teamid;
    private double poeni;
    private int brojigara;
    private int brojplasmana;
    private double usedpoints;

    public GeneralacRecord() {
    }

    public int getTurnirid() {
        return turnirid;
    }

    public void setTurnirid(int turnirid) {
        this.turnirid = turnirid;
    }

    public int getTeamid() {
        return teamid;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    public double getPoeni() {
        return poeni;
    }

    public void setPoeni(double poeni) {
        this.poeni = poeni;
    }

    public int getBrojigara() {
        return brojigara;
    }

    public void setBrojigara(int brojigara) {
        this.brojigara = brojigara;
    }

    public int getBrojplasmana() {
        return brojplasmana;
    }

    public void setBrojplasmana(int brojplasmana) {
        this.brojplasmana = brojplasmana;
    }

    public double getUsedpoints() {
        return usedpoints;
    }

    public void setUsedpoints(double usedpoints) {
        this.usedpoints = usedpoints;
    }

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.GENERALAC);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLGeneralac makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            GeneralacRecord obj = (GeneralacRecord) ent;
            turnirid = obj.getTurnirid();
            teamid = obj.getTeamid();
            poeni = obj.getPoeni();
            brojigara = obj.getBrojigara();
            brojplasmana = obj.getBrojplasmana();
            usedpoints = obj.getUsedpoints();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        GeneralacRecord obj = (GeneralacRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nGeneralac:");
        sb.append("\n\tturnirid : " + turnirid);
        sb.append("\n\tteamid : " + teamid);
        sb.append("\n\tpoeni : " + poeni);
        sb.append("\n\tbrojigara : " + brojigara);
        sb.append("\n\tbrojplasmana : " + brojplasmana);
        sb.append("\n\tusedpoints : " + usedpoints);
        return sb.toString();
    }
}