package rs.logotet.bridge.model.movement;

/**
 * Ideja iz ocne klinike:
 * klasa koja definise sve parametra jedne runde
 * ??? da li je ovo sinonim za Boardset, cini mi se da jeste i da Boardset treba da dobije ovu funkcionalnost
 */
public class MovementStep {
    private int north; // takm, broj north igraca
    private int south; // takm, broj south igraca
    private int east; // takm, broj east igraca
    private int west; // takm, broj west igraca
    private int round; // kolo
    private int tableNumber; // broj stola
    private int boardSet;// broj board seta

    public int getNorth() {
        return north;
    }

    public void setNorth(int north) {
        this.north = north;
    }

    public int getSouth() {
        return south;
    }

    public void setSouth(int south) {
        this.south = south;
    }

    public int getEast() {
        return east;
    }

    public void setEast(int east) {
        this.east = east;
    }

    public int getWest() {
        return west;
    }

    public void setWest(int west) {
        this.west = west;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getBoardSet() {
        return boardSet;
    }

    public void setBoardSet(int boardSet) {
        this.boardSet = boardSet;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("Round:" + round);
        sb.append("\nTable:" + tableNumber);
        sb.append("\nNorth:" + north);
        sb.append("\nSouth:" + south);
        sb.append("\nEast:" + east);
        sb.append("\nWest:" + west);
        sb.append("\nBoardSet:" + boardSet);
        sb.append("\n***********");
        return sb.toString();

    }

}
