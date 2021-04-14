package vmesniki;

import logika.*;
import splosno.Koordinati;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Platno extends JPanel {
 
	private static final long serialVersionUID = 1L; // nevem kaj naredi,ni warninga

//======================================================
	private GraphicsPanel prikazplosce;
	private JTextField    stanjeIgre = new JTextField();
	public Igra igra = new Igra();

//====================================================== konstruktor
	public Platno() {
		//--- Naredi gumbe
		JButton newGameButton = new JButton("New Game");
		JButton undoButton = new JButton("Undo");

		//--- Naredi control panel
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(newGameButton);
		controlPanel.add(undoButton);
     
		//--- Naredi območje kjer bo igralna plošča
		prikazplosce = new GraphicsPanel();
		showNextPlayer();
		//--- Postavitev in dodajanje komponent
		this.setLayout(new BorderLayout());
		this.add(controlPanel , BorderLayout.NORTH);
		this.add(prikazplosce, BorderLayout.CENTER);
		
		
		Font font1 = new Font("SansSerif", Font.BOLD, 18);
		stanjeIgre.setFont(font1);
		this.add(stanjeIgre , BorderLayout.SOUTH);
		stanjeIgre.setHorizontalAlignment(JTextField.CENTER);
		stanjeIgre.setEditable(false);
     
		//-- doda action listeners
		newGameButton.addActionListener(new NovaIgra());
		undoButton.addActionListener(new Razveljavi());
	}

//////////////////////////////////////////////// class GraphicsPanel

	class GraphicsPanel extends JPanel implements MouseListener {
		
		private static final long serialVersionUID = 1L; // nevem kaj naredi,ni warninga
		private final int ROWS = 15;
		private final int COLS = 15;
		private int CELL_SIZE = 30; // Pixels
		private int WIDTH  = COLS * CELL_SIZE;
		private int HEIGHT = ROWS * CELL_SIZE;
		private int CELL_WIDTH = WIDTH/15;
		private int CELL_HEIGHT = HEIGHT/15;
		
		public GraphicsPanel() {
			this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
			Color barvaPlosce = new Color(191, 128, 255); // custom barva
			this.setBackground(barvaPlosce);
			this.addMouseListener(this); // Listen own mouse events.
		}
     
		//============================================== paintComponent
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g; //graphics 2d 
			Color barvaSvetel = new Color(255, 255, 179); // custom svetla barva
			Color barvaTemen = new Color(0, 0, 38); // custom temna barva
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //gladki robovi
			super.paintComponent(g);
			//-- Izris mreže.
			WIDTH = getWidth();
			HEIGHT = getHeight();
			CELL_WIDTH = WIDTH/15;
			CELL_HEIGHT = HEIGHT/15;
			
			for (int r=1; r<ROWS; r++) {        //vodoravne crte
				g2.drawLine(0, r*CELL_HEIGHT, WIDTH, r*CELL_HEIGHT);
			}
			for (int c=1; c<COLS; c++) {        //navpične črte
				g2.drawLine(c*CELL_WIDTH, 0, c*CELL_WIDTH, HEIGHT);
			}
         
			//-- Nariše odigrane poteze na ploščo.
			for (int r=0; r<ROWS; r++) {
				for (int c=0; c<COLS; c++) {
					int x = c * CELL_WIDTH;
					int y = r * CELL_HEIGHT;
					Polje polje = igra.plosca[r][c];
					if (polje != Polje.PRAZNO) {
						if(polje == Polje.X) g.setColor(barvaTemen);
						else g.setColor(barvaSvetel);
						g2.fillOval(x+2, y+2, CELL_WIDTH-4, CELL_HEIGHT-4);
					}
				}
			}
		}
     
		//======================================== listener mousePressed
		public void mousePressed(MouseEvent e) {
			//--- map x,y coordinates into a row and col.
			int col = e.getX()/CELL_WIDTH;
			int row = e.getY()/CELL_HEIGHT;
			Koordinati trenutnaPoteza = new Koordinati(col, row);
         
			if (igra.stanje.equals(Stanje.V_TEKU) && igra.poteza(trenutnaPoteza)) {
				igra.stanje();
				this.repaint();  // Show any updates to game.
				showNextPlayer();
			}
			else Toolkit.getDefaultToolkit().beep();
        
			if (igra.stanje == Stanje.ZMAGA_O) {
				stanjeIgre.setText("BELI ZMAGA");
			}
			else if (igra.stanje == Stanje.ZMAGA_X) {
				stanjeIgre.setText("CRNI ZMAGA");
			}
		
			else if (igra.stanje == Stanje.NEODLOCENO) {
				stanjeIgre.setText("NEODLOCENO");
			}
         
		}
		//========================================== ignore these events
		public void mouseClicked (MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited  (MouseEvent e) {}
	}//konec notranjega classa GraphicsPanel
 
	//======================================= metoda za prikaz naslednjega igralca
 	private void showNextPlayer() {
 		if(igra.naPotezi == Igralec.X)
 			stanjeIgre.setText("Na potezi je črni igralec");
 		else stanjeIgre.setText("Na potezi je beli igralec");
 	}
     
 
///////////////////////////////////////// notranji class NovaIgra
 	private class NovaIgra implements ActionListener {
 		public void actionPerformed(ActionEvent e) {
 			igra = new Igra();
 			showNextPlayer();
 			prikazplosce.repaint();
 		}
 	}
 	
 	private class Razveljavi implements ActionListener {
 		public void actionPerformed(ActionEvent e) {
 			if(igra.odigranePoteze.isEmpty()) Toolkit.getDefaultToolkit().beep();
 			igra.razveljaviPotezo();
 			if (igra.stanje.equals(Stanje.V_TEKU)) showNextPlayer();
 			else Toolkit.getDefaultToolkit().beep();
 			prikazplosce.repaint();
 		}
 	}
 	
}//end class Platno