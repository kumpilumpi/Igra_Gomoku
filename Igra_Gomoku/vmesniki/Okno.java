package vmesniki;

//Okno.java - main program for Five-In-A-Row Program
import javax.swing.JFrame;


class Okno {
 //================================================ method main
public static void main(String[] args) {
     JFrame okno = new JFrame("Pet v vrsto");
     okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     okno.setContentPane(new Platno());
     okno.pack();  // finalize layout
     okno.setResizable(false);
     okno.setVisible(true);  // make window visible
 }//end main
}//endclass Okno
