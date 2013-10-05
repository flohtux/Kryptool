package gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.swtdesigner.SwingResourceManager;

@SuppressWarnings("serial")
public class ErrorDialog extends JDialog {

	private static ErrorDialog instance;
	private final JTextArea textAreaError;
	/**
	 * Create the dialog
	 */
	public ErrorDialog() {
		super();
		this.setIconImage(SwingResourceManager.getImage(ErrorDialog.class, "/Actions-application-exit-icon.png"));
		this.setTitle("Error");
		this.setBounds(100, 100, 424, 159);
		this.textAreaError = new JTextArea();
		this.textAreaError.setEditable(false);
		this.textAreaError.setLineWrap(true);
		this.textAreaError.setWrapStyleWord(true);
		this.getContentPane().add(this.textAreaError, BorderLayout.CENTER);

		final JPanel panel = new JPanel();
		this.getContentPane().add(panel, BorderLayout.EAST);

		final JLabel label = new JLabel();
		label.setIcon(SwingResourceManager.getIcon(ErrorDialog.class, "/Actions-application-exit-icon.png"));
		panel.add(label);
	}
	public static ErrorDialog getInstance() {
		if(ErrorDialog.instance == null) ErrorDialog.instance = new ErrorDialog();
		return ErrorDialog.instance;
	}
	
	public void show(final String text){
		this.textAreaError.setText(text);
		this.setVisible(true);
	}

}
