package me.kyle.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.kyle.Communal.Mode;

public class ClientNetworkManager implements Runnable{
	ObjectOutputStream output; //Object to write to send information
	ObjectInputStream input;
	Client client;//reference to the client
	
	//entire netowrk handler is up in the air for now


	public ClientNetworkManager(){
		//get the socket and construct the object(in/out)puts
	}

	public void run(){
		//run listen asynchronously
	}

	public void listenForIncoming(){
		//black box for now
	}

	public void sendCommand(Mode mode){
		
	}
}
