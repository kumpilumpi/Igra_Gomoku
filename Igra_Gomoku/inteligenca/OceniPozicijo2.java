package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Linija;
import logika.Polje;
import splosno.Koordinati;

public class OceniPozicijo2 {
	//class, ki oceni potezo
	
	public static boolean soStiri;

	
	
// =================================prvaOcena
	
// Slaba ocena, bolj za test na začetku, sedaj ni v uporabi
// Ocena1 : preštejemo sosede (desna, desna diagonala spodaj, spodnja, leva diagonala spodaj)
	
	public static int oceniPozicijo1(Igra igra, Igralec igralec) {
		
		int pariO = 0;
		int pariX = 0;
		
		for (Koordinati poteza : igra.odigranePoteze) {
				switch(igra.plosca[poteza.getY()][poteza.getX()]) {
				case PRAZNO: continue;
				case O: pariO += prestejSosede(poteza.getY(),poteza.getX(),igra, Polje.O);
				case X: pariX = pariX + prestejSosede(poteza.getY(),poteza.getX(),igra, Polje.X);					
			}
		}
		
		return (igralec == Igralec.O) ? (pariO - pariX) : (pariX - pariO);
	}
	
	public static int prestejSosede(int vrsta, int stolpec, Igra igra, Polje primerjava) {
	//pomozna fun preveri sosede v danih smereh
		int pari = 0;
		int[][] smeri = {{1,0},{0,1},{1,1},{-1,1}};
		for (int[] smer :smeri) {
			try {
				if (igra.plosca[vrsta + smer[1]][stolpec+ smer[0]] == primerjava) {
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
	
	// Ocena2 : s pomočjo seznama linije - seznam vseh vrstic,stolpcev in diagonal, ki so dolge vsaj 5
	
	/**
	 *	Ocena točkovanje
	 *	- pogled pet zaporednih elementov iz linije:
	 *		- vseh 5 istih -> ZMAGA (ne rabimo preverjat, ker se samo preverja v logiki in vodja zazna avtomatično.)
	 *		- x mojih elementov + y praznih -> 3 **(moji -1)
	 *			- 1 -> 1 / 2 -> 3 / 3 -> 9 / 4 -> 27 / 5 -> 81
	 *
	 * -> minus vrednosti za nasprotnika (4 na potenco elementov)
	 * 
	 * -> Če je kar koli drugega + 0 ( petorka je prazna || je nihče ne more osvojit)
	 * 
	 */
	
	// mogoče k vsaki oceni dodamo nek mali naključni del, da računalnik ni preveč predvidljv.
	// če najde dvakrat zapored peterko z 4 istimi, ocenimo z ZMAGA
	
	public static int oceniPozicijo2(Igra igra, Igralec igralec) {		
		int ocena = 0;
		for (Linija i : Igra.LINIJE) {
			ocena += oceniLinija(i, igra, igralec);
		}
		return ocena;
	}
	
	public static int oceniLinija(Linija linija, Igra igra, Igralec igralec) {
	//Se sprehodi čez vse možnosti pet zaporednih elementov in vsaki taki petorki priredi oceno
		int meja = linija.x.size() - 4;
		int ocena = 0;
		
		for (int zacetek = 0; zacetek < meja; zacetek++ ) {
		//Zanka indeks začetnega elementa (od 0 do 4 pred koncem)
			int moj = 0;
			int nasprotnik = 0;
			
			for (int k = 0; k < 5; k++) {
			//Zanka petih elementov od začetnega indeksa pogledamo vsako petorko
			//presteje koliko je mojih in nasprotnikovih elementov
				Polje element = igra.plosca[linija.y.get(zacetek+k)][linija.x.get(zacetek+k)];
				if(element == Polje.PRAZNO) {
					continue;
				}else{
					//igra.igralecPolje : Igralec -> Polje
					if (element == igra.igralecPolje(igralec)){++moj;}else{ ++nasprotnik;}
				}
			}
			
			if (nasprotnik > 0 && moj > 0) {
				// oba imata vsaj en element te petorke ne more nihče osvojit
				continue;
			}
			else if(moj == 0 && nasprotnik == 0){
				//  oba imata 0 elementov, prazna petorka
				continue;				
			}
			else if(nasprotnik == 0) {
				//moj != 0 && nasprotnik == 0
				ocena = ocena + ocena(moj); //(int) Math.pow(3, moj-1);
			}
			else {
				//nasprotnik != 0 && moj == 0
				ocena = ocena - ocena(nasprotnik);
			}
		}
		return ocena;
	}
	
	
	public static int ocena (int zaporednih) {
		switch(zaporednih) {
		
			case 0: 
				soStiri = false;
				return 0;
			case 1: 
				soStiri = false;
				return 1;
			case 2: 
				soStiri = false;
				return 3;
			case 3: 
				soStiri = false;
				return 9;
			case 4:	
				if (soStiri) {
					soStiri = false;
					return 81;
				}
				soStiri = true;
				return 81;
			case 5: 
				soStiri = false;
				return 1000;
			
		}
		return zaporednih;
			
	}
	
	
	
	
	
}
