package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

import core.util.MathUtil;
import core.util.PosBigInt;

import Whistle.WhistleLevel;
import Whistle.Whistleblower;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class BinaryExpoModFrame extends JFrame {

	private JPanel contentPane;
	private JTextField xNumber;
	private JTextField yNumber;
	private JTextField mNumber;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JButton btnNewButton;
	private JLabel lblNewLabel_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BinaryExpoModFrame frame = new BinaryExpoModFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BinaryExpoModFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 671, 483);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblNewLabel_3 = new JLabel("x^y mod m");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.gridwidth = 2;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 0;
		contentPane.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("x:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		xNumber = new JTextField();
		GridBagConstraints gbc_xNumber = new GridBagConstraints();
		gbc_xNumber.insets = new Insets(0, 0, 5, 0);
		gbc_xNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_xNumber.gridx = 1;
		gbc_xNumber.gridy = 1;
		contentPane.add(xNumber, gbc_xNumber);
		xNumber.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("y:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		yNumber = new JTextField();
		GridBagConstraints gbc_yNumber = new GridBagConstraints();
		gbc_yNumber.insets = new Insets(0, 0, 5, 0);
		gbc_yNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_yNumber.gridx = 1;
		gbc_yNumber.gridy = 2;
		contentPane.add(yNumber, gbc_yNumber);
		yNumber.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("m:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 3;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		mNumber = new JTextField();
		GridBagConstraints gbc_mNumber = new GridBagConstraints();
		gbc_mNumber.insets = new Insets(0, 0, 5, 0);
		gbc_mNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_mNumber.gridx = 1;
		gbc_mNumber.gridy = 3;
		contentPane.add(mNumber, gbc_mNumber);
		mNumber.setColumns(10);
		
		btnNewButton = new JButton("Berechne");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BinaryExpoModFrame.this.berechne();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 4;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 5;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
	}

	protected void berechne() {
		this.textArea.setText("");
		WhistleLevel levelorg = Whistle.Whistleblower.getInstance().getLevel();
		Whistleblower.getInstance().setLevel(WhistleLevel.E_LEARN);
		PrintStream old = Whistleblower.getInstance().getWriter();
		OutputStream out = new OutputStream() {
			@Override
			public void write(int arg0) throws IOException {
				BinaryExpoModFrame.this.textArea.append(""+(char)arg0);
			}
		};
		Whistleblower.getInstance().setWriter(new PrintStream(out));
		try{
			PosBigInt base  = PosBigInt.create(this.xNumber.getText());
			PosBigInt exponent = PosBigInt.create(this.yNumber.getText());
			PosBigInt m = PosBigInt.create(this.mNumber.getText());
			MathUtil.powerByBinExpMod(base, exponent, m);
			Whistleblower.getInstance().setWriter(old);
			Whistleblower.getInstance().setLevel(levelorg);
		} catch (Exception e) {
			ErrorDialog.getInstance().show("Bitte nur natürliche Zahlen eingeben!");
		}
	}

}
