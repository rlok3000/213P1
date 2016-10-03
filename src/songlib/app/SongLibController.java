package songlib.app;

import songlib.data.Song;

import java.util.LinkedList;
import java.util.ListIterator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;

public class SongLibController {
	protected LinkedList<Song> songLibrary;
	protected Alert duplicateAlert;
	protected Alert missingInputAlert;
	protected Alert successAlert;
	protected MouseEvent songListViewMouseEvent;
	protected int editSongKey;

	@FXML TextField addNewSongName;
	@FXML TextField addNewSongArtist;
	@FXML TextField addNewSongAlbum;
	@FXML TextField addNewSongYear;

	@FXML TextField selectedSongName;
	@FXML TextField selectedSongArtist;
	@FXML TextField selectedSongAlbum;
	@FXML TextField selectedSongYear;
	@FXML Button editSongBtn;

	@FXML ListView<Song> songListView;
	@FXML VBox addSongForm;
	@FXML VBox songDetails;
	@FXML ToggleButton addSongToolBarButton;
	@FXML Button deleteSongToolBarButton;

	protected ObservableList<Song> songLibraryWrapper;

	public SongLibController() {
		songLibrary = new LinkedList<Song>();
		duplicateAlert = new Alert(Alert.AlertType.ERROR, "The song you want to add is a duplicate!", ButtonType.CLOSE);
		missingInputAlert = new Alert(Alert.AlertType.ERROR, "The song cannot be added without a valid song name and artist name!", ButtonType.CLOSE);
		successAlert = new Alert(Alert.AlertType.INFORMATION, "Changes successful!", ButtonType.OK);
		songListViewMouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null);
	}

	@FXML
	public void initialize() {
		songLibraryWrapper = FXCollections.observableList(songLibrary);
		songListView.setItems(songLibraryWrapper);
		songListView.setCellFactory(new Callback<ListView<Song>, ListCell<Song>>(){
			@Override
			public ListCell<Song> call(ListView<Song> p) {
				ListCell<Song> cell = new ListCell<Song>(){
					@Override
					protected void updateItem(Song t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getSongName());
						}
						else {
							setText(null);
						}
					}
				};
				return cell;
			}
		});
		addSongToolBarButton.setSelected(true);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				addNewSongName.requestFocus();
			}
		});
	}

	@FXML
	protected void addSong(ActionEvent event) {
		String newSongName = addNewSongName.getText();
		String newSongArtist = addNewSongArtist.getText();
		String newSongAlbum = addNewSongAlbum.getText();
		String newSongYear = addNewSongYear.getText();

		if(newSongName.isEmpty() || newSongArtist.isEmpty()) {
			missingInputAlert.show();
			return;
		}
		Song newSongData = new Song(newSongName, newSongArtist, newSongAlbum, newSongYear);
		ListIterator<Song> iter = songLibraryWrapper.listIterator();
		int selectionIndex = 0;
		while(iter.hasNext()) {
			Song currentSong = iter.next();
			int songNameCompareVal = newSongName.compareToIgnoreCase(currentSong.getSongName());
			int songArtistCompareVal = newSongArtist.compareToIgnoreCase(currentSong.getSongArtist());
			if(songNameCompareVal < 0) {
				iter.previous();
				iter.add(newSongData);
				songListView.getSelectionModel().clearAndSelect(selectionIndex);
				songListView.refresh();
				songListView.fireEvent(songListViewMouseEvent);
				clearAddSongForm();
				return;
			}
			else if(songNameCompareVal == 0 && songArtistCompareVal == 0) {
				duplicateAlert.show();
				return;
			}
			selectionIndex++;
		}
		songLibraryWrapper.add(newSongData);
		songListView.getSelectionModel().clearAndSelect(selectionIndex);
		songListView.refresh();
		songListView.fireEvent(songListViewMouseEvent);
		clearAddSongForm();
		return;
	}

	protected void clearAddSongForm() {
		addNewSongName.clear();
		addNewSongArtist.clear();
		addNewSongAlbum.clear();
		addNewSongYear.clear();
		return;
	}

	@FXML
	protected void showAddSongForm() {
		if(addSongForm.isVisible()) {
			addSongForm.setVisible(false);
		}
		else {
			addSongForm.setVisible(true);
			songDetails.setVisible(false);
			songListView.getSelectionModel().clearSelection();
			addNewSongName.requestFocus();
		}
		return;
	}

	@FXML
	protected void showSongDetails(MouseEvent event) {
		addSongForm.setVisible(false);
		songDetails.setVisible(true);
		addSongToolBarButton.setSelected(false);
		getSongDetails();
		disableSongDetails();
		return;
	}

	protected void getSongDetails() {
		int selectedSongIndex = songListView.getSelectionModel().getSelectedIndex();
		Song songData = songLibrary.get(selectedSongIndex);//songLibrary.get(selectedSongNameKey);
		selectedSongName.setText(songData.getSongName());
		selectedSongArtist.setText(songData.getSongArtist());
		selectedSongAlbum.setText(songData.getSongAlbum());
		selectedSongYear.setText(songData.getSongYear());
		editSongKey = selectedSongIndex;//selectedSongNameKey;
		return;
	}

	@FXML
	protected void enableField(MouseEvent event) {
		TextField source = (TextField) event.getSource();
		if(source.isEditable()) {
			return;
		}
		else {
			source.setEditable(true);
			editSongBtn.setDisable(false);
		}
	}

	@FXML
	protected void disableSongDetails() {
		selectedSongName.setEditable(false);
		selectedSongArtist.setEditable(false);
		selectedSongAlbum.setEditable(false);
		selectedSongYear.setEditable(false);
		editSongBtn.setDisable(true);
	}
	
	protected void clearSongDetails() {
		selectedSongName.clear();
		selectedSongArtist.clear();
		selectedSongAlbum.clear();
		selectedSongYear.clear();
	}

	@FXML
	protected void editSong() {
		String newSongName = selectedSongName.getText();
		String newSongArtist = selectedSongArtist.getText();
		String newSongAlbum = selectedSongAlbum.getText();
		String newSongYear = selectedSongYear.getText();
		String oldSongName = songLibraryWrapper.get(editSongKey).getSongName();
		String oldSongArtist = songLibraryWrapper.get(editSongKey).getSongArtist();
		LinkedList<Song> songLibraryCopy = songLibrary;
		if(newSongName.isEmpty() || newSongArtist.isEmpty()) {
			missingInputAlert.show();
			return;
		}
		else if(oldSongName.equalsIgnoreCase(newSongName) && oldSongArtist.equalsIgnoreCase(newSongArtist)) {
			Song existingSong = songLibraryWrapper.get(editSongKey);
			existingSong.setSongName(newSongName);
			existingSong.setSongArtist(newSongArtist);
			existingSong.setSongAlbum(newSongAlbum);
			existingSong.setSongYear(newSongYear);
			songListView.refresh();
			getSongDetails();
			disableSongDetails();
			return;
		}
		else {
			songLibraryWrapper.remove(editSongKey);
		}
		Song newSongData = new Song(newSongName, newSongArtist, newSongAlbum, newSongYear);
		ListIterator<Song> iter = songLibraryWrapper.listIterator();
		int selectionIndex = 0;
		while(iter.hasNext()) {
			Song currentSong = iter.next();
			int songNameCompareVal = newSongName.compareToIgnoreCase(currentSong.getSongName());
			int songArtistCompareVal = newSongArtist.compareToIgnoreCase(currentSong.getSongArtist());
			if(songNameCompareVal <= 0) {
				iter.previous();
				iter.add(newSongData);
				songListView.getSelectionModel().clearAndSelect(selectionIndex);
				songListView.refresh();
				getSongDetails();
				disableSongDetails();
				return;
			}
			else if(songNameCompareVal == 0 && songArtistCompareVal == 0) {
				songLibrary = songLibraryCopy;
				duplicateAlert.show();
				return;
			}
			selectionIndex++;
		}
		if(songLibrary.size() == 1)
			selectionIndex--;
		songLibraryWrapper.add(newSongData);
		songListView.getSelectionModel().clearAndSelect(selectionIndex);
		songListView.refresh();
		getSongDetails();
		disableSongDetails();
		return;
	}
	
	@FXML
	protected void deleteSong(ActionEvent event) {
		int selectedIndex = songListView.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0) {
			int newSelectedIndex = 0;
			if(songLibrary.size() == 1) {
				songLibraryWrapper.remove(selectedIndex);
				songListView.getSelectionModel().clearSelection();
				songListView.refresh();
				clearSongDetails();
				showAddSongForm();
				addSongToolBarButton.setSelected(true);
				return;
			}
			else if(selectedIndex+1 < songLibrary.size()) {
				newSelectedIndex = selectedIndex+1;
				songListView.getSelectionModel().clearAndSelect(newSelectedIndex);
				songLibraryWrapper.remove(selectedIndex);
				songListView.refresh();
				getSongDetails();
				disableSongDetails();
				return;
			}
			else {
				newSelectedIndex = selectedIndex-1;
				songListView.getSelectionModel().clearAndSelect(newSelectedIndex);
				songLibraryWrapper.remove(selectedIndex);
				songListView.refresh();
				getSongDetails();
				disableSongDetails();
				return;
			}
		}
	}
}
