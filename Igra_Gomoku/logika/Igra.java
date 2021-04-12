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
		 *  in za preverjanje kdaj bo konec igre.0000000000
		 *  če shranjujemo poteze lahko preverjamo kje je pet v vrsto samo na zadnji potezi.
		 */
		 
	
	// Konstruktorja ========================================= 
	
	public Igra() {
		this(15);
	}
	
	public Igra(int n) {
		velikost = n;	
		this.stanje = Stanje.V_TEKU;
		odigranePoteze = new LinkedList<Koordinati>(); // usvtari prazen seznam
				
				plosca = new Polje[velikost][velikost];
				
				for (int i = 0; i < velikost; i++) {
					for (int j = 0; j < velikost; j++) {
						plosca[i][j] = Polje.PRAZNO;
					}
				}
				naPotezi = Igralec.O;
	}

	// ================ Komentarji =========================
	
	/**
	 * Koordinatah( koordinate poteze) je koordinata.y vrsta, koordinata.x stolpec
	 * (0,0) je levi zgornji kot plosce
	 * Ko dostopamo do Polja v seznamu plosca je prvi indeks vrsta(Y), drugi indeks stolpec(X)
	 */
	
	/**
	 * Vprašanja?
	 * 
	 * Ali je pri primerjanju objekta tipa Polje vredu == ali potrebno .equals() ?
	 * 
	 * static? -> dostopanje na statičen način ?? 
	 * 
	 */
	
	// =========================================
	
	
	// Pet v vrsto =======================================
	// Na pol preverjeno - nisem še najdel napake, pa tud nism siguren, da je brez napak
	
	public int pozitivna(int x) { return (x < 0) ? 0 : x ; } // pomozna funkcija, če je negativni int vrne 0, drugače int
	
	public boolean pomozna (int v0, int s0, int dv, int ds, Polje primerjava) {
	/**
	 * v0 - začetna vrstica / s0 - zacetni stolpec / dv - sprememba vrstica / ds sprememba stolpca / kdo - za kogar preverja ali je pet v vrsto
	 * Preveri če se od danega indeksa v devetih korakih v dani smeri pojavi 5 v vrsto
	 */
		
		int zaporedni = 0; //steje kolikokrat se zaporedoma pojavi iskano polje
		
		for( int i = 0; i < 9; i++ ) {
			
			try {
				if(plosca[v0 + i*dv][s0 + i*ds].equals(primerjava)) {
					++zaporedni;
				}else{
					if(zaporedni > 4) {
						return true;
					}else{
						zaporedni = 0;
					}
				}
			}catch(IndexOutOfBoundsException e){
				continue;
			}
		}
		if(zaporedni > 4) {
			return true;
		}else{
			return false;
		}
	}
	
	
	public boolean petVrsta() {
		/**
		 * Preveri, če je zadnja odigrana poteza postavila pet v vrsto
		 */
		
		Koordinati poteza = odigranePoteze.getLast();
		
		int v0 = poteza.getY();
		int s0 = poteza.getX();
		Polje primerjava = plosca[poteza.getY()][poteza.getX()];		
		
		//stolpec : zacetek (-4,0) sprememba (1,0)
		if (pomozna(v0 - 4, s0, 1, 0, primerjava)) { 	// Kordinati.Y -> Vrstica
			//System.out.print(false);
			return true;			
		}
		//vrstica : zacetek (0,-4) spermemba (0,1)
		if (pomozna(v0, s0 -4, 0, 1, primerjava)) {
			return true;			
		}
		//leva diagonala : zacetek (-4,-4) sprememba (1,1)
		if (pomozna(v0 - 4, s0 - 4, 1, 1, primerjava)) {
			return true;			
		}
		//desna diagonala : zecetek (-4,4) sprememba (1,-1)
		if (pomozna(v0 - 4, s0 + 4, 1, -1, primerjava)) {
			return true;			
		}
		else {
			return false;
		}		
	}
	
	public void stanje() { 
	/**
	 * posodobi igra.stanje -> ZMAGA_X, ZMAGA_O, NEODLOCENO
	 */
		
		if(this.petVrsta()) {
			Koordinati poteza = odigranePoteze.getLast();
			Polje primerjava = plosca[poteza.getY()][poteza.getX()];
			
			this.stanje = (primerjava.equals(Polje.O)) ? Stanje.ZMAGA_O : Stanje.ZMAGA_X ;
		}
		if (odigranePoteze.size() == velikost * velikost) {
			this.stanje = Stanje.NEODLOCENO;
		}
	}
	
	// =========================================
	
	
	public static void naslednji() {
		// spremeni igralca, ki je napotezi
		if (naPotezi.equals(Igralec.X)) {
			naPotezi = Igralec.O;	
		} 
		else naPotezi = Igralec.X;
	}
	
	
	public static boolean jeLegalna(Koordinati poteza) {
		// preveri če je poteza legalna
		if (plosca[poteza.getY()][poteza.getX()].equals(Polje.PRAZNO)) {
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
				if (plosca[x][y].equals(Polje.PRAZNO)) {
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
		plosca[poteza.getY()][poteza.getX()] = Polje.PRAZNO;
	}
		

}
