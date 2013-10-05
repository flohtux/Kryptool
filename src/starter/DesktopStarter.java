package starter;

import gui.MainGui;

import java.awt.EventQueue;

import Whistle.WhistleLevel;
import Whistle.Whistleblower;


public class DesktopStarter {
	
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(final String args[]) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final MainGui frame = new MainGui();
					Whistleblower.getInstance().setLevel(WhistleLevel.ERROR);
					Whistleblower.getInstance().setWriter(System.out);
					frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
