package me.kyle.Client;

import java.util.Random;

public class ClientThread extends Thread {
	
	private int[] numbers;
	private ClientMain main;

	/**
	 * Constructs a new ClientThread that computes and returns random numbers to the main class
	 * 
	 * @param main The main client instance
	 * @param poolmax The length of the client's main data pool array
	 */
	public ClientThread(ClientMain main, int poolmax){
		this.main = main;
		numbers = new int[(int)(poolmax/19)];//based off of 500 MB going to ~25 MB parts
		start();
	}

	public void run(){
		Random x = new Random();
		halt();
		while (true){
			do {
				for (int i = 0; i < numbers.length; i++) {

					numbers[i] = x.nextInt();

				}
			} while (main.submitNumbers(numbers).equals(Status.run));
			main.acknowledgeModeChange();
			System.out.println("stopping");
			halt();
		}
	}

	/**
	 * Pauses the computational thread
	 */
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
