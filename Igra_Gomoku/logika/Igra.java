package logika;

import java.util.LinkedList;

import splosno.Koordinati;

public class Igra {
	
	// VELIKOST igralne plošče
	public static int velikost;
	public  static Polje[][] plosca;  //igralno polje
	private static Igralec naPotezi; // kdor je na potezi
	
	public Stanje stanje;
	
	public static LinkedList<Koordinati> odigranePoteze;
	
		/**
		 *  mogoče probamo beležit poteze, lahko uporabimo za razveljavitev poteze
		 *  in za preverjanje kdaj bo konec igre.
		 *  če shranjujemo poteze lahko preverjamo kje je pet v vrsto samo na zadnji potezi.
		 */
		 
	
	// ========================================= konstruktorja
	
	public Igra() {
		this(15);
	}
	
	public Igra(int n) {
		velikost = n;
		this.stanje = Stanje.V_TEKU;
				
				plosca = new Polje[velikost][velikost];
				
				for (int i = 0; i < velikost; i++) {
					for (int j = 0; j < velikost; j++) {
						plosca[i][j] = Polje.PRAZNO;
					}
				}
				
				naPotezi = Igralec.O;
		
	}

	// =========================================
	
	public boolean petVrsta() {
		// preveri, če je zadnja odigrana poteza kje postavila pet v vrsto
		Koordinati poteza = odigranePoteze.getLast();
		
		for ( int i = 0 ; i < 5 ; i++) { // vse začetne pozicije
			try { // problem če bo IndexOutOfBounds
				
				//stolpec
				for (int j = 1; i<5; i++) {
					if(plosca[poteza.getX() + i][poteza.getY()] == plosca[poteza.getX() + i + 1][poteza.getY()]) {
						return true;
					}
				}
				
				
			}finally {
				continue;
			}
			
		}
			return false;	
		
	}
	
	public void stanje() { 
	// imamo tudi spremenljivko stanje
	// Posodobi stanje igre
		
	}
	
	
	public static void naslednji() {
		// spremeni igralca, ki je napotezi
		if (naPotezi.equals(Igralec.X)) {
			naPotezi = Igralec.O;	
		} 
		else naPotezi = Igralec.X;
	}
	
	
	public static boolean jeLegalna(Koordinati poteza) {
		// preveri če je poteza legalna
		if (plosca[poteza.getX()][poteza.getY()].equals(Polje.PRAZNO) && poteza.getX() < 15 && poteza.getY() < 15) {
			return true;
		}
		else return false;
	}
	
	
	public static Polje naPoteziPolje(Igralec igralec) {
		// pretvori Igralec.O -> Polje.O
		if (naPotezi.equals(Igralec.O)){
			return Polje.O;
		}
		else return Polje.X;
	}
	
	
	public static boolean poteza(Koordinati poteza) { 
		/**
		 * javno metodo boolean odigraj(Koordinati koordinati), 
		 * ki odigra potezo razreda Koordinati, če je možna. 
		 * Metoda vrne true, če je poteza možna, sicer pa false.alca
		 */
		
		if(jeLegalna(poteza)) {
			plosca[poteza.getY()][poteza.getX()] = naPoteziPolje(naPotezi); //zamenjal getX in getY
			naslednji();
			odigranePoteze.add(poteza); // doda odigrano potezo na seznam vseh odigranih potez
			return true;
		}
		else return false;
	}
	
	
	public static LinkedList<Koordinati> moznePoteze() {
		/**
		 * Vrne vse mozne poteze kot seznam koordinat
		 */
		
		LinkedList<Koordinati> mozne = new LinkedList<Koordinati>();
		
		for ( int x = 0; x < velikost; x++ ) {
			for ( int y = 0; y < velikost; y++ ) {
				if (plosca[x][y] == Polje.PRAZNO) {
					mozne.add(new Koordinati(x,y));
				}
			}
		}
		return mozne;
	}
	
	
	public static void razveljaviPotezo() {
		// meotda predlagana na spletni pod opisom projekta
		// kortistna bo ko bomo začeli delati na računalniškem vmesniku
		
		Koordinati poteza = odigranePoteze.getLast();
		odigranePoteze.removeLast();		
		plosca[poteza.getX()][poteza.getY()] = Polje.PRAZNO;
	}
		

}
