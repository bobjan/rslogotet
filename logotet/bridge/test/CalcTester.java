package rs.logotet.bridge.test;

import rs.logotet.bridge.model.*;
import rs.logotet.helper.DBRecordException;
import rs.logotet.util.BJDatum;


/**
 * User: jankovicb
 * Date: Jul 26, 2007
 */
public class CalcTester {
    public static void main(String[] args) throws DBRecordException {
        odradiUspeh();
        odradiPadove();
    }

    public static void odradiPadove() {
        System.out.println("PADOVI");
        Seansa seansa = new Seansa(new Tournament(new BJDatum()));
        for (int brd = 1; brd < 17; brd++) {
            Board board = null;
            board = seansa.getBoard(brd);
            for (int denom = 1; denom < 8; denom++) {     // od 1 do 7 - nivo kontrakta
                for (int made = 0; made < denom + 6; made++) {  // odneti stihovi - padovi
                    for (int dbl = 0; dbl < 3; dbl++) { // sve varijante kontre
                        System.out.println(":" + Board.getVultext(brd));
                        for (int boja = 0; boja < 5; boja++) {
                            if (boja == 0)
                                continue;
                            if (boja == 2)
                                continue;
                            Contract kontrakt = new Contract(denom, boja, dbl);
                            UniqueContract deal = new UniqueContract(board, null, null, "S", kontrakt);
                            deal.setMade(made);
                            System.out.print(deal + "\t\t");
                            deal = new UniqueContract(board, null, null, "W", kontrakt);
                            deal.setMade(made);
                            System.out.println(deal);
                        }
                    }
                }
            }

        }


//        Contract k = new Contract(5, 3, 2);
//        System.out.println(k + " by S made 8 NS= " + ContractCalculator.getRezultatNS("S", k, 12, 3));
    }

    public static void odradiUspeh() {
        System.out.println("USPESI");
        Seansa seansa = new Seansa(new Tournament(new BJDatum()));
        for (int brd = 1; brd < 17; brd++) {
            Board board = seansa.getBoard(brd);
            for (int denom = 1; denom < 8; denom++) {     // od 1 do 7 - nivo kontrakta
                for (int made = denom + 6; made < 14; made++) {  // odneti stihovi - padovi
                    for (int dbl = 0; dbl < 3; dbl++) { // sve varijante kontre
                        System.out.println(":" + board.getVultext(brd));
                        for (int boja = 0; boja < 5; boja++) {
                            if (boja == 0)
                                continue;
                            if (boja == 2)
                                continue;
                            Contract kontrakt = new Contract(denom, boja, dbl);
                            UniqueContract deal = new UniqueContract(board, null,
                                    null, "S", kontrakt);
                            deal.setMade(made);
                            System.out.print(deal + "\t\t");
                            deal = new UniqueContract(board, null,
                                    null, "W", kontrakt);
                            deal.setMade(made);
                            System.out.println(deal);
                        }
                    }
                }
            }
        }
    }

//        Contract k = new Contract(5, 3, 2);
//        System.out.println(k + " by S made 8 NS= " + ContractCalculator.getRezultatNS("S", k, 12, 3));
}

