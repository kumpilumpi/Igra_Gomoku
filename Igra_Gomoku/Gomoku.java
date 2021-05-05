import vmesniki.Okno;
import vodja.Vodja;

public class Gomoku {

	public static void main(String[] args) {
		Okno okno = new Okno();
		okno.pack();
		okno.setVisible(true);
		okno.osvezi();
		Vodja.okno = okno;
	}
}
