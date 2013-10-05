package gui;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;

import Whistle.WhistleLevel;
import Whistle.Whistleblower;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JScrollPane;

public class Console extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox comboBox;
	private JTextArea console;


	public Console() {
		comboBox =  new JComboBox(WhistleLevel.values());
		console = new JTextArea();
		OutputStream out = new OutputStream() {
			@Override
			public void write(int arg0) throws IOException {
				Console.this.console.append(""+(char)arg0);
			}
		};
		PrintStream pr = new PrintStream(out);
		Whistleblower.getInstance().setWriter(pr);
		Whistleblower.getInstance().setLevel(WhistleLevel.ERROR);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblLevel = new JLabel("Level");
		GridBagConstraints gbc_lblLevel = new GridBagConstraints();
		gbc_lblLevel.insets = new Insets(0, 0, 0, 5);
		gbc_lblLevel.anchor = GridBagConstraints.EAST;
		gbc_lblLevel.gridx = 0;
		gbc_lblLevel.gridy = 0;
		panel.add(lblLevel, gbc_lblLevel);
		
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				Whistleblower.getInstance().setLevel(Enum.valueOf(WhistleLevel.class, comboBox.getSelectedItem().toString()));
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		panel.add(comboBox, gbc_comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		console.setLineWrap(true);
		scrollPane.setViewportView(console);
	}

}
