package vmesniki;

//FiveGUI.java
import logika.*;
import splosno.Koordinati;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class Platno extends JPanel {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L; //not sure kaj to naredi sam pol ni warninga
//=============================================== instance variables
	private GraphicsPanel prikazplosce;
	private JTextField    stanjeIgre = new JTextField();
	public Igra igra = new Igra();

 //====================================================== constructor
	public Platno() {
		//--- Create some buttons
		JButton newGameButton = new JButton("New Game");
		JButton undoButton = new JButton("Undo");

		//--- Create control panel
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(newGameButton);
		controlPanel.add(undoButton);
     
		//--- Create graphics panel
		prikazplosce = new GraphicsPanel();
		showNextPlayer();
		//--- Set the layout and add the components
		this.setLayout(new BorderLayout());
		this.add(controlPanel , BorderLayout.NORTH);
		this.add(prikazplosce, BorderLayout.CENTER);
		this.add(stanjeIgre , BorderLayout.SOUTH);
     
		//-- Add action listeners
		newGameButton.addActionListener(new NewGameAction());
		undoButton.addActionListener(new UndoAction());
	}//end constructor

 //////////////////////////////////////////////// class GraphicsPanel
 // This is defined inside the outer class so that
 // it can use the game logic variable.
	class GraphicsPanel extends JPanel implements MouseListener {
     /**
	 * 
	 */
		private static final long serialVersionUID = 1L; //not sure kaj to naredi sam pol ni warninga
		private static final int ROWS = 15;
		private static final int COLS = 15;
		private static final int CELL_SIZE = 30; // Pixels
		private static final int WIDTH  = COLS * CELL_SIZE;
		private static final int HEIGHT = ROWS * CELL_SIZE;
     
		//================================================== constructor
		public GraphicsPanel() {
			this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
			this.setBackground(Color.GRAY);
			this.addMouseListener(this);  // Listen own mouse events.
		}//end constructor
     
		//============================================== paintComponent
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			//-- Paint grid (could be done once and saved).
			for (int r=1; r<ROWS; r++) {  // Horizontal lines
				g.drawLine(0, r*CELL_SIZE, WIDTH, r*CELL_SIZE);
			}
			for (int c=1; c<COLS; c++) {
				g.drawLine(c*CELL_SIZE, 0, c*CELL_SIZE, HEIGHT);
			}
         
			//-- Draw players pieces.
			for (int r=0; r<ROWS; r++) {
				for (int c=0; c<COLS; c++) {
					int x = c * CELL_SIZE;
					int y = r * CELL_SIZE;
					Polje polje = Igra.plosca[r][c];
					if (polje != Polje.PRAZNO) {
						if(polje == Polje.X) g.setColor(Color.BLACK);
						else g.setColor(Color.WHITE);
						g.fillOval(x+2, y+2, CELL_SIZE-4, CELL_SIZE-4);
					}
				}
			}
		}//end paintComponent
     
		//======================================== listener mousePressed
		public void mousePressed(MouseEvent e) {
			//--- map x,y coordinates into a row and col.
			int col = e.getX()/CELL_SIZE;
			int row = e.getY()/CELL_SIZE;
			Koordinati trenutnaPoteza = new Koordinati(col, row);
         
			if (igra.stanje.equals(Stanje.V_TEKU) && Igra.poteza(trenutnaPoteza)) {
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
         
		}//end mousePressed
		//========================================== ignore these events
		public void mouseClicked (MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered (MouseEvent e) {}
		public void mouseExited  (MouseEvent e) {}
	}//end inner class GraphicsPanel
 
	//======================================= untility method showNextPlayer
 	private void showNextPlayer() {
 		if(Igra.naPotezi == Igralec.X)
 			stanjeIgre.setText("Na vrsti je črni igralec.");
 		else stanjeIgre.setText("Na vrsti je beli igralec.");
 	}//end showNextPlayer
     
 
///////////////////////////////////////// inner class NewGameAction
 	private class NewGameAction implements ActionListener {
 		public void actionPerformed(ActionEvent e) {
 			igra = new Igra();
 			showNextPlayer();
 			prikazplosce.repaint();
 		}
 	}//end inner class NewGameAction
 	private class UndoAction implements ActionListener {
 		public void actionPerformed(ActionEvent e) {
 			Igra.razveljaviPotezo();
 			showNextPlayer();
 			prikazplosce.repaint();
 		}
 	}//end inner class UndoAction
 	
}//end class Platno