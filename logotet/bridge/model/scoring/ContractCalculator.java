package rs.logotet.bridge.model.scoring;

import rs.logotet.bridge.base.Bridge;
import rs.logotet.bridge.model.Contract;
import rs.logotet.bridge.base.Bridge;

/**
 * User: jankovicb
 * Date: Jul 26, 2007
 */
public class ContractCalculator {
    /**
     * returns score for NS line
     */
    public static int getRezultat(Contract kontrakt, int made, boolean uZoni) {
        int baseValue = 0;
        int downtricks = 0;
        int overtricks = 0;

        int uspeh = made - kontrakt.getDenomination() - 6;

        if (uspeh < 0)
            downtricks = Math.abs(uspeh);
        else {
            overtricks = uspeh;
        }
        int overValue = 0;
        int downValue = 0;

        if (downtricks > 0) {
            downValue = calculatePad(uZoni, downtricks, kontrakt.getDbl());
            return downValue * (-1);
        } else {
            baseValue = calculateBaseValue(kontrakt, uZoni);
            overValue = getOvertrikcsValue(uZoni, overtricks, kontrakt);
            return (baseValue + overValue);
        }

    }

    private static int getOvertrikcsValue(boolean uZoni, int overtricks, Contract kontrakt) {
        if (overtricks == 0)
            return 0;
        switch (kontrakt.getDbl()) {
            case 0:
                if (kontrakt.isMinor())
                    return overtricks * 20;
                else
                    return overtricks * 30;
            case 1:
                if (uZoni)
                    return overtricks * 200;
                else
                    return overtricks * 100;
            case 2:
                if (uZoni)
                    return overtricks * 400;
                else
                    return overtricks * 200;
            default:
                return 0;
        }

    }

    private static int calculateBaseValue(Contract kontrakt, boolean uZoni) {
        int bonus = 0;
        int vrednost = kontrakt.getContractValue();
        if (kontrakt.getDbl() == Bridge.DOUBLED)
            bonus = 50;
        if (kontrakt.getDbl() == Bridge.REDOUBLED)
            bonus = 100;
        if (uZoni) {
            if (vrednost < 100)
                bonus += 50;    // partscore
            else {
                bonus += 500;
            }
            if (kontrakt.isSmallSlam())
                bonus += 750;
            if (kontrakt.isGrandSlam())
                bonus += 1500;
        } else {
            if (vrednost < 100)
                bonus += 50;    // partscore
            else {
                bonus += 300;
            }
            if (kontrakt.isSmallSlam())
                bonus += 500;
            if (kontrakt.isGrandSlam())
                bonus += 1000;
        }

        return vrednost + bonus;
    }

    private static int calculatePad(boolean uZoni, int downtricks, int dbl) {
        switch (dbl) {
            case 0:
                if (uZoni)
                    return downtricks * 100;
                else
                    return downtricks * 50;
            case 1:
                return getDoubledPad(uZoni, downtricks);
            case 2:
                return 2 * getDoubledPad(uZoni, downtricks);
            default:
                return 0;
        }
    }

    private static int getDoubledPad(boolean uZoni, int downtricks) {
        if (uZoni) {
            if (downtricks == 1)
                return 200;
            return downtricks * 300 - 100;
        } else {
            if (downtricks == 1)
                return 100;
            if (downtricks < 4)
                return downtricks * 200 - 100;
            return downtricks * 300 - 400;

        }
    }
}
