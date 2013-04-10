package me.kyle.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.kyle.Client.Mode;


public class Client {
	//Abstract representation of a client
	Mode mode;
	ClientNetworkManager networkmanager;
	ServerMain main;

	public Client(ObjectInputStream in, ObjectOutputStream out, ServerMain main){
		networkmanager = new ClientNetworkManager(this, in, out);
		this.main = main;
	}
	//TODO have an error/handle if the client cuts out...
	
	public void changeMode(Mode mode){
		networkmanager.sendCommand(mode);
	}

}
