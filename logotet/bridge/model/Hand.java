package rs.logotet.bridge.model;

import rs.logotet.bridge.model.*;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;


/**
 * User: jankovicb
 * Date: Jul 26, 2007
 */
public class Hand {
    private rs.logotet.bridge.model.Card[] karte;
    private Vector vector;

    public Hand() {
        vector = new Vector();
        karte = new rs.logotet.bridge.model.Card[13];
    }

    public void addCard(rs.logotet.bridge.model.Card karta) {
        for (int i = 0; i < karte.length; i++) {
            if (karte[i] == null) {
                karte[i] = karta;
                vector.add(karta);
                break;
            }
        }

        CardComparator cc = new CardComparator();
        Collections.sort(vector, cc);
    }

    public String sortedString() {
        StringBuffer sb = new StringBuffer();
        Enumeration enm = vector.elements();
        while (enm.hasMoreElements()) {
            rs.logotet.bridge.model.Card karta = (rs.logotet.bridge.model.Card) enm.nextElement();
            sb.append(karta.toString());
        }
        return sb.toString();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < karte.length; i++) {
            sb.append(karte[i].toString());
        }
        return sb.toString();
    }

    public int getHcp() {
        int tot = 0;
        for (int i = 0; i < karte.length; i++) {
            tot += karte[i].getHcp();
        }
        return tot;
    }

    public int getHeartsHcp() {
        int tot = 0;
        for (int i = 0; i < karte.length; i++) {
            if (karte[i].getSuit() == 2)
                tot += karte[i].getHcp();
        }
        return tot;
    }

    public int getHeartCount() {
        int tot = 0;
        for (int i = 0; i < karte.length; i++) {
            if (karte[i].getSuit() == 2)
                tot++;
        }
        return tot;
    }

    public int getSpadeCount() {
        int tot = 0;
        for (int i = 0; i < karte.length; i++) {
            if (karte[i].getSuit() == 3)
                tot++;
        }
        return tot;
    }

    public int getDiamCount() {
        int tot = 0;
        for (int i = 0; i < karte.length; i++) {
            if (karte[i].getSuit() == 1)
                tot++;
        }
        return tot;
    }

    public int getClubCount() {
        int tot = 0;
        for (int i = 0; i < karte.length; i++) {
            if (karte[i].getSuit() == 0)
                tot++;
        }
        return tot;
    }

    public String getDistribution() {
        return getSpadeCount() + "-" + getHeartCount() + "-" + getDiamCount() + "-" + getClubCount();

    }
}
