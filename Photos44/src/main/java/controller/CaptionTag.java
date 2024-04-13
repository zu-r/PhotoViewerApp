package controller;

import java.io.IOException;

import application.*;
import application.UserInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for handling the addition and update of captions and tags for a photo.
 * @author Zaaim Rashid & Zuhayr Rashid
 */

public class CaptionTag extends Stage {

    @FXML
    TextField CaptionTextField;
    @FXML
    TextField TagsTextField;

    private UserInformation userInfo;
    private User user;
    private Album album;
    private Photo photo;

    /**
     * Loads the current user into the controller.
     *
     * @param user The User object to be loaded.
     */

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Loads the current userInfo into the controller.
     *
     * @param userInfo The userInfo object to be loaded.
     */

    public void loadUsers(UserInformation userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * Loads the current Album into the controller.
     *
     * @param album The Album object to be loaded.
     */

    public void loadAlbum(Album album) {
        this.album = album;
    }

    /**
     * Loads the current Photo into the controller.
     *
     * @param photo The Photo object to be loaded.
     */

    public void loadPhoto(Photo photo) {
        this.photo = photo;
    }

    /**
     * Updates the caption and tags of the current photo and stores any changes in the userInfo.
     *
     * @param event The ActionEvent triggering the update.
     * @throws IOException If an error occurs during the navigation to the main window.
     */

    public void update(ActionEvent event) throws IOException {

        photo.setCaption(CaptionTextField.getText());

        String text = TagsTextField.getText();

        if (!isValidTag(text)) {
            return;
        } else if (text.contains(",")){
            String name = (text.substring(0, text.indexOf(','))).trim();

            String value = (text.substring(text.indexOf(',')+1)).trim();

            Tag toAdd = new Tag(name, value);

            photo.addTag(toAdd);
        }

        UserInformation.store(userInfo);

        goBackToEditAlbum(event);

    }

    /**
     * Navigates back to the EditAlbum after updating captions and tags, and transfers control to EditAlbumCtrl
     *
     * @param event The ActionEvent triggering the navigation.
     * @throws IOException If an error occurs during the navigation to the main window.
     */

    public void goBackToEditAlbum(ActionEvent event) throws IOException {
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
     * Checks if the provided tag is non-empty and follows the desired format
     *
     * @param tag The tag string to be verified.
     * @return True if the tag is valid, false otherwise.
     */

    private boolean isValidTag(String tag) {
        if (tag.isEmpty()){
            return true;
        }
        String[] tagParts = tag.split(",");

        if (tagParts.length != 2 || tagParts[0].trim().isEmpty() || tagParts[1].trim().isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * Sets the caption text field with the text provided.
     *
     * @param caption The caption to be set in the text field.
     */

    public void setCaption(String caption) {
        CaptionTextField.setText(caption);
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
