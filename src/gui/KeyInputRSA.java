package gui;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.swtdesigner.SwingResourceManager;

import core.key.KeyPairRSA;
import core.key.PrivateKeyRSA;
import core.key.PublicKeyRSA;
import core.util.PosBigInt;

@SuppressWarnings("serial")
public class KeyInputRSA extends JDialog {
	private final JTextField puk;
	private final JTextField prk;
	private final JTextField mainModul;
	@SuppressWarnings("rawtypes")
	private final CallHelper callHelper;
	/**
	 * Create the dialog
	 * @param c 
	 */
	@SuppressWarnings("rawtypes")
	public KeyInputRSA(final CallHelper c) {
		super();
		this.callHelper = c;
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0,0};
		gridBagLayout.rowHeights = new int[] {0,0,0};
		this.getContentPane().setLayout(gridBagLayout);
		this.setIconImage(SwingResourceManager.getImage(KeyInputRSA.class, "/icon_schluessel.gif"));
		this.setTitle("Key input - RSA");
		this.setBounds(100, 100, 500, 210);

		final JLabel mainModulLabel = new JLabel();
		mainModulLabel.setText("Main modul");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 10, 10);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		this.getContentPane().add(mainModulLabel, gridBagConstraints);

		this.mainModul = new JTextField();
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(10, 10, 10, 10);
		gridBagConstraints_3.weightx = 1.0;
		gridBagConstraints_3.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_3.gridy = 0;
		gridBagConstraints_3.gridx = 1;
		this.getContentPane().add(this.mainModul, gridBagConstraints_3);

		final JLabel privateKeyencodeLabel = new JLabel();
		privateKeyencodeLabel.setText("Private key (encode exponent)");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(10, 10, 10, 10);
		gridBagConstraints_1.anchor = GridBagConstraints.WEST;
		gridBagConstraints_1.gridy = 1;
		gridBagConstraints_1.gridx = 0;
		this.getContentPane().add(privateKeyencodeLabel, gridBagConstraints_1);

		this.prk = new JTextField();
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 10, 10, 10);
		gridBagConstraints_4.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_4.gridy = 1;
		gridBagConstraints_4.gridx = 1;
		this.getContentPane().add(this.prk, gridBagConstraints_4);

		final JLabel publicKeydecodeLabel = new JLabel();
		publicKeydecodeLabel.setText("Public key (decode exponent)");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(10, 10, 10, 10);
		gridBagConstraints_2.anchor = GridBagConstraints.WEST;
		gridBagConstraints_2.gridy = 2;
		gridBagConstraints_2.gridx = 0;
		this.getContentPane().add(publicKeydecodeLabel, gridBagConstraints_2);

		this.puk = new JTextField();
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(10, 10, 10, 10);
		gridBagConstraints_5.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_5.gridy = 2;
		gridBagConstraints_5.gridx = 1;
		this.getContentPane().add(this.puk, gridBagConstraints_5);

		final JButton saveButton = new JButton();
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				KeyInputRSA.this.save();
			}
		});
		saveButton.setText("save");
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(10, 10, 10, 10);
		gridBagConstraints_8.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_8.gridwidth = 2;
		gridBagConstraints_8.gridy = 3;
		gridBagConstraints_8.gridx = 0;
		this.getContentPane().add(saveButton, gridBagConstraints_8);
		//
	}
	@SuppressWarnings("unchecked")
	protected void save() {
		try {
			final PublicKeyRSA puk = new PublicKeyRSA( PosBigInt.create(this.mainModul.getText()), PosBigInt.create(this.puk.getText()));
			final PrivateKeyRSA prk = new PrivateKeyRSA( PosBigInt.create(this.mainModul.getText()), PosBigInt.create(this.prk.getText()));
			final KeyPairRSA kp = new KeyPairRSA(puk, prk);
			this.callHelper.callBack(kp);
			this.setVisible(false);
		}catch(final NumberFormatException e){
			ErrorDialog.getInstance().show(Consts.NUMBERFORMATERROR);
		}
	}

}
