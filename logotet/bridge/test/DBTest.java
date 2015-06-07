package rs.logotet.bridge.test;

import rs.logotet.bridge.base.AppPool;
import rs.logotet.bridge.model.db.DBFactory;
import rs.logotet.helper.db.DBClass;
import rs.logotet.helper.db.DBStartException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * kreirano:
 * Date: Nov 25, 2007
 * Time: 11:12:44 AM
 */
public class DBTest {
    public static void main(String[] args) {
        try {
            AppPool pool = new AppPool();//  StartDB.start();
            for (int i = 1; i < 40; i++) {
                proveriTabelu(i);
            }
        } catch (DBStartException e) {
            e.printStackTrace();  //---
        } catch (IOException e) {
            e.printStackTrace();  //---
        }

    }

    private static void proveriTabelu(int i) {
        DBClass klasa = null;
        try {
            klasa = null;
            klasa = (DBClass) DBFactory.getDBClass(i);
            if (klasa == null)
                return;
            Vector niz = klasa.getCollection(null);
            System.out.println("\tklasa:" + klasa.getClass().getName() + " ima: " + niz.size() + " elemenata");
        } catch (SQLException e) {
            if (klasa != null) {
                System.out.println("=====Greska na " + klasa.getClass().getName());
                System.out.println("=====" + e.getMessage());
            } else {
                System.out.println("*****Greska na " + i + " ???");
                System.out.println("*****" + e.getMessage());
            }
        }
    }
}
