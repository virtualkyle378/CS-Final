package me.kyle.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import me.kyle.Communal.ClientMode;
import me.kyle.Communal.STCTransferMode;


/**
 * Abstract representation of a client
 */
public class Client {
	//Abstract representation of a client
	private ServerMain main;
	private ClientMode mode = ClientMode.Sleep;
	private ClientNetworkManager networkmanager;
	public volatile int ID = -1;

	/**
	 * Constructs a new client instance
	 * 
	 * @param socket The network Socket connection of the client
	 * @param in The inputstream connected to the client
	 * @param out The outputstream connected to the client
	 * @param main The main server instance
	 */
	public Client(Socket socket, ObjectInputStream in, ObjectOutputStream out, ServerMain main){
		networkmanager = new ClientNetworkManager(main, socket, this, in, out);
		networkmanager.sendData(STCTransferMode.Init);
		while(ID == -1);//wait for ID to init
		this.main = main;
	}

	/**
	 * @return the current mode of the client
	 */
	public ClientMode getMode(){
		return mode;
	}

	/**
	 * Low-level changes the mode of the client and notifies the server controller.
	 * <b>Never use this method </b> use changeMode() instead
	 * 
	 * @param mode  
	 */
	public void setMode(ClientMode mode){
		this.mode = mode;
		main.controller.updateClient(this);
	}

	/**
	 * Changes the mode of the client
	 * 
	 * @param mode The new mode
	 */
	public void changeMode(ClientMode mode){
		if(!this.mode.equals(mode))
			networkmanager.changeMode(mode);
		
	}
	
	/**
	 * Closes the client and removes it from the server's client list
	 */
	public void closeClient(){
		networkmanager.sendData(STCTransferMode.Exit);
		networkmanager.close();
		main.removeClient(this);
	}
}
