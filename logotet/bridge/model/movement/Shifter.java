package rs.logotet.bridge.model.movement;

/**
 * pomera tri niza tako da reflektuju mitchel kretanje
 * poziva se za svako kolo
 * <p/>
 * *  tri niza su organizovana na isti nacin   tako da kada se pozove makeRound(broj kola)
 * i noz dobije vrednosti npr.:
 * [5,6,7,8,1,2,3,4] onda to znaci na prvom stolu je par broj 5, na drugom stolu par broj 6 itd.
 * <p/>
 * naravno prvi sto broj 1 ima index 0!
 */
public class Shifter {
    private int[] nizNS; // niz North-South brojeva , indexi 0... za stolove 1...
    private int[] nizEW;  // niz East-West brojeva  , indexi 0... za stolove 1...
    private int[] nizBS; // niz board setova  , indexi 0... za stolove 1...

    private int ukupnoParova;
    private int brojStolova;
    private boolean bye;
    private boolean odd;
    private int byeNumber;

    private int round;

    public Shifter(int ukupnoParova) {
        this.ukupnoParova = ukupnoParova;
        bye = false;
        odd = false;
        brojStolova = ukupnoParova / 2;
        if ((2 * brojStolova) < ukupnoParova) {
            bye = true;
            brojStolova++;
        }
        if ((brojStolova % 2) == 1)
            odd = true;

        nizNS = new int[brojStolova];
        nizEW = new int[brojStolova];
        nizBS = new int[brojStolova];
        if (bye)
            byeNumber = brojStolova;
        start();
    }

    private void start() {
        for (int i = 0; i < nizNS.length; i++) {
            nizNS[i] = i + 1;
        }
//        if(bye)
//            nizNS[nizNS.length - 1] = 0;
        for (int i = 0; i < nizEW.length; i++) {
            nizEW[i] = i + 1;
        }
        for (int i = 0; i < nizBS.length; i++) {
            nizBS[i] = i + 1;
        }

    }

    /**
     * rnd 0...
     */
    public void makeRound(int rnd) {
        start();
        round = rnd + 1;
        for (int i = 0; i < rnd; i++) {
            shiftLeft();
            shiftRight();
        }
        if (!odd)
            if (rnd > (brojStolova / 2)) {
                shiftLeft();
                shiftRight();
            }
    }

    /**
     * kolo 1....
     */
    public int[] getForRound(int round, int tbl) {
        makeRound(round - 1);
        int[] NSandEWandBS = new int[3];
        NSandEWandBS[0] = nizNS[tbl - 1];
        NSandEWandBS[1] = nizEW[tbl - 1];
        NSandEWandBS[2] = nizBS[tbl - 1];
        return NSandEWandBS;
    }

    private void shiftLeft() {
        int tmp = nizBS[0];
        for (int i = 0; i < nizBS.length - 1; i++) {
            nizBS[i] = nizBS[i + 1];
        }
        nizBS[nizBS.length - 1] = tmp;
    }

    private void shiftRight() {
        int tmp = nizEW[nizEW.length - 1];
        for (int i = nizEW.length - 1; i >= 1; i--) {
            nizEW[i] = nizEW[i - 1];
        }
        nizEW[0] = tmp;
    }

    public int getUkupnoParova() {
        return ukupnoParova;
    }

    public int getBrojStolova() {
        return brojStolova;
    }

    public boolean isOdd() {
        return odd;
    }

    public boolean isBye() {
        return bye;
    }

    public int getByeNumber() {
        return byeNumber;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("*********\nRound:" + round);
        sb.append("\nNS:");
        for (int i = 0; i < nizNS.length; i++) {
            if (bye && (i == nizNS.length - 1))
                break;
            else{
                if (i > 0)
                    sb.append(",");
                sb.append(nizNS[i]);
            }
        }
        sb.append("\nEW:");
        for (int i = 0; i < nizEW.length; i++) {
            if (bye && (i == nizEW.length - 1))
                sb.append("(");
            sb.append(nizEW[i]);
            if (bye && (i == nizEW.length - 1))
                sb.append(")");
            if (i < nizEW.length - 1)
                sb.append(",");
        }

        sb.append("\nBS:");
        for (int i = 0; i < nizBS.length; i++) {
            if (bye && (i == nizBS.length - 1))
                sb.append("(");
            sb.append(nizBS[i]);
            if (bye && (i == nizBS.length - 1))
                sb.append(")");
            if (i < nizBS.length - 1)
                sb.append(",");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Shifter shifter = new Shifter(15);
        for (int i = 0; i < 7; i++) {
            shifter.makeRound(i);
            System.out.println(shifter);
        }
    }
}
