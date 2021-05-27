package inteligenca;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;
import splosno.KdoIgra;

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
			default:
				break;
			}
		}
		
		//Pokliči alfaBeta
//		if(igra.odigranePoteze.size() < 15) {
//			racunalnikovaInteligenca = new AlfaBeta(1);
//		}
		
		InteligencaPomozna racunalnikovaInteligenca = new AlfaBeta(3);
		
		Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
		
		return poteza;
		
		//Če je igra dokaj začetna prvih 5 potez globina 3
		
		//Glavni del igre seznam 
		
		//Če igra se končuje zadnjih 10 potez -> globina 5
		
		
		
		
		
			
			
		// uporaba alfaBeta algoritma
		// Določit parametre ocene
		// 
		
		
	}
}

