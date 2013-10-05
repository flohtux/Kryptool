package gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.swtdesigner.SwingResourceManager;

@SuppressWarnings("serial")
public class KeyView extends JDialog {


	private static KeyView instance = null;
	private final JTextArea textArea;
	
	public static void show(String titel,String massage){
		if(instance == null) instance = new KeyView(titel, massage);
		instance.setVisible(true);
		instance.setTitle(titel);
		instance.textArea.setText(massage);
	}
	
	/**
	 * Create the dialog
	 */
	private KeyView(final String titel,final String value) {
		super();
		this.setIconImage(SwingResourceManager.getImage(KeyView.class, "/icon_schluessel.gif"));
		this.setTitle("KeyView");
		this.setBounds(100, 100, 569, 201);

		this.textArea = new JTextArea();
		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);
		this.getContentPane().add(this.textArea, BorderLayout.CENTER);

		final JLabel titelLabel = new JLabel();
		titelLabel.setText(titel);
		this.getContentPane().add(titelLabel, BorderLayout.NORTH);
		
		this.textArea.setText(value);
	}

}
