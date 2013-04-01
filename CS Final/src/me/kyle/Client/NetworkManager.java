package me.kyle.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkManager implements Runnable{

	ClientMain main;
	ObjectInputStream in;
	ObjectOutputStream out;
	
	public NetworkManager(ClientMain main) {
		this.main = main;
	}
	
	public boolean initConnection(String IP){
		try {
			Socket socket = new Socket(IP, 49618);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	
}
