package rs.logotet.bridge.model.masterpoeni;

/**
 * Zvanicna rejting formula BSS
 */
public class StubObject {

    public double getKoeficijentKola(int vrstaObracuna) {
        switch (vrstaObracuna) {
            case 0: //parski obracun
                return 1.66;
            case 1: // butler obracun
                return 1.00;


                //timski turnir sa mecevima do 10 bordova
            case 2: // do 3 kola
                return 0.889;
            case 3: //  4 kola
                return 0.8;
            case 4: // 5 kola
                return 0.75;
            default: // 6+ kola
                return 0.72;
        }
    }

    /**
     * broj igraca koji dobijaju MP
     */
    public int getZona(int i) {
        double brojIgraca = getBrojIgraca();
        if (i == 0)// parski turnir
            return (int) Math.round(0.4999 + brojIgraca * 0.25);
        else
        // timski turnir
            return (int) Math.round(0.4999 + brojIgraca * 0.33);
    }

    /**
     * ukupan broj ucesnika turnira
     */
    private double getBrojIgraca() {
        return 16;
    }

    public double getKoeficijentPlasmana(int zona, int plasman) {
        if (plasman == 1) {
            if (zona <= 10)
                return 1.00;
            else {
                return 0.5 + zona * (zona + 9.0) / 200.0;
            }
        } else {
            return 0.1 + (zona - plasman) * (zona - plasman + 11.00) / 200.00;
        }


    }

    public double getProsekSale() {
        return 6.00;
    }
}
