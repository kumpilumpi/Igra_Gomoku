package vmesniki;

import logika.*;
import splosno.Koordinati;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Platno extends JPanel {
 
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
		private static final int ROWS = 15;
		private static final int COLS = 15;
		private static final int CELL_SIZE = 30; // Pixels
		private static final int WIDTH  = COLS * CELL_SIZE;
		private static final int HEIGHT = ROWS * CELL_SIZE;
		
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
					Polje polje = igra.plosca[r][c];
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