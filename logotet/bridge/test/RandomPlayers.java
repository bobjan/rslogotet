package rs.logotet.bridge.test;

/**
 * User: jankovicb
 * Date: Oct 29, 2007
 */
public class RandomPlayers {
    private static String[] igrac = {"Aleksa", "Agaton", "Amfilohije", "Avakum",
                                     "Branko", "Bogdan", "Borivoje", "Berislav",
                                     "Vojislav", "Vladimir",
                                     "Gojko", "Gradimir",
                                     "Dejan", "Djordje", "Dragan", "Dushan", "Djuro",
                                     "Evgenije", "Eustahije",
                                     "Zoran", "Zharko", "Zvezdan",
                                     "Ilija", "Ivan", "Igor",
                                     "Janko", "Jovan", "Jevrem",
                                     "Kosta", "Krsta",
                                     "Lazar", "Ljubisa",
                                     "Milan", "Marko", "Milutin", "Martin", "Marinko", "Milojko",
                                     "Nebojsa", "Nenad", "Nemanja", "Naum",
                                     "Ostoja", "Obrad", "Obren",
                                     "Pavle", "Petar", "Predrag",
                                     "Rista", "Radenko", "Radivoje", "Radosav", "Radojica",
                                     "Stojan", "Stefan",
                                     "Tugomir", "Todor",
                                     "Uros", "Filip", "Hranislav",
                                     "Cvetko", "Chaslav", "Chedomir",
                                     "Shaban"};


    private static String[] pigrac = {"Acimovic", "Aleksic", "Adzic",
                                      "Brankovic", "Bogdanovic", "Boric", "Boskovic",
                                      "Vucic", "Vlajic",
                                      "Gojkovic", "Gladovic",
                                      "Dekic", "Djordjevc", "Dujkovic", "Dulic", "Djurovic",
                                      "Elezovic", "Erakovic",
                                      "Zoric", "Zharkovic", "Zaric",
                                      "Ilic", "Ivanovic", "Igic",
                                      "Jankovic", "Jovanovic", "Jevremovic",
                                      "Kostic", "Krstic",
                                      "Lazarevic", "Ljubic",
                                      "Milic", "Markovic", "Mikic", "Martinovic", "Marinkovic", "Milojkovic",
                                      "Nemanjic", "Nedic", "Novakovic", "Naumovic",
                                      "Ostojic", "Obradovic", "Obrenovic",
                                      "Pavlovic", "Petrovic", "Predic",
                                      "Ristanovic", "Radenkovic", "Radovic", "Rajic", "Rancic",
                                      "Stojanovic", "Stefanovic",
                                      "Trpkovic", "Todorovic",
                                      "Urosevic", "Filipovic", "Hajdukovic",
                                      "Cvetkovic", "Chavic", "Cherovic",
                                      "Shabanovic"};


    private static int[] niz;
    private static int[] pniz;

    public RandomPlayers() {
        niz = new int[igrac.length];
        pniz = new int[pigrac.length];
        for (int i = 0; i < niz.length; i++) {
            niz[i] = 0;
        }
        for (int i = 0; i < pniz.length; i++) {
            pniz[i] = 0;
        }
    }

    public String getRandomIme() {
        int broj;
        int counter = igrac.length * 2;
        while (counter > 0) {
            broj = (int) (Math.random() * (double) igrac.length);
            if (niz[broj] == 0) {
                niz[broj] = 1;
                return igrac[broj];
            }
        }
        return null;
    }

    public String getRandomPrezime() {
        int broj;
        int counter = pigrac.length * 2;
        while (counter > 0) {
            broj = (int) (Math.random() * (double) pigrac.length);
            if (pniz[broj] == 0) {
                pniz[broj] = 1;
                return pigrac[broj];
            }
        }
        return null;
    }

    public static void main(String[] args) {
        RandomPlayers rp = new RandomPlayers();
        for (int i = 0; i < 50; i++) {
            String ime = rp.getRandomIme();
            System.out.println(": " + ime + " " + rp.getRandomPrezime());
        }
    }

}
