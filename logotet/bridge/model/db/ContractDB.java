package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.dbrecord.ContractRecord;
import rs.logotet.helper.Criteria;
import rs.logotet.helper.DBRecordException;
import rs.logotet.helper.db.ConnectionPoolManager;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBRecord;
import rs.logotet.util.BJTraceLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ContractDB extends DBClass {
    private static final String insCmd = "INSERT INTO contract (sesijaid,tablenumber,boardnumber,northplayerid,southplayerid,eastplayerid,westplayerid,declarer,contract_number,contract_suit,contrcat_dbl,contract_made,nsscore,adjtype,adjvalue,status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String selCmd = "SELECT contractid, sesijaid,tablenumber,boardnumber,northplayerid,southplayerid,eastplayerid,westplayerid,declarer,contract_number,contract_suit,contrcat_dbl,contract_made,nsscore,adjtype,adjvalue,status FROM contract WHERE 1 = 1 ";
    private static final String updCmd = "UPDATE contract SET " +
            "sesijaid = ?," +
            "tablenumber + ?," +
            "boardnumber = ?," +
            "northplayerid = ?," +
            "southplayerid = ?," +
            "eastplayerid = ?," +
            "westplayerid = ?," +
            "declarer = ?," +
            "contract_number = ?," +
            "contract_suit = ?," +
            "contrcat_dbl = ?," +
            "contract_made = ?, " +
            "nsscore = ?," +
            "adjtype = ?," +
            "adjvalue = ?," +
            "status = ? WHERE contractid = ?";
    private static final String delCmd = "DELETE FROM  contract WHERE contractid = ?";

    public ContractDB(ConnectionPoolManager cpm) throws SQLException {
        dbConn = cpm.getConnection();
    }

    public DBRecord getById(String id) throws DBRecordException {
        return null;  //---
    }

    public void insert(DBRecord ent) throws DBRecordException {
        ContractRecord contract = (ContractRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(insCmd);
            stmt.setInt(1, contract.getSesijaid());
            stmt.setInt(2, contract.getTablenumber());
            stmt.setInt(3, contract.getBoardNumber());
            stmt.setInt(4, contract.getNorthplayerid());
            stmt.setInt(5, contract.getSouthplayerid());
            stmt.setInt(6, contract.getEastplayerid());
            stmt.setInt(7, contract.getWestplayerid());
            stmt.setString(8, contract.getDeclarer());
            stmt.setInt(9, contract.getContract_number());
            stmt.setString(10, contract.getContract_suit());
            stmt.setInt(11, contract.getContrcat_dbl());
            stmt.setInt(12, contract.getContract_made());

            stmt.setInt(13, contract.getNsscore());
            stmt.setInt(14, contract.getAdjtype());
            stmt.setDouble(15, contract.getAdjvalue());
            stmt.setInt(16, contract.getStatus());

            stmt.executeUpdate();
            stmt.close();
            contract.setContractid(getAutoincrementKey());
            ent.setInDB(true);
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL insert error " + sqle.getMessage());
        }
    }

    public ContractRecord getById(int id) {
        ContractRecord contract = new ContractRecord();
        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + " AND contractid = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mapRecordToObject(rs, contract);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getById  error " + sqle.getMessage());
        }
        return contract;
    }

    public Vector getCollection(Criteria crit) {
        ContractRecord contract;
        Vector coll = new Vector();
        if (crit == null)
            crit = new Criteria();

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(selCmd + crit.getSQLuslov());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                contract = new ContractRecord();
                mapRecordToObject(rs, contract);
                contract.setInDB(true);
                coll.add(contract);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
//throw new EntitetException("SQL getCollection error " + sqle.getMessage());
        }
        return coll;
    }

    public void update(DBRecord ent) throws DBRecordException {
        ContractRecord contract = (ContractRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(updCmd);
            stmt.setInt(1, contract.getSesijaid());
            stmt.setInt(2, contract.getTablenumber());
            stmt.setInt(3, contract.getBoardNumber());
            stmt.setInt(4, contract.getNorthplayerid());
            stmt.setInt(5, contract.getSouthplayerid());
            stmt.setInt(6, contract.getEastplayerid());
            stmt.setInt(7, contract.getWestplayerid());
            stmt.setString(8, contract.getDeclarer());
            stmt.setInt(9, contract.getContract_number());
            stmt.setString(10, contract.getContract_suit());
            stmt.setInt(11, contract.getContrcat_dbl());
            stmt.setInt(12, contract.getContract_made());

            stmt.setInt(13, contract.getNsscore());
            stmt.setInt(14, contract.getAdjtype());
            stmt.setDouble(15, contract.getAdjvalue());
            stmt.setInt(16, contract.getStatus());

            stmt.setInt(17, contract.getContractid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL update error " + sqle.getMessage());
        }
    }

    public void delete(DBRecord ent) throws DBRecordException {
        ContractRecord contract = (ContractRecord) ent;

        PreparedStatement stmt;
        try {
            stmt = dbConn.prepareStatement(delCmd);
            stmt.setInt(1, contract.getContractid());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
            throw new DBRecordException("SQL delete error " + sqle.getMessage());
        }
    }

    private void mapRecordToObject(ResultSet rs, ContractRecord contract) throws SQLException {
        contract.setContractid(rs.getInt(1));
        contract.setSesijaid(rs.getInt(2));
        contract.setTablenumber(rs.getInt(3));
        contract.setBoardNumber(rs.getInt(4));
        contract.setNorthplayerid(rs.getInt(5));
        contract.setSouthplayerid(rs.getInt(6));
        contract.setEastplayerid(rs.getInt(7));
        contract.setWestplayerid(rs.getInt(8));
        contract.setDeclarer(rs.getString(9));
        contract.setContract_number(rs.getInt(10));
        contract.setContract_suit(rs.getString(11));
        contract.setContrcat_dbl(rs.getInt(12));
        contract.setContract_made(rs.getInt(13));

        contract.setNsscore(rs.getInt(14));
        contract.setAdjtype(rs.getInt(15));
        contract.setAdjvalue(rs.getDouble(16));
        contract.setStatus(rs.getInt(17));

        contract.setInDB(true);
    }
}