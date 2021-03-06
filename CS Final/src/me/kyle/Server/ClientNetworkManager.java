package me.kyle.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import me.kyle.Communal.CTSTransferMode;
import me.kyle.Communal.ClientMode;
import me.kyle.Communal.STCTransferMode;


public class ClientNetworkManager extends Thread {
	
	private ServerMain main;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Client client;

	/**
	 * Constructs and starts a networkmanager for the supplied client
	 * 
	 * @param main The main server instance
	 * @param socket The client's socket
	 * @param client The client's instance
	 * @param in The inputstream connected to the client
	 * @param out The outputstream connected to the client
	 */
	public ClientNetworkManager(ServerMain main, Socket socket, Client client, ObjectInputStream in, ObjectOutputStream out){
		this.main = main;
		this.socket = socket;
		this.client = client;
		this.in = in;
		this.out = out;
		start();
	}

	public void run(){
		try {
			while (true) {
				CTSTransferMode mode = (CTSTransferMode) in.readObject();
				if(mode.equals(CTSTransferMode.ModeUpdate))
					client.setMode((ClientMode) in.readObject());
				else if (mode.equals(CTSTransferMode.DataSet))
					main.submitNumbers((int[]) in.readObject());
				else if (mode.equals(CTSTransferMode.Init))
					client.ID = ((Integer) in.readObject()).intValue();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			//client.closeClient(); make disconnected state. If something tries to re-init with same ID then overwrite?
		}
	}
	
	/**
	 * Closes the network manager
	 */
	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a change mode command to the client
	 * 
	 * @param mode The new mode
	 */
	public void changeMode(ClientMode mode){
		sendData(STCTransferMode.ModeChange);
		sendData(mode);
	}
	
	
	/**
	 * Sends an object to the client
	 * 
	 * @param object The object to send
	 */
	public synchronized void sendData(Object object){
		try {
			out.writeObject(object);
		} catch (IOException e) {
			//client.closeClient();
		}
	}
}
