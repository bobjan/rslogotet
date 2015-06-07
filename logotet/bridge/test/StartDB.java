package rs.logotet.bridge.test;


import rs.logotet.bridge.base.AppPool;
import rs.logotet.helper.db.DBStartException;

import java.io.IOException;

/**
 * kreirano:
 * Date: Nov 25, 2007
 * Time: 11:21:37 AM
 */
public class StartDB {
    public static void start() throws DBStartException, IOException {
        AppPool pool = new AppPool();
    }
}
