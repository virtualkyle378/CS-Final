package me.kyle.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import me.kyle.Communal.CTSTransferMode;
import me.kyle.Communal.ClientMode;
import me.kyle.Communal.STCTransferMode;

public class NetworkManager extends Thread{

	private ClientMain main;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public NetworkManager(ClientMain main) {
		this.main = main;
	}
	
	public boolean initConnection(String IP){
		try {
			socket = new Socket(IP, 49618);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}

	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendData(CTSTransferMode mode, Object data) {
		try {
			if(!data.getClass().equals(mode.GetType()))
				throw new IllegalArgumentException("");
			out.writeObject(mode);
			out.writeObject(data);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void run() {
		try {
			while (true) {
				STCTransferMode mode = (STCTransferMode) in.readObject();
				System.out.println("get data");
				if(mode.equals(STCTransferMode.ModeChange)){
					ClientMode clientmode = (ClientMode) in.readObject();
					main.changeMode(clientmode);
					if (clientmode.equals(ClientMode.GenerateNumbers)) {
						System.out.println("compute");
						while(!main.verifyModeChange());
						for(ClientThread i: main.threads){
							synchronized(i){
								i.notify();
							}
						}
					} else if (clientmode.equals(ClientMode.Sleep)) {
						System.out.println("sleep");
					} else if (clientmode.equals(ClientMode.ReturnData)) {
						System.out.println("return");
						while(!main.verifyModeChange()){
							System.out.println(!main.verifyModeChange());
						}
						while(!main.verifyModeChange());
						System.out.println("returning");
						new ClientDataReturn(main);
					}
				} else if(mode.equals(STCTransferMode.Init)){
					System.out.println("init");
					sendData(CTSTransferMode.Init, new Integer(main.ID));
				} else if(mode.equals(STCTransferMode.Exit)){
					System.out.println("exiting");
					System.exit(0);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			System.out.println("server cut out");
			System.exit(0);
		}

	}
	
}
