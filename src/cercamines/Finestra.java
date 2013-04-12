/*
 * Pràctica final Programació II - 2011/2012 - UIB
 * 
 * Classe que implementa totes les finestres emergets que et surten durant
 * el joc. És un simple contenidor de mètodes que mostren la finestra per fer més
 * fàcil d'entendre el codi del main. No es crea cap objecte d'aquest classe, tots
 * els mètodes són estàtics.
 * 
 * 
 */

package cercamines;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Joan Font && Marc Ostendorp
 */

public class Finestra {
    
    
    /**
     * Mètode que mostra una finestra emergent en el cas de que s'hagi perdut
     * la partida. Retorna la opció clicada de les diferents opció perque aquesta
     * pugui ser tractada.
     * 
     * @return 
     */
    public static int partidaPerduda(){
        String titol = "HAS PERDUT";
        String missatge = "Has perdut, que vols fer ara?";
        ImageIcon icon = new ImageIcon("imatges/bomba.png","Bomba");
        String opcions[] = {"Sortir","Obrir una partida","Nova partida"};
        return JOptionPane.showOptionDialog(
                               null                      // Center in window.
                             , missatge       // Message
                             , titol               // Title in titlebar
                             , JOptionPane.YES_NO_OPTION  // Option type
                             , JOptionPane.PLAIN_MESSAGE  // messageType
                             , icon                      // Icon (none)
                             , opcions                    // Button text as above.
                             , opcions[2]    // Default button's label
                           );
            
        
    }
    
    /**
     * Mètode que mostra una finestra emergent en el cas de que s'hagi guanyat
     * la partida. Retorna la opció clicada de les diferents opció perque aquesta
     * pugui ser tractada.
     * 
     * @return 
     */
    public static int partidaGuanyada(){
        String titol = "HAS GUANYAT";
        String missatge = "Has guanyat, que vols fer ara?";
        ImageIcon icon = new ImageIcon("imatges/bandera.png","Bandera");
        String opcions[] = {"Sortir","Obrir una partida","Nova partida"};
        return JOptionPane.showOptionDialog(
                               null                     // Center in window.
                             , missatge       // Message
                             , titol               // Title in titlebar
                             , JOptionPane.YES_NO_OPTION  // Option type
                             , JOptionPane.PLAIN_MESSAGE  // messageType
                             , icon                      // Icon (none)
                             , opcions                    // Button text as above.
                             , opcions[2]    // Default button's label
                           );
    }
    
    /**
     * Mètode que mostra el recuadre de informacio al clicar a l'opció del 
     * menú d'ajuda.
     */
    
    public static void informacio(){
        ImageIcon icon = new ImageIcon("imatges/logo.png", "Cercamines");
        String missatge = "Cercamines 1.2\n\n"
                + "Desenvolupat per: Joan Font Rosillo i Marc Ostendorp\n"
                + "Testejat per: Miguel Angel AP i Oriol Miró Barceló\n"
                + "2012 - UIB";
        JOptionPane.showMessageDialog(null, missatge, "Quant a...", JOptionPane.PLAIN_MESSAGE, icon);
    }
    
    
/**
 * Mètode que mostra un missatge d'error quan el fitxer que s'ha seleccionat
 * per obrir una partida és incompatible amb el sistema.
 * @param fitxer
 * @return 
 */
    public static int fitxerIncompatible(String fitxer) {
        String titol = "FITXER INCOMPATIBLE";
        String missatge = "El fitxer '"+fitxer+"'"
                + "\nés incompatible amb el Cercamines, que vols ver?";
        ImageIcon icon = new ImageIcon("imatges/bomba.png","Bomba");
        String opcions[] = {"Sortir","Obrir una partida","Nova partida"};
        return JOptionPane.showOptionDialog(
                               null                     // Center in window.
                             , missatge       // Message
                             , titol               // Title in titlebar
                             , JOptionPane.YES_NO_OPTION  // Option type
                             , JOptionPane.PLAIN_MESSAGE  // messageType
                             , icon                      // Icon (none)
                             , opcions                    // Button text as above.
                             , opcions[2]    // Default button's label
                           );
    }
}
