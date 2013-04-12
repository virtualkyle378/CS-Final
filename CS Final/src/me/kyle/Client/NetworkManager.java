package me.kyle.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import me.kyle.Communal.CastMode;
import me.kyle.Communal.TransferMode;

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
	
	public void sendData(int[] numbers){
		try {
			out.writeObject(numbers);
		} catch (IOException e) {
			e.printStackTrace();
			}
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				TransferMode mode = (TransferMode) in.readObject();
				System.out.println("get data");
				System.out.println(CastMode.transferToClient(mode) != null);
				if(CastMode.transferToClient(mode) != null)
					main.changeMode(CastMode.transferToClient(mode));
				if (mode.equals(TransferMode.GenerateNumbers)) {
					System.out.println("compute");
					while(!main.verifyModeChange());
					for(ClientThread i: main.threads){
						synchronized(i){
							i.notify();
						}
					}
				} else if (mode.equals(TransferMode.Sleep)) {
					System.out.println("sleep");
				} else if (mode.equals(TransferMode.ReturnData)) {
					System.out.println("return");
					while(!main.verifyModeChange()){
						System.out.println(!main.verifyModeChange());
					}
					while(!main.verifyModeChange());
					System.out.println("going");
					main.returnData();
				}  else if (mode.equals(TransferMode.SendMoreData)) {
					System.out.println("notified");
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
