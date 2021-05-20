package logika;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import splosno.Koordinati;

public class Igra {
	
	// VELIKOST igralne plošče
	
	
	public Polje[][] plosca;  //igralno polje
	
	public Igralec naPotezi; // kdor je na potezi
	
	public Stanje stanje;
	
	public LinkedList<Koordinati> odigranePoteze;

	public LinkedList<Koordinati> moznePoteze; //ne vem če sploh kaj rabva
	
	public Set<Koordinati> kanditatiPoteze; // Kandidati za inteligenco
	
	//final static ==========================================
	
	public static int velikost = 7; // tukaj se nastavi velikost igre
	public static final LinkedList<Linija> LINIJE =  new LinkedList<Linija>(); // vse linije (navpične (15), vodoravne(15), 2 x diagonalne (2x19))
	
	public static void pomozna_static(int x, int y, int[]smer) { // 
		LinkedList<Integer> xi = new LinkedList<Integer>();
		LinkedList<Integer> yi = new LinkedList<Integer>();
		
		while(x >= 0 && x < Igra.velikost && y >= 0 && y < Igra.velikost) {
			xi.add(x);
			yi.add(y);
			
			x += smer[0];
			y += smer[1];
		}
		
		if(xi.size() > 4) { LINIJE.add(new Linija(xi,yi)); }
	}
		
	static { // inicializacija LINIJE - se izvede le enkrat, ko prvic pozenemo program
		
		// Zgleda, da deluje potrebni dodatni testi
		
		
		
		for (int n = 0; n < Igra.velikost; n++) {
			
			pomozna_static(0, n, new int[] {1,0});  					//vodoravne 
			pomozna_static(n, 0, new int[] {0,1});  					//navpicne 
			
			pomozna_static(n, 0, new int[] {1,1});  					//desna diagonala zgornja vrstica		
			pomozna_static(n, 0, new int[] {-1,1}); 				    //leva diagonala zgornja vrstica
			
			if (n != 0) { //da se ena diagonala ne ponovi
				pomozna_static( 0, n, new int[] {1,1});  			    //desna diagonala prvi stolpec
				pomozna_static(Igra.velikost - 1, n, new int[] {-1,1}); //leva diagonala zadnji stolpec
			}	
			}
//		System.out.println(LINIJE);
//		System.out.println(LINIJE.size());
			
	}
		

	// Konstruktorji ========================================= 
	
	public Igra() {
		this(velikost);
	}
	
	public Igra(int n) {
		velikost = n;
		this.stanje = Stanje.V_TEKU;
		odigranePoteze = new LinkedList<Koordinati>(); // usvtari prazen seznam
		
		moznePoteze = new LinkedList<Koordinati>(); // usvtari prazen seznam // prekopirano main
		kanditatiPoteze = new HashSet<Koordinati>();
		
		kanditatiPoteze.add(new Koordinati(Igra.velikost/2, Igra.velikost/2)); // da je že iz prve vsaj en kandidat notr
		
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
		this.odigranePoteze = new LinkedList<Koordinati>();
		this.stanje = igra.stanje;
		
		this.plosca = new Polje[Igra.velikost][Igra.velikost];
		for (int i = 0; i < Igra.velikost; i++) {
			for (int j = 0; j < Igra.velikost; j++) {
				// dodano 
				if(igra.plosca[i][j] == Polje.PRAZNO) this.moznePoteze.add(new Koordinati(i,j)); 
				else {this.odigranePoteze.add(new Koordinati(i,j));}
				this.plosca[i][j] = igra.plosca[i][j];
			}
		}
		this.naPotezi = igra.naPotezi;
		
		
		this.kanditatiPoteze = new HashSet<Koordinati>();
		for (Koordinati p : igra.kanditatiPoteze) {
			this.kanditatiPoteze.add(p);
		}
		
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
	
	public boolean poteza(Koordinati poteza) { // more biti boolean in imenovana odigraj navodila;
		/**
		 * javno metodo boolean odigraj(Koordinati koordinati), 
		 * ki odigra potezo razreda Koordinati, če je možna. 
		 * Metoda vrne true, če je poteza možna, sicer pa false.alca
		 */
		
		if(jeLegalna(poteza)) {
			plosca[poteza.getY()][poteza.getX()] = naPoteziPolje(naPotezi); //zamenjal getX in getY
			naslednji();
			odigranePoteze.add(poteza); // doda odigrano potezo na seznam vseh odigranih potez
			kandidatiPoteze(poteza);
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

	public void kandidatiPoteze(Koordinati zadnjaPoteza){
		
		// Vsa polja, ki niso dalj kot 2mesti od katerekoli ploščice
		// Jih dodaja sproti -> vzame zadnjo pogleda okolico 2 v vsako smer, če je prazna doda
		
		int x0 = zadnjaPoteza.getX();
		int y0 = zadnjaPoteza.getY();
		
		kanditatiPoteze.remove(zadnjaPoteza); // iz množice kandidatov odstrani zadnjo potezo
		
		for( int x = -2; x<3; x++ ) {
			for (int y = -2; y < 3; y++) {
				try {
					if(plosca[y0-y][x0-x]== Polje.PRAZNO) {
						kanditatiPoteze.add(new Koordinati(x0-x,y0-y));
					}
				}catch (IndexOutOfBoundsException e) {
					continue;
				}
			}
		}
	}


	
}
