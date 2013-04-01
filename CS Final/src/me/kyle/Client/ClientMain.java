package me.kyle.Client;

import java.util.ArrayList;
import java.util.Scanner;

import me.kyle.Communal.FileManager;

public class ClientMain {
	NetworkManager networkmanager;
	int[] numbers = new int[3];//Will be assigned the data according to the amount of -RAM available. This is where running a linux kernel would come in handy.
	FileManager filemanager;
	Mode mode;
	ArrayList<ClientThread> threads = new ArrayList<ClientThread>();

	public void main(){
		System.out.println("Client Starting!");
		Scanner x = new Scanner(System.in);
		System.out.println("IP?");
		String IP = x.nextLine();
		x.close();
		networkmanager = new NetworkManager(this);
		if(!networkmanager.initConnection(IP)){
			System.out.println("Could not connect!");
			System.exit(1);
		}
		System.out.println("Connected!");
		filemanager = new FileManager("");
		// make 2 computation threads.	
	}

	public static synchronized Status submitNumbers(int[] numbers){
		return Status.terminate;
	}
	// submit numbers to the main pool the thread will wait for this to complete in the case of file output
	// returns !mode.equals(Mode.GenerateNumbers)
	
	public static void main(String[] args){
		ClientMain main = new ClientMain();
		main.main();
	}
}
