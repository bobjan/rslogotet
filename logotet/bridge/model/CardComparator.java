package rs.logotet.bridge.model;

import rs.logotet.bridge.model.*;

import java.util.Comparator;

/**
 * Comparator koji se zasniva na poredjenju datuma pod pretpostavkom da
 * objekti koji se porede imaju metodu getCard();
 */
public class CardComparator implements Comparator {
    private int smer;	// ako je negativan - desc, pozitivan - asc

    public CardComparator() {
    }

    public int compare(Object obj1, Object obj2) {
        rs.logotet.bridge.model.CardComparable dat1 = (rs.logotet.bridge.model.CardComparable) obj1;
        rs.logotet.bridge.model.CardComparable dat2 = (rs.logotet.bridge.model.CardComparable) obj2;
        if (dat1.getCard().getSuit() != dat2.getCard().getSuit())
            return dat2.getCard().getSuit() - dat1.getCard().getSuit();
        return dat2.getCard().getValue() - dat1.getCard().getValue();
    }

    public boolean equals(Object obj) {
        return false;
    }
}