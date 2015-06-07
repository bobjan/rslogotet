package rs.logotet.bridge.model.masterpoeni;

import rs.logotet.bridge.model.dbrecord.RatinghistoryRecord;
import rs.logotet.bridge.base.Bridge;
import rs.logotet.bridge.model.BridgePlayer;
import rs.logotet.bridge.model.BridgeTeam;
import rs.logotet.bridge.model.Seansa;
import rs.logotet.bridge.base.Bridge;
import rs.logotet.helper.DBRecordException;

import java.util.Enumeration;
import java.util.Vector;

/**
 * ideja za rejting:
 * <p/>
 * prosecan rating je 1000 poena
 * ako neko ostvari 55% njegov "pokazani kvalitet" je prosecan rejting ucesnika * 1.55;
 * nakon toga se njegov novi rejting postavljana sredinu izmedju pokazanog kvaliteta i prehodnog rejtinga!!!
 */
public class BJRating {
    private Seansa seansa;
    double prosekSale;

    public BJRating(Seansa seansa) {
        this.seansa = seansa;
    }

    public void preracunaj() {
        RatinghistoryRecord history = new RatinghistoryRecord();
        Vector timovi = seansa.getTimovi();
        prosekSale = getProsek(timovi);
        System.out.println(seansa.getDatum().getStringDate() + " " + prosekSale);
        Enumeration enm = timovi.elements();
        while (enm.hasMoreElements()) {
            BridgeTeam tim = (BridgeTeam) enm.nextElement();
            double procenat = getBJProcenat(seansa.getScoringType(), tim) / 100.0;
            double noviRating = prosekSale * 2.0 * procenat;
            double ratingIncrement = (noviRating - tim.getRating()) / 10.00;
            Enumeration inner = tim.getIgraci().elements();
            while (inner.hasMoreElements()) {
                BridgePlayer player = (BridgePlayer) inner.nextElement();
//                double novaVrednost = (noviRating + 7 * player.getRating()) / 8.00;
                double novaVrednost = player.getRating() + ratingIncrement;
                player.setRating(novaVrednost);
                player.makePersistent();
                history = new RatinghistoryRecord();
                history.setPlayerid(player.getPlayerid());
                history.setDatum(seansa.getDatum());
                history.setRating(novaVrednost);
                try {
                    history.makePersistent();
                } catch (DBRecordException e) {
                }
            }
        }
    }

    private double getProsek(Vector timovi) {
        if (timovi.size() == 0)
            return Bridge.AVERAGE;
        double total = 0.0;
        Enumeration enm = timovi.elements();
        while (enm.hasMoreElements()) {
            BridgeTeam tim = (BridgeTeam) enm.nextElement();
            total += tim.getRating();
        }
        return total / timovi.size();
    }

    /**
     * zavino od tipa obracuna vraca procenat ili logaritam
     */

    private double getBJProcenat(int tip, BridgeTeam team) {
        return getBJProcenat(tip, team, 28);
    }

    private double getBJProcenat(int tip, BridgeTeam team, int brojBordova) {
        switch (tip) {
            case Bridge.BUTTLER0:
            case Bridge.BUTTLER1:
            case Bridge.BUTTLER2:
                return convertIMP(team.getImps());
            case Bridge.CROSSIMP:
                return convertIMP(team.getImps());
            default:
                return team.getMpProcenat();
        }
    }

    private double convertIMP(double imp, double brojBordova) {
        if (brojBordova == 0)
            return convertIMP(imp, 28.0);
        return 50.0 + imp * Math.log(brojBordova * brojBordova) / brojBordova;
    }

    private double convertIMP(double imp) {
        return convertIMP(imp, 28.0);
    }
}
