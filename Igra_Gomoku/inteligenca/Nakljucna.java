package inteligenca;
import java.util.Random;
import logika.Igra;
import splosno.Koordinati;

public class Nakljucna extends Inteligenca {

	public Nakljucna(String ime) {
		super("Naključna");
	}


	@Override
	public Koordinati izberiPotezo(Igra igra) {
		Random rand = new Random();
		int random = rand.nextInt(igra.moznePoteze.size());
		return igra.moznePoteze.get(random);
	}

}
