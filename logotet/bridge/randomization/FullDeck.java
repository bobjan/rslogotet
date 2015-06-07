package rs.logotet.bridge.randomization;

import rs.logotet.bridge.model.Card;
import rs.logotet.bridge.model.Hand;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * generise veliki broj kombinacija koje zadovoljavaju neke uslove sto se tice poena,
 * a potom u main metodi proverava i odbrojava raspodele radi izracunavanja verovatnoca
 */
public class FullDeck {
    private int[] all;
    private int[] wholerow;
    private int[] north;
    private int[] south;
    private int[] east;
    private int[] west;

    public FullDeck() {
        all = new int[53];
        wholerow = new int[52];
        north = new int[13];
        south = new int[13];
        east = new int[13];
        west = new int[13];

        for (int i = 0; i < all.length; i++) {
            all[i] = 0;
        }
        for (int i = 0; i < wholerow.length; i++) {
            wholerow[i] = 0;
        }

        all[0] = 1;
        for (int i = 0; i < north.length; i++) {
            north[i] = 0;
            south[i] = 0;
            east[i] = 0;
            west[i] = 0;
        }
    }

    private void deal() {
        int broj;
        boolean imaJos = false;
        int idx = 0;
        do {
            do {
                broj = (int) (Math.random() * 53);
            } while (all[broj] == 1);
            wholerow[idx++] = broj;
            all[broj] = 1;

            imaJos = false;
            for (int i = 1; i < all.length; i++) {
                if (all[i] == 0)
                    imaJos = true;
            }
        } while (imaJos);

        for (int i = 0; i < north.length; i++) {
            north[i] = wholerow[i];
            south[i] = wholerow[i + 13];
            east[i] = wholerow[i + 26];
            west[i] = wholerow[i + 39];
        }
    }

    public Hand getNorth() {
        Hand list = new Hand();
        for (int i = 0; i < north.length; i++) {
            Card karta = new Card(north[i]);
//            System.out.println(karta.toString());
            list.addCard(karta);
        }
        return list;
    }

    public Hand getSouth() {
        Hand list = new Hand();
        for (int i = 0; i < south.length; i++) {
            Card karta = new Card(south[i]);
            list.addCard(karta);
        }
        return list;
    }

    public Hand getEast() {
        Hand list = new Hand();
        for (int i = 0; i < east.length; i++) {
            Card karta = new Card(east[i]);
            list.addCard(karta);
        }
        return list;
    }

    public Hand getWest() {
        Hand list = new Hand();
        for (int i = 0; i < west.length; i++) {
            Card karta = new Card(west[i]);
            list.addCard(karta);
        }
        return list;
    }

    public static void main(String[] args) {
        FullDeck fd = new FullDeck();
        fd.deal();
        Hand nt = fd.getNorth();
        Hand st = fd.getSouth();
        Hand es = fd.getEast();
        Hand ws = fd.getWest();
        int[] hcpPoint = new int[11];

        for (int i = 0; i < hcpPoint.length; i++) {
            hcpPoint[i] = 0;
        }

//        System.out.println("North:\t" + nt.sortedString() + " * " + nt.getHcp() + " * " + nt.getDistribution());
//        System.out.println("South:\t" + st.sortedString() + " * " + st.getHcp() + " * " + st.getDistribution());
//        System.out.println("East:\t" + es.sortedString() + " * " + es.getHcp() + " * " + es.getDistribution());
//        System.out.println("West:\t" + ws.sortedString() + " * " + ws.getHcp() + " * " + ws.getDistribution());
        Hashtable hash = new Hashtable(100);
        int totCounter = 0;
        int wholeCounter = 0;
        while (totCounter < 100) {
            fd = new FullDeck();
            fd.deal();
            nt = fd.getNorth();
            st = fd.getSouth();
            es = fd.getEast();
            ws = fd.getWest();
            String kljuc;
            wholeCounter++;
            if ((es.getHcp() > 11) && (es.getHcp() < 16))
                if ((es.getHeartCount() == 5) || (es.getHeartCount() == 6))
                    if ((ws.getHeartCount() == 3) || (ws.getHeartCount() == 4))
                        if ((ws.getHcp() > 5) && (ws.getHcp() < 10)) // ovo gore je uslov za 1H - 2H
                            if (nt.getHeartCount() >= 3)
                                if (nt.getHcp() >= 8)
                                    if (nt.getHcp() <= 12)
                                        if ((nt.getSpadeCount() == 4) || (nt.getSpadeCount() == 5))
                                            if ((nt.getDiamCount() <= 6) && (nt.getClubCount() <= 6)) {
                                                hcpPoint[nt.getHeartsHcp() + st.getHeartsHcp()]++;
                                                String fit = ((nt.getSpadeCount() + st.getSpadeCount()) > 7) ? "Y" : "N";
                                                kljuc = (nt.getHcp() + st.getHcp()) + ":" + fit;
                                                totCounter++;
                                                if (hash.containsKey(kljuc)) {
                                                    Integer brojac = (Integer) hash.get(kljuc);
                                                    int counter = brojac.intValue();
                                                    counter++;
                                                    hash.remove(kljuc);
                                                    hash.put(kljuc, new Integer(counter));
                                                } else
                                                    hash.put(kljuc, new Integer(1));

//                                        System.out.print(".");
//                                        if ((totCounter % 50) == 0)
//                                            System.out.println("*");

                                            }
            if ((ws.getHcp() > 11) && (ws.getHcp() < 16))
                if ((ws.getHeartCount() == 5) || (ws.getHeartCount() == 6))
                    if ((es.getHeartCount() == 3) || (es.getHeartCount() == 4))
                        if ((es.getHcp() > 5) && (es.getHcp() < 10)) // ovo gore je uslov za 1H - 2H
                            if (nt.getHeartCount() >= 3)
                                if (nt.getHcp() >= 8)
                                    if (nt.getHcp() <= 12)
                                        if ((nt.getSpadeCount() == 4) || (nt.getSpadeCount() == 5))
                                            if ((nt.getDiamCount() < 6) && (nt.getClubCount() < 6)) {
                                                hcpPoint[nt.getHeartsHcp() + st.getHeartsHcp()]++;
                                                String fit = ((nt.getSpadeCount() + st.getSpadeCount()) > 7) ? "Y" : "N";
                                                kljuc = (nt.getHcp() + st.getHcp()) + ":" + fit;
                                                totCounter++;
                                                if (hash.containsKey(kljuc)) {
                                                    Integer brojac = (Integer) hash.get(kljuc);
                                                    int counter = brojac.intValue();
                                                    counter++;
                                                    hash.remove(kljuc);
                                                    hash.put(kljuc, new Integer(counter));
                                                } else
                                                    hash.put(kljuc, new Integer(1));
//                                        System.out.print(".");
//                                        if ((totCounter % 50) == 0)
//                                            System.out.println("");
                                            }
        }

        Enumeration enm = hash.keys();
        while (enm.hasMoreElements()) {
            String kljuc = (String) enm.nextElement();
            Integer brojac = (Integer) hash.get(kljuc);
            System.out.println(kljuc + " --> " + brojac.intValue());
        }
        System.out.println("********** *** " + wholeCounter);

//        for (int i = 0; i < hcpPoint.length; i++) {
//            System.out.println(i + " *** " + hcpPoint[i] );
//        }


    }
}
