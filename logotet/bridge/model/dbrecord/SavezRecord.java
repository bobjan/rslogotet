package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class SavezRecord extends DBRecord {
    private int savezid;		// AUTOINCREMENT
    private String naziv;
    private String email;
    private String kontakt;

    public SavezRecord() {
    }

    public int getSavezid() {
        return savezid;
    }

    public void setSavezid(int savezid) {
        this.savezid = savezid;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.SAVEZ);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLSavez makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            SavezRecord obj = (SavezRecord) ent;
            savezid = obj.getSavezid();
            naziv = obj.getNaziv();
            email = obj.getEmail();
            kontakt = obj.getKontakt();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        SavezRecord obj = (SavezRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nSavez:");
        sb.append("\n\tsavezid : " + savezid);
        sb.append("\n\tnaziv : " + naziv);
        sb.append("\n\temail : " + email);
        sb.append("\n\tkontakt : " + kontakt);
        return sb.toString();
    }
}