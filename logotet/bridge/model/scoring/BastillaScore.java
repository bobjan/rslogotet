package rs.logotet.bridge.model.scoring;

/**
 * User: jankovicb
 * Date: Mar 31, 2008
 * <p/>
 * The formulas are :
 * <p/>
 * from	   0	 to   45	 : IMP =  score     	/	 30
 * from	  45	 to  165	 : IMP = (score +   15)	/	 40
 * from	 165	 to  365	 : IMP = (score +   60)	/	 50
 * from	 365	 to  425	 : IMP = (score +  145)	/	 60
 * from	 425	 to  495	 : IMP = (score +  240)	/	 70
 * from	 495	 to  595	 : IMP = (score +  555)	/	100
 * from	 595	 to  895	 : IMP = (score + 1130)	/	150
 * from	 895	 to 1495	 : IMP = (score + 1805)	/	200
 * from	1495	 to 2495	 : IMP = (score + 2630)	/	250
 * over	2495			 : IMP = (score + 7755)	/	500
 */
public class BastillaScore {
    private int[] diff = {45, 165, 365, 425, 495, 595, 895, 1495, 2495};
    private int[] add = {0, 15, 60, 145, 240, 555, 1130, 1805, 2630};
    private int[] divisor = {30, 40, 50, 60, 70, 100, 150, 200, 250};

    private int points1;
    private int points2;

    public BastillaScore(int p1, int p2) {
        this.points1 = p1;
        this.points2 = p2;
    }

    public BastillaScore(int p1) {
        this(p1, 0);
    }

    public double getBatillaPoints() {
        int razlika = points1 - points2;
        double imp = calcBastilla(Math.abs(razlika));
        return (points1 > points2) ? imp : (-1) * imp;
    }

    private double calcBastilla(int razlika) {
        for (int j = 0; j < diff.length; j++) {
            if (razlika <= diff[j]) {
                return (razlika + (double) add[j]) / (double) divisor[j];
            }
        }
        return (razlika + 7755.0) / 500.0;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 4600; i += 10) {
            BastillaScore is = new BastillaScore(i);
            System.out.println(i + " -> " + is.getBatillaPoints());
        }
    }
}
