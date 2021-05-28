package inteligenca;
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
