package me.kyle.Server.GUI;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import me.kyle.Communal.ClientMode;
import me.kyle.Server.Client;
import me.kyle.Server.ServerController;
import me.kyle.Server.ServerMain;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ServerGUIController implements ServerController{

	private JFrame frame;
	static Object waiter = new Object();
	HashMap<Client, ClientWindow> clients = new HashMap<Client, ClientWindow>();
	JPanel panel;
	private ServerMain main;
	private JButton btnCompute;
	private JButton btnReturn;
	private JButton btnSleep;
	final ServerGUIController me;
	
	/**
	 * Launch the application.
	 */
	public ServerGUIController(ServerMain main) {
		this.main = main;
		me = this;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					me.initialize();
					me.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void addClient(final Client client){
		final ClientWindow clientwindow = new ClientWindow(client, this);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				panel.add(clientwindow);
				System.out.println("SUP");
				me.frame.setVisible(true);
			}
		});
		clients.put(client, clientwindow);
		System.out.println("SUP");
	}

	public void removeClient(final Client client){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				panel.remove(clients.remove(client));
				me.frame.setVisible(true);
			}
		});
	}
	
	public void updateClient(Client client){
		clients.get(client).refreshMode();
	}
	
	public void changeModes(ClientMode mode){
		for (ClientWindow i: clients.values()) {
			i.requestModeChange(mode);
		}
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(133dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		frame.getContentPane().add(scrollPane, "8, 2, 1, 11, fill, fill");
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		//panel.setSize(300, 300);
		scrollPane.setViewportView(panel);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.exit();
			}
		});
		
		btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeModes(ClientMode.ReturnData);
			}
		});
		
		btnCompute = new JButton("Compute");
		btnCompute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeModes(ClientMode.GenerateNumbers);
			}
		});
		
		btnSleep = new JButton("Sleep");
		btnSleep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeModes(ClientMode.Sleep);
			}
		});
		frame.getContentPane().add(btnSleep, "4, 4, fill, fill");
		frame.getContentPane().add(btnCompute, "4, 6, fill, fill");
		frame.getContentPane().add(btnReturn, "4, 8, fill, fill");
		frame.getContentPane().add(btnExit, "4, 10");
	}
}
