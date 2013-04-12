/*
 * Pràctica final Programació II - 2011/2012 - UIB
 * 
 * Cercamines
 * Aquesta pràctica consisteix en programar un joc parescut al cercamines de
 * Windows. 
 * 
 * Un joc que tracta de destapar completament un tauler menys les caselles que
 * contenguin mines. Aquestes caselles que contenen mines poden ser marcades
 * amb una bandera. També caselles que no siguin mines poden ser marcades amb
 * una bandera. Caselles marcades amb una bandera no poden ser destapades. Per
 * destapar aquestes caselles cal abans haver llevat la bandera.
 * 
 * Cada vegada que es destapa una casella, es mostrarà el nombre de mines
 * adjacents que té. Si el nombre de mines adjacents d'aquesta casella és zero
 * es destaparan les adjacents a aquesta, i així succesivament.
 * 
 * El joc acaba destapant totes les caselles que no siguin mines amb una victòria
 * o explotant una mina.
 * 
 * El joc es pot guardar en qualsevol moment i es pot tornar a carregar.
 * 
 * ATRIBUTS
 * 
 * - Tauler tauler: Objecte tauler 
 * - JMenuBar menu: la barra de menú principal
 * - JMenu arxiu: menú arxiu
 * - JMenu ajuda: menú ajuda
 * - JMenuItem novaPartida: opció del menu arxiu per crear una partida nova
 * - JMenuItem obrirPartida: opció del menú arxiu per obrir una partida desada
 * - JMenuItem desarPartida: opció del menu arxiu per desar una partida
 * - JMenuItem sortir: opció del menú arxiu per sortir del programa principal
 * - JMenuItem informacio: opció del menu ajuda on es troben els crèdits
 * - JFileChooser dialegArxius: un diàleg per seleccionar arxius
 * - int INPUT_EVENT: En el cas de que l'ordinador sigui un Macintosh es usada
 * la tecla CMD en lloc de CTRL. Això es un atribut int que modifica el nemònic
 * del JMenuItem.
 * 
 * MÈTODES
 * 
 * public Cercamines()
 * 
 * private void initComponents()
 * 
 * private void nova()
 * 
 * private void obrir()
 * 
 * private void guardar()
 * 
 * private void sortir()
 * 
 * private void informacio()
 * 
 * public static boolean isMac()
 * 
 * private void gestioClick()
 * 
 * private int queFarem()
 *
 * public static void main() :
 */
package cercamines;

import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 * @author Joan Font && Marc Ostendorp
 */
public class Cercamines extends JFrame implements MouseListener {

    private Tauler tauler;
    private JMenuBar menu;
    private JMenu arxiu;
    private JMenu ajuda;
    private JMenuItem novaPartida;
    private JMenuItem obrirPartida;
    private JMenuItem desarPartida;
    private JMenuItem sortir;
    private JMenuItem informacio;
    private JFileChooser dialegArxius;
    private int INPUT_EVENT;

    /**
     * El constructor del programa principal, que defineix el títol de la
     * finestra i inicialitza tots els components de la interfície.
     */
    public Cercamines() {
        super("Cercamines");
        initComponents();
    }

    /**
     * Aquest mètode inicialitza tot els components de la part gràfica. Afegeix
     * el tauler al JFrame, posa els escoltadors al tauler, tots els elements
     * dels menús, els ActionListeners de cada element del menú. Crea l'objecte
     * dialegArxius amb el filtre que fa que només es puguin obrir arxius .cm.
     */
    private void initComponents() {

        if (isMac()) {
            INPUT_EVENT = InputEvent.META_MASK;
        } else {
            INPUT_EVENT = InputEvent.CTRL_MASK;
        }

        tauler = new Tauler();
        this.setResizable(false);
        this.getContentPane().add(tauler);
        this.setSize(tauler.getPreferredSize());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        tauler.addMouseListener(this);

        menu = new JMenuBar();

        arxiu = new JMenu();
        arxiu.setText("Arxiu");
        menu.add(arxiu);

        ajuda = new JMenu();
        ajuda.setText("Ajuda");
        menu.add(ajuda);

        novaPartida = new JMenuItem();
        novaPartida.setText("Nova Partida");
        novaPartida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, INPUT_EVENT));
        novaPartida.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                nova();
            }
        });
        arxiu.add(novaPartida);

        obrirPartida = new JMenuItem();
        obrirPartida.setText("Obrir Partida");
        obrirPartida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, INPUT_EVENT));
        obrirPartida.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                obrir();
            }
        });
        arxiu.add(obrirPartida);

        desarPartida = new JMenuItem();
        desarPartida.setText("Desar Partida");
        desarPartida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, INPUT_EVENT));
        desarPartida.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                guardar();
            }
        });
        arxiu.add(desarPartida);

        sortir = new JMenuItem();
        sortir.setText("Sortir");
        sortir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                sortir();
            }
        });
        arxiu.add(sortir);

        informacio = new JMenuItem();
        informacio.setText("Informació");
        informacio.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, INPUT_EVENT));
        informacio.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                informacio();
            }
        });
        ajuda.add(informacio);

        dialegArxius = new JFileChooser();
        dialegArxius.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".cm")
                        || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Partida de Cercamines";
            }
        });

        this.setJMenuBar(menu);


    }

    /**
     * S'encarrega de quan s'apreta la opció del menú Arxiu -> Nova Partida.
     * Genera mines noves, coloca les adjacents, i repinta el tauler. Crida al
     * mètode inicialitzarJoc() del tauler, que és el que s'encarrega de fer tot
     * aquesta feina.
     */
    private void nova() {
        tauler.inicialitzarJoc();
    }

    /**
     * S'encarrega de obrir el diàleg d'arxius per obrir una partida de
     * Cercamines, i crida al mètode obrirPartida() del tauler per processar les
     * dades del fitxer obtingut pel diàleg.
     *
     * @return
     */
    private int obrir() {
        int r = dialegArxius.showOpenDialog(this);
        if (r == JFileChooser.APPROVE_OPTION) {
            try {
                tauler.obrirPartida(dialegArxius.getSelectedFile().getPath());
            } catch (NullPointerException e) {
                try {
                    int queFer, opcio;
                    do {
                        queFer = Finestra.fitxerIncompatible(dialegArxius.getSelectedFile().getPath());
                        opcio = queFarem(queFer);
                    } while (opcio != 0);
                } catch (Exception ex) {
                }
            } catch (Exception e) {
            }
        }
        return r;
    }

    /**
     * Obri un altre diàleg d'arxius, però aquesta vegada per guardar. Al igual
     * que el mètode obrir(), crida al mètode guardarPartida() del tauler per
     * processar les dades del tauler a guardar.
     *
     * @return
     */
    private int guardar() {
        int r = dialegArxius.showSaveDialog(this);
        if (r == JFileChooser.APPROVE_OPTION) {
            try {
                tauler.guardarPartida(dialegArxius.getSelectedFile().getPath());
            } catch (Exception e) {
                System.err.println(e);
            }
        }

        return r;
    }

    /**
     * Mètode per aturar l'execució del programa principal amb un
     * System.exit(0);
     */
    private void sortir() {
        System.exit(0);
    }

    /**
     * Aquest mètode crida al mètode informacio() de la classe Finestra, que
     * conté un JOptionPane amb el quadre que surt. És un clar exemple del TDA
     * (Típus de Dades Abstracte).
     */
    private void informacio() {
        Finestra.informacio();
    }

    /**
     * Aquest mètode detecta si el sistema operatiu és Mac OS X, per canviar
     * quatre aspectes de la interfície gràfica, com pugui ser el menú a la
     * barra superior i no a la finestra. O per detectar si el nemònic ha de ser
     * amb la tecla CMD o amb la tecla CTRL
     *
     * @return
     */
    public static boolean isMac() {
        return System.getProperty("os.name").contains("Mac");
    }

    /**
     * Aquest mètode es podria dir que és el que gestiona totes les accions amb
     * el ratolí. Distingeix entre el boto esquerra o qualsevol altre. En cas de
     * que es tracti de el botó dret comprova que no sigui una bandera,
     * seguidament que no estigui destapada i després que no sigui mina, si no
     * ho es comprovarà si el numero de mines adjacents és 0, si ho és destaparà
     * totes les caselles adjacents, del contrari destapara només la casella
     * clickada. En ca de no clickar una mina, si només queden 10 caselles
     * tapades (o 10 banderes), es mostrarà un missatge anunciant la victòria i
     * vàries opcions a poder fer. Cada vegada que es fa un click, es comprova
     * que el nombre de caselles tapades sigui 10 per poder mostrar aquest
     * missatge.Si la casella clickada és una mina es destapara tot el tauler i
     * seguidament es mostrarà un menu amb vàries opcions.
     *
     * Si el botó clickat no és l'esquerra i la casella esta tapada, es posa o
     * es lleva la bandera, en funció si ja n'hi ha o no.
     *
     * @param i
     * @param j
     * @param boto
     */
    private void gestioClick(int i, int j, int boto) {
        Casella casella = tauler.getCasella(i, j);
        if (boto == MouseEvent.BUTTON1) { // Botó esquerra
            if (!casella.esBandera()) {
                if (!casella.destapada()) {
                    if (casella.esMina()) {
                        casella.minaDestapada();
                        tauler.destaparTauler();
                        int queFer, opcio;
                        do {
                            queFer = Finestra.partidaPerduda();
                            opcio = queFarem(queFer);
                        } while (opcio != 0);
                    } else {
                        if (casella.getAdjacents() == 0) {
                            tauler.destaparAdjacents(i, j);
                        } else {
                            casella.destaparCasella();
                            tauler.decrementarTapades();
                        }
                        if (tauler.comprovarVictoria()) {
                            tauler.destaparTauler();
                            int queFer, opcio;
                            do {
                                queFer = Finestra.partidaGuanyada();
                                opcio = queFarem(queFer);
                            } while (opcio != 0);
                        }
                    }
                }
            }
        } else {
            if (!casella.destapada()) {
                casella.bandera();
            }
        }
        tauler.repaint();
    }

    /**
     * Aquest mètode controla l'acció a dur a terme quan es selecciona una opció
     * de la finestra de partida guanyada / perduda. Si el botó apretat és el de
     * sortir, es surt del programa. Si es el d'obrir i s'ha apretat el boto de
     * Obrir (en el JFileChooser) es tancarà el diàleg, en cas contrari tornarà
     * a sortir, i com a cas final, si s'ha pogut crear una nova partida, es
     * repintarà el tauler i es tancara el diàleg de partida perduda / guanyada.
     *
     * @param opcio
     * @return
     */
    private int queFarem(int opcio) {
        if (opcio == 0) {
            sortir();

        }
        if (opcio == 1) {
            int r = obrir();
            if (r == JFileChooser.APPROVE_OPTION) {
                opcio = 0;
            }
        }
        if (opcio == 2) {
            nova();
            opcio = 0;
        }
        return opcio;
    }

    /**
     * Mètode principal del programa que crea l'objecte cercamines, mostra la
     * finestra, i en el cas de que el sistema operatiu sigui Mac OS X, canvia
     * el menú i el situa a la barra superior. També posa com a Look and Feel el
     * propi del sistema operatiu mitjançant l'UIManager.
     *
     * @param args
     */
    public static void main(String[] args) {

        if (isMac()) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (UnsupportedLookAndFeelException ex) {
        }

        Cercamines cercamines = new Cercamines();
        cercamines.setVisible(true);
    }
    /*
     * A continuació venen els mètodes pertinents al MouseListener, en aquest
     * cas només s'ha usat el mouseRealased() que fà l'acció quan s'ha amollat
     * el botó de la rata. Aquest mètode agafa la posició de la rata dins la
     * pantalla i la divideix entre el tamany de la casella per poder saber
     * exactament a quina casella s'ha clickat. També agafa quin botó s'ha près
     * i es crida al mètode gestioClick() explicat anteriorment.
     *
     * Els altres mètodes del MouseListener no s'empren per res.
     */

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        int i = (int) Math.floor(me.getY() / Tauler.getCostat());
        int j = (int) Math.floor(me.getX() / Tauler.getCostat());
        int boto = me.getButton();
        gestioClick(i, j, boto);
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}