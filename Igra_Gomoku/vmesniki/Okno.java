package vmesniki;

//Okno.java - main program for Five-In-A-Row Program
import javax.swing.JFrame;


class Okno {
 //================================================ method main
 public static void main(String[] args) {
     JFrame window = new JFrame("Pet v vrsto");
     window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     window.setContentPane(new Platno());
     window.pack();  // finalize layout
     window.setResizable(false);
     window.show();  // make window visible
 }//end main
}//endclass Five
