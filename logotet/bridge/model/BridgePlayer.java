package rs.logotet.bridge.model;

import rs.logotet.bridge.base.Bridge;
import rs.logotet.bridge.model.*;
import rs.logotet.bridge.model.db.PlayerDB;
import rs.logotet.bridge.model.dbrecord.PlayerRecord;
import rs.logotet.bridge.model.dbrecord.TeammemberRecord;
import rs.logotet.helper.DBRecordException;

/**
 * User: jankovicb
 * Date: Jul 26, 2007
 */
public class BridgePlayer {
    PlayerRecord playerRecord = null;
    TeammemberRecord teammemberRecord = null;
    private rs.logotet.bridge.model.BridgeTeam memberOf;


    public BridgePlayer(String ime, String prezime) {
        memberOf = null;
        playerRecord = new PlayerRecord();
        PlayerDB playerDB = (PlayerDB) playerRecord.getDBClass();
        playerRecord = playerDB.findBy(ime, prezime);
        if (playerRecord == null) {
            playerRecord = new PlayerRecord();
            playerRecord.setIme(ime);
            playerRecord.setPrezime(prezime);
            playerRecord.setRating(Bridge.AVERAGE);
        }
    }

    public BridgePlayer(PlayerRecord playerRecord) {
        this.playerRecord = playerRecord;
        memberOf = null;
    }

    public BridgePlayer(TeammemberRecord teammemberRecord) {
        this(new PlayerRecord(teammemberRecord.getPlayerid()));
    }


    public int getPlayerid() {
        return playerRecord.getPlayerid();
    }

    public void setPlayerid(int playerid) {
        playerRecord.setPlayerid(playerid);
    }

    public String getIme() {
        return playerRecord.getIme();
    }

    public void setIme(String ime) {
        playerRecord.setIme(ime);
    }

    public String getPrezime() {
        return playerRecord.getPrezime();
    }

    public void setPrezime(String prezime) {
        playerRecord.setPrezime(prezime);
    }

    public rs.logotet.bridge.model.BridgeTeam getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(rs.logotet.bridge.model.BridgeTeam memberOf) {
        this.memberOf = memberOf;
    }

    public double getRating() {
        return playerRecord.getRating();
    }

    public void setRating(double rating) {
        playerRecord.setRating(rating);
    }

    public void makePersistent() {
        try {
            playerRecord.makePersistent();
        } catch (DBRecordException e) {
        }
    }

    public void addPlayedContract(UniqueContract uniqueContract) {
        memberOf.addPlayedContract(uniqueContract);
    }
    public String getFullName(){
        return getPrezime() + " " + getIme();
    }
}