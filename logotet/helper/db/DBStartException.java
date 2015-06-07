package rs.logotet.helper.db;

/**
 * kreirano:
 * Date: Oct 19, 2005
 * Time: 1:09:53 PM
 */
public class DBStartException extends Exception {
    public DBStartException() {
    }

    public DBStartException(String message) {
        super(message);
    }

    public DBStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBStartException(Throwable cause) {
        super(cause);
    }
}
