package inteligenca;

import logika.Igra;

import splosno.Koordinati;
import splosno.KdoIgra;

// Paket inteligenca naj vsebuje (konkretni) razred Inteligenca ki je podrazred razreda splosno.KdoIgra 
// (na predavanjih je bil Inteligenca abstraktni razred)

public abstract class InteligencaPomozna extends KdoIgra { 
	
	public InteligencaPomozna (String ime) {
		super(ime);
	}
	
	public abstract Koordinati izberiPotezo(Igra igra); 

}

//NAVODILA
//Razred Inteligenca naj ima javno (nestatično) metodo Koordinati izberiPotezo(Igra igra), 
//ki implementira rešitev skupine za inteligentnega igralca igre Gomoku. Na tekmovanju bo čas 
//za potezo omejen na 5 sekund. Preverite,
//da vaša metoda izberiPotezo ne potrebuje več kot 5 sekund na plošči velikosti 15 × 15.