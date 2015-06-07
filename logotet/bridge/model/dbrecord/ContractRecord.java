package rs.logotet.bridge.model.dbrecord;

import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.SQLException;

public class ContractRecord extends DBRecord {
    private int contractid;		// AUTOINCREMENT
    private int sesijaid;
    private int tablenumber;
    private int boardNumber;
    private int northplayerid;
    private int southplayerid;
    private int eastplayerid;
    private int westplayerid;
    private String declarer;
    private int contract_number;
    private String contract_suit;
    private int contrcat_dbl;
    private int contract_made;

    private int nsscore;
    private int adjtype;
    private double adjvalue;
    private int status;


    public ContractRecord() {
    }

    public int getContractid() {
        return contractid;
    }

    public void setContractid(int contractid) {
        this.contractid = contractid;
    }

    public int getSesijaid() {
        return sesijaid;
    }

    public void setSesijaid(int sesijaid) {
        this.sesijaid = sesijaid;
    }

    public int getTablenumber() {
        return tablenumber;
    }

    public void setTablenumber(int tablenumber) {
        this.tablenumber = tablenumber;
    }

    public int getBoardNumber() {
        return boardNumber;
    }

    public void setBoardNumber(int boardNumber) {
        this.boardNumber = boardNumber;
    }

    public int getNorthplayerid() {
        return northplayerid;
    }

    public void setNorthplayerid(int northplayerid) {
        this.northplayerid = northplayerid;
    }

    public int getSouthplayerid() {
        return southplayerid;
    }

    public void setSouthplayerid(int southplayerid) {
        this.southplayerid = southplayerid;
    }

    public int getEastplayerid() {
        return eastplayerid;
    }

    public void setEastplayerid(int eastplayerid) {
        this.eastplayerid = eastplayerid;
    }

    public int getWestplayerid() {
        return westplayerid;
    }

    public void setWestplayerid(int westplayerid) {
        this.westplayerid = westplayerid;
    }

    public String getDeclarer() {
        return declarer;
    }

    public void setDeclarer(String declarer) {
        this.declarer = declarer;
    }

    public int getContract_number() {
        return contract_number;
    }

    public void setContract_number(int contract_number) {
        this.contract_number = contract_number;
    }

    public String getContract_suit() {
        return contract_suit;
    }

    public void setContract_suit(String contract_suit) {
        this.contract_suit = contract_suit;
    }

    public int getContrcat_dbl() {
        return contrcat_dbl;
    }

    public void setContrcat_dbl(int contrcat_dbl) {
        this.contrcat_dbl = contrcat_dbl;
    }

    public int getContract_made() {
        return contract_made;
    }

    public void setContract_made(int contract_made) {
        this.contract_made = contract_made;
    }

    public int getNsscore() {
        return nsscore;
    }

    public void setNsscore(int nsscore) {
        this.nsscore = nsscore;
    }

    public int getAdjtype() {
        return adjtype;
    }

    public void setAdjtype(int adjtype) {
        this.adjtype = adjtype;
    }

    public double getAdjvalue() {
        return adjvalue;
    }

    public void setAdjvalue(double adjvalue) {
        this.adjvalue = adjvalue;
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
            dbClass = (DBClass) DBFactory.getDBClass(DBFactory.CONTRACT);
        } catch (SQLException e) {
            BJTraceLogger.log("SQLContract makePersitence()!" + e.getMessage());
        }
        return dbClass;
    }

    public void copyFrom(DBRecord ent) {
        try {
            ContractRecord obj = (ContractRecord) ent;
            contractid = obj.getContractid();
            sesijaid = obj.getSesijaid();
            tablenumber = obj.getTablenumber();
            boardNumber = obj.getBoardNumber();
            northplayerid = obj.getNorthplayerid();
            southplayerid = obj.getSouthplayerid();
            declarer = obj.getDeclarer();
            contract_number = obj.getContract_number();
            contract_suit = obj.getContract_suit();
            contrcat_dbl = obj.getContrcat_dbl();
            contract_made = obj.getContract_made();
        } catch (ClassCastException cce) {
            BJTraceLogger.log("copyFrom() casting error " + cce.getMessage());
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        ContractRecord obj = (ContractRecord) super.clone();
        return obj;
    }

    public String wholeValue() {
        StringBuffer sb = new StringBuffer("\nContract:");
        sb.append("\n\tcontractid : " + contractid);
        sb.append("\n\tturnirid : " + sesijaid);
        sb.append("\n\ttablenumber : " + tablenumber);
        sb.append("\n\tboardid : " + boardNumber);
        sb.append("\n\tewline : " + northplayerid);
        sb.append("\n\tnsline : " + southplayerid);
        sb.append("\n\tdeclarer : " + declarer);
        sb.append("\n\tcontract_number : " + contract_number);
        sb.append("\n\tcontract_suit : " + contract_suit);
        sb.append("\n\tcontrcat_dbl : " + contrcat_dbl);
        sb.append("\n\tcontract_made : " + contract_made);
        return sb.toString();
    }
}