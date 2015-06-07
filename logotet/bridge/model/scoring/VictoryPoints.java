package rs.logotet.bridge.model.scoring;

import java.util.StringTokenizer;

/**
 * User: jankovicb
 * Date: Oct 2, 2007
 */
public class VictoryPoints {
    private static String[] impDiff = {"0-1", "0-1", "0-1", "0-2", "0-2", "0-2", "0-3", "0-3", "0-3", "0-3", "0-3", "0-4", //15-15
                                       "2-5", "2-6", "2-6", "3-7", "3-7", "3-8", "4-9", "4-10", "4-10", "4-11", "4-11", "5-12", //16-14
                                       "6-8", "7-9", "7-9", "8-10", "8-11", "9-12", "10-14", "11-15", "11-16", "12-17", "12-18", "13-20", //17-13
                                       "9-11", "10-12", "10-12", "11-14", "12-15", "13-16", "15-19", "16-20", "17-22", "18-23", "19-25", "21-28", //18-12
                                       "12-14", "13-15", "13-16", "15-18", "16-19", "17-21", "20-24", "21-25", "23-28", "24-29", "26-32", "29-36", //19-11
                                       "15-17", "16-18", "17-20", "19-22", "20-23", "22-16", "25-29", "26-31", "29-34", "30-36", "33-39", "37-44", //20-10
                                       "18-20", "19-21", "21-24", "23-26", "24-27", "27-31", "30-34", "32-37", "25-40", "37-43", "40-46", "45-52", //21-9
                                       "21-23", "22-25", "25-28", "27-30", "28-31", "32-36", "35-39", "28-43", "41-46", "44-50", "47-53", "53-60", //22-8
                                       "24-26", "26-29", "29-32", "31-34", "32-36", "67-41", "40-45", "44-49", "47-52", "51-57", "54-60", "61-68", //23-7
                                       "27-29", "30-33", "33-36", "35-38", "37-41", "42-47", "46-51", "50-55", "53-58", "58-64", "61-68", "69-76", //24-6
                                       "30-33", "34-37", "37-40", "39-43", "42-46", "48-53", "52-57", "56-61", "59-65", "65-71", "69-76", "77-84", //25-5
                                       "34-37", "38-41", "41-45", "44-48", "47-52", "54-59", "58-64", "62-68", "66-73", "72-79", "77-84", "85-93", //25-4
                                       "38-41", "42-45", "46-50", "49-54", "53-58", "60-65", "65-71", "69-73", "74-82", "80-88", "85-93", "94-102", //25-3
                                       "42-45", "46-50", "51-55", "55-60", "59-64", "66-72", "72-79", "77-85", "83-91", "89-97", "94-102", "103-112", //25-2
                                       "46-50", "51-55", "56-61", "61-66", "65-71", "73-79", "80-87", "86-94", "92-100", "98-106", "103-112", "113-123", //25-1
                                       "51+", "56+", "62+", "67+", "72+", "80+", "88+", "95+", "101+", "107+", "113+", "124+" //25-0
    };

    private int impHome;
    private int impAway;
    private int numOfBoards;

    public VictoryPoints(int impHome, int impAway, int numOfBoards) {
        this.impHome = impHome;
        this.impAway = impAway;
        this.numOfBoards = numOfBoards;
    }

  /**
   * vreca niz od dva integera
   * [0] - VP za homeTeam
   * [1] - VP za awayTeam
   *
   * */
    public int[] getVictroyPoints() {
        int[] absV = getAbsVictoryPoints();
        if (impHome > impAway)
            return absV;
        int tmp = absV[0];
        absV[0] = absV[1];
        absV[1] = tmp;
        return absV;
    }

    private int[] getAbsVictoryPoints() {
        int diff = Math.abs(impHome - impAway);
        String[] boardVector = getVertikala();

        for (int i = 0; i < boardVector.length; i++) {
            String s = boardVector[i];
            StringTokenizer st = new StringTokenizer(s, "-");
            int pozplus = s.indexOf("+");
            int prvi;
            int drugi;
            if (pozplus > 0) {
                prvi = (Integer.parseInt(s.substring(0, pozplus)));
                drugi = Integer.MAX_VALUE;
            } else {
                prvi = Integer.parseInt(st.nextToken());
                drugi = Integer.parseInt(st.nextToken());
            }
            if (prvi > diff)
                continue;
            if (drugi < diff)
                continue;
            return getRezPoints(i);

        }

        int[] rez = new int[2];
        rez[0] = 25;
        rez[1] = 0;
        return rez;
    }

    private int[] getRezPoints(int i) {
        int[] rez = new int[2];
        if (i < 11) {
            rez[0] = 15 + i;
            rez[1] = 30 - rez[0];
            return rez;
        }
        rez[0] = 25;
        rez[1] = 15 - i;
        return rez;
    }

    private String[] getVertikala() {
        String[] kol = new String[16];
        int kolIndex = getIndeks();
        for (int j = 0; j < 16; j++) {
            kol[j] = impDiff[j * 12 + kolIndex];
        }
        return kol;
    }


// {8, 10, 12, 14, 16, 20, 24, 28, 32, 36, 40, 48};
    private int getIndeks() {
        if (numOfBoards < 20) {
            int i = (numOfBoards - 8) / 2;
            if (i < 0)
                i = 0;
            return i;
        }
        if (numOfBoards < 48) {
            int i = numOfBoards / 4;
            i += 5;
            if (i > 10)
                i = 10;
            return i;
        }
        return 11;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 100; i++) {
            for (int j = 12; j < 36; j += 4) {
                VictoryPoints vp = new VictoryPoints(i, 0, j);
                int[] rez = vp.getVictroyPoints();
                System.out.println(i + "-" + j + "\t:\t" + rez[0] + "-" + rez[1]);
            }
        }
    }
}
