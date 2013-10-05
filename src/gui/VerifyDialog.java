package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.swtdesigner.SwingResourceManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class VerifyDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private boolean result;
	private final JButton button;


	private static VerifyDialog instance;
	
	public static void getInstanceAndShow(final boolean result){
		if(instance == null) instance = new VerifyDialog();
		instance.setResult(result);
		instance.setVisualResult();
		instance.setVisible(true);
	}
	
	/**
	 * Create the dialog.
	 */
	public VerifyDialog() {
		this.setBounds(100, 100, 404, 217);
		this.getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.getContentPane().add(this.contentPanel, BorderLayout.CENTER);
		final GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		this.contentPanel.setLayout(gbl_contentPanel);
		{
			final JLabel lblNewLabel = new JLabel("Verifizierungsergebnis:");
			final GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.gridheight = 2;
			gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 0;
			this.contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			final JPanel panel = new JPanel();
			final GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridheight = 2;
			gbc_panel.gridwidth = 4;
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 2;
			gbc_panel.gridy = 0;
			this.contentPanel.add(panel, gbc_panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				button = new JButton();
				button.setBorder(null);
				panel.add(button, BorderLayout.CENTER);
			}
		}
		{
			final JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				final JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						VerifyDialog.this.setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				this.getRootPane().setDefaultButton(okButton);
			}
		}
	}

	private void setVisualResult(){
		if(this.isResult()){
			button.setIcon(SwingResourceManager.getIcon(MainGui.class, "/Ok-icon.png"));
		} else {
			button.setIcon(SwingResourceManager.getIcon(MainGui.class, "/Close-2-icon.png"));

		}
	}
	
	/**
	 * @return the result
	 */
	public boolean isResult() {
		return this.result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(final boolean result) {
		this.result = result;
	}

}
