package controller;

import java.io.IOException;
import java.util.ArrayList;

import application.Tag;
import application.User;
import application.UserInformation;
import application.Album;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * The {@code DisplaySearchRes} class controls the user interface for displaying search results.
 * Creates a new album from the search results, navigates through photos, displays photo information,
 * managing tags, returning to the main application window, and quitting the application.
 * @author Zaaim Rashid & Zuhayr Rashid
 */

public class DisplaySearchRes extends Stage {

    private UserInformation userInfo;
    private User user;
    private Album album;
    private int index, size;

    @FXML
    ImageView PhotoView;

    @FXML
    TextArea CaptionTextArea;

    @FXML
    ListView<String> TagsListView;

    @FXML
    TextField NewAlbumNameTextField;

    private ObservableList<String> obsList;

    /**
     * Initializes the setup for the controller.
     */
    public void setup() {
        size = album.getPhotos().size();
        index = album.getPhotos().size() - 1;
        CaptionTextArea.setEditable(false);
        updateLatestView();
    }

    /**
     * Updates the view with the latest photo information.
     */
    public void updateLatestView() {
        if (index < 0 || size == 0) {
            PhotoView.setImage(null);
            CaptionTextArea.setText(album.getName() + "\n" + "(No Result Images)");
            return;
        }

        PhotoView.setImage(album.getImage(index));

        String photoInfo = "";
        photoInfo += album.getName() + "\n";
        photoInfo += (index + 1) + " out of " + size + "\n";
        photoInfo += album.getPhoto(index).getDate().getTime().toString();
        photoInfo += "\n";
        photoInfo += album.getPhoto(index).getCaption();

        CaptionTextArea.setText(photoInfo);
        tagsView();
    }

    /**
     * Displays the tags for the current photo.
     */
    public void tagsView() {
        if (index < 0) {
            TagsListView.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
            return;
        }
        ArrayList<String> tagStringList = new ArrayList<String>();
        for (Tag t : album.getPhoto(index).getTagList()) {
            tagStringList.add(t.toString());
        }
        obsList = FXCollections.observableArrayList(tagStringList);
        TagsListView.setItems(obsList);
    }

    /**
     * Creates a new album using the provided album name.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void createNewAlbum(ActionEvent event) throws IOException {
        String albumName = NewAlbumNameTextField.getText();
        if (albumName.isEmpty() || user.albumExists(albumName)) {
            // maybe have some text popup
            return;
        }
        album.setName(albumName);
        user.addAlbum(album);
        UserInformation.store(userInfo);
        goBackToMainWindow(event);
    }

    /**
     * Navigates to the previous photo in the search results slideshow.
     */
    public void left() {
        if (index == 0) index = size - 1;
        else index--;
        updateLatestView();
    }

    /**
     * Navigates to the next photo in the search results slideshow.
     */
    public void right() {
        if (index + 1 == size) index = 0;
        else index++;
        updateLatestView();
    }

    /**
     * Returns to the main application window. Reverts control to the Main Window
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void goBackToMainWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainWindow.fxml"));
        Parent root = (Parent) loader.load();

        MainCtrl controller = loader.<MainCtrl>getController();
        controller.setUser(user);
        controller.loadUsers(userInfo);
        controller.view();

        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handles the "Quit" button
     *
     * @param event The action event.
     */
    public void quitApp(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Loads user information into the controller.
     *
     * @param user The user object.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Loads user data into the controller.
     *
     * @param userInfo The user data object.
     */
    public void loadUsers(UserInformation userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * Loads album information into the controller.
     *
     * @param album The album object.
     */
    public void loadAlbum(Album album) {
        this.album = album;
    }
}
