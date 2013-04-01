package me.kyle.Server;

import me.kyle.Communal.Mode;

public class Client {
	//Abstract representation of a client
	String IP; //contains IP . Will different ports need to be used?
	Mode mode;
	ClientNetworkManager networkmanager;

	public Client(String IP){}//check sockets to construct the Output and input streams.
	//possibly have an error/handle if the client cuts out...
	
	public void changeMode(Mode mode){
		
	}
	//change the mode in network manager too

}
