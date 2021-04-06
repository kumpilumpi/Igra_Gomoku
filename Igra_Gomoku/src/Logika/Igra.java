package Logika;

import java.util.LinkedList;

public class Igra {
	
	// VELIKOST igralne plošče
	public static final int VELIKOST = 15;
	private static Polje[][] plosca;  //igralno polje
	private static Igralec naPotezi; // kdor je na potezi
	
	public Stanje stanje; 
	
	public static LinkedList<Poteza> odigranePoteze; // ?????????????? 
		/**
		 *  mogoče probamo beležit poteze, lahko uporabimo za razveljavitev poteze
		 *  in za preverjanje kdaj bo konec igre.
		 *  če shranjujemo poteze lahko preverjamo kje je pet v vrsto samo na zadnji potezi.
		 */
		 
	
	// =========================================
	
	public Igra() {
		
		this.stanje = Stanje.V_TEKU;
		
		plosca = new Polje[VELIKOST][VELIKOST];
		
		for (int i = 0; i < VELIKOST; i++) {
			for (int j = 0; j < VELIKOST; j++) {
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
	
	public Stanje stanje() { // imamo tudi spremenljivko stanje
	// Posodobi stanje igre
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
		// pretvori Igralec.O -> Polje.O
		if (naPotezi.equals(Igralec.O)){
			return Polje.O;
		}else {
			return Polje.X;
		}
	}
	
	
	public static void poteza(int vrsta, int stolpec) {
		// naredi potezo, spremeni kdo je napotezi
		// legalnost poteze bo potrebno preverjati drugje, v vmesniku, ko poberemo potezo od igralca
		
		if(jeLegalna(vrsta, stolpec)) {
			plosca[vrsta][stolpec] = naPoteziPolje(naPotezi);
			naslednji();
		}
	}
	
	
	public static int[][] moznePoteze() {
		/**
		 * Vrne seznam možnih potez. V obliki int int list, kjer je notranji seznam
		 * seznam parov števil [vrsta, stolpec] ???
		 * Ali se splača uvesti class koordiant, ki ima dva atributa x,y kot vrsta stolpec??
		 */
		int[][] mozne = new int[VELIKOST * VELIKOST][2];
		
		
		return mozne;
	}
	
	public static void razveljaviPotezo() {
		// meotda predlagana na spletni pod opisom projekta
	}
		

}
