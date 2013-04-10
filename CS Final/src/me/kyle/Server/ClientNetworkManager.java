package me.kyle.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.kyle.Client.Mode;


public class ClientNetworkManager implements Runnable{
	
	ObjectInputStream in;
	ObjectOutputStream out;
	Client client;

	public ClientNetworkManager(Client client, ObjectInputStream in, ObjectOutputStream out){
		this.client = client;
		this.in = in;
		this.out = out;
	}

	public void run(){
		
	}

	public void listenForIncoming(){
		//black box for now
	}

	public void sendCommand(Mode mode){
		try {
			out.writeObject(mode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
