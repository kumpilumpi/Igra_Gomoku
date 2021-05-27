package inteligenca;
import java.util.Random;
import java.util.Set;
import java.util.LinkedList;

import logika.Igra;
import splosno.Koordinati;

public class Nakljucna extends InteligencaPomozna {
	
	public Nakljucna() {
		super("Naključna");
	}

	@Override
	public Koordinati izberiPotezo(Igra igra) {
		return igra.kanditatiPotezeKrajsi.iterator().next();
	}

}
