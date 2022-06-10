/*Ventana principal de la lluvia de palabras
 */
package lluviadepalabras;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class VentanaLluvia extends JFrame {

    private JPanel panel1, panel2;
    private JTextField tPalabra;
    private Timer t, animacion, animacionDeError, tiempoDeEspera, animacionPuntajeA, animacionPuntajeEm, animacionPuntajeEM;
    private ListaDeEtiquetas primero, ultimo;
    private Border tBorde;
    private Random rand;
    private JComboBox CBniveles, CBvelocidad;
    private JLabel Lniveles, Lvelocidades, LPuntuacion, LPuntuacionCifra;
    private JButton BIniciar,BMAcercade,BMComoJugar;
    private JMenuBar menu;
    private int brinco;
    private Point Dpanel2;
    private int puntuacion = 500, errorMenor = 5, errorMayor = 100;
    private JMenu Bmenu;
    private VentanaComojugar v;

    VentanaLluvia() {
        this.setSize(716, 450);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        iniciarComponentes();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Lluvia de palabras");
        this.setResizable(true);
        //animacion.setInitialDelay(2000);
        //animacion.start();

    }

    public void iniciarComponentes() {

        panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(0, 20, 190, this.getHeight() - 20);
        //panel1.setBackground(Color.red);
        this.getContentPane().add(panel1);

        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBounds(200, 20, 500, this.getHeight() - 60);
        panel2.setBorder(BorderFactory.createLineBorder(Color.blue, 2));//Crea un borde y se lo aplica al panel 
        //panel2.setBackground(new Color( 218, 206, 157));
        this.getContentPane().add(panel2);

        tPalabra = new JTextField();
        tPalabra.setBounds(10, 75, 150, 30);
        tPalabra.requestFocus();
        tPalabra.addKeyListener(tecla);
        tBorde = tPalabra.getBorder();
        //panel2.add(tPalabra);
        panel1.add(tPalabra);

        Lniveles = new JLabel("Elige un nivel...");
        Lniveles.setBounds(10, 140, 120, 30);
        panel1.add(Lniveles);

        Lvelocidades = new JLabel("Elige la velocidad...");
        Lvelocidades.setBounds(10, 220, 120, 30);
        panel1.add(Lvelocidades);

        LPuntuacion = new JLabel();
        LPuntuacion.setOpaque(true);
        LPuntuacion.setBounds(0, 0, panel1.getWidth(), 25);
        LPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);
        LPuntuacion.setText("Puntuación:");
        panel1.add(LPuntuacion);
        

        LPuntuacionCifra = new JLabel("500");
        LPuntuacionCifra.setOpaque(true);
        LPuntuacionCifra.setBounds(0, 20, panel1.getWidth(), 40);
        LPuntuacionCifra.setHorizontalAlignment(SwingConstants.CENTER);
        LPuntuacionCifra.setFont(new Font("Impact", 1, 20));
        panel1.add(LPuntuacionCifra);

        String niveles[] = {"Nivel 1", "Nivel 2", "Nivel 3", "Nivel 4"};
        CBniveles = new JComboBox(niveles);
        CBniveles.setBounds(10, 170, 100, 25);
        panel1.add(CBniveles);

        String velocidades[] = {"zzz", "Normal", "Movidito", "Fiuuuuuun"};
        CBvelocidad = new JComboBox(velocidades);
        CBvelocidad.setBounds(10, 250, 100, 25);
        panel1.add(CBvelocidad);

        BIniciar = new JButton("Iniciar");
        BIniciar.setBounds(35, 320, 100, 30);
        BIniciar.addActionListener(actionPerformedBInicar);
        panel1.add(BIniciar);
        
        BMAcercade = new JButton("Acerca de ");
        BMAcercade.setBackground(new Color(240,240,240));
        BMAcercade.setOpaque(true);
        
        BMComoJugar = new JButton("Como jugar");
        BMComoJugar.setBackground(new Color(240,240,240));
        BMComoJugar.setOpaque(false);
        BMComoJugar.addActionListener(BComojugarActionPerformed);

        menu = new JMenuBar();//Barra de menú de la ventana
        menu.setOpaque(true);
        menu.add(BMComoJugar,0);
        menu.add(BMAcercade, 1);//Añade un componente al la barra menú, indicando la posicion que ocupara
       
       // BMAcercade.setBackground();
       // menu.add(Bmenu);
        menu.setBounds(0, 0, this.getWidth(), 20);      
        this.getContentPane().add(menu);

       
        Dpanel2 = new Point(panel2.getX(), panel2.getY());

        t = new Timer(3000, crear);
        animacion = new Timer(100, animar);
        animacionDeError = new Timer(100, error);
        tiempoDeEspera = new Timer(1, esperar);
        tiempoDeEspera.setInitialDelay(1000);
        tiempoDeEspera.setRepeats(false);
        animacionPuntajeA = new Timer(0, animacionPuntajeAscendente);
        animacionPuntajeEm = new Timer(0, animacionPuntajeDescendenteErrorMenor);
        animacionPuntajeEM = new Timer(0, animacionPuntajeDescendenteErrorMayor);

    }
    //Action listeners
    ActionListener animacionPuntajeAscendente = new ActionListener() {
        int contador;

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(contador);
            if (contador < 100) {
                puntuacion++;
                contador++;
                LPuntuacionCifra.setText(puntuacion + "");
            } else {
                contador = 0;
                animacionPuntajeA.stop();
            }
        }
    };

    ActionListener animacionPuntajeDescendenteErrorMenor = new ActionListener() {

        int contador;

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("-" + errorMenor);
            if (contador < errorMenor) {
                puntuacion--;
                contador++;
                LPuntuacionCifra.setText(puntuacion + "");
            } else {

                contador = 0;
                animacionPuntajeEm.stop();

            }
        }
    };

    ActionListener animacionPuntajeDescendenteErrorMayor = new ActionListener() {
        int contador;

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("-" + errorMayor);
            if (contador < errorMayor) {
                puntuacion--;
                contador++;
                LPuntuacionCifra.setText(puntuacion + "");
            } else {
                contador = 0;
                animacionPuntajeEM.stop();
            }
        }
    };

    KeyListener tecla = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

            try {
                if (e.getKeyChar() == '\n' || e.getKeyChar() == ' ') {
                    if (tPalabra.getText().equals(primero.getEtiqueta().getText())) {
                        animacionPuntajeA.start();//Añade 100 puntos 
                        tPalabra.setText(null);
                        primero.getEtiqueta().setVisible(false);
                        panel2.remove(primero.getEtiqueta());
                        removerDeLaLista();

                    } else {
                        tPalabra.setText(null);                        
                        animacionPuntajeEm.start();
                        animacionDeError.start();
                        tiempoDeEspera.start();
                        errorMenor += 10;

                    }
                }
            } catch (NullPointerException evt) {

            }

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    };

    ActionListener crear = new ActionListener() {
        int text, y;

        @Override
        public void actionPerformed(ActionEvent e) {
            int dificultad = CBniveles.getSelectedIndex();
            JLabel etiqueta = new JLabel();

            switch (dificultad) {
                case 0:
                    etiqueta.setText(palabrasRandomNivel1() + "");
                    break;
                case 1:
                    etiqueta.setText(palabrasRandomNivel2());
                    break;

                case 2:
                    etiqueta.setText(palabrasRandomNivel3());
                    break;
                case 3:
                    etiqueta.setText(palabrasRandomNivel4());
                    break;

                default:
                    throw new AssertionError();
            }
            ListaDeEtiquetas nodo = new ListaDeEtiquetas(etiqueta);
            int tamaño = nodo.getEtiqueta().getText().length() * 17;
            panel2.add(etiqueta);
            if (primero == null) {
                primero = nodo;
                ultimo = nodo;
                nodo.getEtiqueta().setBounds(xRandom(tamaño), -10, tamaño, 45);
                // nodo.getEtiqueta().setForeground(Color.blue);
                nodo.getEtiqueta().setFont(new Font("arial", 0, 20));
                panel2.add(nodo.getEtiqueta());

            } else {
                ultimo.setSiguiente(nodo);
                ultimo = nodo;
                nodo.getEtiqueta().setBounds(xRandom(tamaño), -10, tamaño, 45);
                nodo.getEtiqueta().setFont(new Font("arial", 0, 20));
                //primero.getEtiqueta().setForeground(Color.blue);
                panel2.add(nodo.getEtiqueta());

            }

            // imprimir();
        }
    };

    ActionListener animar = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            ListaDeEtiquetas aux = primero;
            try {
                primero.getEtiqueta().setForeground(Color.blue);
            } catch (NullPointerException evt) {

            }

            // System.out.println("Bajando");           
            while (aux != null) {

                aux.getEtiqueta().setLocation(aux.getEtiqueta().getX(), aux.getEtiqueta().getY() + 2);
                if (aux.getEtiqueta().getY() + aux.getEtiqueta().getHeight() >= panel2.getHeight() + 15) {
                    aux.getEtiqueta().setVisible(false);
                    animacionPuntajeEM.start();
                    panel2.remove(aux.getEtiqueta());
                    removerDeLaLista();
                    animacionDeError.start();
                    tiempoDeEspera.start();
                    errorMayor += 50;

                }
                aux = aux.getSiguiente();
            }
            if (puntuacion <= 0) {
                LPuntuacionCifra.setText("0");
                t.stop();
                animacion.stop();
                animacionPuntajeEM.stop();
                animacionPuntajeEm.stop();
                JOptionPane.showMessageDialog(null, "Haz perdido :(");
                int opc = JOptionPane.showConfirmDialog(null, "¿Quieres volver a jugar?");
                switch (opc) {
                    case 0:
                        puntuacion = 500;
                        LPuntuacionCifra.setText("500");
                        aux = primero;
                        while (aux != null) {
                            panel2.remove(aux.getEtiqueta());
                            aux.getEtiqueta().setVisible(false);
                            aux = aux.getSiguiente();
                        }
                        panel2.repaint();
                        primero = null;
                        CBniveles.setEnabled(true);
                        CBvelocidad.setEnabled(true);
                        BIniciar.setEnabled(true);
                        errorMenor=0;
                        errorMayor=0;
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(null, "Gracias por jugar ^^");
                        cerrarVentana();
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(null, "Gracias por jugar ^^");
                        cerrarVentana();
                        break;
                    default:
                    //No se necesita decir nada 
                }

            }
        }
    };

    ActionListener error = new ActionListener() {
        int cont;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (cont % 2 == 0) {
                // panel2.setBorder(BorderFactory.createLineBorder(Color.red, 2));
                panel2.setLocation(panel2.getX() - 3, panel2.getY());
            } else {
                // panel2.setBorder(BorderFactory.createLineBorder(Color.blue, 2));     
                panel2.setLocation(panel2.getX() + 3, panel2.getY());
            }
            tPalabra.setBorder(BorderFactory.createLineBorder(Color.red, 2));
            cont++;
        }
    };

    ActionListener esperar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            animacionDeError.stop();
            panel2.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
            tPalabra.setBorder(tBorde);
            panel2.setLocation(Dpanel2);

        }
    };

    ActionListener actionPerformedBInicar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int velocidad = CBvelocidad.getSelectedIndex();
            switch (velocidad) {
                case 0:
                    brinco = 100;
                    t.setDelay(3000);
                    break;
                case 1:
                    brinco = 70;
                    t.setDelay(2500);
                    break;
                case 2:
                    brinco = 40;
                    t.setDelay(2000);
                    break;
                case 3:
                    brinco = 10;
                    t.setDelay(2000);
                    break;

                default:

            }
            t.start();
            animacion.setDelay(brinco);
            animacion.setInitialDelay(200);
            animacion.start();
            CBniveles.setEnabled(false);
            CBvelocidad.setEnabled(false);
            BIniciar.setEnabled(false);
        }
    };
    
    ActionListener BComojugarActionPerformed = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            v= new VentanaComojugar();
        }
    };
 //Métodos

    public int xRandom(int tamaño) {
        int limiteSuperior = panel2.getWidth() - tamaño;

        int r = (int) (Math.random() * (limiteSuperior - 0 + 1) + 0);
        if (r > 0 && r < limiteSuperior) {
            return r;
        } else {
            return xRandom(tamaño);
        }
    }

    public void removerDeLaLista() {
        ListaDeEtiquetas aux = null;
        aux = primero;
        primero = primero.getSiguiente();
        aux = null;
    }

    public char letrasRandom() {
        char simbolo;
        int numeroRandom = (int) (Math.random() * (122 - 65 + 1) + 65);
        //System.out.println(numeroRandom);
        if ((numeroRandom >= 65 && numeroRandom <= 90) || (numeroRandom >= 97 && numeroRandom <= 122)) {
            simbolo = (char) (numeroRandom);
            return simbolo;
        } else {
            return letrasRandom();
        }

    }

    public char palabrasRandomNivel1() {

        return letrasRandom();
    }

    public String palabrasRandomNivel2() {
        String palabra = "";
        int opc = compararTipoDeCaracter(letrasRandom()) != 'v' ? 0 : 1;

        do {
            switch (opc) {
                case 0:
                    palabra = palabra + getRandomConsonant();
                    opc = 1;
                    break;

                case 1:
                    palabra = palabra + getRandomVowel();
                    opc = 0;
                    break;
                default:
                    opc = 0;
            }

        } while (palabra.length() < 4);

        if (compararTipoDeCaracter(palabra.charAt(palabra.length() - 1)) != 'v') {
            palabra = palabra + getRandomVowel();
        }

        return palabra;
    }

    public String palabrasRandomNivel3() {
        String palabra = "";
        int opc = compararTipoDeCaracter(letrasRandom()) != 'v' ? 0 : 1;

        do {
            switch (opc) {
                case 0:
                    palabra = palabra + getRandomConsonant();
                    opc = 1;
                    break;

                case 1:
                    palabra = palabra + getRandomVowel();
                    opc = 0;
                    break;
                default:
                    opc = 0;
            }

        } while (palabra.length() < 6);

        if (compararTipoDeCaracter(palabra.charAt(palabra.length() - 1)) != 'v') {
            palabra = palabra + getRandomVowel();
        }

        return palabra;
    }

    public String palabrasRandomNivel4() {
        int tablaTransicion[][] = {{1, 2, 3, 100},
        {100, 2, 3, 100},
        {4, 100, 100, 100},
        {4, 2, 3, 100},
        {1, 2, 3, 101}};
        int estado = 0, entrada = 0, tamaño;
        char csimbolo, ctipo;
        String spalabra = "";
        csimbolo = letrasRandom();
        do {
            ctipo = compararTipoDeCaracter(csimbolo);
            tamaño = spalabra.length();
            if (tamaño == 0) {
                tamaño++;
                spalabra = spalabra + csimbolo;
            }

            switch (ctipo) {
                case 'v'://Convierte las vocales en consonantes
                    entrada = 0;
                    csimbolo = getRandomConsonant();
                    spalabra = spalabra + csimbolo;
                    estado = tablaTransicion[estado][entrada];
                    break;
                case 'c'://Convierte las consonantes en vocales
                    entrada = 1;
                    csimbolo = getRandomVowel();
                    spalabra = spalabra + csimbolo;
                    ctipo = 'v';
                    estado = tablaTransicion[estado][entrada];
                    break;
                case 't'://añade silabas como son bra , dra , ect.
                    entrada = 2;
                    csimbolo = getRandomTerriente(spalabra.charAt(tamaño - 1));
                    spalabra = spalabra + csimbolo;
                    estado = tablaTransicion[estado][entrada];
                    break;
                default:
                    estado = 100;
            }

        } while (estado != 100 && tamaño <= 8);

        csimbolo = spalabra.charAt(spalabra.length() - 1);
        if (csimbolo != 'a' && csimbolo != 'e' && csimbolo != 'i' && csimbolo != 'o' && csimbolo != 'u') {
            spalabra = spalabra + getRandomVowel();
        }

        return spalabra;
    }

    public char compararTipoDeCaracter(char csimbolo) {
        char ctipo;
        if (Character.isUpperCase(csimbolo)) {
            Character.toLowerCase(csimbolo);
        }
        if (csimbolo == 'a' || csimbolo == 'e' || csimbolo == 'i' || csimbolo == 'o' || csimbolo == 'u') {
            ctipo = 'v';
        } else if (csimbolo == 'b' || csimbolo == 't' || csimbolo == 'd' || csimbolo == 'g' || csimbolo == 'c') {
            ctipo = 't';
        } else {
            ctipo = 'c';
        }

        return ctipo;
    }

    public char getRandomConsonant() {
        char consonantes[] = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'z', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y'};
        int random = (int) (Math.random() * 20 - 0 + 1) + 0;

        return consonantes[random];
    }

    public char getRandomVowel() {
        char vocales[] = {'a', 'e', 'i', 'o', 'u'};
        int random = (int) (Math.random() * 4 - 0 + 1) + 0;
        return vocales[random];
    }

    public char getRandomTerriente(char ultimalLetra) {
        char RLs[] = {'r', 'l'};
        if (ultimalLetra == 'd') {
            int random = (int) (Math.random() * 1 - 0 + 1) + 0;
            return random == 1 ? 'r' : getRandomVowel();
        } else {
            int rand = (int) (Math.random() * 2 - 0 + 1) + 0;
            return rand == 2 ? getRandomVowel() : RLs[rand];

        }

    }

    public void cerrarVentana() {
        this.dispose();
    }

}
