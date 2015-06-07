package rs.logotet.bridge.model.movement;


import rs.logotet.bridge.model.movement.*;
import rs.logotet.bridge.model.movement.BoardSet;
import rs.logotet.bridge.model.movement.MovementStep;

/**
 * User: jankovicb
 * Date: Mar 20, 2008
 */
public class TestMomevent extends rs.logotet.bridge.model.movement.Movement {
    public TestMomevent(int teams, int rounds, int tables) {
        this.teams = teams;
        this.rounds = rounds;
        this.brojStolova = tables;
    }

    public rs.logotet.bridge.model.movement.BoardSet getBoardSet(int round, int tblNum) {
        if (tblNum == 1) {
            switch (round) {
                case 1:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 1, 6, 1);
                case 2:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 2, 6, 2);
                case 3:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 3, 6, 3);
                case 4:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 4, 6, 4);
                case 5:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 5, 6, 5);
            }
        }
        if (tblNum == 2) {
            switch (round) {
                case 1:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 2, 4, 3);
                case 2:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 3, 5, 4);
                case 3:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 2, 1, 5);
                case 4:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 3, 2, 1);
                case 5:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 5, 3, 2);
            }
        }
        if (tblNum == 3) {
            switch (round) {
                case 1:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 4, 2, 5);
                case 2:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 4, 3, 1);
                case 3:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 1, 4, 2);
                case 4:
                    return new rs.logotet.bridge.model.movement.BoardSet(round, tblNum, 1, 5, 3);
                case 5:
                    return new BoardSet(round, tblNum, 5, 1, 4);
            }
        }


        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public rs.logotet.bridge.model.movement.MovementStep getMovementStep(int north, int south, int east, int west, int rnd, int tblNum, int boardSet) {
        rs.logotet.bridge.model.movement.MovementStep step = new MovementStep();
        step.setEast(east);
        step.setWest(west);
//        step.se
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
