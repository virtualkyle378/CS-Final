package me.kyle.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.kyle.Client.Mode;


public class ClientNetworkManager extends Thread {
	
	ObjectInputStream in;
	ObjectOutputStream out;
	Client client;

	public ClientNetworkManager(Client client, ObjectInputStream in, ObjectOutputStream out){
		this.client = client;
		this.in = in;
		this.out = out;
		start();
	}

	public void run(){
		while (true) {
			try {
				client.main.submitNumbers((int[]) in.readObject());
				out.writeObject("OK!");
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
