package rs.logotet.bridge.model.scoring;

import rs.logotet.bridge.model.Board;
import rs.logotet.bridge.base.Bridge;
import rs.logotet.bridge.model.Contract;
import rs.logotet.bridge.base.Bridge;
import rs.logotet.bridge.model.scoring.ContractCalculator;

/**
 * klasa koja proverava validnost nekog rezultata
 */
public class Scorer {
    private int vul;   // zona odredjenog para
// proverava se validnost rezltata za par koji je navedene vulnerabiluty
    public Scorer(int boardnum) {
        this.vul = Board.getVulnerability(boardnum);
    }

    /**
     * score - rezulta koji je postigao receni par; a ako je u minusu onda se
     * proverava obrnut
     */
    public boolean isValid(int side, int score) {
        if (score == 0)
            return true;
        if (score < 0) {
            if (side == Bridge.NORTH_SOUTH)
                return isValid(Bridge.EAST_WEST, Math.abs(score));
            else
                return isValid(Bridge.NORTH_SOUTH, Math.abs(score));
        }

        if (isValidContract(side, score)) {
            System.out.print("! ");
            return true;
        }
        if (side == Bridge.NORTH_SOUTH) {
            return isValidDown(Bridge.EAST_WEST, score);
        }
        if (side == Bridge.EAST_WEST) {
            return isValidDown(Bridge.NORTH_SOUTH, score);
        }
        System.out.print("??? ");
        return false;
    }

    private boolean isValidDown(int side, int score) {
        boolean testedVul = false;
        if (side == Bridge.NORTH_SOUTH)
            testedVul = ((vul == Bridge.ALL) || (vul == Bridge.NS));
        if (side == Bridge.EAST_WEST)
            testedVul = ((vul == Bridge.ALL) || (vul == Bridge.EW));
        for (int denom = 1; denom < 8; denom++) {     // od 1 do 7 - nivo kontrakta
            for (int made = 0; made < denom + 6; made++) {  // odneti stihovi
                for (int dbl = 0; dbl < 3; dbl++) { // sve varijante kontre
                    for (int boja = 0; boja < 5; boja++) {
                        if (boja == 0)
                            continue;
                        if (boja == 2)
                            continue;
                        Contract tmpContract = new Contract(denom, boja, dbl);
                        int rez = ContractCalculator.getRezultat(tmpContract, made, testedVul);
                        if (rez == (-1) * score) {
                            System.out.print("# ");
                            return true;
                        }
                    }
                }
            }
        }
//        System.out.print("&&& ");
        return false;
    }

    private boolean isValidContract(int side, int score) {
        boolean testedVul = false;
        if (side == Bridge.NORTH_SOUTH)
            testedVul = ((vul == Bridge.ALL) || (vul == Bridge.NS));
        if (side == Bridge.EAST_WEST)
            testedVul = ((vul == Bridge.ALL) || (vul == Bridge.EW));
        for (int denom = 1; denom < 8; denom++) {     // od 1 do 7 - nivo kontrakta
            for (int made = denom; made < 14; made++) {  // odneti stihovi
                for (int dbl = 0; dbl < 3; dbl++) { // sve varijante kontre
                    for (int boja = 0; boja < 5; boja++) {
                        if (boja == 0)
                            continue;
                        if (boja == 2)
                            continue;
                        Contract tmpContract = new Contract(denom, boja, dbl);
                        int rez = ContractCalculator.getRezultat(tmpContract, made, testedVul);
                        if (rez == score)
                            return true;
                    }
                }
            }
        }
        return false;
    }
//  @todo jos istestirati, ali izgleda da radi OK
    public static void main(String[] args) {
        int bn = 2;
        Scorer sc = new Scorer(bn);
        System.out.println("--> " + Board.getVultext(bn));
        for (int i = 50; i < 16000; i += 10) {
            if (sc.isValid(Bridge.NORTH_SOUTH, i))
                System.out.println(i + ": true");
//            System.out.println(i + ": " + sc.isValid(Bridge.NORTH_SOUTH, i));
        }
    }
}
