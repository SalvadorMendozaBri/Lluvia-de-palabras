/*
Ventana que explica como jugar al juego de lluvia de palabras
 */
package lluviadepalabras;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;


public class VentanaComojugar extends JDialog {
    private JTextArea Texto;
    private JScrollPane scroll;
    
    
    
    public VentanaComojugar(){
        this.setSize(650, 400);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setTitle("Como jugar");
        this.setVisible(true);
        IniciarComponentes();
        }
    
    
    public void IniciarComponentes(){
       
         
        Texto = new JTextArea();
        File archivo=new File("Como jugar.txt");
        try{
        BufferedReader leer = new BufferedReader(new FileReader(archivo));
        String linea=leer.readLine();
        while(linea!=null){
            Texto.append(linea+"\n");
            linea = leer.readLine();
        }
        }catch(Exception ext){
            System.out.println("No sirve el archivo :(");
        }
        Texto.setFont(new Font("Microsoft YaHei", 0,16));
        Texto.setBounds(0, 0, this.getWidth(), this.getHeight());
        Texto.setEditable(false);
        //this.getContentPane().add(Texto);
        
        scroll =new JScrollPane(Texto);
        scroll.setBounds(0, 0, this.getWidth()-20, this.getHeight()-40);
        this.getContentPane().add(scroll);
    }
}
