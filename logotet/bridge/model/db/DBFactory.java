package rs.logotet.bridge.model.db;

import rs.logotet.bridge.model.db.*;
import rs.logotet.bridge.model.db.KlubDB;
import rs.logotet.bridge.model.db.PlayerDB;
import rs.logotet.bridge.model.db.RatingDB;
import rs.logotet.bridge.model.db.SavezDB;
import rs.logotet.bridge.model.db.SesijaDB;
import rs.logotet.bridge.model.db.SezonaDB;
import rs.logotet.bridge.model.db.TeamDB;
import rs.logotet.bridge.model.db.TurnirDB;
import rs.logotet.helper.db.ConnectionPoolManager;

import java.sql.SQLException;


public class DBFactory {
    private static ConnectionPoolManager poolManager = null;

    public static final int BOARD = 1;
    public static final int CLANSTVO = 2;
    public static final int CONTRACT = 3;
    public static final int KLUB = 5;
    public static final int PLASMAN = 6;
    public static final int PLAYER = 7;
    public static final int RATING = 8;
    public static final int SAVEZ = 9;
    public static final int SESIJA = 10;
    public static final int SECTION = 11;
    public static final int SEZONA = 12;
    public static final int TEAM = 13;
    public static final int TEAMMEMBER = 14;
    public static final int TURNIR = 15;
    public static final int USERTRANS = 16;
    public static final int MOVEMENT = 17;
    public static final int TEAMMATCH = 18;
    public static final int GENERALAC = 19;
    public static final int RATINGHISTORY = 20;

    public static void initFactory(ConnectionPoolManager cpm) {
        poolManager = cpm;
    }

    public static Object getDBClass(int tip) throws SQLException {
        if (poolManager == null)
            throw new SQLException("DBFactory:Konekcija sa bazom ne postoji");
        switch (tip) {
            case BOARD:
                return new rs.logotet.bridge.model.db.BoardDB(poolManager);
            case CLANSTVO:
                return new ClanstvoDB(poolManager);
            case CONTRACT:
                return new ContractDB(poolManager);
            case KLUB:
                return new KlubDB(poolManager);
            case PLASMAN:
                return new PlasmanDB(poolManager);
            case PLAYER:
                return new PlayerDB(poolManager);
            case RATING:
                return new RatingDB(poolManager);
            case SAVEZ:
                return new SavezDB(poolManager);
            case SESIJA:
                return new SesijaDB(poolManager);
            case SEZONA:
                return new SezonaDB(poolManager);
            case TEAM:
                return new TeamDB(poolManager);
            case TEAMMEMBER:
                return new TeammemberDB(poolManager);
            case TURNIR:
                return new TurnirDB(poolManager);
            case USERTRANS:
                return new UsertransDB(poolManager);
            case MOVEMENT:
                return new MovementDB(poolManager);
            case GENERALAC:
                return new GeneralacDB(poolManager);
            case TEAMMATCH:
                return new TeammatchDB(poolManager);
            case RATINGHISTORY:
                return new RatinghistoryDB(poolManager);
            default:
                return null;
        }
    }

}