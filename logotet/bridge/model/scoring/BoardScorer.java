package rs.logotet.bridge.model.scoring;

import rs.logotet.bridge.model.Board;
import rs.logotet.bridge.model.UniqueContract;
import rs.logotet.bridge.model.scoring.ScoreFrequency;
import rs.logotet.helper.DBRecordException;

import java.util.Enumeration;
import java.util.Vector;

/**
 * 
 */
public class BoardScorer {
    private Board board;
    private int topNS;
    private int bottomNS;
    private int totalScore;
    private int numberOfScores;

    private Vector ferquencyScores;

    public BoardScorer(Board board) {
        this.board = board;
        initAll();
    }

    private void initAll() {
        topNS = -20000;
        bottomNS = 20000;
        totalScore = 0;
        numberOfScores = 0;
        ferquencyScores = new Vector();
    }


    public void setBoard(Board board) {
        this.board = board;
    }

    private void addScoreNS(int score) {
        totalScore += score;
        numberOfScores++;
        if (score > topNS)
            topNS = score;
        if (score < bottomNS)
            score = bottomNS;
        updateFrequencyVector(score);
    }

    private void updateFrequencyVector(int score) {
        Enumeration enm = ferquencyScores.elements();
        while (enm.hasMoreElements()) {
            ScoreFrequency sf = (ScoreFrequency) enm.nextElement();
            if (sf.getScoreNS() == score) {
                try {
                    sf.addScoreNS(score);
                    return;
                } catch (DBRecordException e) {
                }
            }
        }
        ferquencyScores.add(new ScoreFrequency(score));
    }

    public double getAvrgNS() {
        if (numberOfScores > 0)
            return (double) totalScore / numberOfScores;
        else
            return 0.0;
    }

    public double getAvrgNS1() {
        if (numberOfScores > 2)
            return (double) (totalScore - topNS - bottomNS) / (numberOfScores - 2);
        else
            return getAvrgNS();
    }

    public int getTopNS() {
        return topNS;
    }

    public int getBottomNS() {
        return bottomNS;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getNumberOfScores() {
        return numberOfScores;
    }

    public void recalculate() {
        System.out.println("\nBoardScorer.recalculate()\n");
        Vector playedContracts = board.getPlayedContracts();
        Enumeration enm = playedContracts.elements();
        while (enm.hasMoreElements()) {
            UniqueContract obj = (UniqueContract) enm.nextElement();
            obj.annul();
            addScoreNS(obj.getContractResultNS());
        }

        for (int i = 0; i < playedContracts.size() - 1; i++) {
            for (int j = i + 1; j < playedContracts.size(); j++) {
                UniqueContract ci = (UniqueContract) playedContracts.elementAt(i);
                UniqueContract cj = (UniqueContract) playedContracts.elementAt(j);
                ci.crossImp(cj, true);
                if (ci.getContractResultNS() > cj.getContractResultNS())
                    ci.addMatchPointsNS(2);
                if (ci.getContractResultNS() < cj.getContractResultNS())
                    cj.addMatchPointsNS(2);
                if (ci.getContractResultNS() == cj.getContractResultNS()) {
                    cj.addMatchPointsNS(1);
                    ci.addMatchPointsNS(1);
                }
            }
        }
        int br = getNumberOfScores() * 2 - 2;
        enm = playedContracts.elements();
        while (enm.hasMoreElements()) {
            UniqueContract pc = (UniqueContract) enm.nextElement();
            pc.setMatchPointsEW(br - pc.getMatchPointsNS());
            System.out.print(pc.toString());
            System.out.print("\tboard " + pc.getBoard().getBrojborda() + "\t");
            System.out.print("MPs: NS = " + pc.getMatchPointsNS() + "  EW = " + pc.getMatchPointsEW());
            System.out.println("\tIMP: NS = " + pc.getCrossImpNS() + "  EW = " + pc.getCrossImpEW());
        }
    }
}
