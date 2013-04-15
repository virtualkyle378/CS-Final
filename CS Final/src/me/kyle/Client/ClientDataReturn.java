package me.kyle.Client;

import java.io.FileNotFoundException;

import me.kyle.Communal.ClientMode;

public class ClientDataReturn extends Thread{
	
	private ClientMain main;

	public ClientDataReturn(ClientMain main) {
		this.main = main;
		start();
	}
	
	public void run(){
		int currentinput = 0;
		try {
			do{
				main.filemanager.readFile(currentinput, main.numberpool);
				main.filemanager.removeFile(currentinput++);
				main.networkmanager.sendData(main.numberpool);
			} while(main.mode.equals(ClientMode.ReturnData)); 
			int newname = 0;
			while(main.filemanager.renameFile(currentinput++, newname++));
			main.currentoutput = --newname;
			main.acknowledgeModeChange();
		} catch (FileNotFoundException e) {
			main.changeMode(ClientMode.Sleep);
			main.currentoutput = 0;
		} 
	}
}
