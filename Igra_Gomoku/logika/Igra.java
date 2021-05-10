package logika;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import splosno.Koordinati;

public class Igra {
	
	// VELIKOST igralne plošče
	public static int velikost = 7; // tukaj se nastavi velikost igre
	
	public Polje[][] plosca;  //igralno polje
	
	public Igralec naPotezi; // kdor je na potezi
	
	public Stanje stanje;
	
	public LinkedList<Koordinati> odigranePoteze;

	public LinkedList<Koordinati> moznePoteze;
	
	public Set<Koordinati> kanditatiPoteze = new HashSet<Koordinati>(); // Kandidati za inteligenco
	
		/**
		 *  mogoče probamo beležit poteze, lahko uporabimo za razveljavitev poteze
		 *  in za preverjanje kdaj bo konec igre
		 *  če shranjujemo poteze lahko preverjamo kje je pet v vrsto samo na zadnji potezi.
		 */
		 
	
	// Konstruktorji ========================================= 
	
	public Igra() {
		this(velikost);
	}
	
	public Igra(int n) {
		velikost = n;	
		this.stanje = Stanje.V_TEKU;
		odigranePoteze = new LinkedList<Koordinati>(); // usvtari prazen seznam
		
		moznePoteze = new LinkedList<Koordinati>(); // usvtari prazen seznam // prekopirano main
		
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

	// ustvari kopijo igre
	
	public Igra(Igra igra) {
		this.moznePoteze = new LinkedList<Koordinati>();
		this.plosca = new Polje[Igra.velikost][Igra.velikost];
		for (int i = 0; i < Igra.velikost; i++) {
			for (int j = 0; j < Igra.velikost; j++) {
				// dodano 
				if(igra.plosca[i][j] == Polje.PRAZNO) this.moznePoteze.add(new Koordinati(i,j)); //??
				this.plosca[i][j] = igra.plosca[i][j];
			}
		}
		this.naPotezi = igra.naPotezi;
		
		//popravljeno
		
//		this.moznePoteze = igra.moznePoteze; -> moramo drugače incilizirat ker se med zanko drugače spremeni in javi napako
		
		this.odigranePoteze = igra.odigranePoteze;
		this.stanje = igra.stanje;
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
	
	public boolean pomozna (int v0, int s0, int dv, int ds) {
		
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
	
	public boolean petVrsta() {
		Koordinati poteza = odigranePoteze.getLast();
		int v0 = poteza.getY();
		int s0 = poteza.getX();
		
		int[][] smeri = new int[][] {{1,0},{0,1},{1,1},{1,-1}};
		
		for (int[] smer : smeri) {
			if (pomozna(v0, s0, smer[0], smer[1])) {
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
		
		if(petVrsta()) {
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
		
		if (poteza == null) return false; // <-DODANO
		
		if (plosca[poteza.getY()][poteza.getX()].equals(Polje.PRAZNO) && poteza.getX() < 15 && poteza.getY() < 15) {
			return true;
		}
		else return false;
	}
	
	
	public Polje naPoteziPolje(Igralec igralec) {
		// pretvori Igralec.O -> Polje.O
		return (naPotezi.equals(Igralec.O)) ? Polje.O : Polje.X;
	}
	
	public boolean poteza(Koordinati poteza) { // a sploh rabi bit boolean ali void?
		/**
		 * javno metodo boolean odigraj(Koordinati koordinati), 
		 * ki odigra potezo razreda Koordinati, če je možna. 
		 * Metoda vrne true, če je poteza možna, sicer pa false.alca
		 */
		
		if(jeLegalna(poteza)) {
			plosca[poteza.getY()][poteza.getX()] = naPoteziPolje(naPotezi); //zamenjal getX in getY
			naslednji();
			odigranePoteze.add(poteza); // doda odigrano potezo na seznam vseh odigranih potez
			moznePoteze.remove(poteza); // dodano iz maina
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
	
// ======================== funkcije uporabljene v Inteligenci

	public Set<Koordinati> kandidatiPoteze(){
		
		// Za zacetek bi za kandidate vzeli le vsa polja, ki imajo sosede
		
		
		return kanditatiPoteze;
		
	}
	
}
