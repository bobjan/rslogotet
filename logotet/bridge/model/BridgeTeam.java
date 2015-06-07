package rs.logotet.bridge.model;

import rs.logotet.bridge.base.Rankable;
import rs.logotet.bridge.model.*;
import rs.logotet.bridge.model.db.PlasmanDB;
import rs.logotet.bridge.model.db.TeammemberDB;
import rs.logotet.bridge.model.dbrecord.PlasmanRecord;
import rs.logotet.bridge.model.dbrecord.TeamRecord;
import rs.logotet.bridge.model.dbrecord.TeammatchRecord;
import rs.logotet.bridge.model.dbrecord.TeammemberRecord;
import rs.logotet.helper.Criteria;
import rs.logotet.helper.FieldCriteria;

import java.util.Enumeration;
import java.util.Vector;

/**
 * tamicarski tim sa razl. brojem igraca
 * 1 - za ind. turnir
 * 2 - za parski kade je idx == 0; onda NORTH ili EAST;
 * kada je idx == 1; onda je SOUTH ili WEST
 * 4 ili vise igraca za timski turnir
 */
public class BridgeTeam implements Rankable {
    private TeamRecord teamRecord;
    private PlasmanRecord plasmanRecord;
    private TeammatchRecord matchRecord;

    private int teamid;

    private Vector igraci;

    private String teamName;
    private int takmicarskiBroj;
    private Vector playedContracts;
    private double matchPoints;
    private double mpProcenat;
    private double imps;
    private int numberOffset;

    public BridgeTeam() {
        igraci = new Vector();
        playedContracts = new Vector();
        teamName = "";
        matchPoints = 0;
        imps = 0;
        mpProcenat = 0.0;
        numberOffset = 0;
    }

    public BridgeTeam(String ime) {
        this();
        teamName = ime.trim();
    }

    public BridgeTeam(TeamRecord tRecord) {
        this();
        this.teamRecord = tRecord;
        teamid = tRecord.getTeamid();
        teamName = tRecord.getNaziv();
        fillIgraci();
        calculatePlasman();
    }

    /**
     * takmicarski broj je od 1 - broj takmicara
     * a displayed je 1- n za NS, i 1 -M za EW liniju, (n + m) = broj takmicara
     */
    public int getDisplayedTakmicarskiBroj(rs.logotet.bridge.model.Seansa seansa) {
//        if (seansa.isAllNumberDisplay())
            return takmicarskiBroj;
//        return takmicarskiBroj = numberOffset;
    }

    /**
     * ako se koristi   isAllNumberDisplay()
     */
    public void setNumberOffset(int numberOffset) {
        this.numberOffset = numberOffset;
    }

    public int getTakmicarskiBroj() {
        return takmicarskiBroj;
    }

    public void setTakmicarskiBroj(int takmicarskiBroj) {
        this.takmicarskiBroj = takmicarskiBroj;
    }

    public void add(BridgePlayer igrac) {
        igraci.add(igrac);
        igrac.setMemberOf(this);
    }

    public void addPlayedContract(UniqueContract pc) {
        if (!playedContracts.contains(pc))
            playedContracts.add(pc);
    }

    public String getTeamName() {
        if (igraci.size() >= 4) {
            if (teamName.length() != 0)
                return teamName;
            BridgePlayer player = (BridgePlayer) igraci.elementAt(0);
            return "T-" + player.getPrezime().toUpperCase();
        }

        if (igraci.size() == 1) {
            BridgePlayer player = (BridgePlayer) igraci.elementAt(0);
            return player.getIme() + " " + player.getPrezime();
        }

        if (igraci.size() == 2) {
            BridgePlayer player1 = (BridgePlayer) igraci.elementAt(0);
            BridgePlayer player2 = (BridgePlayer) igraci.elementAt(1);
            return player1.getPrezime() + "-" + player2.getPrezime();
        }
        return " ------- ";
    }

    public Vector getIgraci() {
        if (igraci.size() == 0)
            if (teamRecord != null)
                fillIgraci();
        return igraci;
    }

    private void fillIgraci() {
        TeammemberRecord clanTima = new TeammemberRecord();
        TeammemberDB tdb = (TeammemberDB) clanTima.getDBClass();
        Criteria crit = new Criteria();
        crit.addOgranicenje(new FieldCriteria("", "teamid", FieldCriteria.IS_EQUAL, teamRecord.getTeamid()));
        Vector vec = tdb.getCollection(crit);
        Enumeration enm = vec.elements();
        while (enm.hasMoreElements()) {
            clanTima = (TeammemberRecord) enm.nextElement();
            BridgePlayer player = new BridgePlayer(clanTima);
            add(player);
        }
    }

    public BridgePlayer getPlayerAt(int idx) {
        try {
            return (BridgePlayer) igraci.elementAt(idx);
        } catch (Exception e) {
            return null;
        }
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public double getMpProcenat() {
        return mpProcenat;
    }

    public void setMpProcenat(double mpProcenat) {
        this.mpProcenat = mpProcenat;
    }

    public double getMatchPoints() {
        return matchPoints;
    }

    public void setMatchPoints(double matchPoints) {
        this.matchPoints = matchPoints;
    }

    public double getImps() {
        return imps;
    }

    public void setImps(double imps) {
        this.imps = imps;
    }


    public void calculatePlasman() {
        plasmanRecord = new PlasmanRecord();
        PlasmanDB pldb = (PlasmanDB) plasmanRecord.getDBClass();
        plasmanRecord = pldb.getBy(teamRecord);
        if (plasmanRecord != null) {
            mpProcenat = plasmanRecord.getProcenatMatchpoints();
            imps = plasmanRecord.getPointsIMP() + plasmanRecord.getAdjustIMP();
        }
    }

    public double getRating() {
        if (igraci.size() == 0)
            return 0.0;
        Enumeration enm = igraci.elements();
        double rating = 0.0;
        while (enm.hasMoreElements()) {
            BridgePlayer igrac = (BridgePlayer) enm.nextElement();
            rating += igrac.getRating();
        }
        return rating / igraci.size();
    }

    public double getMaxRanking() {
        Enumeration enm = igraci.elements();
        double rnk = Double.MIN_VALUE;
        while (enm.hasMoreElements()) {
            BridgePlayer pl = (BridgePlayer) enm.nextElement();
            if (pl.getRating() > rnk)
                rnk = pl.getRating();
        }
        return rnk;
    }

    public double getAvrgRanking() {
        return getRating();
    }

    public boolean isNS() {
       return true;
    }
}
