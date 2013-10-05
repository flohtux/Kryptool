package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//import overhead.distribute.KryptoClient;
//import overhead.distribute.KrytoServer;

import com.swtdesigner.SwingResourceManager;


@SuppressWarnings("serial")
public class Distribute extends JDialog {

	private final JTextField newServerTextField;
	private final JList list;
	private final JTextField serverAdress;
	private final JCheckBox actAsKryptoserverCheckBox ;
	private final JCheckBox useDistributionCheckBox;
	private final Vector<String> ips;
	private final JTextField textField = new JTextField();
	private final JLabel lblPort;
	/**
	 * Create the frame
	 */
	public Distribute(final Vector<String> ips, final boolean server, final boolean distakt) {
		super();
		this.ips = ips;
		this.setTitle("Distribute");
		this.setIconImage(SwingResourceManager.getImage(Distribute.class, "/network-ring-icon.png"));
		final GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] {0, 0,0,0,0,0};
		gridBagLayout.columnWidths = new int[] {0,0, 0};
		this.getContentPane().setLayout(gridBagLayout);
		this.setBounds(100, 100, 593, 265);

		this.useDistributionCheckBox = new JCheckBox();
		this.getUseDistributionCheckBox().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
			}
		});
		this.getUseDistributionCheckBox().setText("use distribution");
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.insets = new Insets(0, 0, 5, 5);
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.gridy = 0;
		this.getContentPane().add(this.getUseDistributionCheckBox(), gridBagConstraints_6);

		this.actAsKryptoserverCheckBox = new JCheckBox();
		/*this.actAsKryptoserverCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent arg0) {
				if(Distribute.this.actAsKryptoserverCheckBox.isSelected()){
					KrytoServer.getInstance().startServer();
				}else{
					KrytoServer.getInstance().stopServer();
				}
			}
		});*/
		this.actAsKryptoserverCheckBox.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
			}
		});
		
		this.lblPort = new JLabel("Port:");
		final GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(0, 0, 5, 0);
		gbc_lblPort.gridx = 2;
		gbc_lblPort.gridy = 1;
		this.getContentPane().add(this.lblPort, gbc_lblPort);
		this.actAsKryptoserverCheckBox.setText("Act as KryptoServer");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 5);
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridx = 0;
		this.getContentPane().add(this.actAsKryptoserverCheckBox, gridBagConstraints);

		this.serverAdress = new JTextField();
		this.serverAdress.setEnabled(false);
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_1.insets = new Insets(0, 0, 5, 5);
		gridBagConstraints_1.weightx = 1.0;
		gridBagConstraints_1.gridy = 2;
		gridBagConstraints_1.gridx = 1;
		this.getContentPane().add(this.serverAdress, gridBagConstraints_1);
		final GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		this.getContentPane().add(this.textField, gbc_textField);
		this.textField.setColumns(10);

		final JLabel usedKryptoServersLabel = new JLabel();
		usedKryptoServersLabel.setText("Used Krypto Servers");
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(0, 0, 5, 5);
		gridBagConstraints_3.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_3.weighty = 1.0;
		gridBagConstraints_3.gridy = 3;
		gridBagConstraints_3.gridx = 0;
		this.getContentPane().add(usedKryptoServersLabel, gridBagConstraints_3);

		this.list = new JList();
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(0, 0, 5, 5);
		gridBagConstraints_2.gridheight = 2;
		gridBagConstraints_2.weighty = 1.0;
		gridBagConstraints_2.fill = GridBagConstraints.BOTH;
		gridBagConstraints_2.gridy = 3;
		gridBagConstraints_2.gridx = 1;
		this.getContentPane().add(this.list, gridBagConstraints_2);

		final JButton remove = new JButton();
		/*remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent paramActionEvent) {
				KryptoClient.getInstance().removeClient(Distribute.this.ips.get(Distribute.this.list.getSelectedIndex()));
				Distribute.this.ips.remove(Distribute.this.list.getSelectedIndex());
				Distribute.this.list.setListData(Distribute.this.ips);
			}
		});*/
		remove.setText("remove Server");
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.insets = new Insets(0, 0, 5, 5);
		gridBagConstraints_7.fill = GridBagConstraints.BOTH;
		gridBagConstraints_7.gridy = 4;
		gridBagConstraints_7.gridx = 0;
		this.getContentPane().add(remove, gridBagConstraints_7);

		final JButton addServerButton = new JButton();
		/*addServerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent paramActionEvent) {
				Distribute.this.ips.add(Distribute.this.newServerTextField.getText());
				Distribute.this.newServerTextField.setText("");
				Distribute.this.list.setListData(Distribute.this.ips);
				if(Distribute.this.textField.getText().equals(""))
					try {
						throw new Exception();
					} catch (final Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				try {
					KryptoClient.getInstance().addClient(Distribute.this.newServerTextField.getText(),Integer.valueOf(Distribute.this.textField.getText()));
				} catch (final NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (final UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});*/
		addServerButton.setText("Add Server");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(0, 0, 0, 5);
		gridBagConstraints_4.fill = GridBagConstraints.BOTH;
		gridBagConstraints_4.gridy = 5;
		gridBagConstraints_4.gridx = 0;
		this.getContentPane().add(addServerButton, gridBagConstraints_4);

		this.newServerTextField = new JTextField();
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(0, 0, 0, 5);
		gridBagConstraints_5.fill = GridBagConstraints.BOTH;
		gridBagConstraints_5.gridy = 5;
		gridBagConstraints_5.gridx = 1;
		this.getContentPane().add(this.newServerTextField, gridBagConstraints_5);
		
		this.list.setListData(ips);
		this.getUseDistributionCheckBox().setSelected(distakt);
		this.actAsKryptoserverCheckBox.setSelected(server);
		try {
			this.serverAdress.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (final UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public boolean getServer(){
		return this.actAsKryptoserverCheckBox.isSelected();
	}
	
	public boolean getUseDistribution(){
		return this.getUseDistributionCheckBox().isSelected();
	}
	
	public Vector<String> getServerList(){
		return this.ips;
	}

	/**
	 * @return the useDistributionCheckBox
	 */
	public JCheckBox getUseDistributionCheckBox() {
		return this.useDistributionCheckBox;
	}

}
