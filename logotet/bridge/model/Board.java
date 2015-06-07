package rs.logotet.bridge.model;

import rs.logotet.bridge.model.*;
import rs.logotet.bridge.model.dbrecord.BoardRecord;
import rs.logotet.bridge.model.scoring.BoardScorer;
import rs.logotet.bridge.model.sortables.BoardSortable;
import rs.logotet.bridge.base.Bridge;
import rs.logotet.helper.DBRecordException;

import java.util.Vector;

/**
 * treba da bude kasnije extends AbstractEntitet radi rada s bazom
 * <p/>
 * * zaseban package zbog zastite konstruktora klase Board
 */
public class Board implements BoardSortable {

    private BoardRecord boardRecord;

    private static final String[] vultext = {"none", "NS", "EW", "all"};
    private Seansa seansa; // seansa na kome je board igran
    private int boardid;
    private int brojborda;

    private int dealer; // 0 - S; 1 -W; 2-N; 3-E
    private rs.logotet.bridge.model.Hand south;
    private rs.logotet.bridge.model.Hand east;
    private rs.logotet.bridge.model.Hand west;
    private rs.logotet.bridge.model.Hand north;
    private int vul; // vulnerability: 0 - none; 1 - NS; 2 - EW; 3 - both

    private boolean calculated;

    private Vector playedContracts;
    private BoardScorer boardScorer;

    /**
     * ovaj konstruktor se poziva samo iz Seanse !!
     */
    Board(Seansa seansa, int brojborda) throws DBRecordException {
        if (seansa == null)
            throw  new DBRecordException("Board exists in Seansa only");
        if (seansa.hasBoard(brojborda))
            throw  new DBRecordException("Board " + brojborda + " already exists in Seansa");

        playedContracts = new Vector();
        this.seansa = seansa;
        this.brojborda = brojborda;
        dealer = getDealer(brojborda);
        vul = getVulnerability(brojborda);
        calculated = false;
        if (seansa != null)
            seansa.addBoard(this);
        boardScorer = new BoardScorer(this);
    }

    public Board(BoardRecord bRecord) {
        boardRecord = bRecord;
    }

    public int getBoardid() {
        return boardid;
    }

    public void setBoardid(int boardid) {
        this.boardid = boardid;
    }

    public Seansa getSeansa() {
        return seansa;
    }

    public void setSeansa(Seansa seansa) {
        this.seansa = seansa;
    }

    public int getBrojborda() {
        return brojborda;
    }

    public void setBrojborda(int brojborda) {
        this.brojborda = brojborda;
    }

    public int getDealer() {
        return dealer;
    }

    public rs.logotet.bridge.model.Hand getSouth() {
        return south;
    }

    public void setSouth(rs.logotet.bridge.model.Hand south) {
        this.south = south;
    }

    public rs.logotet.bridge.model.Hand getEast() {
        return east;
    }

    public void setEast(rs.logotet.bridge.model.Hand east) {
        this.east = east;
    }

    public rs.logotet.bridge.model.Hand getWest() {
        return west;
    }

    public void setWest(rs.logotet.bridge.model.Hand west) {
        this.west = west;
    }

    public rs.logotet.bridge.model.Hand getNorth() {
        return north;
    }

    public void setNorth(rs.logotet.bridge.model.Hand north) {
        this.north = north;
    }

    public int getVul() {
        return vul;
    }

    public void addPlayedContract(UniqueContract pc) {
        calculated = false;
        if (!playedContracts.contains(pc))
            playedContracts.add(pc);
    }

    public Vector getPlayedContracts() {
        return playedContracts;
    }

    public void calculate() {
        boardScorer.recalculate();
        calculated = true;
    }

    public static int getDealer(int boardNum) {
        int r = boardNum % 4;
        switch (r) {
            case 1:
                return Bridge.NORTH;
            case 2:
                return Bridge.EAST;
            case 3:
                return Bridge.SOUTH;
            default:
                return Bridge.WEST;
        }
    }

    public static int getVulnerability(int boardNum) {
        int r = boardNum % 16;
        switch (r) {
            case 4:
            case 7:
            case 10:
            case 13:
                return Bridge.ALL;
            case 3:
            case 6:
            case 9:
            case 16:
                return Bridge.EW;
            case 2:
            case 5:
            case 12:
            case 15:
                return Bridge.NS;
            default:
                return Bridge.NONE;
        }
    }

    public static String getVultext(int boardNum) {
        return vultext[getVulnerability(boardNum)];
    }

    public int getBoardNumber() {
        return brojborda;
    }
}
