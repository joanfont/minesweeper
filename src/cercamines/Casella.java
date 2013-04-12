/*
 * Pràctica final Programació II - 2011/2012 - UIB
 * 
 * La classe Casella.java conté tots els mètodes corresponents a la interacció
 * amb la casella.
 * 
 * ATRIBUTS
 * 
 * - int x: Atribut que indica la posició (en píxels) de la casella sobre 
 * l'eix X.
 * 
 * - int y: Atribut que indica la posició de la casella sobre l'eix Y.
 * 
 * - int minesAdjacents: Variable entera que conté el nombre de mines adjacents
 * que té la casella.
 * 
 * - boolean esMina: Valor booleà que indica si la casella és una mina.
 * 
 * - boolean destapada: Variable booleana que indica si la casella està destapada
 * o no.
 * 
 * - boolea esBandera: Valor booleà que indica si la casella conté una bandera
 * 
 * - boolean minaDestapada: Variable booleana que serveix per quan cliques una mina
 * surti una bomba vermella enlloc d'una blava.
 * 
 * - Imatge img: Variable de típus Imatge que contindrà la imatge que es 
 * mostrarà a la casella. 
 *
 */
package cercamines;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Joan Font && Marc Ostendorp
 */
public class Casella extends JPanel {

    private int x;
    private int y;
    private int minesAdjacents;
    private boolean esMina;
    private boolean destapada;
    private boolean esBandera;
    private boolean minaDestapada;
    private Imatge img;

    /**
     * Constructor de la classe per instanciar-la. Se li passa per pràmetres la
     * seva posició en píxels a l'eix X i a l'eix Y.
     *
     * S'inicialitzen les variables booleanes a false, el nombre de mines
     * adjacents a 0 i l'objecte Imatge a null.
     *
     * @param x
     * @param y
     */
    public Casella(int x, int y) {
        this.x = x;
        this.y = y;
        this.esMina = false;
        this.esBandera = false;
        this.minesAdjacents = 0;
        this.destapada = false;
        this.minaDestapada = false;
        this.img = null;

    }

    /**
     * Mètode que retorna true si una casella és mina.
     *
     * @return
     */
    public boolean esMina() {
        return this.esMina;
    }

    /**
     * Mètode que s'emplea per colocar una mina a una casella.
     *
     * @param esMina
     */
    public void setMina(boolean esMina) {
        this.esMina = esMina;
    }

    /**
     * Mètode que s'emplea per posar el nombre de mines adjacents a una casella.
     *
     * @param minesAdjacents
     */
    public void setAdjacents(int minesAdjacents) {
        this.minesAdjacents = minesAdjacents;
    }

    /**
     * Getter per obtenir el nombre de mines adjacents d'una casella.
     *
     * @return this.minesAdjacents
     */
    public int getAdjacents() {
        return this.minesAdjacents;
    }

    /**
     * Mètode que serveix per destapar una casella. Posant a true el valor
     * booleà destapada.
     */
    public void destaparCasella() {
        this.destapada = true;
    }

    /**
     * Mètode que serveix per colocar una bandera. Se passa per paràmetre un
     * booleà. Això serveix a nes moment de obrir la partida per posar bé les
     * banderes.
     *
     * @param esBandera
     */
    public void setBandera(boolean esBandera) {
        this.esBandera = esBandera;

    }

    /**
     * Mètode que serveix per posar a true el paràmetre que empleam perque la
     * mina explotada apareixqui en vermell.
     */
    public void minaDestapada() {
        this.minaDestapada = true;
    }

    /**
     * Mètode per colocar o llevar banderes. Si a una casella no hi ha bandera
     * se coloca i si hi ha una bandera se lleva.
     */
    public void bandera() {
        this.esBandera = !this.esBandera;
    }

    /**
     * Mètode que retorna si a una casella hi ha una bandera o no.
     *
     * @return
     */
    public boolean esBandera() {
        return this.esBandera;
    }

    /**
     * Mètode per tornar a tapar caselles. Serveix en el moment de crear noves
     * partides.
     */
    public void taparCasella() {
        this.destapada = false;
    }

    /**
     * Mètode que retorna un booleà en funció de si una casella està tapada o
     * destapada.
     *
     * @return
     */
    public boolean destapada() {
        return this.destapada;
    }

    /**
     * Mètode que es crida automàticament al invocar el contructor de la classe.
     * Pinta les tapes de les caselles i durant el joc en funció de les
     * variables de la classe casella pinta el les diferents informacions damunt
     * una casella cliquetjada.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        if (this.destapada) {
            if (this.esMina) {
                if (this.minaDestapada) {
                    img = new Imatge(Imatge.MINA_DESTAPADA);
                } else {
                    img = new Imatge(Imatge.MINA_TAPADA);
                }
            } else {
                img = new Imatge(this.minesAdjacents);
            }
        } else {
            if (this.esBandera) {
                img = new Imatge(Imatge.BANDERA);
            } else {
                img = new Imatge(Imatge.TAPA);
            }
        }
        this.img.paintComponent(g, this.x, this.y);
    }
}
