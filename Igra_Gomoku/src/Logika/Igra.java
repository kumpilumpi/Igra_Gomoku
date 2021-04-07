package Logika;

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
		 
	
	// ========================================= konstruktorji
	
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
		// preveri, če je kje pet v vrsto 
		
		return false;
	}
	
	public Stanje stanje() { 
	// imamo tudi spremenljivko stanje
	// Posodobi stanje igre
		return Stanje.V_TEKU;
		
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
		 * Metoda naj vrne true, če je poteza možna, sicer pa false.alca
		 */
		
		if(jeLegalna(poteza)) {
			plosca[poteza.getX()][poteza.getY()] = naPoteziPolje(naPotezi);
			naslednji();
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
	}
		

}
