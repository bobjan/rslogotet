package rs.logotet.helper.db;


import rs.logotet.helper.Criteria;
import rs.logotet.helper.DBInUse;
import rs.logotet.helper.DBRecordException;
import rs.logotet.util.BJTraceLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * kreirano:
 * Date: Oct 13, 2005
 * Time: 1:24:40 PM
 */
public abstract class DBClass {
    protected Connection dbConn;

    public abstract void insert(DBRecord ent) throws DBRecordException;

    public abstract void delete(DBRecord ent) throws DBRecordException;

    public abstract void update(DBRecord obj) throws DBRecordException;

    public abstract Vector getCollection(Criteria crit);

    public DBRecord getById(String id) throws DBRecordException{return null;}

    protected int getAutoincrementKey(String tableName, String keyName) {
        PreparedStatement stmt;
        int kljuc = 0;
        String sqlCommand;
//        sqlCommand = "SELECT currval('bc_" + tableName + "." + keyName + "'))";
        sqlCommand = "SELECT last_value FROM " + tableName + "_" + keyName + "_seq";
        if (DBInUse.baza == DBInUse.MYSQL)
            sqlCommand = "SELECT LAST_INSERT_ID()";

        try {
            stmt = dbConn.prepareStatement(sqlCommand);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                kljuc = rs.getInt(1);
            }
            stmt.close();
        } catch (SQLException sqle) {

        }
        return kljuc;
    }
    protected int getAutoincrementKey() {
        PreparedStatement stmt;
        int kljuc = 0;
        try {
            stmt = dbConn.prepareStatement("SELECT LAST_INSERT_ID()");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                kljuc = rs.getInt(1);
            }
            stmt.close();
        } catch (SQLException sqle) {
            BJTraceLogger.log("SQL error from robot code!!" + sqle.getMessage());
        }
        return kljuc;
    }

}
