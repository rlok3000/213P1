package songlib.data;

public class Song {
	protected String name;
	protected String artist;
	protected String album;
	protected String year;
	
	public Song(String name, String artist) {
		this.name = name;
		this.artist = artist;
		this.album = null;
		this.year = null;
	}
	
	public Song(String name, String artist, String album, String year) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public void editSong(String name, String artist, String album, String year) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public String getSongName() {
		return name;
	}
	
	public String getSongArtist() {
		return artist;
	}
	
	public String getSongAlbum() {
		return album;
	}
	
	public String getSongYear() {
		return year;
	}
	
	public void setSongName(String name) {
		this.name = name;
		return;
	}
	
	public void setSongArtist(String artist) {
		this.artist = artist;
		return;
	}
	
	public void setSongAlbum(String album) {
		this.album = album;
		return;
	}
	
	public void setSongYear(String year) {
		this.year = year;
		return;
	}
}
