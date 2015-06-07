package rs.logotet.bridge.model.movement;


/**
 * skup bordova koji se igraju za jednim stolom, u jednom kolu<br>
 * u celoj seansi svi skupovi imaju istu velicinu<br>
 * neki setovi se mogu deliti istovremeno za dva stola<br>
 * mora postojati kolaboracija sa Movement klasama
 * //@todo proveriti vezu izmedju Seanse, BoardSeta, Movementa, i Boarda
 * <br>
 */
public class BoardSet {
    private int round;
    private int tableNumber;
    private int pairNS;
    private int pairEW;


    private int setSize;
    private boolean shared; // ako ga istovremeno deli vise stolova
    private int setNumber;

    private boolean brokenSet; // znaci npr bordovi 1,2,3,22
    private int[] boardNumbers;// kada je brokenSet


    public BoardSet(int round, int tableNumber, int num, int pairNS, int pairEW) {
        setSize = 1;
        shared = false;
        this.round = round;
        this.tableNumber = tableNumber;
        this.setNumber = num;
        this.pairNS = pairNS;
        this.pairEW = pairEW;
        brokenSet = false;
    }

    public int getRound() {
        return round;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getPairNS() {
        return pairNS;
    }

    public int getPairEW() {
        return pairEW;
    }

    public int[] getBoardNumbers() {
        int[] tmp = new int[setSize];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = (setNumber - 1) * setSize + i + 1;
        }
        return tmp;
    }

    public void setSetSize(int setSize) {
        this.setSize = setSize;
    }

    public int getSetSize() {
        return setSize;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }


    public String toString() {
        StringBuffer sb = new StringBuffer(getTableNumber() + "\t");
        sb.append(getRound() + "\t");
        sb.append(getPairNS() + "-");
        sb.append(getPairEW() + "\t");
        if((getPairEW() < 10) && (getPairNS() < 10))
            sb.append("\t");
        sb.append(getSetNumber());
        return sb.toString();
    }
}
