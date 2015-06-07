package rs.logotet.bridge.model.scoring;

/**
 * Each team meets many others, one or two boards each,
 * and each board is scored by dividing 10 Victory Points
 * depending on the net point difference:
 * <p/>
 * Twelve Victory Points will be awarded
 * for each League match, divided in accordance
 * with the following scale:
 * <p/>
 * Total Difference in Match Points			Result in Victory Points
 * 0 - 3	6-6
 * 4- 11	7-5
 * 12 - 23	8-4
 * 24 - 35	9-3
 * 36 - 47	10-2
 * 48 - 59	11-1
 * 60 - over	12-0
 */
public class VP12Score {
    private int[] pointsdiff = {3, 11, 23, 35, 47, 59};

    private int impHome; // poeni home team-a
    private int impAway;  // poeni away team-a

    public VP12Score(int p1, int p2) {
        this.impHome = p1;
        this.impAway = p2;
    }

    public VP12Score(int p1) {
        this(p1, 0);
    }

    public int getHomeVP() {
        int razlika = impHome - impAway;
        int pt = calcPatton(Math.abs(razlika));
        return (impHome > impAway) ? (pt + 6) : (6 - pt);
    }

    public int getAwayVP() {
        return (12 - getHomeVP());
    }

    private int calcPatton(int razlika) {
        for (int j = 0; j < pointsdiff.length; j++) {
            if (razlika <= pointsdiff[j])
                return j;
        }
        return 6;
    }

    public static void main(String[] args) {
        for (int i = -80; i < 0; i++) {
            VP12Score is = new VP12Score(i);
            System.out.println(i + " -> " + is.getHomeVP() + ":" + is.getAwayVP());
        }
    }
}
