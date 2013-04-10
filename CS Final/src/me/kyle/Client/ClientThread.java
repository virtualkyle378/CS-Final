package me.kyle.Client;

import java.util.Random;

public class ClientThread extends Thread {
	
	int[] numbers;
	//Object waiter = new Object();
	private ClientMain main;

	public ClientThread(ClientMain main, int poolmax){
		this.main = main;
		numbers = new int[(int)(poolmax/19)];//based off of 500 MB going to ~25 MB parts
		start();
	}

	public void run(){
		Random x = new Random();
		halt();
		while(true){
			do{
				for(int i = 0; i < numbers.length; i++){

					numbers[i] = x.nextInt();

				}
			} while(main.submitNumbers(numbers).equals(Status.run));//or something to that effect
			main.acknowledgeModeChange();
			halt();
		}
	}
	
	private synchronized void halt(){
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

enum Status{
	pause,
	run
}
