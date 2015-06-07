package rs.logotet.bridge.base;


import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.JSONWrapper;
import rs.logotet.helper.db.ConnectionPoolManager;
import rs.logotet.helper.db.DBStartException;
//import rs.logotet.util.json.JSONException;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * kreirano:
 * Date: Nov 11, 2007
 * Time: 5:52:58 PM
 */
public class AppPool {
    private Properties prop;

    private JSONWrapper json;

    public AppPool() throws DBStartException, IOException {
        this(true);
        ConnectionPoolManager cpm = new ConnectionPoolManager(getProperty("dbdriver"),
                getProperty("dburl"),
                getProperty("dbuser"),
                getProperty("dbpassword"));
        DBFactory.initFactory(cpm);
//        DBInUse.setBaza(getProperty("dbdriver").trim());
    }

    public AppPool(boolean samoprops) throws DBStartException, IOException {
        json = new JSONWrapper();
        prop = new Properties();
        startUp();
//        DBInUse.setBaza(getProperty("dbdriver").trim());
    }

    private void startUp() throws IOException {
        URL url = getClass().getResource("/bridge.props");
        InputStream is = url.openStream();
        prop.load(is);
    }

    public void setProperty(String naziv, String vrednost) {
        prop.setProperty(naziv, vrednost);
    }

    private void saveProps() throws IOException {
        URL url = getClass().getResource("/bridge.props");
        String fileName = url.getPath();
        File newFile = new File(fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(newFile);
//            prop.store(fos,"ha ha ha");
        } catch (FileNotFoundException e) {
        }
    }


//    public String getJsonResponse(int status, String tekst) throws JSONException {
//        return json.getResponse(status, tekst);
//    }

//    public String getJsonResponse(int status, String tekst, String vrednost, String html) throws JSONException {
//        return json.getResponse(status, tekst, vrednost, html);
//    }

    public String getProperty(String naziv) {
        return prop.getProperty(naziv);
    }

    public static void main(String[] args) throws IOException, DBStartException {
        AppPool app = new AppPool();
//          app.setProperty("novo","vrenost novog");
        app.saveProps();
    }
}
