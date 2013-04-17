package me.kyle.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import me.kyle.Communal.ClientMode;
import me.kyle.Communal.STCTransferMode;


public class Client {
	//Abstract representation of a client
	private ServerMain main;
	private ClientMode mode = ClientMode.Sleep;
	private ClientNetworkManager networkmanager;
	public volatile int ID = -1;

	public Client(Socket socket, ObjectInputStream in, ObjectOutputStream out, ServerMain main){
		networkmanager = new ClientNetworkManager(main, socket, this, in, out);
		networkmanager.sendData(STCTransferMode.Init);
		while(ID == -1);//wait for ID to init
		this.main = main;
	}
	
	public ClientMode getMode(){
		return mode;
	}
	
	public void setMode(ClientMode mode){
		this.mode = mode;
		main.controller.updateClient(this);
	}
	
	public void changeMode(ClientMode mode){
		networkmanager.sendCommand(mode);
	}
	
	public void closeClient(){
		networkmanager.sendData(STCTransferMode.Exit);
		networkmanager.close();
		main.removeClient(this);
	}
}
