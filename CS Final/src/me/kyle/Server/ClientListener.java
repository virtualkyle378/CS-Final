package me.kyle.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientListener extends Thread {

	private Socket socket;
	private ServerSocket serverSocket;
	private Client client;
	private ServerMain main;

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
		for(int i = 0; i < 2; i++){
			try {
				socket = null;//client socket
				socket = serverSocket.accept();
				client = new Client(new ObjectInputStream(socket.getInputStream()), new ObjectOutputStream(socket.getOutputStream()));
				main.addClient(client);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
