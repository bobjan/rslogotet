package rs.logotet.bridge.model.movement;

import rs.logotet.bridge.base.BridgeException;
import rs.logotet.bridge.model.movement.*;
import rs.logotet.bridge.model.movement.BoardSet;
import rs.logotet.bridge.model.movement.MovementStep;
import rs.logotet.bridge.model.movement.Shifter;

import java.util.Vector;


/**
 * User: jankovicb
 * Date: Apr 3, 2008
 */
public class MitchelMovement extends rs.logotet.bridge.model.movement.Movement {
    rs.logotet.bridge.model.movement.Shifter shifter = null;

    public MitchelMovement(int teams) {
        tables = new Vector();
        this.teams = teams;
        shifter = new Shifter(teams);
        rounds = shifter.getBrojStolova();
        brojStolova = shifter.getBrojStolova();
        for (int i = 0; i < rounds; i++)
            for (int j = 0; j < brojStolova; j++) {
                rs.logotet.bridge.model.movement.BoardSet set = getBoardSet(i + 1, j + 1);
                tables.add(set);
            }
    }

    public rs.logotet.bridge.model.movement.BoardSet getBoardSet(int round, int tblNum) {
        int[] niz = shifter.getForRound(round, tblNum);
        rs.logotet.bridge.model.movement.BoardSet set = new BoardSet(round, tblNum, niz[2], niz[0], niz[1]);
        return set;
    }


    /**
     * klasa za parametre koji su razliciti od nule pronalazi ostale parametre
     *
     */
    public rs.logotet.bridge.model.movement.MovementStep getMovementStep(int north, int south, int east, int west, int rnd, int tblNum, int boardSet) throws BridgeException {
        if ((north > 0) && (south > 0) && (north != south)) throw new BridgeException("North " + north + " not equal South " + south);
        if ((east > 0) && (west > 0) && (east != west)) throw new BridgeException("East " + east + " not equal West " + west);
        int NS = Math.max(north, south);
        int EW = Math.max(east, west);
        if ((NS > 0) && (tblNum > 0) && (NS != tblNum)) throw new BridgeException("NS " + NS + " not equal table " + tblNum);

        if ((NS > 0) && (tblNum == 0))
            tblNum = NS;
        if ((NS == 0) && (tblNum > 0))
            NS = tblNum;

        return getMovementStep(NS, EW, rnd, boardSet);
    }

    private rs.logotet.bridge.model.movement.MovementStep getMovementStep(int NS, int EW, int rnd, int boardSet) throws BridgeException {
        rs.logotet.bridge.model.movement.MovementStep step = new rs.logotet.bridge.model.movement.MovementStep();
        if ((NS != 0) && (EW != 0)) {
            step.setNorth(NS);
            step.setSouth(NS);
            step.setEast(EW);
            step.setWest(EW);
            step.setTableNumber(NS);
            for (int i = 1; i <= rounds; i++) {
                int[] niz = shifter.getForRound(i, NS);
                if (niz[1] == EW) {
                    if (rnd > 0)
                        if (i != rnd)
                            throw new
                                    BridgeException("Wrong round in NS:" + NS + " EW:" + EW + " round:" + rnd + " boardSet:" + boardSet);
                    step.setRound(i);
                    if (boardSet != 0)
                        if (boardSet != niz[2])
                            throw new
                                    BridgeException("Wrong boardSet in NS:" + NS + " EW:" + EW + " round:" + rnd + " boardSet:" + boardSet);
                    step.setBoardSet(niz[2]);
                    return step;
                }
            }
        }

        if ((NS != 0) && (rnd != 0)) {
            step.setRound(rnd);
            step.setNorth(NS);
            step.setSouth(NS);
            step.setTableNumber(NS);
            int[] niz = shifter.getForRound(rnd, NS);

            step.setEast(niz[1]);
            step.setWest(niz[1]);

            if (boardSet != 0)
                if (boardSet != niz[2])
                    throw new
                            BridgeException("Wrong boardSet in NS:" + NS + " EW:" + EW + " round:" + rnd + " boardSet:" + boardSet);
            step.setBoardSet(niz[2]);
            return step;
        }

        if ((NS != 0) && (boardSet != 0)) {
            step.setNorth(NS);
            step.setSouth(NS);
            step.setBoardSet(boardSet);
            step.setTableNumber(NS);
            for (int i = 1; i <= rounds; i++) {
                int[] niz = shifter.getForRound(i, NS);
                if (niz[2] == boardSet) {
                    if (rnd > 0)
                        if (rnd != i)
                            throw new
                                    BridgeException("Wrong round in NS:" + NS + " EW:" + EW + " round:" + rnd + " boardSet:" + boardSet);
                    step.setRound(i);
                    step.setEast(niz[1]);
                    step.setWest(niz[1]);
                    return step;
                }
            }
        }

        if ((EW != 0) && (rnd != 0)) {
            step.setRound(rnd);
            step.setEast(EW);
            step.setWest(EW);
            for (int i = 1; i <= brojStolova; i++) {
                int[] niz = shifter.getForRound(rnd, i);
                if (niz[1] == EW) {
                    if (boardSet > 0)
                        if (niz[2] != boardSet)
                            throw new
                                    BridgeException("Wrong boardSet in NS:" + NS + " EW:" + EW + " round:" + rnd + " boardSet:" + boardSet);
                    step.setBoardSet(niz[2]);
                    step.setNorth(niz[0]);
                    step.setSouth(niz[0]);
                    step.setTableNumber(niz[0]);
                    return step;
                }
            }
        }

        if ((EW != 0) && (boardSet != 0)) {
            step.setEast(EW);
            step.setWest(EW);
            step.setBoardSet(boardSet);
            for (int i = 1; i <= brojStolova; i++)
                for (int j = 1; j <= rounds; j++) {
                    int[] niz = shifter.getForRound(j, i);
                    if ((niz[1] == EW) && (niz[2] == boardSet)) {
                        step.setRound(j);
                        step.setNorth(niz[0]);
                        step.setSouth(niz[0]);
                        step.setTableNumber(niz[0]);
                        return step;
                    }
                }
        }
        if ((rnd != 0) && (boardSet != 0)) {
            step.setRound(rnd);
            step.setBoardSet(boardSet);
            for (int i = 1; i <= brojStolova; i++){
                int[] niz = shifter.getForRound(rnd, i);
                if (niz[2] == boardSet) {
                    step.setNorth(niz[0]);
                    step.setSouth(niz[0]);
                    step.setTableNumber(niz[0]);
                    step.setEast(niz[1]);
                    step.setWest(niz[1]);
                    return step;
                }
            }
        }

        throw new BridgeException("Not enough parameters NS:" + NS + " EW:" + EW + " round:" + rnd + " boardSet:" + boardSet);
    }

    public static void main(String[] args) throws BridgeException {
        MitchelMovement mov = new MitchelMovement(14);
//        mov.printOut();
        MovementStep step = mov.getMovementStep(1,0,0,0,0,0,0);
        System.out.println(step);
    }

}
