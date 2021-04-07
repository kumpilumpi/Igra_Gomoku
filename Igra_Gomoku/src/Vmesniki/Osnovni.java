package Vmesniki;

import Logika.Igra;
import Logika.Polje;

// Vmesnik, ki v terminal izpisuje zadeve

public class Osnovni {
	
	public static Igra igra;
	
	public static void main(String[] args) {
		igra = new Igra();
		printPlosca();
		
		return;		
	}

    public static void printPlosca() {

        for (int i = 0; i < 15; ++i) {
            String out = "[";

         
            Polje[] row = Igra.plosca[i];
            for (int j = 0; j < 15; ++j) {
                Polje v = row[j];
                
                if (v.equals(Polje.O)){
        			out += "O";
                }
                else if (v.equals(Polje.X)){
        			out += "X";
                }
                else out += "_";
        			
                if (j != row.length - 1) {
                    out += "|";
                }
            }
        out += "]";


            System.out.println(out);
        }
    }
}
