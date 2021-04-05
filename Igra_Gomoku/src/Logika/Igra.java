package Logika;

public class Igra {
	
	// velikost igralne plošče
	public static final int Velikost = 15;
	private static Polje[][] plosca;  //igralno polje
	public static Igralec naPotezi; // kdor je na potezi

	
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
	
	
	
	public static void naslednji() {
		// spremeni igralca, ki je napotezi
		if (naPotezi.equals(Igralec.X)) {
			naPotezi = Igralec.O;	
		} else {
			naPotezi = Igralec.X;	
		}
	}
	
	
	public static boolean jeLegalna(int vrsta, int stolpec) {
		// preveri če je poteza legalna
		if (plosca[vrsta][stolpec].equals(Polje.PRAZNO) && vrsta < 15 && stolpec < 15) {
			return true;
		}else {
			return false;
		}
		
		
	}
	
	public static Polje naPoteziPolje(Igralec igralec) {
		if (naPotezi.equals(Igralec.O)){
			return Polje.O;
		}else {
			return Polje.X;
		}
		
	}
	
	
	public static void poteza(int vrsta, int stolpec) {
		// odigra potezo, spremeni kdo je napotezi
		
		if(jeLegalna(vrsta, stolpec)) {
			plosca[vrsta][stolpec] = naPoteziPolje(naPotezi);
			naslednji();
		}
		
	}
		
	
	
	
}
