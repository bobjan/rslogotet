package rs.logotet.bridge.test;

import rs.logotet.bridge.model.*;
import rs.logotet.bridge.model.movement.BoardSet;
import rs.logotet.bridge.model.movement.Movement;
import rs.logotet.bridge.model.movement.MovementFactory;
import rs.logotet.bridge.model.movement.MovementStep;
import rs.logotet.bridge.test.*;
import rs.logotet.helper.db.DBStartException;
import rs.logotet.util.BJDatum;

import java.io.IOException;

/**
 * User: jankovicb
 * Date: Jul 26, 2007
 */
public class MainTester {
    public static void main(String[] args) throws IOException, DBStartException {
        rs.logotet.bridge.test.StartDB.start();
        Tournament tur = new Tournament(new BJDatum());
        Seansa seansa = new Seansa(tur);
        BridgeTeam tim = new BridgeTeam();
        tim.add(new BridgePlayer("Aleksa", "Aleksic"));
        tim.add(new BridgePlayer("Bmfilohije", "Aleksic"));
        tim.setTakmicarskiBroj(1);
        seansa.add(tim);

        tim = new BridgeTeam();
        tim.add(new BridgePlayer("Cvetko", "Cvetkovic"));
        tim.add(new BridgePlayer("Cezar", "Cvetkovic"));
        tim.setTakmicarskiBroj(2);
        seansa.add(tim);

        tim = new BridgeTeam();
        tim.add(new BridgePlayer("Evgenije", "Eric"));
        tim.add(new BridgePlayer("Eustahije", "Eric"));
        tim.setTakmicarskiBroj(3);
        seansa.add(tim);

        tim = new BridgeTeam();
        tim.add(new BridgePlayer("Goran", "Gojkovic"));
        tim.add(new BridgePlayer("Gojko", "Gojkovic"));
        tim.setTakmicarskiBroj(4);
        seansa.add(tim);

        tim = new BridgeTeam();
        tim.add(new BridgePlayer("Igor", "Ivkovic"));
        tim.add(new BridgePlayer("Ivan", "Ivkovic"));
        tim.setTakmicarskiBroj(5);
        seansa.add(tim);

        tim = new BridgeTeam();
        tim.add(new BridgePlayer("Kosta", "Kostic"));
        tim.add(new BridgePlayer("Kresimir", "Kostic"));
        tim.setTakmicarskiBroj(6);
        seansa.add(tim);

        tim = new BridgeTeam();
        tim.add(new BridgePlayer("Milos", "Milosevic"));
        tim.add(new BridgePlayer("Milan", "Miosevic"));
        tim.setTakmicarskiBroj(7);
        seansa.add(tim);



        tim = new BridgeTeam();
        tim.add(new BridgePlayer("Branimir", "Bogdanovic"));
        tim.add(new BridgePlayer("Bogdan", "Bogdanovic"));
        tim.setTakmicarskiBroj(8);
        seansa.add(tim);

        tim = new BridgeTeam();
        tim.add(new BridgePlayer("Dragan", "Dudic"));
        tim.add(new BridgePlayer("Dusan", "Dudic"));
        tim.setTakmicarskiBroj(9);
        seansa.add(tim);

        tim = new BridgeTeam();
        tim.add(new BridgePlayer("Faruk", "Filipovic"));
        tim.add(new BridgePlayer("Filip", "Filipovic"));
        tim.setTakmicarskiBroj(10);
        seansa.add(tim);

        tim = new BridgeTeam();
        tim.add(new BridgePlayer("Hranislav", "Hasanagic"));
        tim.add(new BridgePlayer("Hasan", "Hasanagic"));
        tim.setTakmicarskiBroj(11);
        seansa.add(tim);

        tim = new BridgeTeam();
        tim.add(new BridgePlayer("Jovan", "Jankovic"));
        tim.add(new BridgePlayer("Janko", "Jankovic"));
        tim.setTakmicarskiBroj(12);
        seansa.add(tim);

        tim = new BridgeTeam();
        tim.add(new BridgePlayer("Lav", "Lazarevic"));
        tim.add(new BridgePlayer("Lazar", "Lazarevic"));
        tim.setTakmicarskiBroj(13);
        seansa.add(tim);

        tim = new BridgeTeam();
        tim.add(new BridgePlayer("Nebojsa", "Nenadovic"));
        tim.add(new BridgePlayer("Nenad", "Nenadovic"));
        tim.setTakmicarskiBroj(14);
        seansa.add(tim);



        Movement mov = MovementFactory.getMovement(1, 6, 5, 3);
        mov = MovementFactory.getMovement("h7Reversed.txt");
        int brojKola = mov.getRounds();
        int brojStolova = mov.getBrojStolova();
        int boardSetSize = 2;
        for (int kolo = 1; kolo <= brojKola; kolo++) {
            for (int sto = 1; sto <= brojStolova; sto++) {
                BoardSet bset = mov.getBoardSet(kolo, sto);
                bset.setSetSize(boardSetSize);
                int[] brdnums = bset.getBoardNumbers();
                for (int i = 0; i < brdnums.length; i++) {
                    int brdnum = brdnums[i];
                    Board board = seansa.getBoard(brdnum);
//                    bset.addBoard(board);
//                    Board board = new Board(seansa,rbr + boardSetSize *(bset.getBoardSetSize() - 1) );
                    Contract k = Contract.getRandom();
                    UniqueContract pc;
                    pc = new UniqueContract(board,
                            seansa.getRegisteredTeam(bset.getPairNS()),             // section umesto seansa
                            seansa.getRegisteredTeam(bset.getPairEW()),
                            "S", k);
                    int made = (int) (Math.random() * 8.0);
                    made += 6;
                    pc.setMade(made);
                    System.out.println(pc.toString());
                }
            }
        }

    }

}
