package rs.logotet.bridge.model.scoring;

/**
 * User: jankovicb
 * Date: Mar 31, 2008
 */
public class IMPScore {
    private int[] imp = {10, 40, 80, 120, 160, 210, 260, 310, 360, 420, 490, 590, 740, 890, 1090, 1290,
                         1490, 1740, 1990, 2240, 2490, 2990, 3490, 3990};

    private int points1;
    private int points2;

    public IMPScore(int p1, int p2) {
        this.points1 = p1;
        this.points2 = p2;
    }

    public IMPScore(int p1) {
        this(p1, 0);
    }

    public int getImp() {
        int razlika = points1 - points2;
        int imp = calcImp(Math.abs(razlika));
        return (points1 > points2) ? imp : (-1) * imp;
    }

    private int calcImp(int razlika) {
        for (int j = 0; j < imp.length; j++) {
            if (razlika <= imp[j])
                return j;
        }
        return 24;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 4600; i += 10) {
            IMPScore is = new IMPScore(i);
            System.out.println(i + " -> " + is.getImp());

        }
    }

}
