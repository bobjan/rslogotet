package rs.logotet.bridge.model.scoring;

import rs.logotet.helper.DBRecordException;

/**
 *
 */
public class ScoreFrequency {
    private int scoreNS;
    private int scoreEW;
    private int count;

    private int matchPointsNS;
    private int matchPointsEW;

    private int impNS;
    private int impEW;

    public ScoreFrequency(int scoreNS) {
        this.scoreNS = scoreNS;
        scoreEW = (-1) * scoreNS;
        count = 1;
    }

    // getters ...
    public int getScoreNS() {
        return scoreNS;
    }

    public int getScoreEW() {
        return scoreEW;
    }

    public int getCount() {
        return count;
    }

    public int getMatchPointsNS() {
        return matchPointsNS;
    }

    public int getMatchPointsEW() {
        return matchPointsEW;
    }

    public int getImpNS() {
        return impNS;
    }

    public int getImpEW() {
        return impEW;
    }

    // getters ... to here
    public void addScoreNS(int val) throws DBRecordException {
        if (val != scoreNS)
            throw new DBRecordException("ScoreFrequency error");
        count++;
    }
}
