package rs.logotet.helper.parser;

import java.io.*;
import java.text.ParseException;

/**
 * klasa koja cita fajl radi parsiranja
 * dovoljno je dati joj pun nazivfajla i metodom readAndPares() joj proslediti parser i
 * sve lepo radi!!!
 */
public class ParsingReader {
    BufferedReader inFile;

    public ParsingReader(String fileName) throws FileNotFoundException {
        inFile = new BufferedReader(new FileReader(fileName));
    }

    public ParsingReader(String fileName, String encod) throws FileNotFoundException {
        try {
            inFile = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),encod));
        } catch (UnsupportedEncodingException e) {
            inFile = new BufferedReader(new FileReader(fileName));
        }
    }
      /*
    public ParsingReader(InputStream stream) {
        InputStreamReader in = null;
//        try {
//            in = new InputStreamReader(stream,"UTF-8");
        in = new InputStreamReader(stream);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
        inFile = new BufferedReader(in);
    }
    */
    public void readAndParse(Parser parser) throws ParseException {
        boolean petlja = true;
        while (petlja) {
            String linija = null;
            try {
                linija = inFile.readLine();
                if (linija != null) {
//                    System.out.println(linija);
                    parser.parse(linija);
                } else
                    petlja = false;
            } catch (NullPointerException e) {
                petlja = false;
            } catch (IOException e) {
                petlja = false;
            }
        }
        try {
            inFile.close();
        } catch (IOException e) {
        }
    }
}
