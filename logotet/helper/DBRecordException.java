package rs.logotet.helper;

/**
 * kreirano:
 * Date: Oct 9, 2007
 * Time: 10:57:35 AM
 */
public class DBRecordException extends Exception {
    public DBRecordException() {
    }

    public DBRecordException(String message) {
        super(message);
    }

    public DBRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBRecordException(Throwable cause) {
        super(cause);
    }
}
