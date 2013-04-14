package me.kyle.Server;

import java.util.ArrayList;
import java.util.Scanner;

import me.kyle.Communal.ClientMode;
import me.kyle.Communal.FileManager;
import me.kyle.Communal.TransferMode;

public class ServerMain {
	int numberpoolsize = 12000000;//to be determined
	//int[] numbers = new int[numberpoolsize];
	FileManager filemanager;
	ArrayList<Client> clients = new ArrayList<Client>();
	ClientListener clientlistener;
	
	public void main(){
		System.out.println("Starting server!");
		filemanager = new FileManager("server", numberpoolsize);
		clientlistener = new ClientListener(this);
		new ServerConsoleController(this);
	}
	
	public void exit(){
		for(Client i: clients)
			i.closeClient();
		System.exit(0);
	}

	public synchronized void submitNumbers(int[] numbers){
		int currentoutput = 0;
		while(filemanager.fileExists(currentoutput++));
		currentoutput--;
		try {
			filemanager.writeFile(numbers, currentoutput);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addClient(Client client){
		clients.add(client);
	}
	
	public void removeClient(Client client){
		clients.remove(client);
	}
	
	public static void main(String[] args){
		ServerMain main = new ServerMain();
		main.main();
	}
}
