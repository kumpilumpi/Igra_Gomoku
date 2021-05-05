package vmesniki;

//Okno.java - main program za igro 5 v vrsto
import javax.swing.JFrame;


public class Okno {
	
	public static JFrame okno;
	
	public Okno() {
		super();
		JFrame okno = new JFrame("Pet v vrsto");
	}
	
 //================================================ main metoda
	public static void main(String[] args) {
		JFrame okno = new JFrame("Pet v vrsto");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setContentPane(new Platno());
		okno.pack();
		okno.setResizable(false);
		okno.setVisible(true);
	}
	
	public void osvezi() {
		okno.repaint(); // ???
	}
}
