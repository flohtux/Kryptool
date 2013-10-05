package gui; 

import gui.SwingWorkers.DeCodeWorker;
import gui.SwingWorkers.EnCodeWorker;
import gui.SwingWorkers.KeyGenWorker;
import gui.SwingWorkers.SignWorker;
import gui.SwingWorkers.VerifyWorker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Util.Observer;
import Util.ProgressObsever;
import Util.Progresser;
import core.algorithm.EnumAlgorithms;
import core.alphabet.EnumAlphabet;
import core.hash.EnumHashFunktions;
import core.util.PosBigInt;
import core.util.StringUtil;

import javax.swing.JScrollPane;

//import overhead.distribute.KryptoClient;
//import overhead.distribute.ServerAddress;

import com.swtdesigner.SwingResourceManager;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

@SuppressWarnings("serial")
public class MainGui extends JFrame implements ProgressObsever, Observer {

	private final SwingWorkers workers = new SwingWorkers();
	private JComboBox algorithmsComboBox;
	private	JComboBox hashFunktioncomboBox;
	private JSlider primTestsslider;
	private JCheckBox useAsciiChckbx;
	private JSlider keySizeSlider;
	private JSlider blockSizeSlider;
	private JProgressBar progressBar;
	private JList keylist;
	private JButton btnKeyGen;
	private JButton btnEncode;
	private JButton btnDeCode;
	private JButton btnSign;
	private JButton btnVerify;
	private JButton btnKeyEnter;
	private JLabel blocksizeValueLabel;
	private JLabel primTestValueLabel;
	private JLabel keySizeValueLabel;
	private JTextArea cleartextTextArea;
	private JTextArea clipertextArea;
	private JComboBox comboBoxAlphabet;
	private JButton btnAbbrechen;

	private LinkedList<SwingWorker> currentWorker = new LinkedList<SwingWorker>();

	
	/**
	 * Create the frame
	 */
	public MainGui() {
		super();
		this.init();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(800, 600));
		this.setTitle("Krytool");
		this.setIconImage(SwingResourceManager.getIcon(MainGui.class, "/Secrecy.png").getImage());
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
	
		this.btnKeyEnter.setBorderPainted(false);
		this.btnKeyEnter.setBorder(null);
		this.btnKeyEnter.setIcon(SwingResourceManager.getIcon(MainGui.class, "/icon_schluessel.gif"));
		toolBar.add(this.btnKeyEnter);
		
		this.btnKeyGen.setBorderPainted(false);
		this.btnKeyGen.setBorder(null);
		this.btnKeyGen.setIcon(SwingResourceManager.getIcon(MainGui.class, "/icon_schluessel.gif"));

		toolBar.add(this.btnKeyGen);
		
		
		this.btnEncode.setBorderPainted(false);
		this.btnEncode.setBorder(null);
		this.btnEncode.setIcon(SwingResourceManager.getIcon(MainGui.class, "/Lock.png"));
		toolBar.add(this.btnEncode);
				
		this.btnDeCode.setBorderPainted(false);
		this.btnDeCode.setBorder(null);
		this.btnDeCode.setIcon(SwingResourceManager.getIcon(MainGui.class, "/Unlock.png"));
		toolBar.add(this.btnDeCode);
				
		this.btnSign.setBorderPainted(false);
		this.btnSign.setBorder(null);
		this.btnSign.setIcon(SwingResourceManager.getIcon(MainGui.class, "/Actions-document-sign-icon.png"));
		toolBar.add(this.btnSign);
		
		
		this.btnVerify.setBorderPainted(false);
		this.btnVerify.setBorder(null);
		this.btnVerify.setIcon(SwingResourceManager.getIcon(MainGui.class, "/Document-Copy-icon.png"));
		toolBar.add(this.btnVerify);
		
		btnAbbrechen.setVisible(false);
		btnAbbrechen.setBorderPainted(false);
		btnAbbrechen.setIcon(SwingResourceManager.getIcon(MainGui.class, "/ajax-loader.gif"));
		toolBar.add(btnAbbrechen);
		
		this.progressBar.setBounds(new Rectangle(5, 5, 5, 5));
		this.getContentPane().add(this.progressBar, BorderLayout.SOUTH);
		
		final JPanel centerPanel = new JPanel();
		centerPanel.setBorder(null);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));
		
		this.keylist.setBorder(new TitledBorder(null, "Schl\u00FCssel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		centerPanel.add(this.keylist, BorderLayout.NORTH);
		
		final JSplitPane centerSplit = new JSplitPane();
		centerSplit.setDividerSize(20);
		centerSplit.setName("Einstellungen");
		centerSplit.setOneTouchExpandable(true);
		centerSplit.setResizeWeight(0.5);
		centerSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
		centerPanel.add(centerSplit, BorderLayout.CENTER);
		
		final JSplitPane splitPane = new JSplitPane();
		splitPane.setAlignmentY(Component.CENTER_ALIGNMENT);
		splitPane.setResizeWeight(0.5);
		centerSplit.setLeftComponent(splitPane);
		splitPane.setOneTouchExpandable(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(null, "Klartext", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPane.setLeftComponent(scrollPane);
		
		cleartextTextArea.setLineWrap(true);
		cleartextTextArea.setBorder(null);
		scrollPane.setViewportView(cleartextTextArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new TitledBorder(null, "Verschl\u00FCsselter Test / Signatur", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPane.setRightComponent(scrollPane_1);
		
		clipertextArea.setLineWrap(true);
		scrollPane_1.setViewportView(clipertextArea);
		
		final JPanel panelSettings = new JPanel();
		panelSettings.setBorder(new TitledBorder(null, "Einstellungen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		centerSplit.setRightComponent(panelSettings);
		final GridBagLayout gbl_panelSettings = new GridBagLayout();
		gbl_panelSettings.columnWidths = new int[]{0, 0, 0};
		gbl_panelSettings.rowHeights = new int[]{0, 0, 0};
		gbl_panelSettings.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelSettings.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelSettings.setLayout(gbl_panelSettings);
		
		final JPanel panelEnDeSettings = new JPanel();
		panelEnDeSettings.setBorder(new TitledBorder(null, "Verschl\u00FCsselung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		final GridBagConstraints gbc_panelEnDeSettings = new GridBagConstraints();
		gbc_panelEnDeSettings.insets = new Insets(0, 0, 5, 5);
		gbc_panelEnDeSettings.fill = GridBagConstraints.BOTH;
		gbc_panelEnDeSettings.gridx = 0;
		gbc_panelEnDeSettings.gridy = 0;
		panelSettings.add(panelEnDeSettings, gbc_panelEnDeSettings);
		final GridBagLayout gbl_panelEnDeSettings = new GridBagLayout();
		gbl_panelEnDeSettings.columnWidths = new int[]{0, 50, 201, 0, 0};
		gbl_panelEnDeSettings.rowHeights = new int[]{0, 0, 39, 0};
		gbl_panelEnDeSettings.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelEnDeSettings.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelEnDeSettings.setLayout(gbl_panelEnDeSettings);
		
		final JLabel lblBlockGre = new JLabel("Blockgr\u00F6\u00DFe:");
		final GridBagConstraints gbc_lblBlockGre = new GridBagConstraints();
		gbc_lblBlockGre.anchor = GridBagConstraints.WEST;
		gbc_lblBlockGre.insets = new Insets(0, 0, 5, 5);
		gbc_lblBlockGre.gridx = 0;
		gbc_lblBlockGre.gridy = 0;
		panelEnDeSettings.add(lblBlockGre, gbc_lblBlockGre);
		
		final GridBagConstraints gbc_blocksizeValueLabel = new GridBagConstraints();
		gbc_blocksizeValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_blocksizeValueLabel.gridx = 1;
		gbc_blocksizeValueLabel.gridy = 0;
		panelEnDeSettings.add(this.blocksizeValueLabel, gbc_blocksizeValueLabel);
		
		
		final GridBagConstraints gbc_blockSizeSlider = new GridBagConstraints();
		gbc_blockSizeSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_blockSizeSlider.insets = new Insets(0, 0, 5, 5);
		gbc_blockSizeSlider.gridx = 2;
		gbc_blockSizeSlider.gridy = 0;
		panelEnDeSettings.add(this.blockSizeSlider, gbc_blockSizeSlider);
		
		final JLabel lblSchlsselgre = new JLabel("Schl\u00FCsselgr\u00F6\u00DFe:");
		final GridBagConstraints gbc_lblSchlsselgre = new GridBagConstraints();
		gbc_lblSchlsselgre.insets = new Insets(0, 0, 5, 5);
		gbc_lblSchlsselgre.gridx = 0;
		gbc_lblSchlsselgre.gridy = 1;
		panelEnDeSettings.add(lblSchlsselgre, gbc_lblSchlsselgre);
		
		final GridBagConstraints gbc_keySizeValueLabel = new GridBagConstraints();
		gbc_keySizeValueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_keySizeValueLabel.gridx = 1;
		gbc_keySizeValueLabel.gridy = 1;
		panelEnDeSettings.add(this.keySizeValueLabel, gbc_keySizeValueLabel);
		
		final GridBagConstraints gbc_keySizeSlider = new GridBagConstraints();
		gbc_keySizeSlider.fill = GridBagConstraints.HORIZONTAL;
		gbc_keySizeSlider.insets = new Insets(0, 0, 5, 5);
		gbc_keySizeSlider.gridx = 2;
		gbc_keySizeSlider.gridy = 1;
		panelEnDeSettings.add(this.keySizeSlider, gbc_keySizeSlider);
		
		final GridBagConstraints gbc_useAsciiChckbx = new GridBagConstraints();
		gbc_useAsciiChckbx.anchor = GridBagConstraints.WEST;
		gbc_useAsciiChckbx.gridwidth = 3;
		gbc_useAsciiChckbx.insets = new Insets(0, 0, 0, 5);
		gbc_useAsciiChckbx.gridx = 0;
		gbc_useAsciiChckbx.gridy = 2;
		panelEnDeSettings.add(this.useAsciiChckbx, gbc_useAsciiChckbx);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Alphabetauswahl", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		panelSettings.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel = new JLabel("Alphabet");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		this.comboBoxAlphabet = new JComboBox(GuiManager.getAlphabets());
		GridBagConstraints gbc_comboBoxAlphabet = new GridBagConstraints();
		gbc_comboBoxAlphabet.gridx = 1;
		gbc_comboBoxAlphabet.gridy = 0;
		panel_1.add(comboBoxAlphabet, gbc_comboBoxAlphabet);
		
		final JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Primzahl Erzeugung", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		final GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 0, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panelSettings.add(panel_2, gbc_panel_2);
		final GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 46, 141, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		final JLabel lblAnzahlPrimtests = new JLabel("Anzahl Tests:");
		final GridBagConstraints gbc_lblAnzahlPrimtests = new GridBagConstraints();
		gbc_lblAnzahlPrimtests.insets = new Insets(0, 0, 0, 5);
		gbc_lblAnzahlPrimtests.gridx = 0;
		gbc_lblAnzahlPrimtests.gridy = 0;
		panel_2.add(lblAnzahlPrimtests, gbc_lblAnzahlPrimtests);
		
		final GridBagConstraints gbc_primTestValueLabel = new GridBagConstraints();
		gbc_primTestValueLabel.insets = new Insets(0, 0, 0, 5);
		gbc_primTestValueLabel.gridx = 1;
		gbc_primTestValueLabel.gridy = 0;
		panel_2.add(this.primTestValueLabel, gbc_primTestValueLabel);
		
		final GridBagConstraints gbc_primTestsslider = new GridBagConstraints();
		gbc_primTestsslider.fill = GridBagConstraints.HORIZONTAL;
		gbc_primTestsslider.gridx = 2;
		gbc_primTestsslider.gridy = 0;
		panel_2.add(this.primTestsslider, gbc_primTestsslider);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Signierung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 1;
		panelSettings.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		final JLabel lblHashfunktion = new JLabel("Hashfunktion");
		GridBagConstraints gbc_lblHashfunktion = new GridBagConstraints();
		gbc_lblHashfunktion.insets = new Insets(0, 0, 0, 5);
		gbc_lblHashfunktion.gridx = 0;
		gbc_lblHashfunktion.gridy = 0;
		panel_3.add(lblHashfunktion, gbc_lblHashfunktion);
		this.hashFunktioncomboBox = new JComboBox(GuiManager.getHashFunktions());
		GridBagConstraints gbc_hashFunktioncomboBox = new GridBagConstraints();
		gbc_hashFunktioncomboBox.gridx = 1;
		gbc_hashFunktioncomboBox.gridy = 0;
		panel_3.add(hashFunktioncomboBox, gbc_hashFunktioncomboBox);
		centerSplit.setDividerLocation(0.5);
		centerSplit.setDividerLocation(350);
		
		final JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		final JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);
		
		final JMenuItem mntmDataOpen = new JMenuItem("Klartext \u00F6ffnen");
		mntmDataOpen.setActionCommand("Klartext \u00F6ffnen");
		mntmDataOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainGui.this.openFile(true);
			}
		});
		mnDatei.add(mntmDataOpen);
		
		JMenuItem mntmChiffreffnen = new JMenuItem("Chiffre/Signatur \u00F6ffnen");
		mntmChiffreffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainGui.this.openFile(false);
			}
		});
		mnDatei.add(mntmChiffreffnen);
		
		final JMenuItem mntmDataSave = new JMenuItem("Datei speichern");
		mnDatei.add(mntmDataSave);
		
		final JMenuItem mntmKeySave = new JMenuItem("Schl\u00FCssel speichern");
		mnDatei.add(mntmKeySave);
		
		final JMenu mnInfo = new JMenu("Info");
		menuBar.add(mnInfo);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Ausgabe Console");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Console con = new Console();
				con.setVisible(true);
			}
		});
		mnInfo.add(mntmNewMenuItem);
		
		final JMenuItem mntmWebsite = new JMenuItem("Website");
		mnInfo.add(mntmWebsite);
		
		JMenu mnExtras = new JMenu("Extras");
		menuBar.add(mnExtras);
		
		JMenuItem mntmBinreExpoten = new JMenuItem("Bin\u00E4re Exponentiation mod m\n");
		mntmBinreExpoten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BinaryExpoModFrame frame = new BinaryExpoModFrame();
				frame.setVisible(true);
			}
		});
		mnExtras.add(mntmBinreExpoten);
		
		final JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		menuBar.add(separator);
		
		final JLabel lblAlgorithmus = new JLabel("Algorithmus");
		menuBar.add(lblAlgorithmus);
		
		menuBar.add(this.algorithmsComboBox);
		
		this.addListeners();
	}

	/* -------------------------------------------- GUI Init ----------------------------------------------*/

	protected void openFile(boolean cleartext) {
		JFileChooser chooser = new JFileChooser();
		chooser.showDialog(null, "");
		File file = chooser.getSelectedFile();
		if(file != null){
			try {
				String fileText = StringUtil.readFile(file);
				if(cleartext){
					cleartextTextArea.setText(fileText);
				} else {
					clipertextArea.setText(fileText);
				}	
			} catch (FileNotFoundException e) {
				ErrorDialog.getInstance().show(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void init() {
		this.algorithmsComboBox = new JComboBox(GuiManager.getAlgorithms());
		this.primTestsslider = new JSlider();
		this.primTestsslider.setMinimum(10); //TODO wo anders
		this.primTestsslider.setMaximum(100);
		this.useAsciiChckbx = new JCheckBox("ascii chiffre");
		this.keySizeSlider = new JSlider();
		this.keySizeSlider.setMinimum(10);
		this.keySizeSlider.setMaximum(2000);
		this.blockSizeSlider = new JSlider();
		this.blockSizeSlider.setMinimum(1);
		this.blockSizeSlider.setMaximum(250);
		this.progressBar = new JProgressBar();
		this.cleartextTextArea = new JTextArea();
		this.clipertextArea = new JTextArea();
		this.clipertextArea.setBorder(null);
		this.keylist = new JList();
		this.btnKeyGen = new JButton("Schl\u00FCsselgenerieung");
		this.btnEncode = new JButton("Verschl\u00FCsseln"); 
		this.btnDeCode = new JButton("Entschl\u00FCsseln");
		this.btnSign = new JButton("Signieren");
		this.btnVerify  = new JButton("Verifizieren");
		this.btnKeyEnter  = new JButton("Schl\u00FCsseleingabe");
		this.blocksizeValueLabel = new JLabel("value");
		this.primTestValueLabel = new JLabel("value");
		this.keySizeValueLabel  = new JLabel("value");
		this.blocksizeValueLabel.setText(String.valueOf(GuiManager.getInstance().getBlocksize()));
		this.keySizeValueLabel.setText(String.valueOf(GuiManager.getInstance().getPrimeBitLenght() * 2));
		this.primTestValueLabel.setText(String.valueOf(GuiManager.getInstance().getPrimeTests()));
		this.blockSizeSlider.setValue(GuiManager.getInstance().getBlocksize());
		this.primTestsslider.setValue(GuiManager.getInstance().getPrimeTests());
		this.keySizeSlider.setValue(GuiManager.getInstance().getPrimeBitLenght() * 2);
		this.keylist.setListData(GuiManager.getInstance().getKeyStringVector());
		this.btnAbbrechen = new JButton("Abbrechen");

		this.progressBar.setStringPainted(true);
	}
	
	protected void showKey(Object selectedValue) {
		KeyView.show("Schlüssel", selectedValue.toString());
	}

	private void cancel(){
		for(SwingWorker worker : this.currentWorker){
			worker.cancel(true);
		}
		this.currentWorker.clear();
		this.btnAbbrechen.setVisible(false);
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
	
	private void addListeners(){
		keylist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				MainGui.this.showKey(keylist.getSelectedValue());
			}
		});
		this.btnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainGui.this.cancel();
			}
		});
		this.algorithmsComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent arg0) {
				MainGui.this.changedAlgorithm();
			}
		});
		this.hashFunktioncomboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent arg0) {
				MainGui.this.changedHashFunktion();
			}
		});
		this.primTestsslider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				MainGui.this.primeTestValueChanged();
				MainGui.this.primTestValueLabel.setText(String.valueOf(MainGui.this.primTestsslider.getValue()));
			}
		});
		this.useAsciiChckbx.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				MainGui.this.useAsciiCliperChanged();
			}
		});
		this.keySizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				MainGui.this.keySizeValueChanged();
				MainGui.this.keySizeValueLabel.setText(String.valueOf(MainGui.this.keySizeSlider.getValue()));
			}
		});
		this.blockSizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				MainGui.this.blockSizeValueChanged();
				MainGui.this.blocksizeValueLabel.setText(String.valueOf(MainGui.this.blockSizeSlider.getValue()));
			}
		});
		this.comboBoxAlphabet.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				MainGui.this.alphabetChanged();
			}
		});
		this.btnKeyGen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				MainGui.this.generateKeys();
			}
		});
		this.btnEncode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				MainGui.this.doEnCode();
			}
		});
		this.btnDeCode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				MainGui.this.doDeCode(); 
			}
		});
		this.btnSign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				MainGui.this.doSign();
			}
		});
		this.btnVerify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				MainGui.this.doVerify();
			}
		});
		this.btnKeyEnter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				MainGui.this.showKeyEnterDialog();
			}
		});
		this.btnKeyEnter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(final MouseEvent arg0) {
			}
		});
	}
	
	/* -------------------------------------------- Kryto Operations ----------------------------------------------*/
	public void doDeCode(){
		this.progressBar.setValue(0);
		final CallHelper<String> helper = new CallHelper<String>() {				
			@Override
			public void callBack(final String callVal) {
				MainGui.this.cleartextTextArea.setText(callVal);
				MainGui.this.btnAbbrechen.setVisible(false);
			}
		};
		final DeCodeWorker worker = this.workers.new DeCodeWorker(this.clipertextArea.getText(), new Progresser(this, 0),helper);
		worker.execute();
		this.handleWorker(worker);
	}
	
	private void handleWorker(SwingWorker worker){
		this.btnAbbrechen.setVisible(true);
		this.currentWorker.add(worker);
	}
	
	public void doEnCode(){
		this.progressBar.setValue(0);
		final CallHelper<String> helper = new CallHelper<String>() {				
			@Override
			public void callBack(final String callVal) {
				MainGui.this.clipertextArea.setText(callVal);
				MainGui.this.btnAbbrechen.setVisible(false);
			}
		};
		final EnCodeWorker worker = this.workers.new EnCodeWorker(this.cleartextTextArea.getText(),new Progresser(this,0),helper);
		worker.execute();
		this.handleWorker(worker);
	}
	
	public void doVerify(){
		this.progressBar.setValue(0);
		final CallHelper<Boolean> helper = new CallHelper<Boolean>() {
			
			@Override
			public void callBack(Boolean callVal) {
				VerifyDialog.getInstanceAndShow(callVal);
				MainGui.this.btnAbbrechen.setVisible(false);
			}
		};
		final VerifyWorker worker = this.workers.new VerifyWorker(this.cleartextTextArea.getText(),PosBigInt.create(this.clipertextArea.getText()),helper);
		worker.execute();
		this.handleWorker(worker);
	}
	
	public void doSign(){
		this.progressBar.setValue(0);
		final CallHelper<String> helper = new CallHelper<String>() {
			
			@Override
			public void callBack(String callVal) {
				MainGui.this.clipertextArea.setText(callVal);
				MainGui.this.btnAbbrechen.setVisible(false);
			}
		};
		final SignWorker worker = this.workers.new SignWorker(this.cleartextTextArea.getText(),helper);
		worker.execute();
		this.handleWorker(worker);
	}
	
	public void generateKeys() {
		final CallHelper<Vector<String>> helper = new CallHelper<Vector<String>>() {
				
			@Override
			public void callBack(final Vector<String> callVal) {
				MainGui.this.keylist.setListData(callVal);
				MainGui.this.btnAbbrechen.setVisible(false);
				MainGui.this.blockSizeSlider.setValue(GuiManager.getInstance().getBlocksize());
				MainGui.this.blockSizeSlider.setMaximum(GuiManager.getInstance().getBlocksize());
			}
		};
		final KeyGenWorker worker = this.workers.new KeyGenWorker(helper);
		worker.execute();
		this.handleWorker(worker);
	}
	protected void showKeyEnterDialog() {
		try {
			GuiManager.getInstance().showKeyEnterDialog(new CallHelper<Vector<String>>() {
				
				@Override
				public void callBack(final Vector<String> callVal) {
					MainGui.this.keylist.setListData(callVal);
					MainGui.this.btnAbbrechen.setVisible(false);
				}
			});
		} catch (final Exception e) {
			ErrorDialog.getInstance().show(e.getMessage());
			e.printStackTrace();
		}
	}

	protected void blockSizeValueChanged() {
		GuiManager.getInstance().setBlocksize(this.blockSizeSlider.getValue());
	}

	protected void keySizeValueChanged() {
		GuiManager.getInstance().setPrimeBitLenght(this.keySizeSlider.getValue() / 2);
	}

	protected void useAsciiCliperChanged() {
		GuiManager.getInstance().setUseAsciiCliper(this.useAsciiChckbx.isSelected());
	}

	protected void primeTestValueChanged() {
		GuiManager.getInstance().setPrimeTests(this.primTestsslider.getValue());
	}

	protected void changedHashFunktion() {
		GuiManager.getInstance().setCurrentHashFunk(EnumHashFunktions.valueOf(this.hashFunktioncomboBox.getSelectedItem().toString()));
	}

	protected void changedAlgorithm() {
		GuiManager.getInstance().setCurrentAlgo(EnumAlgorithms.valueOf(this.algorithmsComboBox.getSelectedItem().toString()));
	}
	
	protected void alphabetChanged() {
		GuiManager.getInstance().setCurrentAlphabet(EnumAlphabet.valueOf(this.comboBoxAlphabet.getSelectedItem().toString()));
		MainGui.this.blockSizeSlider.setValue(GuiManager.getInstance().getBlocksize());
		MainGui.this.blockSizeSlider.setMaximum(GuiManager.getInstance().getBlocksize());
	}
	
	/* -------------------------------------------- Implements ----------------------------------------------*/
	
	@Override
	public void progressUpdate(final long value,final long progressEnd) {
		this.progressBar.setMaximum((int)progressEnd);
		this.progressBar.setValue((int)value);
	}
	
	@Override
	public void handleNotifycation() {
	//	this.clipertextArea.setText(KryptoClient.getInstance().getCurrentResult());
	}

	@Override
	public void progressUpdate(final long value) {
		this.progressBar.setValue((int)value);
	}

}
