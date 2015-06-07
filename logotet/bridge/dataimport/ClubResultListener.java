package rs.logotet.bridge.dataimport;

import rs.logotet.bridge.dataimport.*;
import rs.logotet.bridge.dataimport.IMPParser;
import rs.logotet.bridge.model.db.*;
import rs.logotet.bridge.model.dbrecord.*;
import rs.logotet.bridge.base.Bridge;
import rs.logotet.bridge.base.Bridge;
import rs.logotet.helper.DBRecordException;
import rs.logotet.helper.parser.Parser;
import rs.logotet.helper.parser.ParserListener;
import rs.logotet.util.BJDatum;

import java.sql.SQLException;
import java.util.StringTokenizer;

/**
 * kreirano:
 * Date: Nov 9, 2008
 * Time: 8:04:08 PM
 */
public class ClubResultListener implements ParserListener {
    private static final String[] klubName = {"AS", "DaSilva", "Dummy", "NS", "Ostalo"};
    private int klubIdx;
    private BJDatum datum;
    private static PlayerDB playerDB = null;
    private static SesijaDB sesijaDB = null;
    private static TeamDB teamDB = null;
    private static TeammemberDB teammemberDB = null;

    private static TurnirDB turnirDB = null;
    private SesijaRecord sesijaRecord;
    private TurnirRecord turnirRecord;

    public ClubResultListener(String fileName) {
        try {
            playerDB = (PlayerDB) DBFactory.getDBClass(DBFactory.PLAYER);
            sesijaDB = (SesijaDB) DBFactory.getDBClass(DBFactory.SESIJA);
            turnirDB = (TurnirDB) DBFactory.getDBClass(DBFactory.TURNIR);
            teamDB = (TeamDB) DBFactory.getDBClass(DBFactory.TEAM);
            teammemberDB = (TeammemberDB) DBFactory.getDBClass(DBFactory.TEAMMEMBER);
        } catch (SQLException e) {
        }
        int poz = fileName.indexOf(".txt");
        datum = new BJDatum(Integer.parseInt(fileName.substring(poz - 4, poz)),
                Integer.parseInt(fileName.substring(poz - 6, poz - 4)),
                Integer.parseInt(fileName.substring(poz - 8, poz - 6)));

        int imp = fileName.indexOf("IMP");
        int as = fileName.indexOf("AS");
        int ds = fileName.indexOf("DaSilva");
        int dm = fileName.indexOf("Dummy");
        int ns = fileName.indexOf("NS");
        int os = fileName.indexOf("Ostalo");

        // ovo je kvazi nalazenje kluba radi naziva turnira
        if (as >= 0)
            klubIdx = 0;
        if (ds >= 0)
            klubIdx = 1;
        if (dm >= 0)
            klubIdx = 2;
        if (ns >= 0)
            klubIdx = 3;
        if (os >= 0)
            klubIdx = 4;

        sesijaRecord = new SesijaRecord();
        turnirRecord = new TurnirRecord();

        turnirRecord.setDatum(datum);
        String ime = (imp > 0) ? klubName[klubIdx] + " IMP" : klubName[klubIdx];
        turnirRecord.setNaziv(ime);
        if (imp > 0) {
            turnirRecord.setTipObracuna(Bridge.BUTTLER0);
        } else {
            turnirRecord.setTipObracuna(Bridge.MATCHPOINTS);
        }
        try {
            turnirRecord.makePersistent();
            sesijaRecord.setTurnirid(turnirRecord.getTurnirid());
            sesijaRecord.setOznakasesije(0);
            sesijaRecord.setDatum(datum);
            sesijaRecord.setObracun(turnirRecord.getTipObracuna());
            sesijaRecord.makePersistent();
        } catch (DBRecordException e) {
        }
    }


    public void gotovo(Parser parser) {
        if (sesijaDB == null)
            return;
        if (teamDB == null)
            return;
        if (teammemberDB == null)
            return;
        if (playerDB == null)
            return;
        String prvoIme = "";
        String drugoIme = "";
        int plasman = 0;
        double procenat = 0.0;
        int imp = 0;
        boolean parski = true;
        if (parser instanceof rs.logotet.bridge.dataimport.MatchpointParser) {
            rs.logotet.bridge.dataimport.MatchpointParser mp = (rs.logotet.bridge.dataimport.MatchpointParser) parser;
            prvoIme = mp.getPrvoIme();
            drugoIme = mp.getDrugoIme();
            plasman = mp.getPlasman();
            procenat = mp.getProcenat();
        }
        if (parser instanceof rs.logotet.bridge.dataimport.IMPParser) {
            rs.logotet.bridge.dataimport.IMPParser ip = (IMPParser) parser;
            prvoIme = ip.getPrvoIme();
            drugoIme = ip.getDrugoIme();
            plasman = ip.getPlasman();
            imp = ip.getImp();
            parski = false;
        }

        try {
            PlayerRecord player1 = testCreate(prvoIme);
            PlayerRecord player2 = testCreate(drugoIme);
//            System.out.println(plasman + "." + player1.getFullName() + "-" + player2.getFullName() + "\t: " + procenat);
            TeamRecord tim;
            tim = new TeamRecord();
            tim.setSesijaid(sesijaRecord.getSesijaid());
            tim.setNaziv(player1.getPrezime() + "/" + player2.getPrezime());
            tim.setTeamnumber(plasman);
            tim.makePersistent();
//
            TeammemberRecord member = new TeammemberRecord();
            member.setPlayerid(player1.getPlayerid());
            member.setTeamid(tim.getTeamid());
            member.makePersistent();

            member = new TeammemberRecord();
            member.setPlayerid(player2.getPlayerid());
            member.setTeamid(tim.getTeamid());
            member.makePersistent();
            PlasmanRecord plasmanRecord = new PlasmanRecord();
            plasmanRecord.setTeamid(tim.getTeamid());
            if (parski)
                plasmanRecord.setProcenatMatchpoints(procenat);
            else
                plasmanRecord.setPointsIMP(imp);
            plasmanRecord.makePersistent();

        } catch (DBRecordException e) {
            System.out.println("GRESKA u dodavanju tima ");
        }
    }

    private PlayerRecord testCreate(String punoIme) {
        StringTokenizer tokenizer = new StringTokenizer(punoIme, " ");
        String prezime = tokenizer.nextToken().toUpperCase();
        String imeTmp = tokenizer.nextToken();
        String ime = imeTmp.substring(0, 1).toUpperCase() + imeTmp.substring(1).toLowerCase();
        PlayerRecord pr = playerDB.findBy(ime, prezime);
        if (pr == null) {
            pr = new PlayerRecord();
            pr.setIme(ime);
            pr.setPrezime(prezime);
            pr.setRating(Bridge.AVERAGE);
            try {
                pr.makePersistent();
            } catch (DBRecordException e) {
                System.out.println("NIJE UPISAN " + punoIme);
            }
        }
        return pr;
    }
}
