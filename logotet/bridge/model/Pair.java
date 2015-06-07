package rs.logotet.bridge.model;

import rs.logotet.bridge.model.BridgePlayer;

/**
 * klasa nema svoj record u bazi
 */
public class Pair {
    private BridgePlayer firstPlayer;
    private BridgePlayer secondPlayer;

    public Pair(BridgePlayer firstPlayer, BridgePlayer secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public Pair(BridgePlayer[] niz) {
        this(niz[0],niz[1]);
    }

    public BridgePlayer getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(BridgePlayer firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public BridgePlayer getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(BridgePlayer secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public String getPairName(){
        return firstPlayer.getFullName() + " - " + firstPlayer.getFullName();
    }
    public String getHashKey(){
        int low = firstPlayer.getPlayerid();
        int up = secondPlayer.getPlayerid();
        if(low < up)
            return low + "-" + up;
        return up + "-" + low;
    }
}
