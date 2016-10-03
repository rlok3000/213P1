package songlib.data;

import java.io.Serializable;

/**
 * 
 * @author Russell Lok
 * @author Sam Kovacs
 *
 */
public class Song implements Serializable {
	
	protected String name;
	protected String artist;
	protected String album;
	protected String year;
	
	/**
	 * Creates Song using input name and artist
	 * @param name
	 * @param artist
	 */
	public Song(String name, String artist) {
		this.name = name;
		this.artist = artist;
		this.album = null;
		this.year = null;
	}
	
	/**
	 * Creates Song using input name, artist, album, and year
	 * @param name
	 * @param artist
	 * @param album
	 * @param year
	 */
	public Song(String name, String artist, String album, String year) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	/**
	 * Replaces respective Song variables with input name, artist, album, and year.
	 * Basically performs all set methods in Song class
	 * @param name
	 * @param artist
	 * @param album
	 * @param year
	 */
	public void editSong(String name, String artist, String album, String year) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
		return;
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
