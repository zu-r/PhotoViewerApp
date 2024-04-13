package controller;

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

import java.io.IOException;

public class CreateAlbumCtrl {

    @FXML
    Button CreateButton;
    @FXML
    Button CancelButton;
    @FXML
    TextField AlbumNameTextField;
    @FXML
    Button QuitButton;

    private UserInformation userInfo;
    private User user;

    /**
     * Creates a new album using the provided album name,
     * Checks to ensure that it is unique and non-empty.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void createAlbum(ActionEvent event) throws IOException {
        String albumName = AlbumNameTextField.getText().trim();
        if (albumName.equals("") || user.albumExists(albumName)) {
            return;
        }
        albumName = albumName.trim();
        user.addAlbum(albumName);
        UserInformation.store(userInfo);
        goBackToMainWindow(event);
    }

    /**
     * Returns back to the main application window after creating an album.
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
}
