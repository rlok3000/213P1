package songlib.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class SavedSongsHandler {
	
	private String filePath;
	private File songFile;
	
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
	
	public void initFile() throws IOException {
		songFile = new File(filePath);
		songFile.createNewFile();
	}
	
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
	
	public void writeSongs(LinkedList<Song> newSongs) throws IOException {
		ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(songFile));
		writer.writeObject(newSongs);
		writer.flush();
		writer.close();
		return;
	}
}
