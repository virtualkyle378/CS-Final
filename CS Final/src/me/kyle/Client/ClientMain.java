package me.kyle.Client;

import me.kyle.Communal.FileManager;
import me.kyle.Communal.Mode;

public class ClientMain {
	NetworkManager networkmanager;
	int[] numbers = new int[3];//Will be assigned the data according to the amount of -RAM available. This is where running a linux kernel would come in handy.
	FileManager filemanager;
	Mode mode;

	public static void main(String[] args){
		
	}
	// prompt for IP or get from remote server.. external hosting
	// construct file IO upon connected
	// make 2 computation threads. don’t start em yet

	public static synchronized boolean submitNumbers(int[] numbers){
		return false;
	}
	// submit numbers to the main pool the thread will wait for this to complete in the case of file output
	// returns !mode.equals(Mode.GenerateNumbers)//havent decided how to halt the threads. one thought is to have it wait on an object and notify it when ready, other is to release all reference to it and create a new one when I need to start generating again
}
