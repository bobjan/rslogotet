package rs.logotet.bridge.model;

import rs.logotet.bridge.base.Bridge;


/**
 * belongs to Deal, and doesn't exist outside Deal, i ima samo
 * one atribute koji se odnose na kontrakt u najuzem smislu
 */
public class Contract {
    private static final String boje[] = {"C", "D", "H", "S", "NT"};
    private int suit; // 0 - clubs; 1-diamonds; 2-hearts; 3 - spades; 4 - NT

    private int denomination; // 1 - 7

    private int dbl; // 0 - no; 1 - doubled; 2 - redoubled

    public Contract(int denomination, int suit, int dbl) {
        this.suit = suit;
        this.denomination = denomination;
        this.dbl = dbl;
    }

    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public int getDbl() {
        return dbl;
    }

    public void setDbl(int dbl) {
        this.dbl = dbl;
    }

    public String toString() {
        if(isAllPassed())
            return "PASSED";
        String s = denomination + boje[suit];
        switch (dbl) {
            case 0:
                break;
            case 1:
                s += "X";
                break;
            case 2:
                s += "XX";
                break;
            default:
                break;
        }
        return s;
    }


    public int getContractValue() {
        int vrednost;
        if (suit < 2)
            vrednost = 20;
        else
            vrednost = 30;
        vrednost *= denomination;
        if (suit == 4)    // NT
            vrednost += 10;
        if (dbl == Bridge.DOUBLED)
            vrednost *= 2;
        if (dbl == Bridge.REDOUBLED)
            vrednost *= 4;
        return vrednost;
    }

    public boolean isMinor() {
        if (suit < 2)
            return true;
        return false;
    }

    public boolean isSmallSlam() {
        return (denomination == 6) ? true : false;
    }

    public boolean isGrandSlam() {
        return (denomination == 7) ? true : false;
    }

    public boolean isAllPassed(){
        return (denomination == 0);
    }


    public static Contract getRandom() {
        int boja;
        int nivo;
        int dbl;
        double tmp;
        Contract cntr;
        boja = (int) (Math.random() * 4.0);
        nivo = (int) (Math.random() * 8.0);
        if (nivo > 4) {
            nivo = (int) (Math.random() * 7.0) + 1;
        }
        tmp = Math.random() * 100.00;
        dbl = (tmp < 80.00) ? 0 : ((tmp < 97.00) ? 1 : 2);
        cntr = new Contract(nivo, boja, dbl);
        return cntr;
    }
}
