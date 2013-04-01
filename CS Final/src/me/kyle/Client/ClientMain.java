package me.kyle.Client;

import java.util.ArrayList;
import java.util.Scanner;

import me.kyle.Communal.FileManager;

public class ClientMain {
	NetworkManager networkmanager;
	int[] numbers = new int[3];//Will be assigned the data according to the amount of -RAM available. This is where running a linux kernel would come in handy.
	int currentindex = 0;
	int currentoutput = 0;
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
		networkmanager.start();
		for(int i = 0; i < 2; i++){
			threads.add(new ClientThread(this, numbers.length));
		}
		// make 2 computation threads.	
	}

	public synchronized Status submitNumbers(int[] numbers){
		if(!mode.equals(Mode.GenerateNumbers))
			return Status.pause;
		int needed = this.numbers.length - currentindex;
		if(needed > numbers.length)
			needed = numbers.length;
		for(int i = 0; i < needed; i++){
			this.numbers[currentindex++] = numbers[i];
		}
		if(currentindex == numbers.length){
			filemanager.writeFile(currentoutput++);
			currentindex = 0;
		}
		return Status.run;
	}
	
	public static void main(String[] args){
		ClientMain main = new ClientMain();
		main.main();
	}
}
