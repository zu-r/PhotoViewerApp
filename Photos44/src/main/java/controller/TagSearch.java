package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Photo;
import application.Tag;
import application.User;
import application.UserInformation;
import application.Album;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The {@code TagSearch} class controls the user interface for searching photos by tags.
 * It provides methods for searching photos with one or two tags, adding and removing tags,
 * updating the view, loading search results, returning to the main application window, and quitting the application.
 * @author Zaaim & Zuhayr Rashid
 */

public class TagSearch implements Initializable {

    // Instance Variables
    private UserInformation userInfo;
    private User user;
    private Album newAlbum;
    private ArrayList<Tag> tags;
    @FXML
    private BorderPane root;
    @FXML
    private TextField TagsTextField;
    @FXML
    private ListView<String> TagsListView;
    private ObservableList<String> obsList;

    /**
     * Initializes the controller, so that the window size has certain dimensions and loads user data.
     *
     * @param url The location to resolve the root object for the controller, or {@code null} if not known.
     * @param temp The resources used to localize the root object, or {@code null} if not known.
     */

    public void initialize(URL url, ResourceBundle temp) {
        userInfo = UserInformation.load();
        root.setPrefWidth(700);
        root.setPrefHeight(400);
    }

    /**
     * Initializes the setup for the controller by clearing the list of tags and calling the updateView() method.
     */

    public void setup() {
        tags = new ArrayList<Tag>();
        updateView();
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
     * Loads user information into the controller.
     *
     * @param userInfo The user object.
     */

    public void loadUsers(UserInformation userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * Updates the view with the selected tags.
     */

    public void updateView() {
        ArrayList<String> tagStringList = new ArrayList<String>();

        for (Tag tag : tags) {
            tagStringList.add(tag.toString());
        }

        obsList = FXCollections.observableArrayList(tagStringList);
        TagsListView.setItems(obsList);
    }

    /**
     * Adds a tag to the list of selected tags. Limits the maximum number of tags for search criteria to be 2.
     *
     * @param event The action event.
     */

    public void addTag(ActionEvent event) {
        String tag = TagsTextField.getText().trim();

        if (!isValidTag(tag) || tags.size() == 2){
            return;
        }

        String name = (tag.substring(0, tag.indexOf(','))).trim();
        String value = (tag.substring(tag.indexOf(',')+1)).trim();

        Tag toAdd = new Tag(name, value);
        for (Tag item : tags) {
            if (item.equals(toAdd)) {
                return;
            }
        }

        tags.add(new Tag(name,value));
        updateView();
    }

    /**
     * Removes a tag from the list of selected tags for search criteria.
     *
     * @param event The action event.
     */

    public void removeTag(ActionEvent event) {
        String tag = TagsListView.getSelectionModel().getSelectedItem();
        if (tag == null) return;

        String name = tag.substring(0, tag.indexOf(':'));
        String value = tag.substring(tag.indexOf(':')+1);

        for (int i= tags.size() - 1; i >=0; i--) {
            if (tags.get(i).getName().equals(name) && tags.get(i).getValue().equals(value)) {
                tags.remove(i);
            }
        }

        updateView();
    }

    /**
     * Searches for photos with one tag and loads the search results window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */

    public void searchOneTag(ActionEvent event) throws IOException {
        if (tags.isEmpty()){
            return;
        }

        newAlbum = new Album("");
        ArrayList<Album> albums = user.getAlbums();

        for (Album album : albums) {
            for (int j = 0; j < album.getSize(); j++) {
                Photo photo = album.getPhoto(j);
                boolean photoAdded = false;
                for (int k = 0; k < photo.getTagList().size(); k++) {
                    if (photoAdded) break;
                    Tag tag = photo.getTagList().get(k);
                    Tag targetTag = tags.get(0);
                    if (tag.getName().equalsIgnoreCase(targetTag.getName()) && tag.getValue().equalsIgnoreCase(targetTag.getValue())) {
                        newAlbum.addPhoto(photo);
                        photoAdded = true;
                    }
                }
            }
        }
        loadSearchResultsWindow(event);
    }

    /**
     * Searches for photos with two tags using AND logic and loads the search results window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */

    public void searchAndTags(ActionEvent event) throws IOException {
        if (tags.isEmpty()) return;
        if (tags.size() !=2) return;

        Tag targetTagOne = tags.get(0);
        Tag targetTagTwo = tags.get(1);

        newAlbum = new Album("");
        ArrayList<Album> albums = user.getAlbums();
        for (Album album : albums) {
            for (int j = 0; j < album.getSize(); j++) {
                Photo photo = album.getPhoto(j);
                boolean hasTargetTagOne = false;
                boolean hasTargetTagTwo = false;
                for (int k = 0; k < photo.getTagList().size(); k++) {
                    Tag tag = photo.getTagList().get(k);
                    if(tag.getName().trim().equalsIgnoreCase(targetTagOne.getName().trim()) && tag.getValue().trim().equalsIgnoreCase(targetTagOne.getValue().trim())){
                        hasTargetTagOne = true;
                    }
                    if(tag.getName().trim().equalsIgnoreCase(targetTagTwo.getName().trim()) && tag.getValue().trim().equalsIgnoreCase(targetTagTwo.getValue().trim())){
                        hasTargetTagTwo = true;
                    }
                }
                if (hasTargetTagOne && hasTargetTagTwo) {
                    newAlbum.addPhoto(photo);
                }

            }
        }
        loadSearchResultsWindow(event);
    }

    /**
     * Searches for photos with two tags using OR logic and loads the search results window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */

    public void searchOrTags(ActionEvent event) throws IOException {
        if (tags.isEmpty()) return;
        if (tags.size() !=2) return;

        Tag targetTagOne = tags.get(0);
        Tag targetTagTwo = tags.get(1);

        newAlbum = new Album("");
        ArrayList<Album> albums = user.getAlbums();
        for (Album album : albums) {
            for (int j = 0; j < album.getSize(); j++) {
                Photo photo = album.getPhoto(j);
                boolean hasTargetTagOne = false;
                boolean hasTargetTagTwo = false;
                for (int k = 0; k < photo.getTagList().size(); k++) {
                    Tag tag = photo.getTagList().get(k);
                    if(tag.getName().trim().equalsIgnoreCase(targetTagOne.getName().trim()) && tag.getValue().trim().equalsIgnoreCase(targetTagOne.getValue().trim())){
                        hasTargetTagOne = true;
                    }
                    if(tag.getName().trim().equalsIgnoreCase(targetTagTwo.getName().trim()) && tag.getValue().trim().equalsIgnoreCase(targetTagTwo.getValue().trim())){
                        hasTargetTagTwo = true;
                    }
                }
                if (hasTargetTagOne || hasTargetTagTwo) {
                    newAlbum.addPhoto(photo);
                }

            }
        }
        loadSearchResultsWindow(event);
    }

    /**
     * Loads the search results window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */

    public void loadSearchResultsWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SearchWindow.fxml"));
        Parent root = (Parent) loader.load();

        DisplaySearchRes controller = loader.<DisplaySearchRes>getController();
        controller.setUser(user);
        controller.loadUsers(userInfo);
        controller.loadAlbum(newAlbum);
        controller.setup();

        setScene(event, root);
    }

    /**
     * Returns to the main application window.
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

        setScene(event, root);
    }

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

    /**
     * Handles the "Quit" button
     *
     * @param event The action event.
     */
    public void quitApp(ActionEvent event){
        System.exit(0);
    }

    /**
     * Checks if the provided tag is valid, based on the way it is entered
     *
     * @param tag The tag to validate.
     * @return {@code true} if the tag is valid, {@code false} otherwise.
     */

    private boolean isValidTag(String tag) {
        if (tag.isEmpty() || tag.indexOf(',') == -1 || tag.indexOf(',')+1 == tag.length() || tag.substring(tag.indexOf(',')+1).indexOf(',') != -1){
            return false;
        }
        return true;
    }
}
