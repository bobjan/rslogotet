package rs.logotet.bridge.test;

import rs.logotet.bridge.model.*;
import rs.logotet.bridge.base.Bridge;
import rs.logotet.bridge.test.RandomPlayers;
import rs.logotet.bridge.test.StartDB;
import rs.logotet.util.BJDatum;
import rs.logotet.helper.db.DBStartException;

import java.io.IOException;

/**
 * kreirano:
 * Date: Aug 31, 2008
 * Time: 8:03:27 PM
 */
public class Tester {
    public static void main(String[] args) throws IOException, DBStartException {
        StartDB.start();
        Tournament tur = new Tournament(new BJDatum());
        Seansa seansa = new Seansa(tur);
        Section section = new Section(seansa);
        RandomPlayers rp = new RandomPlayers();
        BridgePlayer bp1;
        BridgePlayer bp2;
        BridgeTeam bt;
        for (int i = 0; i < 11; i++) {
            bp1 = new BridgePlayer(rp.getRandomIme(), rp.getRandomPrezime());
            bp2 = new BridgePlayer(rp.getRandomIme(), rp.getRandomPrezime());
            bt = new BridgeTeam();
            bt.add(bp1);
            bt.add(bp2);
            bt.setTakmicarskiBroj(i + 1);
            section.addTeam(bt, Bridge.NORTH_SOUTH);

            bp1 = new BridgePlayer(rp.getRandomIme(), rp.getRandomPrezime());
            bp2 = new BridgePlayer(rp.getRandomIme(), rp.getRandomPrezime());
            bt = new BridgeTeam();
            bt.add(bp1);
            bt.add(bp2);
            bt.setTakmicarskiBroj(i + 1);
            section.addTeam(bt, Bridge.EAST_WEST);
        }

    }
}
