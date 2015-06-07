package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class KlubRecord extends DBRecord {
    private int klubid;		// AUTOINCREMENT
    private int savezid;
    private String naziv;
    private String adresa;
    private String telefon;
    private String email;
    private String kontakt;
    private String username;
    private String password;
    private int status;

    public KlubRecord() {
    }

    public int getKlubid() {
        return klubid;
    }

    public void setKlubid(int klubid) {
        this.klubid = klubid;
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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return null;  //---
    }

    public DBClass getDBClass() {
        try {
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.KLUB);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLKlub makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            KlubRecord obj = (KlubRecord) ent;
            klubid = obj.getKlubid();
            savezid = obj.getSavezid();
            naziv = obj.getNaziv();
            adresa = obj.getAdresa();
            telefon = obj.getTelefon();
            email = obj.getEmail();
            kontakt = obj.getKontakt();
            username = obj.getUsername();
            password = obj.getPassword();
            status = obj.getStatus();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        KlubRecord obj = (KlubRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nKlub:");
        sb.append("\n\tklubid : " + klubid);
        sb.append("\n\tsavezid : " + savezid);
        sb.append("\n\tnaziv : " + naziv);
        sb.append("\n\tadresa : " + adresa);
        sb.append("\n\ttelefon : " + telefon);
        sb.append("\n\temail : " + email);
        sb.append("\n\tkontakt : " + kontakt);
        sb.append("\n\tusername : " + username);
        sb.append("\n\tpassword : " + password);
        sb.append("\n\tstatus : " + status);
        return sb.toString();
    }
}