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
		 
	
	// Konstruktorja ========================================= 
	
	public Igra() {
		this(15);
	}
	
	public Igra(int n) {
		velikost = n;	
		this.stanje = Stanje.V_TEKU;
		this.odigranePoteze = new LinkedList<Koordinati>(); // usvtari prazen seznam
				
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
	 */
	
	// =========================================
	
	
	// Pet v vrsto =============================
	// Ni še preverjeno
	
	public boolean pomozna (int v0, int s0, int dv, int ds, Polje kdo) {
	/**
	 * v0 - začetna vrstica / s0 - zacetni stolpec / dv - sprememba vrstica / ds sprememba stolpca / kdo - za kogar preverja ali je pet v vrsti
	 * Preveri če se od danega indeksa v devetih korakih v dani smeri pojavi 5 v vrsto
	 */
		
		int zaporedni = 0; //steje kolikokrat se zaporedoma pojavi iskano polje
		
		for( int i = 0; i < 9; i++ ) {
			
			try {
				if(plosca[v0 + i*dv][s0 + i*ds].equals(kdo)) {
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
		return false;
	}
	
	
	public boolean petVrsta() {
		/**
		 * Preveri, če je zadnja odigrana poteza postavila pet v vrsto
		 */
		
		Koordinati poteza = odigranePoteze.getLast();
		Polje kdo = plosca[poteza.getY()][poteza.getX()];
		int v0 = poteza.getY() - 4;
		if(v0 < 0) {v0 = 0;} // če negativna nastavi na 0
		int s0 = poteza.getX() - 4;
		if(s0 < 0) {s0 = 0;}
		
		//stolpec
		if(pomozna(poteza.getX() - 4, poteza.getY() - 4, 1, 0, kdo)) {
			return true;			
		}
		//vrstica
		if(pomozna(poteza.getX() - 4, poteza.getY() - 4, 0, 1, kdo)) {
			return true;			
		}
		//leva diagonala (levo zgoraj)
		if(pomozna(poteza.getX() - 4, poteza.getY() - 4, 1, 1, kdo)) {
			return true;			
		}
		//desna diagonala
		if(pomozna(poteza.getX() - 4, poteza.getY() - 4, 1, -1, kdo)) {
			return true;			
		}
		else {
			return false;
		}		
	}
	
	// =========================================
	
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
