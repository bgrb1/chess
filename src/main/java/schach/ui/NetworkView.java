package schach.ui;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.util.List;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import schach.data.Variables;
import schach.data.network.ListeningMode;
import schach.data.network.OwnOpenGame;
import schach.data.network.ScanResult;
import schach.data.network.UDPClient;
import schach.data.network.UDPListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;


@SuppressWarnings("serial")
public class NetworkView extends JFrame {

	private JPanel contentPane;
	
	private JTextField nameField;
	private JPasswordField pwField;
	
	private UDPClient udp;
	public UDPListener listener;
	private DatagramSocket socket;

	
	private List<ScanResult> results;
	private JList<String> list;
	
	private Frame subframe;

	public NetworkView() {
		try {
			socket = new DatagramSocket(Variables.PORT);
			socket.setBroadcast(true);
			udp = new UDPClient(socket, this);
			listener = new UDPListener(udp, socket);
			setTitle("Multiplayer-Partie finden");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(Variables.adaptToResolution(300), Variables.adaptToResolution(100), Variables.adaptToResolution(1336), Variables.adaptToResolution(885));
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			getContentPane().setBackground(Variables.LIGHT_COLOR);
			contentPane.setLayout(null);

			final JButton btnBeitreten = new JButton("Partie beitreten!");
			btnBeitreten.setFocusPainted(false);
			btnBeitreten.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int selected = list.getSelectedIndex();
					ScanResult sr = results.get(selected);
					setEnabled(false);
					listener.setMode(ListeningMode.HANDSHAKE);
					if(!sr.isPublic()) {
						String pw = "";
						do {
							pw =  JOptionPane.showInputDialog(null, "Gib das Passwort ein:", "Passwort", JOptionPane.NO_OPTION);
						} while (pw.isEmpty());
						(subframe = new WaitingFrame2()).setVisible(true);
						udp.joinGame(sr, pw);
					} else {
						(subframe = new WaitingFrame2()).setVisible(true);
						udp.joinGame(sr);
					}
				}
			});
			btnBeitreten.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(40)));
			btnBeitreten.setEnabled(false);
			btnBeitreten.setBounds(Variables.adaptToResolution(10), Variables.adaptToResolution(726), Variables.adaptToResolution(1300), Variables.adaptToResolution(98));
			btnBeitreten.setBackground(Variables.DARK_COLOR);
			contentPane.add(btnBeitreten);
			
	        list = new JList<String>();
	        list.addListSelectionListener(new ListSelectionListener() {
	        	public void valueChanged(ListSelectionEvent arg0) {
	        		btnBeitreten.setEnabled(!list.isSelectionEmpty());
	        	}
	        });
	        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        list.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(20)));
	     
			JScrollPane scrollpane = new JScrollPane(list);
			scrollpane.setBounds(Variables.adaptToResolution(10), Variables.adaptToResolution(198), Variables.adaptToResolution(1300), Variables.adaptToResolution(517));
			contentPane.add(scrollpane);
			
			JLabel lblOffenePartien = new JLabel("Offene Partien:");
			lblOffenePartien.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(25)));
			lblOffenePartien.setBounds(Variables.adaptToResolution(10), Variables.adaptToResolution(161), Variables.adaptToResolution(193), Variables.adaptToResolution(31));
			contentPane.add(lblOffenePartien);
			
			JLabel lblNeuePartieErstellen = new JLabel("Neue Partie erstellen:");
			lblNeuePartieErstellen.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(25)));
			lblNeuePartieErstellen.setBounds(Variables.adaptToResolution(10), Variables.adaptToResolution(23), Variables.adaptToResolution(266), Variables.adaptToResolution(47));
			contentPane.add(lblNeuePartieErstellen);
			
			JLabel lblName = new JLabel("Name:");
			lblName.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(17)));
			lblName.setBounds(Variables.adaptToResolution(10), Variables.adaptToResolution(98), Variables.adaptToResolution(121), Variables.adaptToResolution(23));
			contentPane.add(lblName);
			
			nameField = new JTextField();
			nameField.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(17)));
			nameField.setBounds(Variables.adaptToResolution(69), Variables.adaptToResolution(97), Variables.adaptToResolution(314), Variables.adaptToResolution(24));
			contentPane.add(nameField);
			nameField.setColumns(10);
			
			JLabel lblPasswortempfohlen = new JLabel("Passwort (empfohlen):");
			lblPasswortempfohlen.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(17)));
			lblPasswortempfohlen.setBounds(Variables.adaptToResolution(408), Variables.adaptToResolution(98),Variables.adaptToResolution(185), Variables.adaptToResolution(23));
			contentPane.add(lblPasswortempfohlen);
			
			pwField = new JPasswordField();
			pwField.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(17)));
			pwField.setColumns(10);
			pwField.setBounds(Variables.adaptToResolution(588), Variables.adaptToResolution(97), Variables.adaptToResolution(314), Variables.adaptToResolution(24));
			contentPane.add(pwField);
			
			JButton btnErstellen = new JButton("Partie erstellen!");
			btnErstellen.setFocusPainted(false);
			btnErstellen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String uid = generateRandomUID(); // TODO generieren
					String name = nameField.getText();
					if(!name.isEmpty() && name.length() < 24) {
						setEnabled(false);
						(subframe = new WaitingFrame1(uid)).setVisible(true);
						String password = new String (pwField.getPassword());
						OwnOpenGame game;
						game = password.isEmpty() ? new OwnOpenGame(name, uid) : new OwnOpenGame(name, password, uid);
						udp.setOwnGame(game);
						listener.setMode(ListeningMode.WAIT_FOR_PLAYER);
					} else {
						JOptionPane.showMessageDialog(null, "Der Name darf nicht leer sein oder mer als 24 Zeichen haben!");
					}
				}
			});
			btnErstellen.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(30)));
			btnErstellen.setBounds(Variables.adaptToResolution(912), Variables.adaptToResolution(23), Variables.adaptToResolution(398), Variables.adaptToResolution(98));
			contentPane.add(btnErstellen);
			btnErstellen.setBackground(Variables.DARK_COLOR);
			
			JButton btnReload = new JButton("Neu laden");
			btnReload.setFocusPainted(false);
			btnReload.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					reload();
				}
			});
			btnReload.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(18)));
			btnReload.setBounds(Variables.adaptToResolution(223), Variables.adaptToResolution(161), Variables.adaptToResolution(160), Variables.adaptToResolution(30));
			contentPane.add(btnReload);
			btnReload.setBackground(Variables.DARK_COLOR);
			listener.start();
			udp.listener = listener; //TODO
			reload();
		} catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Fatal Network Error");
		}
	}
	
	private void reload() {
		setEnabled(false);
		showLoadingModel();
		listener.setMode(ListeningMode.SCAN);
		udp.searchForOpenGames();
	}
	
	public void reportFailedJoining() {
		listener.setMode(ListeningMode.OFF);
		subframe.dispose();
		JOptionPane.showMessageDialog(null, "Beitritt fehlgeschlagen!");
	}
	
	private void showLoadingModel() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		model.addElement("Spiele werden gesucht...");
		list.setModel(model);
	}
	
	public void updateList(List<ScanResult> results){
		this.results = results;
		listener.setMode(ListeningMode.OFF);
		setEnabled(true);
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(ScanResult result : results) {
			String s = null;
			if(result.isPublic()) {
				s = addPadding(result.getName()) + " | #" + result.getUID() + " | " + result.getAddress() + " | öffentlich";
			} else {
				s = addPadding(result.getName()) + " | #" + result.getUID() + " | " + result.getAddress() + " | passwortgeschützt";
			}
			model.addElement(s);
		}
		list.setModel(model);
	} 	
	
	private static String addPadding(String text) {
		int x = 24-text.length();
		StringBuilder builder = new StringBuilder();
		builder.append(text);
		for(int i = 0; i < x; i++) {
			builder.append(' ');
		}
		return builder.toString();
	}
	
	private static String generateRandomUID() {
		Random r = new Random();
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < Variables.UID_SIZE; i++) {
			builder.append(r.nextInt(10));
		}
		return builder.toString();
	}
	
	@Override
	public void dispose() {
		if(subframe != null) {
			subframe.dispose();
		}
		super.dispose();
	}
	
	private class WaitingFrame1 extends JFrame {

		private JPanel contentPane;

		protected WaitingFrame1(String uid) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(Variables.adaptToResolution(750), Variables.adaptToResolution(400), Variables.adaptToResolution(390), Variables.adaptToResolution(254));
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			getContentPane().setBackground(Variables.LIGHT_COLOR);
			setTitle("Warten...");

			JLabel lblAufGegnerWarten = new JLabel("Auf Gegner warten...\nID: #" + uid);
			lblAufGegnerWarten.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(30)));
			lblAufGegnerWarten.setBounds(Variables.adaptToResolution(54), Variables.adaptToResolution(31), Variables.adaptToResolution(286), Variables.adaptToResolution(63));
			contentPane.add(lblAufGegnerWarten);
			
			JLabel lblNewLabel = new JLabel("ID: #" + uid);
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(20)));
			lblNewLabel.setBounds(Variables.adaptToResolution(52), Variables.adaptToResolution(102), Variables.adaptToResolution(286), Variables.adaptToResolution(23));
			contentPane.add(lblNewLabel);
			
			JButton btnAbbrechen = new JButton("Abbrechen");
			btnAbbrechen.setFocusPainted(false);
			btnAbbrechen.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(18)));
			btnAbbrechen.setBounds(Variables.adaptToResolution(112), Variables.adaptToResolution(153), Variables.adaptToResolution(159), Variables.adaptToResolution(23));
			contentPane.add(btnAbbrechen);
			btnAbbrechen.setBackground(Variables.DARK_COLOR);
			btnAbbrechen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					listener.setMode(ListeningMode.OFF);
					NetworkView.this.setEnabled(true);
					dispose();
				}
			});
			setAlwaysOnTop(true);
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}
	}
	private class WaitingFrame2 extends JFrame {

		private JPanel contentPane;

		protected WaitingFrame2() {
			setTitle("Spiel beitreten...");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(Variables.adaptToResolution(750), Variables.adaptToResolution(400), Variables.adaptToResolution(390), Variables.adaptToResolution(227));
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			getContentPane().setBackground(Variables.LIGHT_COLOR);

			JLabel lblBeitreten = new JLabel("Versuche dem Spiel beizutreten....");
			lblBeitreten.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(20)));
			lblBeitreten.setBounds(Variables.adaptToResolution(46), Variables.adaptToResolution(34), Variables.adaptToResolution(354), Variables.adaptToResolution(63));
			contentPane.add(lblBeitreten);
			
			JButton btnAbbrechen = new JButton("Abbrechen");
			btnAbbrechen.setFocusPainted(false);
			btnAbbrechen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					listener.setMode(ListeningMode.OFF);
					NetworkView.this.setEnabled(true);
					dispose();
				}
			});
			btnAbbrechen.setFont(new Font("Tahoma", Font.PLAIN, Variables.adaptToResolution(18)));
			btnAbbrechen.setBounds(Variables.adaptToResolution(100), Variables.adaptToResolution(110), Variables.adaptToResolution(159), Variables.adaptToResolution(23));
			contentPane.add(btnAbbrechen);
			btnAbbrechen.setBackground(Variables.DARK_COLOR);

			setAlwaysOnTop(true);
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}
	}
}
