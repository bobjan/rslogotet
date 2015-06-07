package rs.logotet.helper.db;


import rs.logotet.helper.DBInUse;

import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * Manages a java.sql.Connection pool.
 */
public class ConnectionPoolManager {
    private final int SIZE = 10;
    private Vector connections = new Vector(SIZE);
    private String url = null;
    private String user = null;
    private String password = null;
    private boolean traceOn = true;
    Driver driverInstance;
    private int connCounter;

    public ConnectionPoolManager(String driver, String url) throws DBStartException {
        this(driver, url, "", "");

    }

    public ConnectionPoolManager(String driverName, String url, String user, String password) throws DBStartException {
        connCounter = 0;
        this.url = url;
        this.user = user;
        this.password = password;
        DBInUse.setBaza(driverName.trim());
        setEnableTrace(false);
        try {
            Class c = null;
            c = Class.forName(driverName);
            //         driverInstance =  (Driver)c.newInstance();
            DriverManager.registerDriver((Driver) c.newInstance());
            setInitOpenConnections(SIZE);
        } catch (SQLException sqle) {
            throw new DBStartException("DBStart error ", sqle.getCause());
        } catch (IllegalAccessException iae) {
            throw new DBStartException("DBStart error ", iae.getCause());
        } catch (InstantiationException ie) {
            throw new DBStartException("DBStart error ", ie.getCause());
        } catch (ClassNotFoundException cnfe) {
            throw new DBStartException("DBStart error ", cnfe.getCause());
        }
    }

    public void setEnableTrace(boolean enable) {
        traceOn = enable;
    }


    /**
     * Returns a Vector of java.sql.Connection objects
     */
    public Vector getConnectionList() {
        return connections;
    }


    /**
     * Opens specified "count" of connections and adds them to the existing pool
     */
    public synchronized void setInitOpenConnections(int count)
            throws SQLException {
        Connection conn = null;
        ConnectionObject connObject = null;

        for (int i = 0; i < count; i++) {
            conn = createConnection();
            connObject = new ConnectionObject(conn, false);
            connections.addElement(connObject);
            trace("ConnectionPoolManager==: Adding new DB connection to pool (" + connections.size() + ")");
        }
    }

    /**
     * Returns a count of open connections
     */
    public int getConnectionCount() {
        return connections.size();
    }


    /**
     * Vraca jednu od 10 pocetno inicijalizovanih konekcija
     */
    public synchronized Connection getConnection() throws SQLException {
        boolean connFound = false;
        Connection conn;
        conn = null;
        ConnectionObject connObject = null;

        while (!connFound) {
            try {
                connObject = (ConnectionObject) connections.elementAt(connCounter++);
                conn = connObject.getConnection();
                connFound = true;
            } catch (ArrayIndexOutOfBoundsException be) {
                connCounter = 0;
            }
        }
        return conn;
    }


    /**
     * Marks a flag in the ConnectionObject to indicate this connection is no longer in use
     */
    public synchronized void releaseConnection(Connection c) {
        if (c == null)
            return;

        ConnectionObject co = null;

        Enumeration enm = connections.elements();
        while (enm.hasMoreElements()) {
            co = (ConnectionObject) enm.nextElement();
            if (c == co.getConnection()) {
                co.setInUse(false);
                break;
            }
        }

    }

    private Connection createConnection() throws SQLException {
        Connection conn;
        conn = null;

        try {
            if (user == null)
                user = "";
            if (password == null)
                password = "";

            Properties props = new Properties();
            props.put("user", user);
            props.put("password", password);

            conn = DriverManager.getConnection(url, props);
            //       conn = driverInstance.connect(url,props);
            int i = 0;
            i++;
        } catch (Throwable t) {
            throw new SQLException(t.getMessage());
        }
        return conn;
    }

    /**
     * Closes all connections and clears out the connection pool
     */
    public void finalize() {
        trace("ConnectionPoolManager.finalize()");
        ConnectionObject connObject;
        connObject = null;

        for (int i = 0; i < connections.size(); i++) {
            connObject = (ConnectionObject) connections.elementAt(i);
            try {
                connObject.getConnection().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            connObject = null;
        }
        connections.removeAllElements();
    }

    private void trace(String s) {
        if (traceOn)
            System.out.println(s);
    }

    /**
     * LayeredTest driver.
     */
    public static void main(String args[]) throws Exception {
        String dbDriver = "org.gjt.mm.mysql.Driver";
        String dbURL = "jdbc:mysql://localhost/lastadb";
//        String dbURL      = "jdbc:mysql://localhost/lotodb?user=root&password=bobjan";
        String dbUser = "root";
        String dbPassword = "bobjan";

        ConnectionPoolManager cm = new ConnectionPoolManager(dbDriver, dbURL, dbUser, dbPassword);

        cm.setEnableTrace(true);


        Connection c = cm.getConnection();
        Connection c2 = cm.getConnection();
        Connection c3 = cm.getConnection();
        Connection c4 = cm.getConnection();

        cm.releaseConnection(c2);
        cm.releaseConnection(c3);
        cm.releaseConnection(c4);

        cm.trace("Connection count = " + cm.getConnectionCount());

        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("select * from tres where numone = 33");

        while (rs.next())
            System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));

        rs.close();
        s.close();
        cm.releaseConnection(c);
    }
}


