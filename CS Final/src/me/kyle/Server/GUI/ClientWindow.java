package me.kyle.Server.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JRadioButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ButtonGroup;

import me.kyle.Communal.ClientMode;
import me.kyle.Server.Client;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientWindow extends JPanel {

	
	private static final long serialVersionUID = -5380565358058262254L;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Client client;
	JRadioButton btnSleep;
	JRadioButton btnCompute;
	JRadioButton btnReturn;
	JButton btnDisconnect;
	JLabel lblAtMode;

	/**
	 * Create the panel.
	 */
	public ClientWindow(Client client) {
		this.client = client;
		
		setBackground(Color.ORANGE);
		setBorder(BorderFactory.createLineBorder(Color.black));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		btnSleep = new JRadioButton("Sleep");
		btnSleep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestModeChange(ClientMode.Sleep);
			}
		});
		btnSleep.setSelected(true);
		btnSleep.setBackground(Color.ORANGE);
		buttonGroup.add(btnSleep);
		GridBagConstraints gbc_btnSleep = new GridBagConstraints();
		gbc_btnSleep.anchor = GridBagConstraints.WEST;
		gbc_btnSleep.insets = new Insets(0, 0, 5, 5);
		gbc_btnSleep.gridx = 1;
		gbc_btnSleep.gridy = 1;
		add(btnSleep, gbc_btnSleep);
		
		JLabel lblClient = new JLabel("Client " + client.ID);
		GridBagConstraints gbc_lblClient = new GridBagConstraints();
		gbc_lblClient.insets = new Insets(0, 0, 5, 0);
		gbc_lblClient.gridx = 3;
		gbc_lblClient.gridy = 1;
		add(lblClient, gbc_lblClient);
		
		btnCompute = new JRadioButton("Compute");
		btnCompute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestModeChange(ClientMode.GenerateNumbers);
			}
		});
		btnCompute.setBackground(Color.ORANGE);
		buttonGroup.add(btnCompute);
		GridBagConstraints gbc_btnCompute = new GridBagConstraints();
		gbc_btnCompute.anchor = GridBagConstraints.WEST;
		gbc_btnCompute.insets = new Insets(0, 0, 5, 5);
		gbc_btnCompute.gridx = 1;
		gbc_btnCompute.gridy = 2;
		add(btnCompute, gbc_btnCompute);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeClient();
			}
		});
		GridBagConstraints gbc_btnDisconnect = new GridBagConstraints();
		gbc_btnDisconnect.insets = new Insets(0, 0, 5, 0);
		gbc_btnDisconnect.gridx = 3;
		gbc_btnDisconnect.gridy = 2;
		add(btnDisconnect, gbc_btnDisconnect);
		
		btnReturn = new JRadioButton("Return");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestModeChange(ClientMode.ReturnData);
			}
		});
		btnReturn.setBackground(Color.ORANGE);
		buttonGroup.add(btnReturn);
		GridBagConstraints gbc_btnReturn = new GridBagConstraints();
		gbc_btnReturn.anchor = GridBagConstraints.WEST;
		gbc_btnReturn.insets = new Insets(0, 0, 0, 5);
		gbc_btnReturn.gridx = 1;
		gbc_btnReturn.gridy = 3;
		add(btnReturn, gbc_btnReturn);
		
		lblAtMode = new JLabel("At Mode", JLabel.CENTER);
		lblAtMode.setBackground(Color.GREEN);
		lblAtMode.setOpaque(true);
		GridBagConstraints gbc_lblAtMode = new GridBagConstraints();
		gbc_lblAtMode.fill = GridBagConstraints.BOTH;
		gbc_lblAtMode.gridx = 3;
		gbc_lblAtMode.gridy = 3;
		add(lblAtMode, gbc_lblAtMode);
	
	}
	
	public void requestModeChange(ClientMode mode){
		if(!client.getMode().equals(mode)){
			client.changeMode(mode);
			setAtMode(false);
		}
	}
	
	private void closeClient(){
		client.closeClient();
	}
	
	public void setAtMode(boolean atmode){
		lblAtMode.setBackground(atmode ? Color.GREEN : Color.RED);
	}
	
	public void setMode(ClientMode mode){
		if(client.getMode().equals(ClientMode.GenerateNumbers)){
			btnCompute.setSelected(true);
		} else if(client.getMode().equals(ClientMode.ReturnData)){
			btnReturn.setSelected(true);
		} else if(client.getMode().equals(ClientMode.Sleep)){
			btnSleep.setSelected(true);
		}
	}
	
	public void refreshMode(){
		setMode(client.getMode());
		setAtMode(true);
	}

}
