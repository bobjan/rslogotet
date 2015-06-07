package rs.logotet.bridge.test;

import rs.logotet.bridge.model.Contract;

import java.util.Enumeration;
import java.util.Vector;

/**
 * User: jankovicb
 * Date: Oct 29, 2007
 */
public class ResultTester {
    public ResultTester() {
    }

    /**
     * expected 7 - 13 kao ulaz, a izlaz 3 - 13;
     * maks. -4 down i isto toliko overtrick ali da ne predje 13
     */
    public int getRandomizedTricksTaken(int expected) {
        double rnd = Math.random();
        if (rnd < 0.55)
            return expected;
        rnd = Math.random();
        if (rnd < 0.15)
            return expected - 4;
        if (rnd < 0.25)
            return expected - 3;
        if (rnd < 0.4)
            return expected - 2;
        if (rnd < 0.55)
            return expected - 1;

        int tot = expected + 1;
        if (rnd > 0.65)
            tot += 1;
        if (rnd > 0.75)
            tot += 1;
        if (rnd > 0.9)
            tot += 1;
        if (tot > 13)
            tot = 13;
        return tot;
    }

    public Vector prepareTestBoards(int broj) {
        int boja;
        int nivo;
        int dbl;
        double tmp;
        Vector v = new Vector();
        Contract cntr;
        for (int i = 0; i < broj; i++) {
            boja = (int) (Math.random() * 4.0);
            nivo = (int) (Math.random() * 7.0) + 1;
            if (nivo > 4) {
                nivo = (int) (Math.random() * 7.0) + 1;
            }
            tmp = Math.random() * 100.00;
            dbl = (tmp < 80.00) ? 0 : ((tmp < 97.00) ? 1 : 2);
            cntr = new Contract(nivo, boja, dbl);
            v.add(cntr);
        }
        return v;
    }

    public static void main(String[] args) {
        ResultTester rt = new ResultTester();
        Vector c = rt.prepareTestBoards(10);
        Enumeration enm = c.elements();
        while (enm.hasMoreElements()) {
            Contract cnt = (Contract) enm.nextElement();
            System.out.println(cnt.toString() + " " + rt.getRandomizedTricksTaken(cnt.getDenomination() + 6));
        }
    }

}
