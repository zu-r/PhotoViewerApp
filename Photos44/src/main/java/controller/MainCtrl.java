package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.User;
import application.UserInformation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * The {@code MainCtrl} class controls the main application window.
 * It provides methods for handling user actions such as searching by tags or date,
 * creating, deleting, renaming, or opening albums, logging out, and quitting the application.
 * It also initializes the user interface components and updates the view with album information.
 * @author Zaaim & Zuhayr Rashid
 */

public class MainCtrl implements Initializable {

    // Instance Variables
    UserInformation userInfo;
    User user;

    @FXML
    private ListView<String> AlbumView;
    @FXML
    private TextArea PhotoInfoTextArea;

    private ObservableList<String> AlbumsObsList;

    // Initialization Methods

    /**
     * Initializes the user interface components and adds a listener for album selection changes.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        PhotoInfoTextArea.setEditable(false);
        AlbumView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                String albumName = arg2;
                if (albumName == null) {
                    PhotoInfoTextArea.setText("");
                    return;
                }
                user.getAlbum(albumName).findDateRange();
                PhotoInfoTextArea.setText("Name: "+user.getAlbum(albumName).getName()+"\n"+
                        "Size: "+user.getAlbum(albumName).getSize()+"\n"+
                        "Earliest Photo: "+user.getAlbum(albumName).getFirstDate()+"\n"+
                        "Latest Photo: "+user.getAlbum(albumName).getLateDate());
            }
        });

        userInfo = UserInformation.load();
    }

    // Scene Manipulation Methods

    /**
     * Sets the scene of the current stage with the provided root.
     *
     * @param event The action event triggering the scene change.
     * @param root  The root node of the new scene.
     */
    public void setScene(ActionEvent event, Parent root){
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // User Action Methods

    /**
     * Initiates a search operation based on user-defined tags.
     *
     * @param event The action event triggering the search.
     * @throws IOException If an I/O error occurs.
     */
    public void searchByTags(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/TagSearchWindow.fxml"));
        Parent root = (Parent) loader.load();

        TagSearch controller = loader.<TagSearch>getController();
        controller.setUser(user);
        controller.loadUsers(userInfo);
        controller.setup();

        setScene(event, root);
    }

    /**
     * Initiates a search operation based on a date range.
     *
     * @param event The action event triggering the search.
     * @throws IOException If an I/O error occurs.
     */
    public void searchByDate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/DateSearchWindow.fxml"));
        Parent root = (Parent) loader.load();

        DateSearch controller = loader.<DateSearch>getController();
        controller.setUser(user);
        controller.loadUsers(userInfo);

        setScene(event, root);
    }

    /**
     * Creates a new album.
     *
     * @param event The action event triggering the album creation.
     * @throws IOException If an I/O error occurs.
     */
    public void createAlbum(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/CreateAlbum.fxml"));
        Parent root = (Parent) loader.load();

        CreateAlbumCtrl controller = loader.<CreateAlbumCtrl>getController();
        controller.setUser(user);
        controller.loadUsers(userInfo);

        setScene(event, root);
    }

    /**
     * Deletes the selected album.
     */
    public void deleteAlbum() {
        String album = AlbumView.getSelectionModel().getSelectedItem();
        if (album == null) return;

        user.deleteAlbum(album);
        UserInformation.store(userInfo);
        view();
    }

    /**
     * Renames the selected album.
     *
     * @param event The action event triggering the album renaming.
     * @throws IOException If an I/O error occurs.
     */
    public void renameAlbum(ActionEvent event) throws IOException {
        String album = AlbumView.getSelectionModel().getSelectedItem();

        if (album != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/RenameAlbum.fxml"));
            Parent root = (Parent) loader.load();

            RenameAlbum controller = loader.<RenameAlbum>getController();
            controller.setUser(user);
            controller.loadUsers(userInfo);
            controller.loadAlbum(user.getAlbum(album));

            setScene(event, root);
        }
    }

    /**
     * Opens the selected album.
     *
     * @param event The action event triggering the album opening.
     * @throws IOException If an I/O error occurs.
     */
    public void openAlbum(ActionEvent event) throws IOException {
        String album = AlbumView.getSelectionModel().getSelectedItem();

        if (album != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/PhotoDisplay.fxml"));
            Parent root = (Parent) loader.load();

            AlbumOpenedCtrl controller = loader.<AlbumOpenedCtrl>getController();
            controller.setUser(user);
            controller.loadUsers(userInfo);
            controller.loadAlbum(user.getAlbum(album));
            controller.setup();

            setScene(event, root);
        }
    }

    /**
     * Logs out the user.
     *
     * @param event The action event triggering the logout.
     * @throws IOException If an I/O error occurs.
     */
    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Login.fxml"));
        Parent root = (Parent) loader.load();
        setScene(event, root);
    }

    /**
     * Quits the application.
     *
     * @param event The action event triggering the quit.
     */
    public void quitApp(ActionEvent event){
        System.exit(0);
    }

    // Helper Methods

    /**
     * Updates the view with the list of albums.
     */
    public void view() {
        AlbumsObsList = FXCollections.observableArrayList(user.getAlbumNames());
        AlbumView.setItems(AlbumsObsList);
    }

    /**
     * Loads the user object into the controller.
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
}
