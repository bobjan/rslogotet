package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class PlasmanRecord extends DBRecord {
    private int plasmanid;		// AUTOINCREMENT
    private int teamid;
    private double scoreMatchpoints;
    private double procenatMatchpoints;
    private double adjustMatchpoints;

    private double pointsIMP;
    private double adjustIMP;

    private double victoryPoints;
    private double adjustVictoryPoints;


    public PlasmanRecord() {
    }

    public int getPlasmanid() {
        return plasmanid;
    }

    public void setPlasmanid(int plasmanid) {
        this.plasmanid = plasmanid;
    }


    public int getTeamid() {
        return teamid;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    public double getScoreMatchpoints() {
        return scoreMatchpoints;
    }

    public void setScoreMatchpoints(double scoreMatchpoints) {
        this.scoreMatchpoints = scoreMatchpoints;
    }

    public double getPointsIMP() {
        return pointsIMP;
    }

    public void setPointsIMP(double pointsIMP) {
        this.pointsIMP = pointsIMP;
    }

    public double getProcenatMatchpoints() {
        return procenatMatchpoints;
    }

    public void setProcenatMatchpoints(double procenatMatchpoints) {
        this.procenatMatchpoints = procenatMatchpoints;
    }

    public double getAdjustMatchpoints() {
        return adjustMatchpoints;
    }

    public void setAdjustMatchpoints(double adjustMatchpoints) {
        this.adjustMatchpoints = adjustMatchpoints;
    }

    public double getAdjustIMP() {
        return adjustIMP;
    }

    public void setAdjustIMP(double adjustIMP) {
        this.adjustIMP = adjustIMP;
    }

    public double getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(double victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public double getAdjustVictoryPoints() {
        return adjustVictoryPoints;
    }

    public void setAdjustVictoryPoints(double adjustVictoryPoints) {
        this.adjustVictoryPoints = adjustVictoryPoints;
    }

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.PLASMAN);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLPlasman makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            PlasmanRecord obj = (PlasmanRecord) ent;
            plasmanid = obj.getPlasmanid();
            teamid = obj.getTeamid();
            scoreMatchpoints = obj.getScoreMatchpoints();
            pointsIMP = obj.getPointsIMP();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        PlasmanRecord obj = (PlasmanRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nPlasman:");
        sb.append("\n\tplasmanid : " + plasmanid);
        sb.append("\n\tteamid : " + teamid);
        sb.append("\n\tmpscore : " + scoreMatchpoints);
        sb.append("\n\timpoints : " + pointsIMP);
        return sb.toString();
    }
}