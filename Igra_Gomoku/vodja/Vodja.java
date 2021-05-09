package vodja;

import java.util.Map;
import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;
import vmesniki.Okno;
import inteligenca.Inteligenca;
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

	//Različne inteligence
	
	public static Inteligenca racunalnikovaInteligenca = new Nakljucna("Hej") ;// <-String z imenom
	
	public static Inteligenca racunalnikovaInteligenca2 = new Minimax(2);
	
	
	// 
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetkaIgra = igra;
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {
			@Override
			protected Koordinati doInBackground() {
				Koordinati poteza = racunalnikovaInteligenca2.izberiPotezo(igra);
				try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {};
				return poteza;
			}
			@Override
			protected void done () {
				Koordinati poteza = null;
				try {poteza = get();} catch (Exception e) {};
				if (igra == zacetkaIgra) {
					
					if (poteza == null) System.out.println("Napaka"); // tukaj hoče izvršiti potezo null <-PREVERJANJE
					
					igra.poteza(poteza);
					igramo ();
				}
			}
		};
		worker.execute();
	}
		
	public static void igrajClovekovoPotezo(Koordinati poteza) {
		if (igra.stanje.equals(Stanje.V_TEKU) && igra.poteza(poteza)) clovekNaVrsti = false; //preveri tudi stanje
		igramo ();                                                                          //da ne moremo igrati po zmagi
	}


}
