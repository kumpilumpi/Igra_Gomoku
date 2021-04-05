package Logika;

public class Igra {
	
	// velikost igralne plošče
	public static final int Velikost = 15;
	private Polje[][] plosca;  //igralno polje
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

	// =========================================
	
	public boolean petVrsta() {
		// preveri, če je kje pet v vrsto 
		
		return false;
		
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
	
	
	public boolean jeLegalna(int vrsta, int stolpec) {
		// preveri če je poteza legalna
		if (plosca.
		return true;
		
	}
	
	
	public static void poteza(int vrsta, int stolpec) {
		// odigra potezo, spremeni kdo je napotezi
		
	}
	
	
	
}
