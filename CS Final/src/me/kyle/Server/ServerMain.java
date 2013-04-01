package me.kyle.Server;

import java.util.ArrayList;

import me.kyle.Communal.FileManager;

public class ServerMain {
	int X = 3;//to be determined
	int[] numbers = new int[X];
	FileManager writer;
	ArrayList<Client> clients = new ArrayList<Client>();
	ClientListener clientlistener;
	
	public void main(){
		System.out.println("Starting server!");
		writer = new FileManager("");
		clientlistener = new ClientListener(this);
		new Thread(clientlistener).start();
	}
	
	public synchronized void submitNumbers(int[] numbers){
		
	}
	
	public void addClient(Client client){
		clients.add(client);
	}
	
	public static void main(String[] args){
		ServerMain main = new ServerMain();
		main.main();
	}
}
