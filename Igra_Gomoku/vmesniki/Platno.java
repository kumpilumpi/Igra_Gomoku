package vmesniki;

//FiveGUI.java
import logika.*;
import splosno.Koordinati;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


class Platno extends JPanel {
 //=============================================== instance variables
 private GraphicsPanel prikazplosce;
 private JTextField    stanjeIgre = new JTextField();
 private Igra igra = new Igra();
 private Stanje stanje = Stanje.V_TEKU;

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
     
     //--- Set the layout and add the components
     this.setLayout(new BorderLayout());
     this.add(controlPanel , BorderLayout.NORTH);
     this.add(prikazplosce, BorderLayout.CENTER);
     this.add(stanjeIgre , BorderLayout.SOUTH);
     
     //-- Add action listeners
     newGameButton.addActionListener(new NewGameAction());
 }//end constructor

 //////////////////////////////////////////////// class GraphicsPanel
 // This is defined inside the outer class so that
 // it can use the game logic variable.
 class GraphicsPanel extends JPanel implements MouseListener {
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
         
         Polje igranoPolje = Igra.plosca[row][col];
         if (stanje == Stanje.V_TEKU && Igra.poteza(trenutnaPoteza)) {
             switch (igra.stanje) {
             
                 case ZMAGA_O: // Player one wins.  Game over.
                         stanjeIgre.setText("BELI ZMAGA");
                         break;
                         
                 case ZMAGA_X: // Player two wins.  Game over.
                         stanjeIgre.setText("CRNI ZMAGA");
                         break;
                         
                 case NEODLOCENO:  // Tie game.  Game over.
                         stanjeIgre.setText("NEODLOCENO");
                         break;
                         
                 default: showNextPlayer();
             }
                         
         } else {  // Not legal
             Toolkit.getDefaultToolkit().beep();
         }
         
         this.repaint();  // Show any updates to game.
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
	else stanjeIgre.setText("Na vrsti je črni igralec.");
 }//end showNextPlayer
     
 
 ///////////////////////////////////////// inner class NewGameAction
 private class NewGameAction implements ActionListener {
     public void actionPerformed(ActionEvent e) {
         igra = new Igra();
         showNextPlayer();
         prikazplosce.repaint();
     }
 }//end inner class NewGameAction

}//end class FiveGUI