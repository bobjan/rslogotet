package rs.logotet.helper;

/**
 * kreirano:
 * Date: Aug 27, 2008
 * Time: 2:02:02 PM
 */
public class DBInUse {
    public static int baza;

    public static final int MYSQL = 1;
    public static final int POSTGRES = 2;
    public static final int MSSQL = 3;

    public static void setBaza(String driver) {
        baza = DBInUse.POSTGRES;
        if (driver.equals("org.gjt.mm.mysql.Driver"))
            baza = DBInUse.MYSQL;
    }
}
