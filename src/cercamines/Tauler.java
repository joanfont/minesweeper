/*
 * Pràctica final Programació II - 2011/2012 - UIB
 * 
 * La classe Tauler.java és en termes concrets, una col·lecció de caselles. És
 * en aquesta classe on es troba l'escoltador del mouse. A continuació
 * s'explicarà el que es fa a cada mètode i per que serveix cada atribut.
 * 
 * ATRIBUTS
 * 
 * - Casella caselles[][] : Array bi-dimensional de caselles, cada casella conté
 * informació sobre el seu estat.
 * 
 * - int DIMENSIO : Variable entera que representa la dimensió del tauler. Per 
 * defecte és 9.
 * 
 * - int DIMENSIO_VIRTUAL : Variable que representa la dimensió que s'usa per
 * calcular les mines adjacents que té una casella. S'explicarà al mètode 
 * corresponent.
 * 
 * -int COSTAT : En píxels, la dimensió de cada casella, en aquest cas 50x50.
 * -int PANELL : Dimensió del panell, donada per n vegades el tamany de la
 * casella. En aquest cas 9*50 = 450.
 * 
 * -int NUMERO_MINES : Variable per guardar el número de mines que té el tauler.
 * Per defecte inicialitzada a 10. 
 * 
 * -int CASELLES_TAPADES : Aquesta variable entera s'usarà per controlar la
 * victoria del jugador. Conté el nombre de caselles tapades que té el tauler.
 * Inicialitzada a DIMENSIO*DIMENSIO, en aquest cas 9x9 = 81.
 * 
 * MÈTODES
 * 
 * public Tauler()
 * 
 * public int getCostat()
 * 
 * public Casella getCasella()
 * 
 * public void decrementarTapades()
 * 
 * public void inicialitzarJoc()
 * 
 * private void generarTauler()
 * 
 * private void colocarMines()
 * 
 * private void calcularAdjacents()
 * 
 * [EXTRA] public void destaparAdjacents()
 * 
 * private void destaparTauler()
 * 
 * public boolean comprovarVictoria()
 * 
 * public void guardarPartida()
 * 
 * public void obrirPartida()
 * 
 * public void paintComponent()
 * 
 * public Dimension getPreferredSize()
 * 
 */
package cercamines;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

/**
 * @author Joan Font && Marc Ostendorp
 */
public class Tauler extends JPanel {

    private Casella caselles[][];
    private static final int DIMENSIO = 9;
    private static final int DIMENSIO_VIRTUAL = DIMENSIO + 2;
    private static final int COSTAT = 50;
    private static final int PANELL = DIMENSIO * COSTAT;
    private static final int NUMERO_MINES = 10;
    private int CASELLES_TAPADES = DIMENSIO * DIMENSIO;

    /**
     * Constructor de la classe que genera el tauler, coloca les mines i calcula
     * les adjacent
     */
    public Tauler() {
        generarTauler();
        colocarMines();
        calcularAdjacents();

    }

    /**
     * Degut a que l'atribut COSTAT és privat, s'ha creat un getter per poder-lo
     * usar desde qualsevol classe. Retorna el costat de la casella.
     *
     * @return
     */
    public static int getCostat() {
        return Tauler.COSTAT;
    }

    /**
     * Mètode que retorna una casella específica de l'array bi-dimensional de
     * caselles.
     *
     * @param i
     * @param j
     * @return
     */
    public Casella getCasella(int i, int j) {
        return this.caselles[i][j];
    }

    /**
     * Aquest mètode es crida cada vegada que l'usuari fa click damunt una
     * casella i aquesta no és mina. Decrementa el nombre de caselles tapades
     * que hi ha el tauler. Serveix per controlar la victròria.
     */
    public void decrementarTapades() {
        this.CASELLES_TAPADES--;
    }

    /**
     * Aquest mètode és una manera de generalitzar la manera en la que
     * s'inicialitza el joc. Fa exactament el mateix que el constructor, pero
     * s'ha creat de manera que es pugui cridar desde qualsevol classe, per això
     * s'ha fet públic, ja que tots els mètodes que conté són privats. Genera el
     * tauler, coloca les mines, calcula les adjacents i reinicia la variable de
     * CASELLES_TAPADES i repinta el tauler.
     */
    public void inicialitzarJoc() {
        generarTauler();
        colocarMines();
        calcularAdjacents();
        this.CASELLES_TAPADES = DIMENSIO * DIMENSIO;
        repaint();
    }

    /**
     * Mètode que inicialitza principalment l'array de caselles amb dimensió
     * 9x9. Crea l'objecte Casella i li passa per arguments els píxels on l'ha
     * de crear. Va sumant el costat a unes variables x i y que començen a 0.
     */
    private void generarTauler() {
        caselles = new Casella[DIMENSIO][DIMENSIO];
        int x, y = 0;
        for (int i = 0; i < DIMENSIO; i++) {
            x = 0;
            for (int j = 0; j < DIMENSIO; j++) {
                caselles[i][j] = new Casella(x, y);
                x += COSTAT;
            }
            y += COSTAT;
        }
    }

    /**
     * Aquest mètode s'encarrega principalment de colocar les mines a posicions
     * del tauler aleatòries. S'usa la classe Random, que generera un nombre
     * aleatori que com a màxim serà un altre nombre que tu li hagis passat per
     * paràmetres. Generarà dues coordenades, i comprovarà que allà no hi ha una
     * mina. Si ja n'hi ha una, en generarà unes noves coordenades i així fins
     * que el nombre de mines generades sigui igual a les que demana el joc, en
     * aquest cas 10.
     */
    private void colocarMines() {
        int numeroMines = 0;
        Random random = new Random();
        while (numeroMines < NUMERO_MINES) {
            int x = random.nextInt(DIMENSIO);
            int y = random.nextInt(DIMENSIO);
            if (!caselles[x][y].esMina()) {
                caselles[x][y].setMina(true);
                System.out.print("(" + x + "," + y + ") ");
                numeroMines++;
            }
        }
        System.out.println();
    }

    /**
     * En aquest apartat es calculen les mines adjacents que té una casella, és
     * a dir, el nombre de mines que te a les caselles el qual comparteix
     * costat. Per això s'ha creat un array bi-dimensional booleà que contindrà
     * si hi ha una mina o no, de dimensions DIMENSIO+2 i DIMENSIO+2. En altres
     * paraules, el tauler original del joc s'ha ampliat una fila per amunt, una
     * fila per avall, una per l'esquerra i una per la dreta amb la finalitat de
     * generalitzar el més possible aquest càlcul.
     *
     * Primer de tot recorre el vector de caselles i mentres no sigui ni la fila
     * 0 ni la DIMENSIO + 1, ni la columna 0 ni la DIMENSIO + 1 i copiarà
     * l'atribut esMina de la casella. D'aquesta manera quedarà el tauler
     * ampliat de la següent manera:
     *
     * - Fila 0: tots els elements a fals. - Fila DIMENSIO+1: tots els elements
     * a fals. - Columna 0: tots els elements a fals. - Columna DIMENSIÓ+1: tots
     * els elements a fals - La resta dels elements: en funció de el valor de
     * l'atribut esMina de les caselles originals del joc creades al mètode
     * colocarMines().
     *
     * Una vegada tenim el tauler virtual creat. El recorrem per cercar allà on
     * hi ha mines. Una vegada hem trobat una mina (denotarem la seva posició
     * per [i][j]. Feim un doble recorregut desde [i-1] a [i+1] i desde [j-1] a
     * [j+1]. Si en aquestes noves coordenades hi ha mina, incrementam una
     * variable contadora de mines adjacents. Al finalitzar aquest doble
     * recorregut, s'assignara aquesta variable contadora d'adjacents a
     * l'atribut minesAdjcents de l'objecte [i][j] casella. Aquest primer
     * recorregut per a la cerca de mines, comença desde la posició [1][1] ja
     * que a la fila 0 i columna 0 no hi ha mines.
     */
    private void calcularAdjacents() {
        boolean matriuAdjacencies[][] = new boolean[DIMENSIO_VIRTUAL][DIMENSIO_VIRTUAL];
        for (int i = 0; i < DIMENSIO_VIRTUAL; i++) {
            for (int j = 0; j < DIMENSIO_VIRTUAL; j++) {
                if (i > 0 && j > 0 && i < DIMENSIO + 1 && j < DIMENSIO + 1) {
                    matriuAdjacencies[i][j] = caselles[i - 1][j - 1].esMina();
                } else {
                    matriuAdjacencies[i][j] = false;
                }
            }

        }
        int adjacents;
        for (int i = 1; i < DIMENSIO_VIRTUAL - 1; i++) {
            for (int j = 1; j < DIMENSIO_VIRTUAL - 1; j++) {
                adjacents = 0;
                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        if (matriuAdjacencies[i + k][j + l]) {
                            adjacents++;
                        }
                    }
                }
                caselles[i - 1][j - 1].setAdjacents(adjacents);
            }
        }
    }

    /**
     * Aquest és un mètode extra i no necessàri per a la elaboració de la
     * pràctica. S'encarrega de que si en el cas de destapar una casella que no
     * te cap bomba adjacent. Destapa aquestes, i si aquestes no en tenen cap,
     * també destapa les seves caselles adjacents.
     *
     * Per dur-ho a terme s'ha usat el concepte de recursivitat. Un programa que
     * es crida a sí mateix. La seva estructura és la següent: en el cas de que
     * se'l cridi aquest iniciarà un doble recorregut per files i columnes com
     * quan es calculen les mines adjacents en el cas de trobar una casella que
     * tengui 0 com a mines adjacents. Tornara a cridar a aquest mètode a partir
     * d'aquesta casella.
     *
     * L'unic inconvenient a l'hora d'implementa-ho, és la condició de quan s'ha
     * d'aturar, sense condició el programa entra en bucle infinit i no s'atura
     * mai de destapar caselles. Primer s'ha d'assegurar de que no es va a
     * destapar una casella que no existeix (per exemple [-1][0]), quan s'està
     * segur d'això es mira que aquesta casella tengui 0 mines adjacents i que
     * estigui tapada. Això de que estigui tapada és molt important perquè si no
     * s'imposa aquesta condició ocorre el bucle infinit. Si es compleixen
     * aquestes tres condicions primer es destapa la casella, es decrementa el
     * nombre de caselles destapades (atribut d'objecte) i es torna a cridar al
     * mètode.
     *
     * @param i
     * @param j
     */
    public void destaparAdjacents(int i, int j) {
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                int z = i + x;
                int t = j + y;
                if (z > -1 && t > -1 && z < DIMENSIO && t < DIMENSIO) {
                    if (caselles[i][j].getAdjacents() == 0 && !caselles[z][t].destapada()) {
                        caselles[z][t].destaparCasella();
                        this.decrementarTapades();
                        destaparAdjacents(z, t);
                    }
                }
            }
        }
    }

    /**
     * Aquest mètode fa un recorregut per tot el tauler destapant totes les
     * caselles i després repinta el tauler amb un repaint(). Es empreat en el
     * cas de fer click a una mina i perdre el joc.
     */
    public void destaparTauler() {
        for (int i = 0; i < DIMENSIO; i++) {
            for (int j = 0; j < DIMENSIO; j++) {
                if (!caselles[i][j].esBandera()) {
                    caselles[i][j].destaparCasella();
                }
            }
        }
        repaint();
    }

    /**
     * Mètode que indica el seu nom serveix per comprovar si s'ha produit una
     * sitiació en que el jugador ha guanyat. Simplement retorna un 'true' si el
     * nombre de caselles tapades es igual al nombre de mines. Si torna un
     * 'true' vol dir que només les 10 mines queden tapades, es a dir, que el
     * jugador ha guanyat.
     *
     * @return
     */
    public boolean comprovarVictoria() {
        return CASELLES_TAPADES == NUMERO_MINES;
    }

    /**
     * Aquest es el mètode que es encarregat de guardar una partida. Crea una
     * array bi-dimensional que anirà omplit segons el estat de la casella de la
     * partida. Les caselles es poden trobar en cinc estats diferents. Per cada
     * estat escrivim un cert numero a la array bi- dimensional que guardam.
     * Aquests numeros son els següents:
     *
     * 0 -> Si la casella està TAPADA, NO és BANDERA i NO és MINA. 1 -> Si la
     * casella està TAPADA, NO és BANDERA i ÉS MINA. 2 -> Si la casella està
     * TAPADA, ÉS BANDERA i NO és MINA. 3 -> Si la casella està TAPADA, ÉS
     * BANDERA i ÉS MINA. 4 -> Si la casella està DESTAPADA.
     *
     * Una vegada que tenim aquesta array generada cream un nou objecte Partida
     * passant per paràmetre l'array, i després cridam el mètode de desar de
     * aquest objecte Partida.
     *
     * @param arxiu
     */
    public void guardarPartida(String arxiu) {
        int matriu[][] = new int[DIMENSIO][DIMENSIO];
        for (int i = 0; i < DIMENSIO; i++) {
            for (int j = 0; j < DIMENSIO; j++) {
                int num;
                if (!caselles[i][j].destapada() && !caselles[i][j].esBandera() && !caselles[i][j].esMina()) {
                    num = 0;
                } else if (!caselles[i][j].destapada() && !caselles[i][j].esBandera() && caselles[i][j].esMina()) {
                    num = 1;
                } else if (!caselles[i][j].destapada() && caselles[i][j].esBandera() && !caselles[i][j].esMina()) {
                    num = 2;
                } else if (!caselles[i][j].destapada() && caselles[i][j].esBandera() && caselles[i][j].esMina()) {
                    num = 3;
                } else {
                    num = 4;
                }
                matriu[i][j] = num;
            }
        }
        Partida partida = new Partida(matriu);
        partida.desar(arxiu);
    }

    /**
     * Aquest mètode conté tot el codi necesari per obrir una partida guardada
     * anteriorment. Per això primer cream un nou objecte Partida i amb el
     * mètode de obrir() passant per paràmetre el nom de l'arxiu obtenim l'array
     * amb els numeros codificats anteriorment guardats. Ara només hem de fer un
     * recorregut per aquesta matriu i llegir el numero codificat de cada
     * casella. Després al nostre tauler ajustam les condicions de la casella a
     * les corresponen del numero de la array guardada. Si llegim un 0, posam
     * que la casella està tapada que no duu bandera i que no duu mina. Si
     * llegim un 1 posam que la casella està tapada, que no duu bandera però que
     * és una mina. Si llegim un 2 posam que la casella està tapada,que duu una
     * bandera però que no és una mina. Si llegim un 3 posam que la casella està
     * tapada, que duu una bandera i que és una mina; i si llegim un 4,
     * directament posam que la casella està destapada.
     *
     * Quan tenim totes aquests estats ben posats tornam a calcular el adjacents
     * cridant el mètode corresponent i repintam el tauler amb un repaint().
     *
     * @param arxiu
     * @throws Exception
     */
    public void obrirPartida(String arxiu) throws Exception {
        Partida partida = new Partida();
        partida.obrir(arxiu);
        int[][] matriu = partida.getPartida();
        System.out.print("Partida carregada de '" + arxiu + "' > ");
        this.CASELLES_TAPADES = DIMENSIO * DIMENSIO;
        for (int i = 0; i < DIMENSIO; i++) {
            for (int j = 0; j < DIMENSIO; j++) {
                int numero = matriu[i][j];
                switch (numero) {
                    case 0:
                        caselles[i][j].taparCasella();
                        caselles[i][j].setBandera(false);
                        caselles[i][j].setMina(false);
                        break;

                    case 1:
                        caselles[i][j].taparCasella();
                        caselles[i][j].setBandera(false);
                        caselles[i][j].setMina(true);
                        System.out.print("(" + i + "," + j + ") ");
                        break;

                    case 2:
                        caselles[i][j].taparCasella();
                        caselles[i][j].setBandera(true);
                        caselles[i][j].setMina(false);
                        break;

                    case 3:
                        caselles[i][j].taparCasella();
                        caselles[i][j].setBandera(true);
                        caselles[i][j].setMina(false);
                        break;

                    case 4:
                        caselles[i][j].destaparCasella();
                        caselles[i][j].setBandera(false);
                        caselles[i][j].setMina(false);
                        this.CASELLES_TAPADES--;
                        break;
                }
            }
        }
        System.out.println();
        this.calcularAdjacents();
        this.repaint();
    }

    /**
     * Mètode que es crida automàticament al afegir el tauler al Panell. Aquest
     * mètode torna a cridar el mètode de paintComponent() de Casella per així,
     * pintar tot el tauler casella per casella en el moment de ser afegit al
     * Panell.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        for (int i = 0; i < DIMENSIO; i++) {
            for (int j = 0; j < DIMENSIO; j++) {
                caselles[i][j].paintComponent(g);
            }
        }

    }

    /**
     * Mètode que torna les mides el tauler. Inclou un condicional per si es
     * executat a un Macintosh o qualsevol altre màquina. Necessitam aquest
     * condicional ja que en els Macintosh hem incorporat el menu en la barra
     * del sistema de Macintosh. Així que no hem de comptar amb aquest espai de
     * menu dins el tamany del tauler. En el cas de no tratarse de un Macintosh
     * hem de retornar unes mides amb un parell de pixels més per així poder
     * incorporar el menu en la part superior damunt el tauler de joc.
     *
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        int width;
        int height;
        if (Cercamines.isMac()) {
            width = PANELL;
            height = PANELL + 20;
        } else {
            width = PANELL + 5;
            height = PANELL + 45;
        }
        return new Dimension(width, height + 2);
    }
}
