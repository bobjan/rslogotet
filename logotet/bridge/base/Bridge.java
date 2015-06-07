package rs.logotet.bridge.base;

/**
 * konstante za bridge aplikaciju
 */
public class Bridge {
    // avrg. rating
    public static final double AVERAGE = 1000.00;
    // sides
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    // linije igre
    public static final int NORTH_SOUTH = 0;
    public static final int EAST_WEST = 1;


    // vulnerability
    public static final int NONE = 0;
    public static final int NS = 1;
    public static final int EW = 2;
    public static final int ALL = 3;
    // contract denomination and suits
    public static final int CLUBS = 0;
    public static final int DIAMONDS = 1;
    public static final int HEARTS = 2;
    public static final int SPADES = 3;
    public static final int NT = 4;

    public static final String[] suitName = {"Clubs", "Diamonds", "Hearts", "Spades", "NoTrump"};
    public static final String[] suitShortName = {"C", "D", "H", "S", "NT"};

    public static final String[] sideName = {"North", "East", "South", "West"};
    public static final String[] sideShortName = {"N", "E", "S", "W"};


    // movements
    public static final int ROUND_ROBIN = 1;
    public static final int SWISS = 2;
    public static final int MITCHEL = 10;
    public static final int HOWELL = 11;
    public static final int TRIAL = 12;


    public static final int TEAM = 0;
    public static final int PAIR = 1;
    public static final int INDIVIDUAL = 2;


    // scoring types
    public static final int MATCHPOINTS = 0;
    public static final int BUTTLER0 = 1;
    public static final int BUTTLER1 = 2;
    public static final int BUTTLER2 = 3;

    public static final int CROSSIMP = 5;
    public static final int PATTON = 6;
    public static final int VP = 7;  // only for multi session tournamenst ??


    public static final int NO_DOUBLE = 0;
    public static final int DOUBLED = 1;
    public static final int REDOUBLED = 2;


    public static int getSuitInteger(String suit) {
        for (int i = 0; i < suitShortName.length; i++) {
            if (suit.trim().equals(suitShortName[i]))
                return i;
        }
        return -1;
    }
}
