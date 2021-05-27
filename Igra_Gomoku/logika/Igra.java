package logika;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import splosno.Koordinati;

public class Igra {	
	
	public Polje[][] plosca;  //igralno polje
	
	public Igralec naPotezi; // kdor je na potezi
	
	public Stanje stanje;
	
	public LinkedList<Koordinati> odigranePoteze;

	public LinkedList<Koordinati> moznePoteze; 
	
	public Set<Koordinati> kanditatiPoteze; // Kandidati za inteligenco (do dve polji oddaljeni sosedi)
	public Set<Koordinati> kanditatiPotezeKrajsi; // (Eno polje oddaljeni sosedi)
	
	public LinkedList<Koordinati> zmagovalnaVrsta; // Za obarvanje zmagovalne petorke
	
	//final static ==========================================
	
	public static int velikost = 15; // tukaj se nastavi velikost igre
	public static final LinkedList<Linija> LINIJE =  new LinkedList<Linija>(); // vse linije (navpične (15), vodoravne(15), 2 x diagonalne (2x19))
	
	public static void pomozna_static(int x, int y, int[]smer) { 
	//pomozna kodo za static spodaj
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
		
	static { 
	// inicializacija LINIJE - se izvede le enkrat, ko prvič pozenemo program		
		for (int n = 0; n < Igra.velikost; n++) {
			
			pomozna_static(0, n, new int[] {1,0});  					//vodoravne 
			pomozna_static(n, 0, new int[] {0,1});  					//navpicne 
			pomozna_static(n, 0, new int[] {1,1});  					//desna diagonala zgornja vrstica		
			pomozna_static(n, 0, new int[] {-1,1}); 				    //leva diagonala zgornja vrstica
			if (n != 0) { 
			//da se glavni diagonali ne ponovita
				pomozna_static( 0, n, new int[] {1,1});  			    //desna diagonala prvi stolpec
				pomozna_static(Igra.velikost - 1, n, new int[] {-1,1}); //leva diagonala zadnji stolpec
			}	
			}
	}
		
// Konstruktorji ========================================= 
	
	public Igra() {
		this(velikost);
	}
	
	public Igra(int n) {
		velikost = n;
		this.stanje = Stanje.V_TEKU;
		
		// ustvari prazne LinkedList sezname
		odigranePoteze = new LinkedList<Koordinati>(); 
		kanditatiPoteze = new HashSet<Koordinati>();
		kanditatiPotezeKrajsi = new HashSet<Koordinati>();
		zmagovalnaVrsta = new LinkedList<Koordinati>();
		
		kanditatiPoteze.add(new Koordinati(Igra.velikost/2, Igra.velikost/2)); 
			// Da se vsaj en kandidat nahaj notr v seznamu, da lahko začne
		
		kanditatiPotezeKrajsi.add(new Koordinati(Igra.velikost/2, Igra.velikost/2));
		
		
		//ustvari plosco
		plosca = new Polje[velikost][velikost];
			for (int i = 0; i < velikost; i++) {
				for (int j = 0; j < velikost; j++) {
					plosca[i][j] = Polje.PRAZNO;
				}
			}
		
		naPotezi = Igralec.O;
	}

	// Za ustvarjanje kopije igre
	public Igra(Igra igra) {
		// !! ne sme biti this.plosca = igra.plosca , seznama imata isto mesto v pomnilniku
		
		this.naPotezi = igra.naPotezi;
		this.stanje = igra.stanje;
		
		this.zmagovalnaVrsta = new LinkedList<Koordinati>();
		this.moznePoteze = new LinkedList<Koordinati>();
		this.odigranePoteze = new LinkedList<Koordinati>();
		
		
		this.plosca = new Polje[Igra.velikost][Igra.velikost];
		for (int i = 0; i < Igra.velikost; i++) {
			for (int j = 0; j < Igra.velikost; j++) {
				if(igra.plosca[i][j] != Polje.PRAZNO) {this.odigranePoteze.add(new Koordinati(i,j));}
				this.plosca[i][j] = igra.plosca[i][j];
			}
		}
		
		this.kanditatiPoteze = new HashSet<Koordinati>();
		for (Koordinati p : igra.kanditatiPoteze) {
			this.kanditatiPoteze.add(p);
		}
		
		this.kanditatiPotezeKrajsi = new HashSet<Koordinati>();
		for (Koordinati p : igra.kanditatiPotezeKrajsi) {
			this.kanditatiPotezeKrajsi.add(p);
		}
		
	}
	
	
	// ================ Komentarji =========================
	
	/**
	 * Koordinata.y vrsta, koordinata.x stolpec, -> plosca[y-vrsta][x-vrsta]
	 * Ko dostopamo do Polja v seznamu plosca je prvi indeks vrsta(Y), drugi indeks stolpec(X)
	 * (0,0) je levi zgornji kot plosce / (14,14) desni spodnji kot.
	 */
	
	// =========================================
	
public int pozitivna(int x) { return (x < 0) ? 0 : x ; } // <-- Uporabljena??
	//Pomozna funkcija, če je negativni int vrne 0, drugače int
	
	public boolean pomozna (int v0, int s0, int dv, int ds) {
	//Od zacetnega polja (v0,s0) preveri koliko v vrsto jih je v smeri (dv,ds) in (-dv,-ds)
		zmagovalnaVrsta.clear();
		zmagovalnaVrsta.add(new Koordinati(s0,v0));
		Polje primerjava = plosca[v0][s0];
		int zaporedni = 1;
		int[] smeri = new int[] {1,-1}; // 1 -> naprej, -1 -> nazaj
		for (int smer : smeri) {
			int i = 1;
			dv = dv * smer;
			ds = ds * smer;
			try {
				while((zaporedni < 5) && (plosca[v0 + i*dv][s0 + i*ds].equals(primerjava))) {
				i++;
				zaporedni++;
				zmagovalnaVrsta.add(new Koordinati(s0 + i*ds-ds, v0 + i*dv-dv));
				}
			}catch (IndexOutOfBoundsException e) {
				continue;
			}
		}
		return (zaporedni < 5) ? false : true;	
	}
	
	public boolean petVrsta() {
	//Uporabi zgornjo funkcijo pomozna po vseh smiselnih smereh
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

	// =========================================
	
	public void stanje() { 
	//Posodobi this.stanje		
		if(petVrsta()) {
			Koordinati poteza = odigranePoteze.getLast();
			Polje primerjava = plosca[poteza.getY()][poteza.getX()];
			stanje = (primerjava.equals(Polje.O)) ? Stanje.ZMAGA_O : Stanje.ZMAGA_X ;
		}
		if (odigranePoteze.size() == velikost * velikost) {
			stanje = Stanje.NEODLOCENO;
		}
	}
	
	public void naslednji() { 
		//Spremeni igralca, ki je napotezi
		naPotezi = (naPotezi == Igralec.X) ? Igralec.O : Igralec.X;
	}
	
	
	public boolean jeLegalna(Koordinati poteza) {
	//Preveri če je poteza legalna
		if (poteza == null) return false; // prazna poteza je ilegalna
		if (plosca[poteza.getY()][poteza.getX()].equals(Polje.PRAZNO) && (poteza.getX() < Igra.velikost) && (poteza.getY() < Igra.velikost)) {
			return true;
		}
		else return false;
	}
	
	public Polje igralecPolje(Igralec igralec) {
	//Pretvori Igralec.O -> Polje.O
		return (igralec == Igralec.O) ? Polje.O : Polje.X;
	}
	
	public boolean odigraj(Koordinati poteza) {
	// Odigra potezo če je ta možna, nastavi naslednjega igralca, spremeni sezname potez (kandidati,odigrane,mozne), spremeni stanje
	// Mora biti tipa boolean po navodilih
		if(jeLegalna(poteza)) {
			plosca[poteza.getY()][poteza.getX()] = igralecPolje(naPotezi);
			naslednji();
			odigranePoteze.add(poteza);
			kandidatiPoteze(poteza);
			stanje();
			return true;
		}
		else return false;
	}
	
	public void razveljaviPotezo() {
	//Razveljavi zadnjo potezo
		if(!odigranePoteze.isEmpty() && stanje.equals(Stanje.V_TEKU)) {
			Koordinati poteza = odigranePoteze.getLast();
			odigranePoteze.removeLast();	
			plosca[poteza.getY()][poteza.getX()] = Polje.PRAZNO;
			naslednji();
		}
		return;
	}
	
// ======================== Funkcije uporabljene v Inteligenci

	public void kandidatiPoteze(Koordinati zadnjaPoteza){
	// Primerno spremeni seznam kandidatov, doda vse prazna polja, ki niso oddaljene dalj kot
	// dve polji od nepraznega polja
		// Jih dodaja sproti -> vzame zadnjo pogleda okolico 2 v vsako smer, če je prazna doda
		// Kot argument sprejme zadnjo odigrano potezo.
		
		int x0 = zadnjaPoteza.getX();
		int y0 = zadnjaPoteza.getY();
		kanditatiPoteze.remove(zadnjaPoteza); 
		// iz množice kandidatov odstrani zadnjo odigrano potezo 
		
		kanditatiPotezeKrajsi.remove(zadnjaPoteza); // <----------------------K
		
		for( int x = -2; x<3; x++ ) {
			for (int y = -2; y < 3; y++) {
				try {
					if(plosca[y0-y][x0-x]== Polje.PRAZNO) {
						kanditatiPoteze.add(new Koordinati(x0-x,y0-y));
						if(Math.abs(x) < 2 && Math.abs(y) < 2 ){ // <----------------------K
							kanditatiPotezeKrajsi.add(new Koordinati(x0-x,y0-y));
						}
					}
				}catch (IndexOutOfBoundsException e) {
					continue;
				}
			}
		}
	}

	
	
	
	
}
