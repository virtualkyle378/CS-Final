package me.kyle.Server;

import java.util.ArrayList;

import me.kyle.Communal.Files.UncompressedFileManager;
import me.kyle.Server.GUI.ServerGUIController;

/**
 * Manages clients to calculate and return sets of random numbers
 */
public class ServerMain {
	int numberpoolsize = 12000000;//to be determined
	//int[] numbers = new int[numberpoolsize];
	private UncompressedFileManager filemanager;
	ArrayList<Client> clients = new ArrayList<Client>();
	ServerController controller;
	
	/**
	 * Starts the server, and initializes everything
	 */
	public void main(){
		System.out.println("Starting server!");
		filemanager = new UncompressedFileManager("server", numberpoolsize);
		new ClientListener(this);
		//controller = new ServerConsoleController(this);
		controller = new ServerGUIController(this);
	}
	
	/**
	 * Closes all of the clients and shuts down the server
	 */
	public void exit(){
		for(Object i: clients.toArray())//To avoid Concurrent Modification
			((Client)i).closeClient();
		System.exit(0);
	}

	/**
	 * Takes the numbers returned from the client and writes them to disk
	 * 
	 * @param numbers Array of numbers to write to disk
	 */
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

	/**
	 * Adds a client to the main pool
	 * 
	 * @param client Client to add
	 */
	public void addClient(Client client){
		clients.add(client);
		controller.addClient(client);
	}
	
	/**
	 * Removes a client to the main pool
	 * 
	 * @param client Client to remove
	 */
	public void removeClient(Client client){
		controller.removeClient(client);
		clients.remove(client);
	}
	
	public static void main(String[] args){
		ServerMain main = new ServerMain();
		main.main();
	}
}
