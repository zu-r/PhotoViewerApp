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
 * The {@code CopyPhoto} class is a controller responsible for controlling the user interface for copying a photo to another album.
 * It provides functionality for selecting the destination album, copying the photo, and navigating back to the main application window.
 * @author Zaaim Rashid & Zuhayr Rashid
 */
public class CopyPhoto {

    @FXML
    Button CancelButton;
    @FXML
    ListView<String> DestinationAlbumList;

    private UserInformation userInfo;
    private User user;
    private Album album;
    private Photo photo;
    private ObservableList<String> obsList;

    private String destinationAlbumName;

    /**
     * Loads the current user for the controller.
     *
     * @param user The current user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Loads the userInfo for the controller.
     *
     * @param userInfo The userInfo.
     */
    public void loadUsers(UserInformation userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * Loads the current album for the controller.
     *
     * @param album The current album.
     */
    public void loadAlbum(Album album) {
        this.album = album;
    }

    /**
     * Initializes the view by populating the destination album list.
     */
    public void view() {
        obsList = FXCollections.observableArrayList(user.getAlbumNames());
        DestinationAlbumList.setItems(obsList);
    }

    /**
     * Gets the name of the destination album.
     *
     * @return The name of the destination album.
     */
    public String getDestinationAlbumName(){
        return destinationAlbumName;
    }

    /**
     * Copies the selected photo to the chosen destination album.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void copy(ActionEvent event) throws IOException {
        destinationAlbumName = DestinationAlbumList.getSelectionModel().getSelectedItem();
        if (destinationAlbumName != null){
            user.getAlbum(destinationAlbumName).addPhoto(photo);
            UserInformation.store(userInfo);
            goBackToEditAlbum(event);
        }
    }

    /**
     * Navigates back to the edit application window after copying the photo.
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
        controller.pasteSetup();

        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Navigates back to the edit application window after cancelling the copy photo operation.
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
     * Loads the current photo for the controller.
     *
     * @param photo The current photo.
     */
    public void loadPhoto(Photo photo) {
        this.photo = photo;
    }

    /**
     * Handles the "Quit" button
     *
     * @param event The action event.
     */
    public void quitApp(ActionEvent event){
        System.exit(0);
    }
}
