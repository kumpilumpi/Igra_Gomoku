package vmesniki;

import logika.*;
import splosno.Koordinati;
import vodja.Vodja;
import vmesniki.Okno;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")

class Platno extends JPanel implements MouseListener {
	public Platno() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		Color barvaPlosce = new Color(191, 128, 255);
		this.setBackground(barvaPlosce);
		this.addMouseListener(this);
	}
	public static Okno okno;
	private static final int ROWS = Igra.velikost;
	private static final int COLS = Igra.velikost;
	private static int CELL_SIZE = 30; // Pixels
	private static int WIDTH  = COLS * CELL_SIZE;
	private static int HEIGHT = ROWS * CELL_SIZE;
	private int CELL_WIDTH = WIDTH/15;
	private int CELL_HEIGHT = HEIGHT/15;
 
	//============================================== paintComponent
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Color barvaSvetel = new Color(255, 255, 179);
		Color barvaTemen = new Color(0, 0, 38);
		Color barvaZmaga = new Color(255, 160, 128);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
		//-- Izris mreže.
		WIDTH = getWidth();
		HEIGHT = getHeight();
		CELL_WIDTH = WIDTH/Igra.velikost;
		CELL_HEIGHT = HEIGHT/Igra.velikost;;
		
		for (int r=1; r<ROWS; r++) {
			g2.drawLine(0, r*CELL_HEIGHT, WIDTH, r*CELL_HEIGHT);
		}
		for (int c=1; c<COLS; c++) {
			g2.drawLine(c*CELL_WIDTH, 0, c*CELL_WIDTH, HEIGHT);
		}
		
		//zmagovalna vrsta se obarva
		if(Vodja.igra != null && (Vodja.igra.stanje.equals(Stanje.ZMAGA_O)||Vodja.igra.stanje.equals(Stanje.ZMAGA_X))) {
			for (Koordinati polje : Vodja.igra.zmagovalnaVrsta) {
				int i = polje.getX();
				int j = polje.getY();
				g2.setColor(barvaZmaga);
				g2.fillRect((int)(CELL_WIDTH * i+1), (int)(CELL_HEIGHT * j+1), (int)CELL_WIDTH-1, (int)CELL_HEIGHT-1);
			}
		}
		
		//-- Nariše odigrane poteze na ploščo.
		if (Vodja.igra != null) {
			for (int r=0; r<ROWS; r++) {
				for (int c=0; c<COLS; c++) {
					int x = c * CELL_WIDTH;
					int y = r * CELL_HEIGHT;
					Polje polje = Vodja.igra.plosca[r][c];
					if (polje != Polje.PRAZNO) {
						if(polje == Polje.X) g.setColor(barvaTemen);
						else g.setColor(barvaSvetel);
						g2.fillOval(x+2, y+2, CELL_WIDTH-4, CELL_HEIGHT-4);
					}
				}
			}
		}		
	}
 
	@Override
	public void mousePressed(MouseEvent e) {
		//--- map x,y coordinates into a row and col.
		int col = e.getX()/CELL_WIDTH;
		int row = e.getY()/CELL_HEIGHT;
		
		if (Vodja.igra != null && Vodja.clovekNaVrsti==true) Vodja.igrajClovekovoPotezo (new Koordinati(col, row));
		
     
	}
	public void mouseClicked (MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited  (MouseEvent e) {}
 	
}
