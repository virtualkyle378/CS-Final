package me.kyle.Server;

import java.util.ArrayList;
import java.util.Scanner;

import me.kyle.Client.Mode;
import me.kyle.Communal.FileManager;

public class ServerMain {
	int numberpoolsize = 3;//to be determined
	int[] numbers = new int[numberpoolsize];
	FileManager writer;
	ArrayList<Client> clients = new ArrayList<Client>();
	ClientListener clientlistener;
	
	public void main(){
		System.out.println("Starting server!");
		writer = new FileManager("", numberpoolsize);
		clientlistener = new ClientListener(this);
		Scanner x = new Scanner(System.in);
		while(true){
			String input = x.nextLine();
			if(input.equalsIgnoreCase("compute")){//make it so you cannot command it the current command
				clients.get(0).changeMode(Mode.GenerateNumbers);
			}else if(input.equalsIgnoreCase("sleep")){
				clients.get(0).changeMode(Mode.Sleep);
			}else if(input.equalsIgnoreCase("return")){
				clients.get(0).changeMode(Mode.ReturnData);
			}
		}
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
