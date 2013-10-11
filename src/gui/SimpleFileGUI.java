package gui;

import gui.SwingWorkers.DeCodeFileWorker;
import gui.SwingWorkers.DeCodeWorker;
import gui.SwingWorkers.EnCodeFileWorker;
import gui.SwingWorkers.EnCodeWorker;
import gui.SwingWorkers.KeyGenWorker;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.JProgressBar;
import javax.swing.JButton;

import Util.ProgressObsever;
import Util.Progresser;

import core.KryptoFacade;
import core.algorithm.EnumAlgorithms;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class SimpleFileGUI extends JFrame  implements DropTargetListener,ProgressObsever{

	private JPanel contentPane;
	private final DropTarget dtde;
	private final JLabel op_Label;
	private final JLabel key_Label;
	private final JButton btnAbbrechen;
	private final JComboBox algo;
	private final JProgressBar progressBar;
	private final SwingWorkers workers = new SwingWorkers();
	private LinkedList<SwingWorker> currentWorker = new LinkedList<SwingWorker>();
	private JLabel sliderLabel;
	private JSlider slider;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimpleFileGUI frame = new SimpleFileGUI();
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
	public SimpleFileGUI() {
		setTitle("Kryptool - QFile");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 315, 300);
		contentPane = new JPanel();
		this.dtde = new DropTarget(contentPane, this);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblAlgorthmus = new JLabel("Algorthmus");
		GridBagConstraints gbc_lblAlgorthmus = new GridBagConstraints();
		gbc_lblAlgorthmus.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlgorthmus.anchor = GridBagConstraints.EAST;
		gbc_lblAlgorthmus.gridx = 0;
		gbc_lblAlgorthmus.gridy = 0;
		contentPane.add(lblAlgorthmus, gbc_lblAlgorthmus);
		
		algo = new JComboBox(KryptoFacade.getAlgorithms());
		algo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				GuiManager.getInstance().setCurrentAlgo(EnumAlgorithms.valueOf(SimpleFileGUI.this.algo.getSelectedItem().toString()));
			}
		});
		GridBagConstraints gbc_algo = new GridBagConstraints();
		gbc_algo.gridwidth = 2;
		gbc_algo.insets = new Insets(0, 0, 5, 0);
		gbc_algo.fill = GridBagConstraints.HORIZONTAL;
		gbc_algo.gridx = 1;
		gbc_algo.gridy = 0;
		contentPane.add(algo, gbc_algo);
		
		sliderLabel = new JLabel("Bitl\u00E4nge:");
		GridBagConstraints gbc_sliderLabel = new GridBagConstraints();
		gbc_sliderLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sliderLabel.gridx = 0;
		gbc_sliderLabel.gridy = 1;
		contentPane.add(sliderLabel, gbc_sliderLabel);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				SimpleFileGUI.this.sliderLabel.setText("Bitlänge: "+(slider.getValue()*8));
				GuiManager.getInstance().setSecLevel(slider.getValue()*8);
			}
		});
		slider.setMaximum(300);
		slider.setMinimum(1);
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.insets = new Insets(0, 0, 5, 0);
		gbc_slider.gridx = 2;
		gbc_slider.gridy = 1;
		contentPane.add(slider, gbc_slider);
		
		key_Label = new JLabel("Schl\u00FCsselstatus");
		GridBagConstraints gbc_key_Label = new GridBagConstraints();
		gbc_key_Label.insets = new Insets(0, 0, 5, 0);
		gbc_key_Label.gridwidth = 3;
		gbc_key_Label.gridx = 0;
		gbc_key_Label.gridy = 2;
		contentPane.add(key_Label, gbc_key_Label);
		
		op_Label = new JLabel("Operationsstatus");
		GridBagConstraints gbc_op_Label = new GridBagConstraints();
		gbc_op_Label.gridwidth = 3;
		gbc_op_Label.insets = new Insets(0, 0, 5, 0);
		gbc_op_Label.gridx = 0;
		gbc_op_Label.gridy = 3;
		contentPane.add(op_Label, gbc_op_Label);
		
		JButton btnNewButton = new JButton("Schl\u00FCssel berechenen");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SimpleFileGUI.this.generateKeys();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridwidth = 3;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 4;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		JButton btnVerschlsseln = new JButton("Verschl\u00FCsseln");
		btnVerschlsseln.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleFileGUI.this.encode();
			}
		});
		GridBagConstraints gbc_btnVerschlsseln = new GridBagConstraints();
		gbc_btnVerschlsseln.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnVerschlsseln.gridwidth = 3;
		gbc_btnVerschlsseln.insets = new Insets(0, 0, 5, 0);
		gbc_btnVerschlsseln.gridx = 0;
		gbc_btnVerschlsseln.gridy = 5;
		contentPane.add(btnVerschlsseln, gbc_btnVerschlsseln);
		
		JButton btnEntschlsseln = new JButton("Entschl\u00FCsseln");
		btnEntschlsseln.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleFileGUI.this.deCode();
			}
		});
		GridBagConstraints gbc_btnEntschlsseln = new GridBagConstraints();
		gbc_btnEntschlsseln.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEntschlsseln.gridwidth = 3;
		gbc_btnEntschlsseln.insets = new Insets(0, 0, 5, 0);
		gbc_btnEntschlsseln.gridx = 0;
		gbc_btnEntschlsseln.gridy = 6;
		contentPane.add(btnEntschlsseln, gbc_btnEntschlsseln);
		
		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleFileGUI.this.cancel();
			}
		});
		GridBagConstraints gbc_btnAbbrechen = new GridBagConstraints();
		gbc_btnAbbrechen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAbbrechen.gridwidth = 3;
		gbc_btnAbbrechen.insets = new Insets(0, 0, 5, 0);
		gbc_btnAbbrechen.gridx = 0;
		gbc_btnAbbrechen.gridy = 7;
		contentPane.add(btnAbbrechen, gbc_btnAbbrechen);
		
		progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressBar.gridwidth = 3;
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 8;
		contentPane.add(progressBar, gbc_progressBar);
	}

	protected void deCode() {
		this.progressBar.setValue(0);
		final CallHelper<String> helper = new CallHelper<String>() {				
			@Override
			public void callBack(final String callVal) {
				SimpleFileGUI.this.op_Label.setText(callVal);
				SimpleFileGUI.this.btnAbbrechen.setVisible(false);
			}
		};
		final DeCodeFileWorker worker = this.workers.new DeCodeFileWorker(new Progresser(this, 0),helper);
		worker.execute();
		this.handleWorker(worker);		
	}

	private void cancel(){
		for(SwingWorker worker : this.currentWorker){
			worker.cancel(true);
		}
		this.currentWorker.clear();
		this.btnAbbrechen.setVisible(false);
		this.progressUpdate(0, 0);
	}
	
	private void removeDone(){
		Iterator<SwingWorker> i = this.currentWorker.iterator();
		while(i.hasNext()){
			SwingWorker current = i.next();
			if(current.isDone()) i.remove();
		}
		if(this.currentWorker.isEmpty()){
			this.btnAbbrechen.setVisible(false);
		}
	}
	
	protected void encode() {
		this.progressBar.setValue(0);
		final CallHelper<String> helper = new CallHelper<String>() {				
			@Override
			public void callBack(final String callVal) {
				SimpleFileGUI.this.op_Label.setText(callVal);
				SimpleFileGUI.this.btnAbbrechen.setVisible(false);
			}
		};
		final EnCodeFileWorker worker = this.workers.new EnCodeFileWorker(new Progresser(this,0),helper);
		worker.execute();
		this.handleWorker(worker);		
	}

	public void generateKeys() {
		final CallHelper<Vector<String>> helper = new CallHelper<Vector<String>>() {
				
			@Override
			public void callBack(final Vector<String> callVal) {
				SimpleFileGUI.this.key_Label.setText(callVal.get(0));
				GuiManager.getInstance().saveKeys();
				SimpleFileGUI.this.progressUpdate(0, 0);
			}
		};
		final KeyGenWorker worker = this.workers.new KeyGenWorker(helper);
		worker.execute();
		this.progressUpdate(999, 1000);
		this.handleWorker(worker);
	}

	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void drop(DropTargetDropEvent e) {
		try {
            Transferable transfer = e.getTransferable();
            if(transfer.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                e.acceptDrop(DnDConstants.ACTION_COPY);
                List objects = (List)transfer.getTransferData(DataFlavor.javaFileListFlavor);
                for(Object object : objects) {
                    if(object instanceof File) {
                        File source = (File)object;
                        if(source.getName().endsWith(".ecc") || source.getName().endsWith(".rsa")){
                        	GuiManager.getInstance().loadKeys(source.getAbsolutePath());
                        	this.key_Label.setText(source.getName() + ", Schlüsseldatei geladen!");
                        	this.setAlgoFromKeyFile(source.getName());
                        	this.updateSecLevel();
                        } else {
                        	GuiManager.getInstance().setOpFile(source.getAbsolutePath());
                        	this.op_Label.setText(source.getName() + " bereit");
                        }
                    }
                }
            }
        } catch(Exception ex) {
        	ErrorDialog.getInstance().show(ex.getMessage());
        } finally {
            e.dropComplete(true);
        }
	}

	private void updateSecLevel() {
		BigInteger p = GuiManager.getInstance().getEccKeys().getPublicKey().getP();
		this.slider.setValue(p.bitLength()/8);
	}

	private void setAlgoFromKeyFile(String name) {
		if(name.endsWith("ecc")){
			GuiManager.getInstance().setCurrentAlgo(EnumAlgorithms.valueOf(this.algo.getSelectedItem().toString()));
			this.algo.setSelectedItem(EnumAlgorithms.ECC);
		}
	}

	private void handleWorker(@SuppressWarnings("rawtypes") SwingWorker worker){
		this.btnAbbrechen.setVisible(true);
		this.currentWorker.add(worker);
	}
	
	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
	}

	@Override
	public void progressUpdate(final long value,final long progressEnd) {
		this.progressBar.setMaximum((int)progressEnd);
		this.progressBar.setValue((int)value);
	}

	@Override
	public void progressUpdate(final long value) {
		this.progressBar.setValue((int)value);
	}
}
