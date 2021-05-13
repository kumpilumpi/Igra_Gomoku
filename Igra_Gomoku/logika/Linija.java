package logika;

import java.util.Arrays;

public class Linija {
	
	// Linija vrstica na plošči, kjer se lahko pojavi 5 v vrsto (vse navpične,vodoravne in diagonalne)
	// Predstavljena kot seznama indeksov x in y
	
	public int[] x;
	public int[] y;
	
	public Linija(int[] x, int[] y) {
		this.x = x;
		this.y = y;		
	}
	
	
	//Samo za izpisovanje ,ni bistveno
	@Override
	public String toString() {
		return "Linija [x=" + Arrays.toString(x) + ", y=" + Arrays.toString(y) + "]";
	}
	

}
