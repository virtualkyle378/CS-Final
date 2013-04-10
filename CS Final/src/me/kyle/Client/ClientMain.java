package me.kyle.Client;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import me.kyle.Communal.FileManager;

public class ClientMain {
	private NetworkManager networkmanager;
	private int numberpoolsize = 12000000;
	private int[] numberpool = new int[numberpoolsize];//Will be assigned the data according to the amount of -RAM available. This is where running a linux kernel would come in handy.
	private int currentindex = 0;
	private int currentoutput = 0;
	FileManager filemanager;
	private Mode mode;
	private boolean acknowledged;
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
		filemanager = new FileManager("", numberpoolsize);
		networkmanager.start();
		for(int i = 0; i < 2; i++){
			threads.add(new ClientThread(this, numberpool.length));
		}
	}

	public synchronized Status submitNumbers(int[] numbers){
		System.out.println("running");
		if(!mode.equals(Mode.GenerateNumbers))
			return Status.pause;
		int needed = numberpool.length - currentindex;
		if(needed > numbers.length)
			needed = numbers.length;
		for(int i = 0; i < needed; i++){
			numberpool[currentindex++] = numbers[i];
		}
		if(currentindex == numbers.length){
			filemanager.writeFile(numbers, currentoutput++);
			currentindex = 0;
		}
		return Status.run;
	}
	
	public void returnData(){
		int currentinput = 0;
		try {
			do{
			filemanager.readFile(currentinput++, numberpool);
			networkmanager.sendData(numberpool);
			} while(mode.equals(Mode.ReturnData)); 
			int newname = 0;
			while(filemanager.renameFile(currentinput++, newname++));
			verifyModeChange();
		} catch (FileNotFoundException e) {
			changeMode(Mode.Sleep);
		} finally {
			currentindex = 0;
		}
	}
	
	public void changeMode(Mode mode){
		this.mode = mode;
		acknowledged = mode.equals(Mode.Sleep);
	}
	
	public void acknowledgeModeChange(){
		acknowledged = true;
	}
	
	public boolean verifyModeChange(){
		return acknowledged;
	}
	
	public static void main(String[] args){
		ClientMain main = new ClientMain();
		main.main();
	}
}
