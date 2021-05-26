package vodja;

import java.util.Map;
import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;
import vmesniki.Okno;
import inteligenca.AlfaBeta;
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
			case R:
				igrajRacunalnikovoPotezo();
				break;
			}
		}
	}

	
	//Različne inteligence
	
//	public static Inteligenca racunalnikovaInteligenca = new Nakljucna("Naključna poteza") ;// <-String z imenom
	
	public static InteligencaPomozna racunalnikovaInteligenca = new Minimax(3);
	
	//minimax(3) igra zelo čudno
	// Če ima zagotovljeno zmago, tudi če nasprotnik kaj blokira, jo mogoče ne odigra saj misli 
	// da je vsaka poteza vredna 100 
	
	
	
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetnaIgra = igra;
		
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {
			
			@Override
			protected Koordinati doInBackground() {
				Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
//   			try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {};				
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
