package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Linija;
import logika.Polje;

public class OceniPozicijo {
	//class, ki oceni potezo

	
	
// =================================prvaOcena -> zelo slaba časovno zahtevna
	
	/**
	 * @param igra
	 * @param igralec
	 * @return ocena
	 * 
	 * Vrne oceno odigrane poteze.
	 * 
	 * Prvotna ocena: preštejemo vse postavitve dveh elementov - vse postavitve dveh elementov nasprotnika
	 * 		- nas ne zanima če naš par ima sploh možnosz dokončanja do 5 v vrsto
	 * 
	 *  	- a je dovolj, da pogledamo samo koliko novih parov pride iz zadnje poteze
	 */
	
	
	public static int oceniPozicijo1(Igra igra, Igralec igralec) {
		
		int pariO = 0;
		int pariX = 0;
		
		for (int vrsta = 0; vrsta < Igra.velikost; vrsta++) {
			for (int stolpec = 0; stolpec < Igra.velikost; stolpec ++) {
				switch(igra.plosca[vrsta][stolpec]) {
				case PRAZNO: continue;
				case O: pariO = pariO + prestejSosede(vrsta,stolpec,igra, Polje.O);
				case X: pariX = pariX + prestejSosede(vrsta,stolpec,igra, Polje.X);					
				}
			}
		}
		//System.out.println(pariO);
		
		return (igralec == Igralec.O) ? (pariO - pariX) : (pariX - pariO);
	}
	
	
	//pomozna fun preveri sosede v danih smereh
	
	public static int prestejSosede(int vrsta, int stolpec, Igra igra, Polje primerjava) {
		int pari = 0;
		int[][] smeri = {{1,0},{0,1},{1,1},{-1,1}};
		
		for (int[] smer :smeri) {
			try {
				if (igra.plosca[vrsta + smer[0]][stolpec+ smer[1]] == primerjava) {
					pari++;
				}
			}
			catch(IndexOutOfBoundsException e) {
				continue;
			}
		}
		
		return pari;
		
	}

	
// ===========================================================
	// ocena2  s pomočjo seznama linije
	
	
	
	/**
	 *	Ocena točkovanje
	 *	- pogleda linijo -> pet zaporednih elementov
	 *		- vseh 5 istih -> ZMAGA ( ne vem, če rabiva preverjat, ker se samo preverja v logiki in vodja zazna avtomatično. )
	 *		- 4 + 1 PRAZNO -> 64
	 *		- 3 + 2 PRAZNO -> 16
	 *		- 2 + 3 PRAZNO -> 4
	 *		- 1 + 4 PRAZNO -> 1
	 *
	 * -> minus vrednosti za nasprotnika 
	 * -> Če je kar koli drugega + 0
	 * 
	 */
	
	
	public static int oceniPozicijo2(Igra igra, Igralec igralec) {		
		int ocena = 0;
		for (Linija i : Igra.LINIJE) {
			ocena += oceniLinija(i, igra, igralec);
		}
		return ocena;
		
	}
	
	public static int oceniLinija(Linija linija, Igra igra, Igralec igralec) {
		int meja = linija.x.size() - 5;
		int ocena = 0;
		for (int i = 0; i < meja; i++ ) { // skozi cel seznam
			int moj = 0;
			int nasprotnik = 0; 
			for (int k = 0; k < 5; k++) { //pogledamo vsako petorko
				Polje element = igra.plosca[linija.y.get(i+k)][linija.x.get(i+k)];
				if(element == Polje.PRAZNO) {
					continue;
				}else{
					if (element == igra.naPoteziPolje(igralec)) {++moj;}else{ ++nasprotnik;}
				}
			}
			if (nasprotnik > 0 && moj > 0) { //Če ima vsak v dani petki vsaj element se je ne bo moglo zapolnit
				continue;
			}
			else if(nasprotnik == 0) {
				if(moj == 0) { // vse prazno
					continue;
				}else {
					ocena = ocena + (int) Math.pow(4, moj-1);
				}
					
			} 
			else {ocena = ocena - (int) Math.pow(4, nasprotnik-1);}
		}
		return ocena;
	}
	
	public static int ocenaPomozna(int x) {
		switch (x) {
			case 1: return 1;
			case 2: return 4;
			case 3: return 16;
			case 4: return 32;
			default: return 0;
		}
	}
	
	
}
