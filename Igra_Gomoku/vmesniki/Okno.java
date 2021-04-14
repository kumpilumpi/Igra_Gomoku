package vmesniki;

//Okno.java - main program za igro 5 v vrsto
import javax.swing.JFrame;


class Okno {
 //================================================ main metoda
	public static void main(String[] args) {
		JFrame okno = new JFrame("Pet v vrsto");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setContentPane(new Platno());
		okno.pack();
		okno.setResizable(false);
		okno.setVisible(true);
	}
}
