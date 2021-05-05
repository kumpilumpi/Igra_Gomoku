package vmesniki;

import logika.*;
import splosno.Koordinati;
import vodja.Vodja;
import vmesniki.Okno;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
//preuredil strukturo programa po profesorjevem zgledu
class Platno extends JPanel implements MouseListener {
	public Platno() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		Color barvaPlosce = new Color(191, 128, 255); // custom barva
		this.setBackground(barvaPlosce);
		this.addMouseListener(this); // Listen own mouse events.
	}
	public static Okno okno;
	private static final int ROWS = 15;
	private static final int COLS = 15;
	private static final int CELL_SIZE = 30; // Pixels
	private static final int WIDTH  = COLS * CELL_SIZE;
	private static final int HEIGHT = ROWS * CELL_SIZE;	
 
	//============================================== paintComponent
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g; //graphics 2d 
		Color barvaSvetel = new Color(255, 255, 179); // custom svetla barva
		Color barvaTemen = new Color(0, 0, 38); // custom temna barva
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //gladki robovi
		super.paintComponent(g);
		//-- Izris mreže.
		for (int r=1; r<ROWS; r++) {        //vodoravne crte
			g2.drawLine(0, r*CELL_SIZE, WIDTH, r*CELL_SIZE);
		}
		for (int c=1; c<COLS; c++) {
			g2.drawLine(c*CELL_SIZE, 0, c*CELL_SIZE, HEIGHT);
		}
     
		//-- Nariše odigrane poteze na ploščo.
		for (int r=0; r<ROWS; r++) {
			for (int c=0; c<COLS; c++) {
				int x = c * CELL_SIZE;
				int y = r * CELL_SIZE;
				Polje polje = Vodja.igra.plosca[r][c];
				if (polje != Polje.PRAZNO) {
					if(polje == Polje.X) g.setColor(barvaTemen);
					else g.setColor(barvaSvetel);
					g2.fillOval(x+2, y+2, CELL_SIZE-4, CELL_SIZE-4);
				}
			}
		}
	}
 
	//======================================== listener mousePressed
	public void mousePressed(MouseEvent e) {
		//--- map x,y coordinates into a row and col.
		int col = e.getX()/CELL_SIZE;
		int row = e.getY()/CELL_SIZE;
		Vodja.igrajClovekovoPotezo (new Koordinati(col, row));
		
     
	}
	//========================================== ignore these events
	public void mouseClicked (MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited  (MouseEvent e) {}
 	
}//end class Platno