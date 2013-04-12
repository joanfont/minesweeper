/*
 * Pràctica final Programació II - 2011/2012 - UIB
 * 
 * La classe Imatge.java és el contingut de la casella, s'encarrega de pintar
 * les imatges.
 * 
 * ATRIBUTS
 * 
 * - String TAPA: ruta a la imatge que fa de tapa. 
 * - String MINA_TAPADA: ruta a la imatge que fa de mina tapada.
 * - String MINA_DESTAPADA: ruta a la imatge que fa de mina destapada.
 * - String BANDERA: ruta a la imatge que fa de bandera.
 * - BufferedImage img: Objecte que pinta la imatge.
 */
package cercamines;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Joan Font && Marc Ostendorp
 */
public class Imatge {

    public static final String TAPA = "imatges/tapa.png";
    public static final String MINA_TAPADA = "imatges/mina.png";
    public static final String MINA_DESTAPADA = "imatges/bomba.png";
    public static final String BANDERA = "imatges/bandera.png";
    private BufferedImage img;

    /**
     * Constructor que instancia la imatge quan se li passa un String. Aquest
     * String sempre serà una variable de les públiques d'aquesta classe.
     * @param img 
     */
    public Imatge(String img) {
        try {
            this.img = ImageIO.read(new File(img));
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }
    
    /**
     * Quan l'argument passat al constructor sigui un enter (que correspon al
     * nombre de mines adjacents). Es pot veure que cridarà a les imatges en funció
     * del enter i hi afageix l'extensió. Si la casella te 3 mines adjacents, cridarà
     * a la imatge 3.png.
     * @param img 
     */
    public Imatge(int img) {
        try {
            this.img = ImageIO.read(new File("imatges/" + img + ".png"));
        } catch (IOException ex) {
            System.err.print(ex);
        }
    }

    /**
     * Mètode que pintara la imatge a una determinada posició passada per arguments.
     * @param g
     * @param x
     * @param y 
     */
    public void paintComponent(Graphics g, float x, float y) {
        g.drawImage(img, (int) x, (int) y, null);
    }
}
