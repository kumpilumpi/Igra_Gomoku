package inteligenca;

import logika.Igra;
import logika.Igralec;
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
	
	
	public static int oceniPozicijo(Igra igra, Igralec igralec) {
		
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
	
	
}
