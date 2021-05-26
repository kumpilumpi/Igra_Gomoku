package inteligenca;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;
import splosno.KdoIgra;

public class Inteligenca extends KdoIgra { 
	
	public Inteligenca (String ime) {
		super(ime);
	}
	
	public Koordinati izberiPotezo(Igra igra) {
		// vračanje odgovora za tekmovanje
		
		for (Koordinati p : igra.kanditatiPotezeKrajsi) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(p);
			switch (kopijaIgre.stanje) {
				case ZMAGA_O: return p;
				case ZMAGA_X: return p;
			default:
				break;
			}
		}
		
		//Pokliči alfaBeta
		
		
		
		
		
			
			
		// uporaba alfaBeta algoritma
		// Določit parametre ocene
		// 
		
		
		return null;
		
	}
}

