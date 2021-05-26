package inteligenca;

import logika.Igra;

import splosno.Koordinati;
import splosno.KdoIgra;

public abstract class InteligencaPomozna extends KdoIgra { 
	
	public InteligencaPomozna (String ime) {
		super(ime);
	}
	
	public abstract Koordinati izberiPotezo(Igra igra); 

}
