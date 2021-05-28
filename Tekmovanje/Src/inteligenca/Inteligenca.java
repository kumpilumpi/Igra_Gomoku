package inteligenca;

import logika.Igra;
import splosno.KdoIgra;
import splosno.Koordinati;

public class Inteligenca extends KdoIgra { 
	
	public Inteligenca() {
		super("TeoJaka");
	}
	
	public Inteligenca (String ime) {
		super(ime);
	}
	
// Vračanje odgovora na tekmovanju
	public Koordinati izberiPotezo(Igra igra) {
		
		
		//ker alfa beta noče zaključit zmagovalne poteze
		//odigra potezo, če ta prinese zmago ali bi prnesla poraz
		
		for (Koordinati p : igra.kanditatiPotezeKrajsi) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(p);
			switch (kopijaIgre.stanje) {
				case ZMAGA_O: return p;
				case ZMAGA_X: return p;
				default: continue;
			}
		}
		
		
		//Pokliči alfaBeta - glede na stanje igre (koliko potez je bilo odigranih)
		
		InteligencaPomozna racunalnikovaInteligenca;
		
		if (igra.kanditatiPotezeKrajsi.size() < 30 ) {
			racunalnikovaInteligenca = new AlfaBeta(4);
		}
		else {
			racunalnikovaInteligenca = new AlfaBeta(3);
		}
		Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
		
		return poteza;
		
		
	}
}

