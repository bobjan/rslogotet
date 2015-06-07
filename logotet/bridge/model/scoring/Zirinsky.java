package rs.logotet.bridge.model.scoring;

/**
 * This is a method of determining the victory points long
 * used in Far East Championships. All boards with zero IMPs
 * are scored as one to each team. The winning score is
 * then multiplied by the number four and divided by the
 * losing score. There is a maximum of 8 points possible.
 * The losing team receives the balance of the 8 points.
 * This formula or provision was introduced by
 * Mr. Victor Zirinsky of Hong Kong as a modification
 * to the original concept which gave inequitable
 * results in low-scoring matches.
 * The so-called push boards,
 * with zero IMPs, are scored as one to each team.
 * Then the winning score is multiplied by four and
 * divided by the losing score, with a maximum of eight VPs.
 * The losing Team receives the balance of the eight points at
 * stake. Mr. Victor Zirinsky introduced this method as a modification
 * to the original concept which provided inadequate results in
 * low-scoring matches.
 */
public class Zirinsky {
    private int koeficijent; // default 4 za forumulu koja deli 8 poena

    private int pushes;
    private int impHome;
    private int impAway;

    public Zirinsky(int impHome, int impAway, int pushes) {
        this(impHome, impAway, pushes, 4);
    }


    public Zirinsky(int impHome, int impAway, int pushes, int koeficijent) {
        this.impHome = impHome;
        this.impAway = impAway;
        this.koeficijent = koeficijent;
        this.pushes = pushes;
    }

    public double[] getVictroyPoints() {
        double[] tmp = new double[2];
        double winner = getWinnerPoints();
        tmp[0] = (impHome >= impAway) ? winner : (koeficijent * 2 - winner);
        tmp[1] = (koeficijent * 2 - tmp[0]);
        return tmp;
    }

    private double getWinnerPoints() {
        int winner = (impHome >= impAway) ? impHome : impAway;
        int loser = (impHome >= impAway) ? impAway : impHome;
        return (double) koeficijent * (double) (winner + pushes) / (double) (loser + pushes);
    }

    /**
     * nesto ne valja
     */
    public static void main(String[] args) {
        for (int i = 0; i < 80; i++) {
            Zirinsky zir = new Zirinsky(i * 2, i, 6, 6);
            double[] zirp = zir.getVictroyPoints();
            System.out.println(i + ". " + zirp[0] + "   " + zirp[1]);


        }
    }

}
