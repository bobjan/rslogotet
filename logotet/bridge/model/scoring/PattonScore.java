package rs.logotet.bridge.model.scoring;

/**
 * Each team meets many others, one or two boards each,
 * and each board is scored by dividing 10 Victory Points
 * depending on the net point difference:
 * <p/>
 * VPs    Net point difference
 * 5-5    0-10
 * 6-4    20-100
 * 7-3    110-240
 * 8-2    250-490
 * 9-1    500-740
 * 10-0  750 or more
 */
public class PattonScore {
    private int[] pointsdiff = {10, 100, 240, 490, 740};

    private int points1; // poeni home team-a
    private int points2;  // poeni away team-a

    public PattonScore(int p1, int p2) {
        this.points1 = p1;
        this.points2 = p2;
    }

    public PattonScore(int p1) {
        this(p1, 0);
    }

    public int getHomePatton() {
        int razlika = points1 - points2;
        int pt = calcPatton(Math.abs(razlika));
        return (points1 > points2) ? (pt + 5) : (5 - pt);
    }

    public int getAwayPatton() {
        return (10 - getHomePatton());
    }

    private int calcPatton(int razlika) {
        for (int j = 0; j < pointsdiff.length; j++) {
            if (razlika <= pointsdiff[j])
                return j;
        }
        return 5;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 4600; i += 10) {
            PattonScore is = new PattonScore(i);
            System.out.println(i + " -> " + is.getHomePatton() + ":" + is.getAwayPatton());
        }
    }
}
