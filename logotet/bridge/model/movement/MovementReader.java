package rs.logotet.bridge.model.movement;

import rs.logotet.bridge.model.movement.*;
import rs.logotet.bridge.model.movement.BoardSet;
import rs.logotet.bridge.model.movement.FiledMovement;

import java.io.*;
import java.net.URL;
import java.util.StringTokenizer;

/**
 * User: jankovicb
 * Date: Apr 15, 2008
 */
public class MovementReader {
    private BufferedReader bufferedReader;
    private String urlBase;

    public MovementReader() {
        urlBase = "/com/logotet/bridge/";

    }

    public rs.logotet.bridge.model.movement.Movement getMovement(String name) throws IOException {
        rs.logotet.bridge.model.movement.FiledMovement mv = new FiledMovement();
        makeBufferedReader(name);
        try {
            while (true) {
                String red = bufferedReader.readLine();
                if (red == null)
                    break;
                if(red.length() == 0)
                    continue;
                if(red.substring(0,1).equals("#"))
                    continue;
                mv.addBoardSet(makeBridgeTable(red));
            }
        } catch (Exception e) {
        }
        return mv;
    }

    private rs.logotet.bridge.model.movement.BoardSet makeBridgeTable(String red) {
        StringTokenizer st = new StringTokenizer(red, " ");
        try {
            int tbl = Integer.parseInt(st.nextToken());
            int rnd = Integer.parseInt(st.nextToken());
            int ns = Integer.parseInt(st.nextToken());
            int ew = Integer.parseInt(st.nextToken());
            int bset = Integer.parseInt(st.nextToken());
            return new BoardSet(rnd, tbl, bset, ns, ew);
        } catch (Exception nfe) {
            return null;
        }
    }

    private void makeBufferedReader(String file) throws IOException {
        URL url = getClass().getResource(urlBase + file);
        InputStream is = url.openStream();
        Reader reader = new InputStreamReader(is);
        bufferedReader = new BufferedReader(reader);
    }
}
