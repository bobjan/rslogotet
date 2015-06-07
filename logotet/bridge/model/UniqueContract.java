package rs.logotet.bridge.model;

import rs.logotet.bridge.model.*;
import rs.logotet.bridge.model.Board;
import rs.logotet.bridge.model.BridgePlayer;
import rs.logotet.bridge.model.BridgeTeam;
import rs.logotet.bridge.model.Contract;
import rs.logotet.bridge.model.scoring.ContractCalculator;
import rs.logotet.bridge.model.scoring.IMPScore;
import rs.logotet.bridge.model.sortables.BoardSortable;
import rs.logotet.bridge.model.sortables.RoundSortable;
import rs.logotet.bridge.model.sortables.TableSortable;
import rs.logotet.bridge.base.Bridge;

/**
 * jedna ruka za jednim stolom sa jednim "kvartetom" igraca
 * koji su postigli
 * jedan kontrakt i odigrali ga kako su znali
 */
public class UniqueContract implements BoardSortable, RoundSortable, TableSortable {
    private int dealid;
    private rs.logotet.bridge.model.Board board;

    private rs.logotet.bridge.model.BridgePlayer north;
    private rs.logotet.bridge.model.BridgePlayer south;
    private rs.logotet.bridge.model.BridgePlayer east;
    private rs.logotet.bridge.model.BridgePlayer west;

    private String declarer; // "S", "N", "W", "E"
    private rs.logotet.bridge.model.Contract kontrakt;
    private int made; // broj uzetih stihova 0 - 13;

    private boolean declarerLineNS;   // declarer side

    private int contractResultNS;

    private int matchPointsNS;
    private int matchPointsEW;

    private int impNS;
    private int impEW;

    private int crossImpNS;
    private int crossImpEW;

    private boolean allPass; // svi su pasirali

    /**
     *
     * */

    private UniqueContract(rs.logotet.bridge.model.Board board, String declarer, rs.logotet.bridge.model.Contract kontrakt) {
        north = null;
        south = null;
        west = null;
        east = null;

        allPass = kontrakt.isAllPassed();

        this.board = board;
        this.declarer = declarer;
        this.kontrakt = kontrakt;
        if (declarer.toUpperCase().equals("S") || declarer.toUpperCase().equals("N"))
            declarerLineNS = true;
        else
            declarerLineNS = false;
        dopuniKonstruktor();
    }

    public boolean isAllPass() {
        return allPass;
    }

    public void setAllPass(boolean allPass) {
        this.allPass = allPass;
    }

    private void dopuniKonstruktor() {
        annul();
        board.addPlayedContract(this);
        rs.logotet.bridge.model.Seansa seansa = board.getSeansa();
        if (seansa != null)
            seansa.addPlayedContract(this);
        try {
            north.addPlayedContract(this);
            south.addPlayedContract(this);
            east.addPlayedContract(this);
            west.addPlayedContract(this);
        } catch (NullPointerException npe) {
        }
    }

    public UniqueContract(rs.logotet.bridge.model.Board board, rs.logotet.bridge.model.BridgePlayer north, rs.logotet.bridge.model.BridgePlayer south, rs.logotet.bridge.model.BridgePlayer east, rs.logotet.bridge.model.BridgePlayer west, String declarer, rs.logotet.bridge.model.Contract kontrakt) {
        this(board, declarer, kontrakt);
        setPlayer(north, Bridge.NORTH);
        setPlayer(east, Bridge.EAST);
        setPlayer(south, Bridge.SOUTH);
        setPlayer(west, Bridge.WEST);
        dopuniKonstruktor();
    }

    public UniqueContract(rs.logotet.bridge.model.Board board, rs.logotet.bridge.model.BridgeTeam northsouth, BridgeTeam eastwest, String declarer, rs.logotet.bridge.model.Contract kontrakt) {
        this(board, declarer, kontrakt);
        setPlayer(northsouth.getPlayerAt(0), Bridge.NORTH);
        setPlayer(northsouth.getPlayerAt(1), Bridge.SOUTH);
        setPlayer(eastwest.getPlayerAt(0), Bridge.EAST);
        setPlayer(eastwest.getPlayerAt(1), Bridge.WEST);
        dopuniKonstruktor();
    }


    public void setPlayer(rs.logotet.bridge.model.BridgePlayer player, int position) {
        switch (position) {
            case Bridge.NORTH:
                north = player;
                break;
            case Bridge.EAST:
                east = player;
                break;
            case Bridge.WEST:
                west = player;
                break;
            case Bridge.SOUTH:
                south = player;
                break;
        }
    }

    public void setMade(int made) {
        this.made = made;
    }

    public void setDealid(int dealid) {
        this.dealid = dealid;
    }

    public void setMatchPointsNS(int matchPointsNS) {
        this.matchPointsNS = matchPointsNS;
    }

    public void setMatchPointsEW(int matchPointsEW) {
        this.matchPointsEW = matchPointsEW;
    }

    public void setImpNS(int impNS) {
        this.impNS = impNS;
    }

    public void setImpEW(int impEW) {
        this.impEW = impEW;
    }

    public int getDealid() {
        return dealid;
    }

    public Board getBoard() {
        return board;
    }

    public rs.logotet.bridge.model.BridgePlayer getNorth() {
        return north;
    }

    public rs.logotet.bridge.model.BridgePlayer getSouth() {
        return south;
    }

    public rs.logotet.bridge.model.BridgePlayer getEast() {
        return east;
    }

    public rs.logotet.bridge.model.BridgePlayer getWest() {
        return west;
    }

    public String getDeclarer() {
        return declarer;
    }

    public Contract getKontrakt() {
        return kontrakt;
    }

    public int getMade() {
        return made;
    }

    public boolean isDeclarerLineNS() {
        return declarerLineNS;
    }

    public int getContractResultNS() {
        if(allPass)
        return 0;
        if (contractResultNS == 0) {
            contractResultNS = ContractCalculator.getRezultat(kontrakt, made, isDeclarerVulnerable());
            if (!isDeclarerLineNS())
                contractResultNS *= -1;
        }
        return contractResultNS;
    }

    public int getMatchPointsNS() {
        return matchPointsNS;
    }

    public int getMatchPointsEW() {
        return matchPointsEW;
    }

    public int getImpNS() {
        return impNS;
    }

    public int getImpEW() {
        return impEW;
    }

    public int getCrossImpNS() {
        return crossImpNS;
    }

    public int getCrossImpEW() {
        return crossImpEW;
    }

    public boolean isDeclarerVulnerable() {
        switch (board.getVul()) {
            case Bridge.NONE:
                return false;
            case Bridge.NS:
                if (declarer.toUpperCase().equals("S") || declarer.toUpperCase().equals("N"))
                    return true;
                else
                    return false;
            case Bridge.EW:
                if (declarer.toUpperCase().equals("S") || declarer.toUpperCase().equals("N"))
                    return false;
                else
                    return true;
            case Bridge.ALL:
                return true;
            default:
                return false;
        }
    }

    public rs.logotet.bridge.model.BridgePlayer[] getPairBySide(int side) {
        rs.logotet.bridge.model.BridgePlayer[] niz = new BridgePlayer[2];
        if (side == Bridge.NORTH_SOUTH) {
            niz[0] = north;
            niz[1] = south;
        } else {
            niz[0] = east;
            niz[1] = west;
        }
        return niz;
    }


    public String getTextMade() {
        int uspeh = made - kontrakt.getDenomination() - 6;
        if (uspeh == 0)
            return "=";
        if (uspeh < 0)
            return "" + uspeh;
        return "+" + uspeh;
    }

    public String toString() {
//        String s = kontrakt + " by " + declarer + " made " + getTextMade() + " NS= " + getContractResultNS();
        StringBuffer sb = new StringBuffer("Contract:");
        try {
            sb.append("\t" + board.getBrojborda() + "\t" + north.getMemberOf().getTakmicarskiBroj() + "-" + east.getMemberOf().getTakmicarskiBroj());
        } catch (NullPointerException noe) {
        }
        sb.append("\t" + kontrakt.toString() + "\t\t" + (kontrakt.isAllPassed() ? "" : "\t" + getTextMade()) + "\t" + getContractResultNS() + " **");
        return sb.toString();
    }

//    public void compareTo(UniqueContract pc, boolean rekurzivno) {
//        if (pc.getBoard() != getBoard())
//            return;
//        int scoreThis = getContractResultNS();
//        int scoreOther = pc.getContractResultNS();
//        if (scoreThis > scoreOther) {
//            matchPointsNS = 2;
//            matchPointsEW = 0;
//        } else if (scoreThis == scoreOther) {
//            matchPointsNS = 2;
//            matchPointsEW = 0;
//        } else {
//            matchPointsNS = 0;
//            matchPointsEW = 2;
//        }
//        if (rekurzivno) {
//            pc.compareTo(this, false);
//        }
//    }

    public void addMatchPointsNS(int i) {
        matchPointsNS += i;
    }

    /**
     * anulira sve prethodno obracunate vrednosti
     * poziva se prilikom svake promene
     */
    public void annul() {
        matchPointsEW = 0;
        matchPointsNS = 0;
        impEW = 0;
        impNS = 0;
        crossImpNS = 0;
        crossImpEW = 0;
    }

    public void crossImp(UniqueContract cj, boolean both) {
        IMPScore imp = new IMPScore(contractResultNS, cj.getContractResultNS());
        crossImpNS += imp.getImp();
        crossImpEW -= imp.getImp();
        if (both)
            cj.crossImp(this, false);
    }

    /**
     * radi sortiranja po bordu ili kolu
     * <p/>
     * *
     */
    public int getBoardNumber() {
        return board.getBrojborda();
    }

    public int getRound() {
        return 0;  //---
    }

    public int getTableNumber() {
        return 0;  //---
    }
}
