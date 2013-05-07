package me.kyle.Server;

import java.util.ArrayList;

import me.kyle.Communal.Files.UncompressedFileManager;
import me.kyle.Server.GUI.ServerGUIController;

public class ServerMain {
	int numberpoolsize = 12000000;//to be determined
	//int[] numbers = new int[numberpoolsize];
	private UncompressedFileManager filemanager;
	ArrayList<Client> clients = new ArrayList<Client>();
	ServerController controller;
	
	public void main(){
		System.out.println("Starting server!");
		filemanager = new UncompressedFileManager("server", numberpoolsize);
		new ClientListener(this);
		//controller = new ServerConsoleController(this);
		controller = new ServerGUIController(this);
	}
	
	public void exit(){
		for(Object i: clients.toArray())//To avoid Concurrent Modification
			((Client)i).closeClient();
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
		controller.addClient(client);
	}
	
	public void removeClient(Client client){
		controller.removeClient(client);
		clients.remove(client);
	}
	
	public static void main(String[] args){
		ServerMain main = new ServerMain();
		main.main();
	}
}
