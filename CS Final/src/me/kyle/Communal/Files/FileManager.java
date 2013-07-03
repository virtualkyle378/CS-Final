package me.kyle.Communal.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

/**
 * Manages FileIO for both the client and server
 */
public abstract class FileManager {
	
	protected String dir;
	protected ByteBuffer buffer;
	protected int totalnumbers;
	
	/**
	 * Writes a data file to disk
	 * 
	 * @param numbers The numbers to write
	 * @param name The index of the file
	 */
	public abstract void writeFile(int[] numbers, int name);
	
	/**
	 * Reads a data file from disk
	 * 
	 * @param name Index of file
	 * @param array The array to load the data into
	 * @throws FileNotFoundException if the file does not exist
	 */
	public abstract void readFile(int name, int[] array) throws FileNotFoundException;
	
	/**
	 * Renames a file
	 * 
	 * @param oldname The old index
	 * @param newname The new index
	 * @return True if the rename succeeded
	 */
	public boolean renameFile(int oldname, int newname){
		return getFile(oldname).renameTo(getFile(newname));
	}
	
	/**
	 * Checks if a file exists
	 * 
	 * @param name Index of file
	 * @return True if the desired file exists
	 */
	public boolean fileExists(int name){
		File file = getFile(name);
		return file.exists();
	}
	
	/**
	 * Deletes a file from disk
	 * 
	 * @param name Index of file
	 */
	public void removeFile(int name){
		File file = getFile(name);
		file.delete();
	}
	
	/**
	 * Gets the file of desired index
	 * 
	 * @param name Index of file
	 * @return The file
	 */
	protected File getFile(int name){
		return new File(/*dir + File.separator + */dir + "numbers" + name + ".txt");
	}
}
