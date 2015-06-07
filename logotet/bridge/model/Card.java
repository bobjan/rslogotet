package rs.logotet.bridge.model;

import rs.logotet.bridge.model.CardComparable;

/**
 * User: jankovicb
 * Date: Jul 26, 2007
 */
public class Card implements CardComparable {
    private static final String boje[] = {"C", "D", "H", "S"};
    private static final String face[] = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};

    private int suit; // 0 - clubs; 1-diamonds; 2-hearts; 3 - spades

    private int value;

    public Card(int idx) {
        int idxBoje = (idx - 1) / 13;
        int idxFace = (idx - 1) % 13;
        setSuit(idxBoje);
        setValue(idxFace + 2);
    }


    public Card(int suit, int karta) {
        setSuit(suit);
        setValue(karta);
    }


    public Card(int suit, String karta) {
        setSuit(suit);
        setValue(karta);
    }

    public Card(String suit, String karta) {
        setSuit(suit);
        setValue(karta);

    }

    public Card(String suit, int karta) {
        setSuit(suit);
        setValue(karta);
    }

    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public void setSuit(String boja) {
        if (boja.toUpperCase().equals("C"))
            this.suit = 0;
        if (boja.toUpperCase().equals("D"))
            this.suit = 1;
        if (boja.toUpperCase().equals("H"))
            this.suit = 2;
        if (boja.toUpperCase().equals("S"))
            this.suit = 3;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setValue(String karta) {
        if (karta.toUpperCase().equals("2"))
            this.value = 2;
        if (karta.toUpperCase().equals("3"))
            this.value = 3;
        if (karta.toUpperCase().equals("4"))
            this.value = 4;
        if (karta.toUpperCase().equals("5"))
            this.value = 5;
        if (karta.toUpperCase().equals("6"))
            this.value = 6;
        if (karta.toUpperCase().equals("7"))
            this.value = 7;
        if (karta.toUpperCase().equals("8"))
            this.value = 8;
        if (karta.toUpperCase().equals("9"))
            this.value = 9;
        if (karta.toUpperCase().equals("10"))
            this.value = 10;
        if (karta.toUpperCase().equals("T"))
            this.value = 10;
        if (karta.toUpperCase().equals("J"))
            this.value = 11;
        if (karta.toUpperCase().equals("Q"))
            this.value = 12;
        if (karta.toUpperCase().equals("K"))
            this.value = 13;
        if (karta.toUpperCase().equals("A"))
            this.value = 14;
    }

    public String toString() {
        String s1;
        switch (value) {
            case 10:
                s1 = "T";
                break;
            case 11:
                s1 = "J";
                break;
            case 12:
                s1 = "Q";
                break;
            case 13:
                s1 = "K";
                break;
            case 14:
                s1 = "A";
                break;
            default:
                s1 = "" + value;
                break;

        }

        return s1 + boje[suit];

    }

    public int getHcp() {
        if (value > 10)
            return value - 10;
        return 0;
    }

    public Card getCard() {
        return this;
    }
}

