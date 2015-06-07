package rs.logotet.bridge.model;

import rs.logotet.bridge.base.Bridge;
import rs.logotet.bridge.base.BridgeException;
import rs.logotet.bridge.model.*;
import rs.logotet.bridge.model.db.TeamDB;
import rs.logotet.bridge.model.dbrecord.SectionRecord;
import rs.logotet.bridge.model.dbrecord.TeamRecord;
import rs.logotet.helper.Criteria;
import rs.logotet.helper.FieldCriteria;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Sekcija tj. sala ; a za timske meceve OpenRoom(0) ili ClosedRoom(1)
 */
public class Section {
    private SectionRecord sectionRecord = null;
    private rs.logotet.bridge.model.Seansa parrent = null;
    private Vector timovi;

    private int broj;// od 1 pa anadalje

    public Section(rs.logotet.bridge.model.Seansa parrent, int broj) {
        this.parrent = parrent;
        this.broj = broj;
        timovi = new Vector();
    }


    public Section(rs.logotet.bridge.model.Seansa s, SectionRecord sRecord) {
        this(s);
        sectionRecord = sRecord;
        fillTeams();
    }

    public Section(rs.logotet.bridge.model.Seansa s) {
        this();
        parrent = s;
    }
    public Section() {
        timovi = new Vector();
    }

    private void fillTeams() {
        TeamRecord teamRecord = new TeamRecord();
        TeamDB teamDB = (TeamDB) teamRecord.getDBClass();
        Criteria crit = new Criteria();
        crit.addOgranicenje(new FieldCriteria("", "sesijaid", FieldCriteria.IS_EQUAL, sectionRecord.getSesijaid()));
        crit.addOgranicenje(new FieldCriteria("", "sectionnumber", FieldCriteria.IS_EQUAL, sectionRecord.getSectionnumber()));
        Vector vec = teamDB.getCollection(crit);
        Enumeration enm = vec.elements();
        while (enm.hasMoreElements()) {
            TeamRecord obj = (TeamRecord) enm.nextElement();
            BridgeTeam team = new BridgeTeam(obj);
            addTeam(team);
        }
    }


    public Vector getTimovi() {
        return timovi;
    }

    public void addTeam(BridgeTeam team) {
        if (!timovi.contains(team))
            timovi.add(team);
    }

    public void addTeam(BridgeTeam team, int pozicija) {
        if (!timovi.contains(team))
            timovi.add(team);
        // @ todo ovo razraditi
    }
    /**
     *
     * @ todo ovo nesto ne valja
     * */
    public BridgeTeam getRegisteredTeam(int broj, int linija) throws BridgeException {
        Vector tmp = new Vector();
        Enumeration enm = timovi.elements();
        while (enm.hasMoreElements()) {
            BridgeTeam obj = (BridgeTeam) enm.nextElement();
            if (obj.getTakmicarskiBroj() == broj)
                tmp.add(obj);
        }
        if (tmp.size() == 0)
            throw new BridgeException("(class)Section :ne postoji tim sa takm. brojem " + broj);
        if (tmp.size() == 1)
            return (BridgeTeam) tmp.elementAt(0);

        if (tmp.size() > 2)
            throw new BridgeException("(class)Section :vise od dva tima ");

        switch (linija) {
            case Bridge.NORTH_SOUTH:
                if (((BridgeTeam) tmp.elementAt(0)).isNS())
                    return (BridgeTeam) tmp.elementAt(0);
                else
                    return (BridgeTeam) tmp.elementAt(1);
            default:
                if (((BridgeTeam) tmp.elementAt(0)).isNS())
                    return (BridgeTeam) tmp.elementAt(1);
                else
                    return (BridgeTeam) tmp.elementAt(0);

        }
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

}
