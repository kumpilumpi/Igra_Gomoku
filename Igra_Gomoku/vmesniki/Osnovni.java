package vmesniki;
import logika.*;

import splosno.Koordinati;
import java.util.Scanner;


// Vmesnik, ki v terminal izpisuje zadeve

public class Osnovni {
	
	public static Igra igra;
	
	public static void main(String[] args) {
		
		igra = new Igra();
		int potezaX;
		int potezaY;
		/**= 
		 * Mogoče se bi bolj splačalo pogledati samo dolžino odigranePoteze, 
		 * mozne poteze vsakič znova preveri celotno polje
		 * ?
		 * @jaka
		 */
		printPlosca();
		Scanner scanner = new Scanner(System.in);
		
		while(igra.stanje == Stanje.V_TEKU) {
			
			System.out.print("Vpiši x koordinato poteze: ");
			potezaX = scanner.nextInt() - 1;
			System.out.print("Vpiši y koordinato poteze: ");
			potezaY = scanner.nextInt() - 1;
			
			Koordinati trenutnaPoteza = new Koordinati(potezaX, potezaY);
			
			if (trenutnaPoteza.getX() > 0 && trenutnaPoteza.getY() > 0 && trenutnaPoteza.getX() < 15 && trenutnaPoteza.getY() < 15 && igra.poteza(trenutnaPoteza)){
				printPlosca();
				igra.stanje();
			}
			else System.out.print("Neveljavna poteza! \n");
			
			if (igra.stanje == Stanje.ZMAGA_O) {
				System.out.print("Zmaga Igralec O!");
			}
			else if (igra.stanje == Stanje.ZMAGA_X) {
				System.out.print("Zmaga Igralec X!");
			}
			
			else if (igra.stanje == Stanje.NEODLOCENO) {
				System.out.print("Neodločen izid!");
			}
			
			
		}
		scanner.close();
		return;		
	}
	//izpis igralne plošče v terminalu
    public static void printPlosca() {

        for (int i = 0; i < 15; ++i) {
            String out = "[";

         
            Polje[] row = igra.plosca[i];
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