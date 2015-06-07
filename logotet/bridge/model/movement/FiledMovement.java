package rs.logotet.bridge.model.movement;


import rs.logotet.bridge.model.movement.*;
import rs.logotet.bridge.model.movement.Movement;
import rs.logotet.bridge.model.movement.MovementStep;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Movement koji se nalazi u nekom fajlu i treba ga parsirati
 */
public class FiledMovement extends rs.logotet.bridge.model.movement.Movement {
    public FiledMovement() {
        tables = new Vector();
    }

    public void addBoardSet(rs.logotet.bridge.model.movement.BoardSet bset) {
        if (bset == null)
            return;
        if (!tables.contains(bset))
            tables.add(bset);
        if (bset.getRound() > rounds)
            rounds = bset.getRound();
        if (bset.getTableNumber() > brojStolova)
            brojStolova = bset.getTableNumber();
        if (bset.getPairNS() > teams)
            teams = bset.getPairNS();
        if (bset.getPairEW() > teams)
            teams = bset.getPairEW();
    }


    public rs.logotet.bridge.model.movement.BoardSet getBoardSet(int round, int tblNum) {
        Enumeration enm = tables.elements();
        while (enm.hasMoreElements()) {
            rs.logotet.bridge.model.movement.BoardSet bt = (rs.logotet.bridge.model.movement.BoardSet) enm.nextElement();
            if (bt.getRound() == round)
                if (bt.getTableNumber() == tblNum)
                    return bt;
        }
        return null;
    }

    public MovementStep getMovementStep(int north, int south, int east, int west, int rnd, int tblNum, int boardSet) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static void main(String[] args) throws IOException {
        Movement mv = MovementFactory.getMovement("h7.txt");
        mv.printOut();
    }
}
