package me.kyle.Server;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import me.kyle.Communal.ClientMode;

public class ServerConsoleController extends Thread implements ServerController{
	
	private Scanner intscanner;
	private Scanner stringscanner;
	private ServerMain main;
	private ArrayList<Client> clients = new ArrayList<Client>();
	
	public ServerConsoleController(ServerMain main) {
		this.main = main;
		intscanner = new Scanner(System.in);
		stringscanner = new Scanner(System.in);
		start();
	}

	public void run(){
		while(true){
			System.out.println("Select a proccess:");
			System.out.println("1: View Cleints");
			System.out.println("2: Manage Client");
			System.out.println("3: Exit");
			try {
				switch(intscanner.nextInt()){
				case 1:
					listClients();
					break;
				case 2:
					manageClient();
					break;
				case 3:
					intscanner.close();
					stringscanner.close();
					main.exit();
					break;
				default:
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("You need to enter in a number between 1 and 3!");
			}
		}
	}

	private void listClients(){
		int index = 1;
		for(Client i: clients){
			System.out.println(index++ + ": Client" + i.ID + ": " + i.getMode());
		}
	}

	private void manageClient(){
		System.out.println("Select a client");
		listClients();
		Client client = null;
		while (client == null) {
			try {
				client = main.clients.get(intscanner.nextInt() - 1);
			} catch (InputMismatchException e) {
				System.out.println("Enter in an integer!");
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Select a valid client!");
			}
		}
		System.out.println("Enter in an operation:");
		System.out.println("Compute");
		System.out.println("Sleep");
		System.out.println("Return");
		String input = stringscanner.nextLine();
		if(input.equalsIgnoreCase("compute")){
			client.changeMode(ClientMode.GenerateNumbers);
		} else if(input.equalsIgnoreCase("sleep")){
			client.changeMode(ClientMode.Sleep);
		} else if(input.equalsIgnoreCase("return")){
			client.changeMode(ClientMode.ReturnData);
		} else {
			System.out.println("Invalid Operation!");
		}
	}
	
	public void addClient(Client client){
		clients.add(client);
	}
	
	public void removeClient(Client client){
		clients.remove(client);
	}
	
	public void updateClient(Client client){}
}
