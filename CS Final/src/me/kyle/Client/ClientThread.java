package me.kyle.Client;

import java.util.Random;

public class ClientThread {
	int[] numbers;

	public ClientThread(int poolmax){
		numbers = new int[(int)(poolmax/19)];//based off of 500 MB going to ~25 MB parts
	}

	public void run(){
		Random x = new Random();
		do{

			for(int i = 0; i < numbers.length; i++){

				numbers[i] = x.nextInt();

			}
		} while(ClientMain.submitNumbers(numbers).equals(Status.run));//or something to that effect
	}
}

enum Status{
	pause,
	terminate,
	run
}
