package rs.logotet.bridge.model.masterpoeni;

import rs.logotet.bridge.model.masterpoeni.*;

/**
 * User: jankovicb
 * Date: Sep 19, 2007
 */
public class MasterCalculator {
    private double koeficijentKola;
    private double prosekSale;
    double koeficijentPlasmana;
    int zona;
    rs.logotet.bridge.model.masterpoeni.StubObject objekat = new rs.logotet.bridge.model.masterpoeni.StubObject();

    public double getMP() {
        koeficijentKola = objekat.getKoeficijentKola(1);
        zona = objekat.getZona(1);
        koeficijentPlasmana = objekat.getKoeficijentPlasmana(zona, 1);
        prosekSale = objekat.getProsekSale();
        return prosekSale * koeficijentKola * koeficijentPlasmana * 0.7;
    }

    public static void main(String[] args) {
        MasterCalculator mc = new MasterCalculator();
        System.out.println("" + mc.getMP());
    }
}
