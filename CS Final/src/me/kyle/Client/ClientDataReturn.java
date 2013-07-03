package me.kyle.Client;

import java.io.FileNotFoundException;

import me.kyle.Communal.CTSTransferMode;
import me.kyle.Communal.ClientMode;

/**
 * Returns the processed data back to the server
 */
public class ClientDataReturn extends Thread{
	
	private ClientMain main;

	/**
	 * Starts the client's data return mechanism
	 * 
	 * @param main The main client instance
	 */
	public ClientDataReturn(ClientMain main) {
		this.main = main;
		start();
	}
	
	public void run(){
		int currentinput = 0;
		try {
			do{
				System.out.println("returning");
				main.filemanager.readFile(currentinput, main.numberpool);
				main.filemanager.removeFile(currentinput++);
				main.networkmanager.sendData(CTSTransferMode.DataSet, main.numberpool);
			} while(main.mode.equals(ClientMode.ReturnData)); 
			int newname = 0;
			while(main.filemanager.renameFile(currentinput++, newname++));
			main.currentoutput = --newname;
			main.acknowledgeModeChange();
		} catch (FileNotFoundException e) {
			main.changeMode(ClientMode.Sleep);
			main.acknowledgeModeChange();
			main.currentoutput = 0;
		} 
	}
}
