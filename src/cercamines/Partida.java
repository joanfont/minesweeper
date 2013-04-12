/*
 * Pràctica final Programació II - 2011/2012 - UIB
 * 
 * La classe Partida.java és l'objecte que es guarda. Per implementar el sistema 
 * de guardar i obrir partides, es guarda aquest objecte (veure l'implements 
 * Serializable). 
 * 
 * ATRIBUTS
 * 
 * - int[][] partida: l'array bidimensional de la partida, on a cada element hi 
 * ha un enter que representa l'estat de la casella.
 */
package cercamines;

import java.io.*;

/**
 *
 * @author Joan Font && Marc Ostendorp
 */
public class Partida implements Serializable {

    private int[][] partida;

    /**
     * Constructor buit. S'usa en el cas de que l'acció a fer sigui obrir una
     * partida.
     */
    public Partida() {
    }

    /**
     * Constructor usat en el cas de guardr una partida. Inicialitza la variable
     * partida.
     *
     * @param partida
     */
    public Partida(int[][] partida) {
        this.partida = partida;
    }

    /**
     * Mètode que retorna l'array bidimensional de la partida. S'usa en el cas
     * d'obrir una partida per interpretar els resultats.
     *
     * @return
     */
    public int[][] getPartida() {
        return this.partida;
    }

    /**
     * Mètode per desar una partida. S'enarrega de guardar el mateix objecte
     * (this) en el fitxer especificat per paràmetres. En el cas de que el nom
     * del fitxer no dugui l'extensió triada per al joc, se li afageix. S'usen
     * ObjectOutputStream per guardar l'objecte.
     *
     * @param arxiu
     */
    public void desar(String arxiu) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        if (!arxiu.endsWith(".cm")) {
            arxiu += ".cm";
        }
        try {
            fos = new FileOutputStream(arxiu);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        } catch (Exception ex) {
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (Exception ex) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    /**
     * Mètode per obrir una partida. De la ruta especificada per paràmetres,
     * extreu l'objecte partida i l'assigna a un nou objecte Partida. S'usa el
     * ObjectInputStream per llegir el fitxer. Al final, assigna la matriu
     * bidimensional de l'objecte partida llegit al d'aquest objecte.
     *
     * @param arxiu
     * @throws Exception
     */
    public void obrir(String arxiu) throws Exception {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Partida p = null;
        try {
            fis = new FileInputStream(arxiu);
            ois = new ObjectInputStream(fis);
            p = (Partida) ois.readObject();
        } catch (Exception ex) {
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (Exception ex) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception ex) {
                }
            }
        }
        if (p != null) {
            this.partida = p.partida;
        }
    }
}
