package rs.logotet.helper.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ConnectionObject
 * Created by : Boban
 * Date: Feb 21, 2005
 */
public class ConnectionObject {

    private Connection connection = null;
    private boolean inUse = false;

    public ConnectionObject(Connection c, boolean useFlag) {
        connection = c;
        inUse = useFlag;
    }

    public boolean isClosed() {
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public boolean isInUse() {
        return inUse;
    }
}
