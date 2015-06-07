package rs.logotet.bridge.base;

/**
 * 
 */
public class BridgeException extends Exception {
    public BridgeException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public BridgeException(String message) {
        super(message);
    }

    public BridgeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BridgeException(Throwable cause) {
        super(cause);
    }
}
