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
			File file = new File("numbers" + currentoutput + ".txt");
			file.createNewFile();
			fc = new FileOutputStream(file).getChannel();
			fc.write(buffer, 0);
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
	
	public int[] readFile(int currentinput){
		File file = new File("numbers" + currentinput + ".txt");
		try {
			FileChannel fc = new FileInputStream(file).getChannel();
			fc.read(buffer);
			buffer.rewind();
			int[] output = new int[totalnumbers];
			for(int i = 0; i < totalnumbers; i++){
				output[i] = buffer.getInt();
			}
			return output;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
