package inteligenca;

import logika.Igra;

import splosno.Koordinati;
import splosno.KdoIgra;

public class Inteligenca extends KdoIgra { 
	
	public Inteligenca (String ime) {
		super(ime);
	}
	
	public Koordinati izberiPotezo(Igra igra) {
		// vračanje odgovora za tekmovanje
		
		// uporaba alfa beta algoritma
		// Določit parametre ocene
		// 
		
		
		return null;
		
	}

}

//NAVODILA
//Razred Inteligenca naj ima javno (nestatično) metodo Koordinati izberiPotezo(Igra igra), 
//ki implementira rešitev skupine za inteligentnega igralca igre Gomoku. Na tekmovanju bo čas 
//za potezo omejen na 5 sekund. Preverite,
//da vaša metoda izberiPotezo ne potrebuje več kot 5 sekund na plošči velikosti 15 × 15.