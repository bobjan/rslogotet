package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class SectionRecord extends DBRecord {
    private int sesijaid;		// AUTOINCREMENT
    private int sectionnumber;


    public SectionRecord() {
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
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.SECTION);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLSedenje makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            SectionRecord obj = (SectionRecord) ent;
            sesijaid = obj.getSesijaid();
            sectionnumber = obj.getSectionnumber();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        SectionRecord obj = (SectionRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nSedenje:");
        sb.append("\n\tsesijaid : " + sesijaid);
        sb.append("\n\tsectionnumber : " + sectionnumber);

        return sb.toString();
    }
}