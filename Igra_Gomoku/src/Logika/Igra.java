package Logika;

public class Igra {
	
	// velikost igralne plošče
	public static final int Velikost = 15;
	private Polje[][] plosca;
	public Igralec naPotezi;

	
	public Igra() {
		
		plosca = new Polje[Velikost][Velikost];
		for (int i = 0; i < Velikost; i++) {
			for (int j = 0; j < Velikost; j++) {
				plosca[i][j] = Polje.PRAZNO;
			}
		}
		
		naPotezi = Igralec.O;
		
	}
	
	
	public Stanje stanje() { 
	// kakšno je stanje igre
		
		return Stanje.V_TEKU;
		
	}
	
	
	
	public void naslednji() {
		// spremeni igralca, ki je napotezi
		if (this.naPotezi.equals(Igralec.X)) {
			this.naPotezi = Igralec.O;			
		} else {
			this.naPotezi = Igralec.X;	
		}
		
	}
	
	
	
	public static void poteza(int vrsta, int stolpec) {
		// odigra potezo
		
	}
	
	
	
}
