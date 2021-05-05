package vodja;

import java.util.Map;

import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;

import vmesniki.Okno;
import inteligenca.Inteligenca;
import inteligenca.Nakljucna;
import logika.Igra;
import logika.Igralec;


//import inteligenca.Minimax ;
//import inteligenca.RandomMinimax;

import splosno.Koordinati;
import splosno.KdoIgra;

public class Vodja {	
	
	public static Map<Igralec,VrstaIgralca> vrstaIgralca;
	public static Map<Igralec,KdoIgra> kdoIgra;
	
	public static Okno okno;
	
	public static Igra igra;
	
	public static boolean clovekNaVrsti = false;
		
	public static void igramoNovoIgro () {
		igra = new Igra ();
		igramo ();
	}
	
	public static void igramo () {
		okno.osvezi(); // <- ??
		switch (igra.stanje) {
		case ZMAGA_O:
		case ZMAGA_X: 
		case NEODLOCENO:
			return; // odhajamo iz metode igramo
		case V_TEKU:
			Igralec igralec = igra.naPotezi;
			VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
			switch (vrstaNaPotezi) {
			case C: 
				clovekNaVrsti = true;
				break;
			case R:
				igrajRacunalnikovoPotezo ();
				break;
			}
		}
	}

	
	public static Inteligenca racunalnikovaInteligenca = new Nakljucna("hej") ;//new Minimax(3); // <-String z imenom
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetkaIgra = igra;
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {
			@Override
			protected Koordinati doInBackground() {
				Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {};
				return poteza;
			}
			@Override
			protected void done () {
				Koordinati poteza = null;
				try {poteza = get();} catch (Exception e) {};
				if (igra == zacetkaIgra) {
					igra.poteza(poteza);
					igramo ();
				}
			}
		};
		worker.execute();
	}
		
	public static void igrajClovekovoPotezo(Koordinati poteza) {
		if (igra.poteza(poteza)) clovekNaVrsti = false;
		igramo ();
	}


}
