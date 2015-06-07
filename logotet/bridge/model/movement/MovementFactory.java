package rs.logotet.bridge.model.movement;

import rs.logotet.bridge.model.movement.*;
import rs.logotet.bridge.model.movement.Movement;
import rs.logotet.bridge.model.movement.MovementReader;

import java.io.IOException;

/**
 * Kreira klasu koja na osnovu tipa ili jos necega vraca klasu koja obradjuje movement
 */
public class MovementFactory {
    /**
     * tip - vrsta kretanja
     * teams - broj ucesnika
     * rounds - broj kola
     * brojStolova - broj stolova
     * <p/>
     * - napomena:
     * broj stolova i ucesnika su u tesnoj vezi, pa je potrebno obaviti dalju proveru ili
     * naci algoritam da se jedan parametar izbaci (a mozda zavisi od tipa ?)
     * <p/>
     * *
     */

    public static rs.logotet.bridge.model.movement.Movement getMovement(int tip) {
        return new rs.logotet.bridge.model.movement.TestMomevent(6, 5, 3); // ?? za tipove koji imaju predefinisanu dimenziju
    }


    public static rs.logotet.bridge.model.movement.Movement getMovement(int tip, int teams, int rounds, int tables) {
        return new rs.logotet.bridge.model.movement.TestMomevent(teams, rounds, tables);
    }

    public static Movement getMovement(String file) throws IOException {
        rs.logotet.bridge.model.movement.MovementReader rdr = new MovementReader();
        return rdr.getMovement(file);
    }
}
