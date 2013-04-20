package me.kyle.Client;

import java.util.ArrayList;
import java.util.Scanner;

import me.kyle.Communal.CTSTransferMode;
import me.kyle.Communal.ClientMode;
import me.kyle.Communal.FileManager;

public class ClientMain {
	NetworkManager networkmanager;
	int ID;
	private int numberpoolsize = 12000000;//12000000;125000000
	int[] numberpool = new int[numberpoolsize];//Will be assigned the data according to the amount of -RAM available. This is where running a linux kernel would come in handy.
	private int currentindex = 0;
	int currentoutput = 0;
	FileManager filemanager;
	ClientMode mode = ClientMode.Sleep;
	private boolean acknowledged = true;
	ArrayList<ClientThread> threads = new ArrayList<ClientThread>();

	public void main(){
		System.out.println("Client Starting!");
		Scanner x = new Scanner(System.in);
		System.out.println("ID");
		ID = Integer.parseInt(x.nextLine());
		System.out.println("IP?");
		String IP = x.nextLine();
		x.close();
		networkmanager = new NetworkManager(this);
		if(!networkmanager.initConnection(IP)){
			System.out.println("Could not connect!");
			System.exit(1);
		}
		System.out.println("Connected!");
		filemanager = new FileManager("client" + ID, numberpoolsize);
		networkmanager.start();
		for(int i = 0; i < 2; i++){
			threads.add(new ClientThread(this, numberpool.length));
		}
	}

	public synchronized Status submitNumbers(int[] numbers){
		System.out.println("running");
		if(!mode.equals(ClientMode.GenerateNumbers))
			return Status.pause;
		int needed = numberpool.length - currentindex;
		if(needed > numbers.length)
			needed = numbers.length;
		for(int i = 0; i < needed; i++){
			numberpool[currentindex++] = numbers[i];
		}
		if(currentindex == numberpool.length){
			filemanager.writeFile(numberpool, currentoutput++);
			System.out.println(currentindex);
			currentindex = 0;
		}
		return Status.run;
	}

	public void changeMode(ClientMode mode){
		if(this.mode.equals(ClientMode.Sleep)){
			networkmanager.sendData(CTSTransferMode.ModeUpdate, mode);
			acknowledged = true;
		} else {
			acknowledged = false;
		}
		this.mode = mode;
		System.out.println("mode change");
	}
	
	public synchronized void acknowledgeModeChange(){
		acknowledged = true;
		System.out.println("ACK'D");
		networkmanager.sendData(CTSTransferMode.ModeUpdate, mode);
	}
	
	public boolean verifyModeChange(){
		return acknowledged;
	}
	
	public static void main(String[] args){
		ClientMain main = new ClientMain();
		main.main();
	}
}
