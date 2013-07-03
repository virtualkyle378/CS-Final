package me.kyle.Server;

public interface ServerController {
	
	/**
	 * Notifies the server controller of a new client
	 * 
	 * @param client Client to add
	 */
	public void addClient(Client client);
	
	/**
	 * Removes a client from the server controller
	 * 
	 * @param client Client to remove
	 */
	public void removeClient(Client client);
	
	/**
	 * Notifies the server controller of updated client information
	 * 
	 * @param client Client to update
	 */
	public void updateClient(Client client);
}
