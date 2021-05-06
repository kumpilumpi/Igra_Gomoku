package vmesniki;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import javax.swing.*;

import vodja.Vodja;
import vodja.VrstaIgralca;
import logika.Igralec;
import logika.Stanje;


@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {
	/**
	 * JPanel, v katerega igramo
	 */
	private Platno platno;

	
	//Statusna vrstica v spodnjem delu okna
	private JLabel status;
	
	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	private JMenuItem potezaRazveljavi;

	/**
	 * Ustvari novo glavno okno in prični igrati igro.
	 */
	
	public Okno() {
		this.setTitle("Pet v vrsto");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
	
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("New Game");
		menu_bar.add(igra_menu);
		JMenu poteza_menu = new JMenu("Edit");
		menu_bar.add(poteza_menu);

		igraClovekRacunalnik = new JMenuItem("Človek – računalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("Računalnik – človek");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);
		
		igraClovekClovek = new JMenuItem("Človek – človek");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
		igraRacunalnikRacunalnik = new JMenuItem("Računalnik – računalnik");
		igra_menu.add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);
		
		potezaRazveljavi = new JMenuItem("Razveljavi");
		poteza_menu.add(potezaRazveljavi);
		potezaRazveljavi.addActionListener(this);
		
		// igralno  polje
		platno = new Platno();

		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(platno, polje_layout);
		
		// statusna vrstica za sporočila
		status = new JLabel();
		Font font1 = new Font("SansSerif", Font.BOLD, 18);
		status.setFont(font1);
		
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		status.setText("Izberite način igre!");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.O, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.X, VrstaIgralca.C);
			Vodja.igramoNovoIgro();
		}
		
		else if (e.getSource() == potezaRazveljavi) {
			if (Vodja.igra != null) {	
				if(Vodja.igra.odigranePoteze.isEmpty()) Toolkit.getDefaultToolkit().beep();
	 			else if (Vodja.igra.stanje.equals(Stanje.V_TEKU)) {
	 				Vodja.igra.razveljaviPotezo();
	 				osvezi();}
			}
 			Toolkit.getDefaultToolkit().beep();
		}
	}
	
	public void osvezi() {
		if (Vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(Vodja.igra.stanje) {
			case NEODLOCENO: status.setText("Neodločeno!"); break;
			case V_TEKU:
				String barva = Vodja.igra.naPotezi.equals(Igralec.O) ? "Beli" : "Črni";	
				status.setText("Na potezi je " + barva + 
						" - " + Vodja.vrstaIgralca.get(Vodja.igra.naPotezi)); 
				break;
			case ZMAGA_O: 
				status.setText("Zmagal je Beli - " + 
						Vodja.vrstaIgralca.get(Igralec.O));
				break;
			case ZMAGA_X: 
				status.setText("Zmagal je Črni - " + 
						Vodja.vrstaIgralca.get(Igralec.X));
				break;
			}
		}
		platno.repaint();
	}
}
