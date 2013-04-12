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
		Scanner x = new Scanner(System.in);
		while(true){
			String input = x.nextLine();
			if(input.equalsIgnoreCase("compute")){//make it so you cannot command it the current command
				clients.get(0).changeMode(ClientMode.GenerateNumbers);
			}else if(input.equalsIgnoreCase("sleep")){
				clients.get(0).changeMode(ClientMode.Sleep);
			}else if(input.equalsIgnoreCase("return")){
				clients.get(0).changeMode(ClientMode.ReturnData);
			}else if(input.equalsIgnoreCase("data")){
				clients.get(0).networkmanager.sendData(TransferMode.SendMoreData);
			}
		}
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
	
	public static void main(String[] args){
		ServerMain main = new ServerMain();
		main.main();
	}
}
