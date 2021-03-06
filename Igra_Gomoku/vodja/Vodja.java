package vodja;

import java.util.Map;
import javax.swing.SwingWorker;
import vmesniki.Okno;
import inteligenca.Inteligenca;
import inteligenca.InteligencaPomozna;
import inteligenca.Minimax;
import inteligenca.Nakljucna;
import logika.Igra;
import logika.Igralec;
import logika.Stanje;

//import inteligenca.Minimax ;
//import inteligenca.RandomMinimax;

import splosno.Koordinati;
import splosno.KdoIgra;

public class Vodja {	
	
	public static Map<Igralec,VrstaIgralca> vrstaIgralca;
	public static Map<Igralec,KdoIgra> kdoIgra;
	
	public static Okno okno;
	public static Igra igra = null;
	public static boolean clovekNaVrsti = false;
	
	
	public static void igramoNovoIgro () {
		igra = new Igra ();
		igramo ();
	}
	
	public static void igramo () {
		okno.osvezi(); 
		switch (igra.stanje) {
		case ZMAGA_O:
		case ZMAGA_X: 
		case NEODLOCENO:
			return; // zaključimo metodo igramo
		case V_TEKU:
			Igralec igralec = igra.naPotezi;
			VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
			switch (vrstaNaPotezi) {
			case C: 
				clovekNaVrsti = true;
				break;
			case random:
				igrajRacunalnikovoPotezo();
				break;
			case minimax:
				igrajRacunalnikovoPotezo();
				break;
			case alfabeta:
				igrajRacunalnikovoPotezo();
				break;
			}
		}
	}

	public static InteligencaPomozna racunalnikovaInteligenca;
	public static Inteligenca racunalnikovaInteligencaTekmovanje;
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetnaIgra = igra;
		Igralec igralec = igra.naPotezi;
		VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
		switch(vrstaNaPotezi) {
		
		case random:
			racunalnikovaInteligenca = new Nakljucna("hello");
			break;
		case minimax:
			racunalnikovaInteligenca = new Minimax(2);
			break;
		case alfabeta:
			racunalnikovaInteligencaTekmovanje = new Inteligenca();
			break;
		default:
			break;
		}
		
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {
			
			
			
			@Override
			protected Koordinati doInBackground() {
				Koordinati poteza;
				if (vrstaNaPotezi.equals(VrstaIgralca.alfabeta)){
					poteza = racunalnikovaInteligencaTekmovanje.izberiPotezo(igra);
					
				}
				else {poteza = racunalnikovaInteligenca.izberiPotezo(igra);}
				
				return poteza;
			}
			
			@Override
			protected void done () {
				Koordinati poteza = null;
				try {poteza = get();} catch (Exception e) {};
				if (igra == zacetnaIgra) {
					igra.odigraj(poteza);
					igramo ();
				}
			}
		};
		worker.execute();
	}
	
	
	public static void igrajClovekovoPotezo(Koordinati poteza) {
	//Počaka, da človek odigra potezo, preveri stanje, da nemoremeo igrati po zmagi
		if (igra.stanje.equals(Stanje.V_TEKU) && igra.odigraj(poteza)) clovekNaVrsti = false; 
		igramo ();                                                                        
	}


}
