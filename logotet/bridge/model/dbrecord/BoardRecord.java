package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class BoardRecord extends DBRecord {
    private int sesijaid;
    private int boardNumber;
    private String southhand;
    private String northhand;
    private String easthand;
    private String westhand;

    public BoardRecord() {
    }


    public int getSesijaid() {
        return sesijaid;
    }

    public void setSesijaid(int sesijaid) {
        this.sesijaid = sesijaid;
    }

    public int getBoardNumber() {
        return boardNumber;
    }

    public void setBoardNumber(int boardNumber) {
        this.boardNumber = boardNumber;
    }

    public String getSouthhand() {
        return southhand;
    }

    public void setSouthhand(String southhand) {
        this.southhand = southhand;
    }

    public String getNorthhand() {
        return northhand;
    }

    public void setNorthhand(String northhand) {
        this.northhand = northhand;
    }

    public String getEasthand() {
        return easthand;
    }

    public void setEasthand(String easthand) {
        this.easthand = easthand;
    }

    public String getWesthand() {
        return westhand;
    }

    public void setWesthand(String westhand) {
        this.westhand = westhand;
    }

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.BOARD);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLBoard makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            BoardRecord obj = (BoardRecord) ent;
            sesijaid = obj.getSesijaid();
            boardNumber = obj.getBoardNumber();
            southhand = obj.getSouthhand();
            northhand = obj.getNorthhand();
            easthand = obj.getEasthand();
            westhand = obj.getWesthand();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        BoardRecord obj = (BoardRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nBoard:");
        sb.append("\n\tturnirid : " + sesijaid);
        sb.append("\n\tboardnumber : " + boardNumber);
        sb.append("\n\tsouthhand : " + southhand);
        sb.append("\n\tnorthhand : " + northhand);
        sb.append("\n\teasthand : " + easthand);
        sb.append("\n\twesthand : " + westhand);
        return sb.toString();
    }
}