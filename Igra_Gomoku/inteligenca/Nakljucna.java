package inteligenca;
import java.util.Random;

import java.util.LinkedList;

import logika.Igra;
import splosno.Koordinati;

public class Nakljucna extends Inteligenca {

	public Nakljucna(String ime) {
		super("Naključna");
	}


	@Override
	public Koordinati izberiPotezo(Igra igra) {
		Random rand = new Random();
		LinkedList<Koordinati> mozne = igra.moznePoteze;
		int random = rand.nextInt(mozne.size());
		
		return mozne.get(random);
	}

}
