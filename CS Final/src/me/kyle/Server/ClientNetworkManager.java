package me.kyle.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import me.kyle.Communal.CastMode;
import me.kyle.Communal.ClientMode;
import me.kyle.Communal.TransferMode;


public class ClientNetworkManager extends Thread {
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Client client;

	public ClientNetworkManager(Socket socket, Client client, ObjectInputStream in, ObjectOutputStream out){
		this.socket = socket;
		this.client = client;
		this.in = in;
		this.out = out;
		start();
	}

	public void run(){
		try {
			while (true) {
				client.main.submitNumbers((int[]) in.readObject());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			System.out.println("client cut out");
			
		}
	}
	
	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
