package me.kyle.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import me.kyle.Communal.ClientMode;
import me.kyle.Communal.TransferMode;


public class Client {
	//Abstract representation of a client
	private ServerMain main;
	private ClientMode mode;
	private ClientNetworkManager networkmanager;

	public Client(Socket socket, ObjectInputStream in, ObjectOutputStream out, ServerMain main){
		networkmanager = new ClientNetworkManager(main, socket, this, in, out);
		this.main = main;
	}
	
	public ClientMode getMode(){
		return mode;
	}
	
	public void changeMode(ClientMode mode){
		networkmanager.sendCommand(mode);
		this.mode = mode;
		//TODO send a request for the mode as it changes
	}
	
	public void closeClient(){
		networkmanager.sendData(TransferMode.Exit);
		networkmanager.close();
		main.removeClient(this);
	}
}
