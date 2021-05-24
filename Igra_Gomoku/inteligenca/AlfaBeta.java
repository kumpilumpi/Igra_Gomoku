package inteligenca;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;



public class AlfaBeta extends Inteligenca {
//Predelava kode za MiniMax
	
	//Konstante
	private static final int ZMAGA = 1000;
	private static final int ZGUBA = -ZMAGA;
	private static final int NEODLOC = 0;
	
	//Globina preverjanja
	private int globina;
	
	public AlfaBeta (int globina) {
		super("AlfaBeta " + globina);
		this.globina = globina;
	}
	
	@Override
	public Koordinati izberiPotezo (Igra igra) {
	//Pokliče minimax, ki poišče najboljšo potezo
		OcenjenaPoteza najboljsaPoteza = alfaBeta(igra, this.globina, ZGUBA, ZMAGA,igra.naPotezi);

		System.out.println(najboljsaPoteza); // <- preverjanje
		
		return najboljsaPoteza.poteza;	
	}
	
	//alfa največja
	
	
	public OcenjenaPoteza alfaBeta(Igra igra, int globina, int alfa, int beta, Igralec jaz) {
	//Vrne najboljso ocenjeno potezo z vidika igralca (jaz)
		OcenjenaPoteza najboljsaPoteza = null;
		
		//Računalnik max -> ZGUBA , Igralec min -> ZMAGA 
		najboljsaPoteza = new OcenjenaPoteza(igra.moznePoteze.get(0), (igra.naPotezi == jaz) ? ZGUBA : ZMAGA);
			// igra.moznePoteze.get(0), samo, da je nekaj za začetek ali ko ve da izgubi, vseeno odigra kaj
		
		for (Koordinati p: igra.kanditatiPoteze) {
			
			int ocena;
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(p);
			switch (kopijaIgre.stanje) {
			case ZMAGA_O: ocena = (jaz == Igralec.O ? ZMAGA : ZGUBA); break;
			case ZMAGA_X: ocena = (jaz == Igralec.X ? ZMAGA : ZGUBA); break;
			case NEODLOCENO: ocena = NEODLOC; break;
			default:
				// nekdo je na potezi
				if (globina == 1) ocena = OceniPozicijo.oceniPozicijo2(kopijaIgre, jaz); // <- ZAČASNO NASTAVLJANJE KATERA OCENA MREŽE SE POKLIČE ===================================
				// globina > 1
				else ocena = alfaBeta(kopijaIgre, globina-1, alfa, beta, jaz).ocena;
			}
			
			if (igra.naPotezi == jaz) {
				// Maksimiramo oceno
				if (ocena > najboljsaPoteza.ocena) { 
					najboljsaPoteza = new OcenjenaPoteza(p, ocena);
					alfa = (alfa > najboljsaPoteza.ocena) ? alfa : najboljsaPoteza.ocena;
				}
			} else { // igra.naPotezi() != jaz,  minimiziramo oceno
				if (ocena < najboljsaPoteza.ocena) {
					najboljsaPoteza = new OcenjenaPoteza(p, ocena);
					beta = (beta < najboljsaPoteza.ocena) ? beta : najboljsaPoteza.ocena;					
				}	
			}
			if (alfa >= beta) {
				// Izstopimo iz zanke
				return najboljsaPoteza;
			}
		}
		return najboljsaPoteza;
	}
	
}