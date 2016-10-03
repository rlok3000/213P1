/*
 * Som Kovacs
 * Russell Lok 
 */

package songlib.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * 
 * @author Sam Kovacs
 * @author Russell Lok
 *
 */
public class SavedSongsHandler {
	
	private String filePath;
	private File songFile;
	
	/**
	 * Creates SavedSongsHandler using input filepath.
	 * File is created or accessed based on filepath.
	 * @param filePath
	 * @throws IOException
	 */
	public SavedSongsHandler(String filePath) throws IOException {
		this.filePath = filePath;
		songFile = new File(filePath);
		songFile.createNewFile();
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * Initializes file at given filepath. 
	 * If no file exists, it is created. 
	 * Necessary to avoid FileNotFoundException with ObjectInputStream methods
	 * @throws IOException
	 */
	public void initFile() throws IOException {
		songFile = new File(filePath);
		songFile.createNewFile();
	}
	
	/**
	 * Reads and returns serialized object of file at filepath passed in constructor.
	 * If file is empty, returns allocated linked list of Songs
	 * @return
	 */
	public LinkedList<Song>readSongs() {
		ObjectInputStream reader;
		LinkedList<Song> output;
		try {
			reader = new ObjectInputStream(new FileInputStream(songFile));
			output = (LinkedList<Song>) reader.readObject();
			reader.close();
			return output;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new LinkedList<Song>();
		}
	}
	
	/**
	 * Writes passed Song linked list into file initialized by constructor.
	 * @param newSongs
	 * @throws IOException
	 */
	public void writeSongs(LinkedList<Song> newSongs) throws IOException {
		ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(songFile));
		writer.writeObject(newSongs);
		writer.flush();
		writer.close();
		return;
	}
}
