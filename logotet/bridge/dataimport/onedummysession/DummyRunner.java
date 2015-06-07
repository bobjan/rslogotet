package rs.logotet.bridge.dataimport.onedummysession;

import rs.logotet.bridge.dataimport.onedummysession.*;
import rs.logotet.bridge.model.BridgeTeam;
import rs.logotet.bridge.model.Seansa;
import rs.logotet.bridge.model.Tournament;
import rs.logotet.helper.parser.ParsingReader;
import rs.logotet.util.BJDatum;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;

/**
 *
 * Date: May 13, 2009
 * Time: 12:04:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class DummyRunner {

    String igraci = "STANKOVIC Ognjen\tTOMIC Jovana" +
            "JURIŠIC Dušan\tKOSTIC Branislav" +
            "ARSOVIC Marijana\tMANIC Vladimir" +
            "BELICAJEVIC Vesna\tTOMIC Dragan" +
            "JANKOVIC Boban\tPOPOVIC Saša" + // mi smo bilo EW 3 ili NS 3
            "JURIŠIC Rada\tKLEBANSKY Sanja" +
            "TRIFUNOVIC Vojin\tŠUDIC Sabit" +
            "STEPIC Duško\tMITIC Milan" +
            "DRENJAKOVIC Nevena\tJOVANOVIC Aleksandar" +
            "ŠOVRAN Miroslav\tLESKOVAC Gordana" +
            "BLAGOJEVIC Nikola\tCVETKOVIC Sandra" +
            "ERIC Tatjana\tTOMIC Aleksandar" +
            "PEJOVIC Biserka\tPRICA Željko" +
            "PAVLOVIC Ljiljana\tPEŠKO Gordana" +
            "EKMECIC Jelena\tKOMAZEC Katja" +
            "SEDMAK Stojan\tCAVIC Edita" +
            "VALENT Branislav\tRAKOCEVIC Mihajlo" +
            "RISTIC Aleksandar\tDOSTANIC Ivan" +
            "BESAROVIC Ines\tSIMONOVIC Slobodan";


    private File dummyFile;
    private Seansa seansa;

    public DummyRunner(String fileName, BJDatum dat) {
        dummyFile = new File(fileName);
        seansa = new Seansa(new Tournament(dat));
        seansa.setBoardSetSize(3);
        popuniImena();

    }

    private void popuniImena() {
        BridgeTeam tim = new BridgeTeam();

    }

    public void odradi() {
        try {
            ParsingReader pr = new ParsingReader(dummyFile.getAbsolutePath());
            DummyListener dl = new DummyListener();
            rs.logotet.bridge.dataimport.onedummysession.DummyParser dp = new rs.logotet.bridge.dataimport.onedummysession.DummyParser();
            dl.setSeansa(seansa);
            dp.setListener(dl);
            pr.readAndParse(dp);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        DummyRunner dr = new DummyRunner("F://Bridge/Data/Dummy27-04.txt", new BJDatum());
        dr.odradi();
    }
}
