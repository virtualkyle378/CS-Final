package me.kyle.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkManager extends Thread{

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
		while (true) {
			try {
				Mode mode = (Mode) in.readObject();
				main.mode = mode;
				if (mode.equals(Mode.GenerateNumbers)) {
					System.out.println("compute");
					for(ClientThread i: main.threads){
						synchronized(i){
							i.notify();
						}
					}
				} else if (mode.equals(Mode.Sleep)) {
					System.out.println("sleep");
				} else if (mode.equals(Mode.ReturnData)) {
					System.out.println("return");
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
