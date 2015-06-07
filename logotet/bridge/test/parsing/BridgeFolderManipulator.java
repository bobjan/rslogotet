package rs.logotet.bridge.test.parsing;

import rs.logotet.bridge.dataimport.ClubResultListener;
import rs.logotet.helper.parser.Parser;
import rs.logotet.helper.parser.ParsingReader;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.text.ParseException;

/**
 * parsira sve razultatete u datom folderu
 */
public class BridgeFolderManipulator {
    private File folder;

    public BridgeFolderManipulator(String folderName, Parser rp) {
        folder = new File(folderName);
    }

    public boolean isOK() {
        return folder.isDirectory();
    }

    public void manipulate(Parser mp) {
        File[] lista = folder.listFiles(new TxtFilter());
        for (int i = 0; i < lista.length; i++) {
            File file = lista[i];
            ClubResultListener bl = new ClubResultListener(file.getAbsolutePath());
            mp.setListener(bl);
            try {
                System.out.println(file.getAbsolutePath());
                ParsingReader mdr = new ParsingReader(file.getAbsolutePath());
                mdr.readAndParse(mp);
            } catch (FileNotFoundException e) {
            } catch (ParseException e) {

            }
        }
    }

    class TxtFilter implements FileFilter {
        public boolean accept(File pathname) {
            String ime = pathname.getName();
            return imaGodinu(ime);
        }

        /**
         * svi fajlovi su tipa xxxddmmyyyy.txt za godine 2008 - 2011
         */
        private boolean imaGodinu(String ime) {
            for (int i = 2008; i < 2012; i++) {
                int pos = ime.indexOf(i + ".txt");
                if (pos > 0)
                    return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        BridgeFolderManipulator fm = new BridgeFolderManipulator("E://Transfers/AS", null);
        if (fm.isOK())
            fm.manipulate(null);
        else
            System.out.println("NIJE folder");
    }
}
