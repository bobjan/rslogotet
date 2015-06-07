package rs.logotet.bridge.model;

import rs.logotet.bridge.base.Bridge;
import rs.logotet.bridge.model.*;
import rs.logotet.bridge.model.Board;
import rs.logotet.bridge.model.db.BoardDB;
import rs.logotet.bridge.model.db.SectionDB;
import rs.logotet.bridge.model.dbrecord.BoardRecord;
import rs.logotet.bridge.model.dbrecord.SectionRecord;
import rs.logotet.bridge.model.dbrecord.SesijaRecord;
import rs.logotet.bridge.model.movement.Movement;
import rs.logotet.helper.Criteria;
import rs.logotet.helper.DBRecordException;
import rs.logotet.helper.FieldCriteria;
import rs.logotet.util.BJDatum;

import java.util.Enumeration;
import java.util.Vector;


/**
 * <p/>
 * wrapper around SesijaRecord(DBRecord) radi rada s bazom
 * <p/>
 * jedan turinir ima jednu ili vise seansi
 * <p/>
 * <p/>
 * zaseban package zbog zastite konstruktora klase Board
 */
public class Seansa {
    private SesijaRecord sesijaRecord = null;
    private Tournament turnir;
    private Movement movement;

//    private Vector timovi;
    private Vector boards;
    private Vector playedContracts;

    private Vector sections;
    
    private int movementType;
    private boolean twoWinners; // za mitchel movment

    private int seansaid;
    private int turnirid;

    private int tipSeanse; // 0 - individualni; 1 - parski; 2 - timski
    // kada su senase  npr.parske; a konsolidacija plasmana se vrsi na ind. nivou
    private BJDatum datum;


    private int boardSetSize;
    private int scoringType;

    public Seansa(Tournament tur, SesijaRecord sesijaRecord) {
        this(tur);
        this.sesijaRecord = sesijaRecord;
        this.datum = sesijaRecord.getDatum();
        scoringType = sesijaRecord.getObracun();
        fillSections();
        fillBoards();
    }

    public Seansa(Tournament trn) {
        turnir = trn;
        tipSeanse = trn.getTipTurnira(); // kao default
        turnir = null;
        if (turnir != null)
            datum = turnir.getDatum();
        else
            datum = new BJDatum();
        playedContracts = new Vector();
//        timovi = new Vector();
        sections = new Vector();
//        EWtimovi = new Vector();
//        NStimovi = new Vector();
        boards = new Vector();
        boardSetSize = 0;
        twoWinners = false;
        tipSeanse = Bridge.PAIR;
        movement = null;
        scoringType = Bridge.MATCHPOINTS;
        sections.add(new Section());
    }

    public Seansa(Tournament turnir, BJDatum datum) {
        this(turnir);
        this.datum = datum;
    }


    public void createSections(int broj){
        for (int i = 0; i < broj; i++) {
              sections.add(new Section(this, i+1));
        }

    }


    private void fillSections() {
        SectionRecord sectionRecord = new SectionRecord();
        SectionDB sectionDB = (SectionDB) sectionRecord.getDBClass();
        Criteria crit = new Criteria();
        crit.addOgranicenje(new FieldCriteria("", "sesijaid", FieldCriteria.IS_EQUAL, sesijaRecord.getSesijaid()));
        Vector vec = sectionDB.getCollection(crit);
        Enumeration enm = vec.elements();
        while (enm.hasMoreElements()) {
            SectionRecord sr = (SectionRecord) enm.nextElement();
            addSection(new Section(this,sr));
        }
    }

    private void fillBoards() {
        BoardRecord boardRecord = new BoardRecord();
        BoardDB boardDB = (BoardDB) boardRecord.getDBClass();
        Criteria crit = new Criteria();
        crit.addOgranicenje(new FieldCriteria("", "sesijaid", FieldCriteria.IS_EQUAL, sesijaRecord.getSesijaid()));
        Vector vec = boardDB.getCollection(crit);
        Enumeration enm = vec.elements();
        while (enm.hasMoreElements()) {
            BoardRecord sr = (BoardRecord) enm.nextElement();
            addBoard(new rs.logotet.bridge.model.Board(sr));
        }
    }

    public int getSeansaid() {
        return seansaid;
    }

    public void setSeansaid(int seansaid) {
        this.seansaid = seansaid;
    }

    public int getTurnirid() {
        return turnirid;
    }

    public void setTurnirid(int turnirid) {
        this.turnirid = turnirid;
    }

    public int getTipSeanse() {
        return tipSeanse;
    }

    public void setTipSeanse(int tipSeanse) {
        this.tipSeanse = tipSeanse;
    }

    public Tournament getTurnir() {
        return turnir;
    }

    public void setTurnir(Tournament turnir) {
        this.turnir = turnir;
    }

    public BJDatum getDatum() {
        return datum;
    }

    public void setDatum(BJDatum datum) {
        this.datum = datum;
    }

    public int getScoringType() {
        return scoringType;
    }

    public void setScoringType(int scoringType) {
        this.scoringType = scoringType;
    }

    public int getBoardSetSize() {
        return boardSetSize;
    }

    public void setBoardSetSize(int boardSetSize) {
        this.boardSetSize = boardSetSize;
    }

    public int getMovementType() {
        return movementType;
    }

    public void setMovementType(int movementType) {
        this.movementType = movementType;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }


    public void add(BridgeTeam tim) {
        Section tmp = (Section) sections.elementAt(0);
        tmp.addTeam(tim);
    }

    public void add(BridgeTeam tim, int section) {
        Enumeration enm = sections.elements();
        while(enm.hasMoreElements()){
            Section tmp = (Section) enm.nextElement();
           if(tmp.getBroj() == section)
               tmp.addTeam(tim);
        }
    }

    public Vector getTimovi() {
        Vector timovi = new Vector();
        Enumeration enm = sections.elements();
        while(enm.hasMoreElements()){
            Section tmp = (Section) enm.nextElement();
            timovi.addAll(tmp.getTimovi());
        }
        return timovi;
    }

    public void add(BridgeTeam tim, Section section) {
        if (sections.contains(section)) {
            section.addTeam(tim);
        } else {
            Section s = new Section();
            sections.add(s);
            s.addTeam(tim);
        }
    }

    public void addSection(Section s) {
        if (!sections.contains(s)) {
            sections.add(s);
        }
    }

    public void addPlayedContract(UniqueContract pc) {
        if (!playedContracts.contains(pc))
            playedContracts.add(pc);
    }

    public void addPlayedContract(int boardNum, int NSbroj, int EWbroj, int denomination, int suit, int dbl, String decl, int made) {
        UniqueContract uc = getPlayedContract(boardNum, NSbroj, EWbroj);
        if (uc == null) {
            uc = new UniqueContract(getBoard(boardNum),
                    getRegisteredTeam(NSbroj, Bridge.NORTH_SOUTH),
                    getRegisteredTeam(EWbroj, Bridge.EAST_WEST),
                    decl, new Contract(denomination, suit, dbl));
            uc.setMade(denomination + made); // ako je format -1, ...
        }
        addPlayedContract(uc);

    }

    private UniqueContract getPlayedContract(int boardNum, int nSbroj, int eWbroj) {
        Enumeration enm = playedContracts.elements();
        while (enm.hasMoreElements()) {
            UniqueContract uc = (UniqueContract) enm.nextElement();
            if (uc.getBoardNumber() == boardNum){
                if (uc.getPairBySide(Bridge.NORTH_SOUTH)[0].getMemberOf().getTakmicarskiBroj() == nSbroj)  // north broj
                    if (uc.getPairBySide(Bridge.EAST_WEST)[0].getMemberOf().getTakmicarskiBroj() == eWbroj) // east broj
                        return uc;
        }                                        }
        return null;
    }

    public void addBoard(rs.logotet.bridge.model.Board board) {
        if (!boards.contains(board))
            boards.add(board);
        if (boardSetSize == 0)
            return;
    }

    public rs.logotet.bridge.model.Board getBoard(int brojBorda) {
        Enumeration enm = boards.elements();
        while (enm.hasMoreElements()) {
            rs.logotet.bridge.model.Board board = (rs.logotet.bridge.model.Board) enm.nextElement();
            if (board.getBrojborda() == brojBorda)
                return board;
        }
        try {
            return new rs.logotet.bridge.model.Board(this, brojBorda);     // @todo throw Exception
        } catch (DBRecordException e) {
            return null;// can not happen
        }
    }

    //proverava da li vec postoji board sa ovim brojem
    public boolean hasBoard(int brojBorda) {
        Enumeration enm = boards.elements();
        while (enm.hasMoreElements()) {
            rs.logotet.bridge.model.Board board = (Board) enm.nextElement();
            if (board.getBrojborda() == brojBorda)
                return true;
        }
        return false;
    }

    public double getAverageRating() {
        Enumeration enm = getTimovi().elements();
        double rating = 0.0;
        while (enm.hasMoreElements()) {
            BridgeTeam tim = (BridgeTeam) enm.nextElement();
            rating += tim.getRating();
        }
        return rating / getTimovi().size();
    }


    public BridgeTeam getRegisteredTeam(int broj, int linija) {
        return getRegisteredTeam(broj, linija, 0);
    }

    public BridgeTeam getRegisteredTeam(int broj, int linija, int section) {
        try {
            Section s = (Section) sections.elementAt(section);
            return s.getRegisteredTeam(broj, linija);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * bez linije je za ind. takmicenja ili timska
     */
    public BridgeTeam getRegisteredTeam(int broj) {
        Enumeration enm;
        enm = getTimovi().elements();
        while (enm.hasMoreElements()) {
            BridgeTeam obj = (BridgeTeam) enm.nextElement();
            if (obj.getTakmicarskiBroj() == broj)
                return obj;
        }
        return null;    // @todo throw Exception
    }


    public void makePersistent() {
        try {
            sesijaRecord.makePersistent();
        } catch (DBRecordException e) {

        }
    }

}
