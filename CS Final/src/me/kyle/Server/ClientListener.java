package me.kyle.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Listens for new clients attempting to connect to the server
 */
public class ClientListener extends Thread {

	private Socket socket;
	private ServerSocket serverSocket;
	private Client client;
	private ServerMain main;

	/**
	 * Constructs and starts the client listener
	 * 
	 * @param main The main server instance
	 */
	public ClientListener(ServerMain main) {
		this.main = main;
		start();
	}

	@Override
	public void run(){
		try {
			serverSocket = new ServerSocket(49618);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(;;){
			try {
				socket = null;//client socket
				socket = serverSocket.accept();
				client = new Client(socket, new ObjectInputStream(socket.getInputStream()), new ObjectOutputStream(socket.getOutputStream()), main);
				main.addClient(client);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
