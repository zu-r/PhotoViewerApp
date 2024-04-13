package controller;

import java.io.IOException;

import application.Album;
import application.Photo;
import application.User;
import application.UserInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * The {@code MovePhoto} class controls the user interface for moving a photo to another album.
 * It provides methods for handling the movement of a photo, loading the main application window, and quitting the application.
 * @author Zaaim & Zuhayr Rashid
 */

public class MovePhoto {

    // Instance Variables
    @FXML
    private Button CancelButton;
    @FXML
    private ListView<String> DestinationAlbumList;

    private ObservableList<String> obsList;
    private UserInformation userInfo;
    private User user;
    private Album album;
    private Photo photo;
    private int index;
    private String destinationAlbumName;

    /**
     * Loads the user information into the controller.
     *
     * @param user The user object.
     */

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Loads the user data into the controller.
     *
     * @param userInfo The user data object.
     */

    public void loadUsers(UserInformation userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * Loads the album information into the controller.
     *
     * @param album The album object.
     */

    public void loadAlbum(Album album) {
        this.album = album;
    }

    /**
     * Loads the photo information into the controller.
     *
     * @param photo The photo object.
     */

    public void loadPhoto(Photo photo) {
        this.photo = photo;
    }

    /**
     * Loads the index of the photo into the controller.
     *
     * @param index The index of the photo in the album.
     */

    public void loadIndex(int index) {
        this.index = index;
    }

    /**
     * Moves the selected photo to the destination album and updates the user data.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */

    public void move(ActionEvent event) throws IOException {

        destinationAlbumName = DestinationAlbumList.getSelectionModel().getSelectedItem();

        if (destinationAlbumName != null){
            user.getAlbum(destinationAlbumName).addPhoto(photo);
            album.removeIndex(index);

            UserInformation.store(userInfo);

            goBackToEditAlbum(event);
        }

    }

    /**
     * Loads the edit application window after the photo has been moved.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */

    public void goBackToEditAlbum(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/EditAlbum.fxml"));
        Parent root = (Parent) loader.load();

        EditAlbumCtrl controller = loader.<EditAlbumCtrl>getController();
        controller.setUser(user);
        controller.loadUsers(userInfo);
        controller.loadAlbum(user.getAlbum(destinationAlbumName));
        controller.moveSetup();

        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Navigates back to the edit application window after cancelling the move photo operation.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */

    public void goBackToEditAlbumCancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/EditAlbum.fxml"));
        Parent root = (Parent) loader.load();

        EditAlbumCtrl controller = loader.<EditAlbumCtrl>getController();
        controller.setUser(user);
        controller.loadUsers(userInfo);
        controller.loadAlbum(album);
        controller.setup();

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

    public void quitApp(ActionEvent event){
        System.exit(0);
    }

    /**
     * Updates the view with the available destination albums.
     */

    public void view() {
        obsList = FXCollections.observableArrayList(user.getAlbumNames());
        DestinationAlbumList.setItems(obsList);
    }
}
