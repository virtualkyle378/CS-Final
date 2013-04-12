package me.kyle.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.kyle.Communal.CastMode;
import me.kyle.Communal.ClientMode;
import me.kyle.Communal.TransferMode;


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
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendCommand(ClientMode mode){
		sendData(CastMode.clientToTransfer(mode));
	}
	
	public synchronized void sendData(TransferMode mode){
		try {
			out.writeObject(mode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
