package me.kyle.Communal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileManager {
	
	private String dir;
	ByteBuffer buffer;
	private int totalnumbers;

	public FileManager(String dir, int totalnumbers){
		this.dir = dir;
		buffer = ByteBuffer.allocate(4 * totalnumbers);
		this.totalnumbers = totalnumbers;
	}
	
	public void writeFile(int[] numbers, int currentoutput){
		System.out.println("running2");
		for (int i : numbers){
			buffer = buffer.putInt(i);
		}
		buffer.rewind();

		FileChannel fc = null;
		try {
			File file = getFile(currentoutput);
			file.createNewFile();
			FileOutputStream fs = new FileOutputStream(file);
			fc = fs.getChannel();
			fc.write(buffer, 0);
			fs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			buffer.clear();
			try {
				if (fc != null) {
					fc.close();
				}
			} catch (IOException e) {}
		}
	}
	
	public void readFile(int currentinput, int[] array) throws FileNotFoundException{
		if(array.length != totalnumbers)
			throw new ArrayIndexOutOfBoundsException("Array is not of the correct size!");
		File file = getFile(currentinput);
		try {
			FileInputStream fs = new FileInputStream(file);
			FileChannel fc = fs.getChannel();
			fc.read(buffer);
			buffer.rewind();
			for(int i = 0; i < totalnumbers; i++){
				array[i] = buffer.getInt();
			}
			fs.close();
			return;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public boolean renameFile(int oldname, int newname){
		File file = getFile(oldname);
		if(!file.renameTo(getFile(newname))){
			System.out.println("file not deleted");
			return false;
		}
		return true;
	}
	
	public void removeFile(int name){
		File file = getFile(name);
		file.delete();
	}
	
	private File getFile(int number){
		return new File(/*dir + File.separator + */"numbers" + number + ".txt");
	}
}