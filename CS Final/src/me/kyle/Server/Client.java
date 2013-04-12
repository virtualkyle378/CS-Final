package me.kyle.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import me.kyle.Communal.ClientMode;


public class Client {
	//Abstract representation of a client
	ClientMode mode;
	ClientNetworkManager networkmanager;
	ServerMain main;

	public Client(Socket socket, ObjectInputStream in, ObjectOutputStream out, ServerMain main){
		networkmanager = new ClientNetworkManager(socket, this, in, out);
		this.main = main;
	}
	
	public void changeMode(ClientMode mode){
		networkmanager.sendCommand(mode);
	}
	
	public void closeClient(){
		networkmanager.close();
		main.removeClient(this);
	}
}
