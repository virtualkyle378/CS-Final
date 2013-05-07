package me.kyle.Communal.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

public abstract class FileManager {
	
	protected String dir;
	protected ByteBuffer buffer;
	protected int totalnumbers;
	
	public abstract void writeFile(int[] numbers, int name);
	
	public abstract void readFile(int name, int[] array) throws FileNotFoundException;
	
	public boolean renameFile(int oldname, int newname){
		return getFile(oldname).renameTo(getFile(newname));
	}
	
	public boolean fileExists(int name){
		File file = getFile(name);
		return file.exists();
	}
	
	public void removeFile(int name){
		File file = getFile(name);
		file.delete();
	}
	
	protected File getFile(int name){
		return new File(/*dir + File.separator + */dir + "numbers" + name + ".txt");
	}
}
