package logika;

import java.util.LinkedList;

public class Linija {
	
	// Linija vrstica na plošči, kjer se lahko pojavi 5 v vrsto (vse navpične,vodoravne in diagonalne)
	// Predstavljena kot seznama indeksov x in y
	
	public LinkedList<Integer> x;
	public LinkedList<Integer> y;
	
	public Linija(LinkedList<Integer> x, LinkedList<Integer> y) {
		this.x = x;
		this.y = y;		
	}
	
	
	//Samo za izpisovanje, ni bistveno
	@Override
	public String toString() {
		String xstr = "";
		for(int xint : x) {
			xstr += " " + String.valueOf(xint) + ",";
		}
		String ystr = "";
		for(int yint : y) {
			ystr += " " + String.valueOf(yint) + ",";
		}
		return "Linija [x= [" + xstr + "] , y= [" + ystr + "] ]";
	}
	

}
