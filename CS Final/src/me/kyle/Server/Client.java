package me.kyle.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.kyle.Client.Mode;


public class Client {
	//Abstract representation of a client
	String IP;
	Mode mode;
	ClientNetworkManager networkmanager;

	public Client(ObjectInputStream in, ObjectOutputStream out){
		networkmanager = new ClientNetworkManager(this, in, out);
	}
	//TODO have an error/handle if the client cuts out...
	
	public void changeMode(Mode mode){
		networkmanager.sendCommand(mode);
	}

}
