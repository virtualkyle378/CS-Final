package me.kyle.Communal.Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

/**
 * Manages Compressed FileIO for both the client and server
 */
public class CompressedFileManager extends FileManager {

	/**
	 * Constructs a new CompressedFileManager
	 * 
	 * @param dir The desired directory and prefix of the written files
	 * @param totalnumbers The length of the data files to write/read
	 */
	public CompressedFileManager(String dir, int totalnumbers) {
		this.dir = dir;
		buffer = ByteBuffer.allocate(4 * totalnumbers);
		this.totalnumbers = totalnumbers;
	}

	@Override
	public void writeFile(int[] numbers, int name){
		for (int i : numbers){
			buffer = buffer.putInt(i);
		}
		buffer.rewind();

		FileChannel fc = null;
		try {
			File file = getFile(name);
			file.createNewFile();
			BZip2CompressorOutputStream fs = new BZip2CompressorOutputStream(new FileOutputStream(file));
			//FileOutputStream fs = new FileOutputStream(file);
			fs.write(buffer.array(), 0, buffer.capacity());
			fs.finish();
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
	
	@Override
	public void readFile(int name, int[] array) throws FileNotFoundException{
		if(array.length != totalnumbers)
			throw new ArrayIndexOutOfBoundsException("Array is not of the correct size!");
		File file = getFile(name);
		try {
			BZip2CompressorInputStream fs = new BZip2CompressorInputStream(new FileInputStream(file));;
			fs.read(buffer.array(), 0, buffer.capacity()); 
//			FileChannel fc = fs.getChannel();
//			fc.read(buffer);
			buffer.rewind();
			for(int i = 0; i < totalnumbers; i++){
				array[i] = buffer.getInt();
			}
			buffer.clear();
			fs.close();
			return;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
}
