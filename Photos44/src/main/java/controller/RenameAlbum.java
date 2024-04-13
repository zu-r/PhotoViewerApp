package controller;

import java.io.IOException;

import application.Album;
import application.User;
import application.UserInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * The {@code RenameAlbum} class controls the user interface for renaming an album.
 * It provides methods for handling the renaming of an album, loading the main application window, and quitting the application.
 * @author Zaaim & Zuhayr Rashid
 */


public class RenameAlbum {

    // Instance Variables
    @FXML
    private Button RenameButton;
    @FXML
    private Button CancelButton;
    @FXML
    private TextField AlbumNameTextField;

    private UserInformation userInfo;
    private Album album;
    private User user;

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
     * Renames the album and updates the user data.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */

    public void rename(ActionEvent event) throws IOException {

        String albumName = AlbumNameTextField.getText().trim();

        if (albumName.isEmpty() || user.albumExists(albumName)) {
            // potential welcome/error text
            return;
        }

        album.setName(albumName);
        UserInformation.store(userInfo);

        goBackToMainWindow(event);

    }

    /**
     * Loads the main application window after the album has been renamed.
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
    public void quitApp(ActionEvent event){
        System.exit(0);
    }

}
