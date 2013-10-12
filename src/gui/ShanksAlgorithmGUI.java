package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FocusTraversalPolicy;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import core.util.NumberGeneration;
import core.shanks.Tuple;


import javax.swing.JScrollPane;

public class ShanksAlgorithmGUI {

	private JFrame frmBerechnungDesDiskreten;
	private JTextField textP;
	private JTextField textG;
	private JTextField textE;
	private JTextField textResult;
	protected BigInteger p;
	protected BigInteger g;
	protected core.shanks.Triple<BigInteger, List<Tuple>, List<Tuple>> resultTriple;
	protected BigInteger q;

	/**
	 * Launch the application.
	 */
	public static void core(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShanksAlgorithmGUI window = new ShanksAlgorithmGUI();
					window.frmBerechnungDesDiskreten.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ShanksAlgorithmGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBerechnungDesDiskreten = new JFrame();
		frmBerechnungDesDiskreten.setTitle("Berechnung des diskreten Logarithmus");
		frmBerechnungDesDiskreten.setBounds(100, 100, 490, 268);
		frmBerechnungDesDiskreten.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelAll = new JPanel();
		frmBerechnungDesDiskreten.getContentPane().add(panelAll, BorderLayout.NORTH);
		GridBagLayout gbl_panelAll = new GridBagLayout();
		gbl_panelAll.columnWidths = new int[]{448, 0};
		gbl_panelAll.rowHeights = new int[]{79, 79, 0, 0, 0, 0};
		gbl_panelAll.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelAll.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panelAll.setLayout(gbl_panelAll);

		JPanel panelContent = new JPanel();
		GridBagConstraints gbc_panelContent = new GridBagConstraints();
		gbc_panelContent.weightx = 1.0;
		gbc_panelContent.fill = GridBagConstraints.BOTH;
		gbc_panelContent.insets = new Insets(10, 10, 5, 10);
		gbc_panelContent.gridx = 0;
		gbc_panelContent.gridy = 0;
		panelAll.add(panelContent, gbc_panelContent);
		GridBagLayout gbl_panelContent = new GridBagLayout();
		gbl_panelContent.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panelContent.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelContent.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelContent.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelContent.setLayout(gbl_panelContent);
		
		JLabel lblP = new JLabel("Primzahl (p)");
		GridBagConstraints gbc_lblP = new GridBagConstraints();
		gbc_lblP.anchor = GridBagConstraints.EAST;
		gbc_lblP.insets = new Insets(0, 0, 5, 5);
		gbc_lblP.gridx = 0;
		gbc_lblP.gridy = 0;
		panelContent.add(lblP, gbc_lblP);
		
		textP = new JTextField();
		GridBagConstraints gbc_textP = new GridBagConstraints();
		gbc_textP.weightx = 1.0;
		gbc_textP.insets = new Insets(0, 0, 5, 5);
		gbc_textP.fill = GridBagConstraints.HORIZONTAL;
		gbc_textP.gridx = 1;
		gbc_textP.gridy = 0;
		panelContent.add(textP, gbc_textP);
		textP.setColumns(10);
		
		final JButton btnFindP = new JButton("Suche Primzahl");


		
		final JLabel lblStateP = new JLabel("Status");
		GridBagConstraints gbc_lblStateP = new GridBagConstraints();
		gbc_lblStateP.insets = new Insets(0, 0, 5, 5);
		gbc_lblStateP.gridx = 2;
		gbc_lblStateP.gridy = 0;
		panelContent.add(lblStateP, gbc_lblStateP);
		btnFindP.setHorizontalAlignment(SwingConstants.LEFT);
		lblP.setLabelFor(btnFindP);
		GridBagConstraints gbc_btnFindP = new GridBagConstraints();
		gbc_btnFindP.insets = new Insets(0, 0, 5, 0);
		gbc_btnFindP.gridx = 3;
		gbc_btnFindP.gridy = 0;
		panelContent.add(btnFindP, gbc_btnFindP);
		
		JLabel lblG = new JLabel("Primitivwurzel (g)");
		GridBagConstraints gbc_lblG = new GridBagConstraints();
		gbc_lblG.anchor = GridBagConstraints.EAST;
		gbc_lblG.insets = new Insets(0, 0, 5, 5);
		gbc_lblG.gridx = 0;
		gbc_lblG.gridy = 1;
		panelContent.add(lblG, gbc_lblG);
		
		textG = new JTextField();
		GridBagConstraints gbc_textG = new GridBagConstraints();
		gbc_textG.weightx = 1.0;
		gbc_textG.insets = new Insets(0, 0, 5, 5);
		gbc_textG.fill = GridBagConstraints.HORIZONTAL;
		gbc_textG.gridx = 1;
		gbc_textG.gridy = 1;
		panelContent.add(textG, gbc_textG);
		textG.setColumns(10);
		
		JButton btnFindG = new JButton("Suche Primitivwurzel");

		
		final JLabel lblStateG = new JLabel("Status");
		GridBagConstraints gbc_lblStateG = new GridBagConstraints();
		gbc_lblStateG.insets = new Insets(0, 0, 5, 5);
		gbc_lblStateG.gridx = 2;
		gbc_lblStateG.gridy = 1;
		panelContent.add(lblStateG, gbc_lblStateG);
		btnFindG.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnFindG = new GridBagConstraints();
		gbc_btnFindG.insets = new Insets(0, 0, 5, 0);
		gbc_btnFindG.gridx = 3;
		gbc_btnFindG.gridy = 1;
		panelContent.add(btnFindG, gbc_btnFindG);
		
		JLabel lblE = new JLabel("e");
		GridBagConstraints gbc_lblE = new GridBagConstraints();
		gbc_lblE.anchor = GridBagConstraints.EAST;
		gbc_lblE.insets = new Insets(0, 0, 0, 5);
		gbc_lblE.gridx = 0;
		gbc_lblE.gridy = 2;
		panelContent.add(lblE, gbc_lblE);
		
		textE = new JTextField();
		GridBagConstraints gbc_textE = new GridBagConstraints();
		gbc_textE.weightx = 1.0;
		gbc_textE.insets = new Insets(0, 0, 0, 5);
		gbc_textE.fill = GridBagConstraints.HORIZONTAL;
		gbc_textE.gridx = 1;
		gbc_textE.gridy = 2;
		panelContent.add(textE, gbc_textE);
		textE.setColumns(10);
		
		final JLabel lblStateE = new JLabel("Status");
		GridBagConstraints gbc_lblStateE = new GridBagConstraints();
		gbc_lblStateE.insets = new Insets(0, 0, 0, 5);
		gbc_lblStateE.gridx = 2;
		gbc_lblStateE.gridy = 2;
		panelContent.add(lblStateE, gbc_lblStateE);
		
		JPanel panelResult = new JPanel();
		GridBagConstraints gbc_panelResult = new GridBagConstraints();
		gbc_panelResult.insets = new Insets(15, 10, 10, 0);
		gbc_panelResult.fill = GridBagConstraints.BOTH;
		gbc_panelResult.gridx = 0;
		gbc_panelResult.gridy = 1;
		panelAll.add(panelResult, gbc_panelResult);
		GridBagLayout gbl_panelResult = new GridBagLayout();
		gbl_panelResult.columnWidths = new int[]{193, 62, 0};
		gbl_panelResult.rowHeights = new int[]{0, 15, 0, 0, 0};
		gbl_panelResult.columnWeights = new double[]{1.0, 0.0, 1.0};
		gbl_panelResult.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelResult.setLayout(gbl_panelResult);
		
		JButton btnDiskretenLogarithmusBerechen = new JButton("Diskreten Logarithmus berechen!");

		GridBagConstraints gbc_btnDiskretenLogarithmusBerechen = new GridBagConstraints();
		gbc_btnDiskretenLogarithmusBerechen.gridwidth = 3;
		gbc_btnDiskretenLogarithmusBerechen.insets = new Insets(0, 0, 5, 0);
		gbc_btnDiskretenLogarithmusBerechen.gridx = 0;
		gbc_btnDiskretenLogarithmusBerechen.gridy = 0;
		panelResult.add(btnDiskretenLogarithmusBerechen, gbc_btnDiskretenLogarithmusBerechen);
		
		JLabel lblResult = new JLabel("Ergebnis:");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblResult = new GridBagConstraints();
		gbc_lblResult.gridwidth = 3;
		gbc_lblResult.insets = new Insets(0, 0, 5, 0);
		gbc_lblResult.gridx = 0;
		gbc_lblResult.gridy = 1;
		panelResult.add(lblResult, gbc_lblResult);
		
		textResult = new JTextField();
		textResult.setHorizontalAlignment(SwingConstants.CENTER);
		textResult.setEditable(false);
		textResult.setText("g^x = e (mod p)");
		GridBagConstraints gbc_textResult = new GridBagConstraints();
		gbc_textResult.insets = new Insets(0, 0, 5, 0);
		gbc_textResult.gridwidth = 3;
		gbc_textResult.fill = GridBagConstraints.HORIZONTAL;
		gbc_textResult.gridx = 0;
		gbc_textResult.gridy = 2;
		panelResult.add(textResult, gbc_textResult);
		textResult.setColumns(10);
		
		JButton btnListenAnzeigen = new JButton("Listen anzeigen");

		GridBagConstraints gbc_btnListenAnzeigen = new GridBagConstraints();
		gbc_btnListenAnzeigen.insets = new Insets(0, 0, 5, 0);
		gbc_btnListenAnzeigen.gridx = 0;
		gbc_btnListenAnzeigen.gridy = 2;
		panelAll.add(btnListenAnzeigen, gbc_btnListenAnzeigen);
		
		final JTextArea txtrResult = new JTextArea();
		txtrResult.setColumns(20);
		txtrResult.setEditable(false);

		
		JScrollPane scroll = new JScrollPane(txtrResult);
		GridBagConstraints gbc_txtrResult = new GridBagConstraints();
		gbc_txtrResult.weighty = 1.0;
		gbc_txtrResult.insets = new Insets(0, 0, 5, 0);
		gbc_txtrResult.fill = GridBagConstraints.BOTH;
		gbc_txtrResult.gridx = 0;
		gbc_txtrResult.gridy = 3;
		panelAll.add(scroll, gbc_txtrResult);
		
		
		btnFindP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BigInteger value;
				try {
					value = core.shanks.ShanksAlgorithm.parseStringToBigInteger(textP.getText());
					core.util.Tuple<BigInteger, BigInteger> pAndQ = NumberGeneration.generateSecurePrimeAndQ(value);
					p = pAndQ.getFirst();
					q = pAndQ.getSecond();
					textP.setText(p.toString());
					lblStateP.setText("+");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					lblStateP.setText("-");
					e1.printStackTrace();
				}
			}
		});
		

		
		btnDiskretenLogarithmusBerechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (p!= null && g != null) {
					try {
						BigInteger e = core.shanks.ShanksAlgorithm.parseStringToBigInteger(textE.getText());
						textE.setText(e.toString());
						
						resultTriple = core.shanks.ShanksAlgorithm.discreteLog(g, e, p);
						
						textResult.setText(String.format("%s^x=%s (mod %s) --> x = %s",
								g.toString(), e.toString(), p.toString(), resultTriple.getA().toString()));
						lblStateE.setText("+");
					} catch (ParseException e1) {
						lblStateE.setText("-");
						e1.printStackTrace();
					}
				}
			}
		});
		
		btnFindG.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("suche g"+textG.getText());
				if (p!= null) {
					try {
						BigInteger value = core.shanks.ShanksAlgorithm.parseStringToBigInteger(textG.getText());
						g = NumberGeneration.generatePrimitiveRootModPAbove(value, p, q);
						textG.setText(g.toString());
						lblStateG.setText("+");
					} catch (ParseException e1) {
						lblStateG.setText("-");
						e1.printStackTrace();
					}
				}
			}
		});
		btnListenAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (resultTriple != null) {
					String text = "";
//					Collections.sort(resultTriple.getB());
//					Collections.sort(resultTriple.getC());
					int c = 0;
					BigInteger matchingB =null;
					outer:
					for (Tuple elem1 : resultTriple.getB()) {
						for (Tuple elem2: resultTriple.getC()) {
							if (elem1.getB().compareTo(elem2.getB()) == 0) { // y == y)
								matchingB = elem1.getB();
								break outer;
							}
						}
					}
					
					for (Tuple i : resultTriple.getB()) {
						if (matchingB != null && i.getB().compareTo(matchingB) == 0) {
							text+= "[";
						}
						text += "("+i.getA()+", "+i.getB()+")";
						if (matchingB != null && i.getB().compareTo(matchingB) == 0) {
							text+= "]";
						}
						c++;
						if (c == 4) {
							c=0;
							text+= "\n";
						} else {
							text+= "\t";
						}

					}
					text+= "\n\n";
					c=0;
					for (Tuple j : resultTriple.getC()) {
						if (matchingB != null && j.getB().compareTo(matchingB) == 0) {
							text+= "[";
						}
						text += "("+j.getA()+", "+j.getB()+")";
						if (matchingB != null && j.getB().compareTo(matchingB) == 0) {
							text+= "]";
						}
						c++;
						if (c == 4) {
							c=0;
							text+= "\n";
						} else {
							text+= "\t";
						}
					}
					
					txtrResult.setText(text);
				}
			}
		});
		
		textE.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				lblStateE.setText("?");
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
		textP.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				lblStateP.setText("?");
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
		textG.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				lblStateG.setText("?");
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
	}

}
