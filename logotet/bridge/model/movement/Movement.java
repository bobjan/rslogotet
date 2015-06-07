package rs.logotet.bridge.model.movement;

import rs.logotet.bridge.base.BridgeException;
import rs.logotet.bridge.model.movement.*;

import java.util.Enumeration;
import java.util.Vector;

/**
 * User: jankovicb
 * Date: Mar 20, 2008
 */
public abstract class Movement {
    protected Vector tables;
    protected int rounds;
    protected int teams;
    protected int brojStolova; // = max NS
    protected int boardSetSize;


    public abstract rs.logotet.bridge.model.movement.BoardSet getBoardSet(int round, int tblNum);

    public int getRounds() {
        return rounds;
    }

    public int getTeams() {
        return teams;
    }

    public int getBrojStolova() {
        return brojStolova;
    }

    public int getBoardSetSize() {
        return boardSetSize;
    }

    public int getRoundBy(int board, int NS, int EW) {
        return 0;
    }

    public int getRoundBy(int board, int NS) {
        return 0;
    }

    public int getEastWestBy(int board, int NS) {
        return 0;
    }


    public int getTableBy(int board, int NS, int EW) {
        return 0;
    }


    public void showTable(int tblnum) {
        Vector tmp = new Vector();
        Enumeration enm = tables.elements();
        while (enm.hasMoreElements()) {
            rs.logotet.bridge.model.movement.BoardSet bt = (rs.logotet.bridge.model.movement.BoardSet) enm.nextElement();
            if (bt.getTableNumber() == tblnum)
                tmp.add(bt);
        }

        enm = tmp.elements();
        while (enm.hasMoreElements()) {
            rs.logotet.bridge.model.movement.BoardSet bt = (rs.logotet.bridge.model.movement.BoardSet) enm.nextElement();
            System.out.println(bt.toString());
        }
    }

    /**
     * mozda samo ovo treba da postoji
     * <p/>
     * *
     */
    public abstract MovementStep getMovementStep(int north, int south, int east, int west, int rnd, int tblNum, int boardSet) throws BridgeException;

    public void printOut(){
        for (int i = 1; i <= brojStolova ; i++) {
            System.out.println("Table " + i);
            System.out.println("table\tround\tNS-EW\tboardSet");
            showTable(i);
            System.out.println("*********************");
        }
    }

}
