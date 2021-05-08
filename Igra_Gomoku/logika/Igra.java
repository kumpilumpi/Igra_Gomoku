package logika;

import java.util.LinkedList;

import splosno.Koordinati;

public class Igra {
	
	// VELIKOST igralne plošče
	public static int velikost;
	
	public Polje[][] plosca;  //igralno polje
	
	public Igralec naPotezi; // kdor je na potezi
	
	public Stanje stanje;
	
	public LinkedList<Koordinati> odigranePoteze;
	
	public LinkedList<Koordinati> moznePoteze;
	
		/**
		 *  mogoče probamo beležit poteze, lahko uporabimo za razveljavitev poteze
		 *  in za preverjanje kdaj bo konec igre
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
		moznePoteze = new LinkedList<Koordinati>(); // usvtari prazen seznam
		
		for ( int x = 0; x < velikost; x++ ) {     //napolni mozne
			for ( int y = 0; y < velikost; y++ ) {
				moznePoteze.add(new Koordinati(x,y));
			}
		}
		
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
	 * Bo to problem? Če prav razumem static pomeni, da je za vse instance of an object ista spremenljivka ali funkcija
	 * 
	 * Linkedlist.size() -> časovna zahtevnost?  ==> O(1), ni problem
	 * 
	 * 
	 */
	
	// =========================================
	
	
	
	// Pet v vrsto =======================================
	// Dela
	
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
		return (zaporedni > 4) ? true : false;
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
	
	//========================= Mogoče malo hitrejše iskanje pet v vrsto
	//zgleda da deluje
	
	public boolean pomozna2 (int v0, int s0, int dv, int ds) {
		
		Polje primerjava = plosca[v0][s0];
		int zaporedni = 1;
		
		
		int[] smeri = new int[] {1,-1};
		
		for (int smer : smeri) { //preveri naprej in nazaj
			
			int i = 1;
			dv = dv * smer;
			ds = ds * smer;
			try {
				while((zaporedni < 5) && (plosca[v0 + i*dv][s0 + i*ds].equals(primerjava))) {
				i++;
				zaporedni++;
				}
			}catch (IndexOutOfBoundsException e) {
				continue;
			}
			
		}
		
		return (zaporedni < 5) ? false : true;	
	}
	//this is a change
	public boolean petVrsta2() {
		Koordinati poteza = odigranePoteze.getLast();
		int v0 = poteza.getY();
		int s0 = poteza.getX();
		
		int[][] smeri = new int[][] {{1,0},{0,1},{1,1},{1,-1}};
		
		for (int[] smer : smeri) {
			if (pomozna2(v0, s0, smer[0], smer[1])) {
				return true;
			}
		}
		
		return false;
	}

//========
	
	public void stanje() { 
	/**
	 * posodobi igra.stanje -> ZMAGA_X, ZMAGA_O, NEODLOCENO
	 */
		
		if(petVrsta2()) {
			Koordinati poteza = odigranePoteze.getLast();
			Polje primerjava = plosca[poteza.getY()][poteza.getX()];
			
			stanje = (primerjava.equals(Polje.O)) ? Stanje.ZMAGA_O : Stanje.ZMAGA_X ;
		}
		if (odigranePoteze.size() == velikost * velikost) {
			stanje = Stanje.NEODLOCENO;
		}
	}
	
	// =========================================
	
	
	public void naslednji() {
		// spremeni igralca, ki je napotezi
		if (naPotezi.equals(Igralec.X)) {
			naPotezi = Igralec.O;	
		} 
		else naPotezi = Igralec.X;
	}
	
	
	public boolean jeLegalna(Koordinati poteza) {
		// preveri če je poteza legalna
		if (plosca[poteza.getY()][poteza.getX()].equals(Polje.PRAZNO) && poteza.getX() < 15 && poteza.getY() < 15) {
			return true;
		}
		else return false;
	}
	
	
	public Polje naPoteziPolje(Igralec igralec) {
		// pretvori Igralec.O -> Polje.O
		return (naPotezi.equals(Igralec.O)) ? Polje.O : Polje.X;
	}
	
	
	public boolean poteza(Koordinati poteza) { 
		/**
		 * javno metodo boolean odigraj(Koordinati koordinati), 
		 * ki odigra potezo razreda Koordinati, če je možna. 
		 * Metoda vrne true, če je poteza možna, sicer pa false.alca
		 */
		
		if(jeLegalna(poteza)) {
			plosca[poteza.getY()][poteza.getX()] = naPoteziPolje(naPotezi); //zamenjal getX in getY
			naslednji();
			odigranePoteze.add(poteza); // doda odigrano potezo na seznam vseh odigranih potez
			moznePoteze.remove(poteza); // odstrani odigrano potezo s seznama vseh moznih potez
			stanje();
			return true;
		}
		else return false;
	}
	
	public void razveljaviPotezo() {
		// meotda predlagana na spletni pod opisom projekta
		// kortistna bo ko bomo začeli delati na računalniškem vmesniku
		if(!odigranePoteze.isEmpty() && stanje.equals(Stanje.V_TEKU)) {
			Koordinati poteza = odigranePoteze.getLast();
			odigranePoteze.removeLast();		
			plosca[poteza.getY()][poteza.getX()] = Polje.PRAZNO;
			naslednji();
		}
		return;
	}
}
