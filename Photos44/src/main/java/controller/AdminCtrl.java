package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.UserInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The {@code AdminCtrl} class controls the admin system interface,
 *
 * The administrator to create new users, delete existing users, logout, and quit the application.
 *
 * This class implements the {@link Initializable} interface to initialize the controller.
 *
 * @author Zuhayr Rashid & Zaaim Rashid
 */
public class AdminCtrl implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private Button CreateNewButton;
    @FXML
    private Button DeleteUserButton;
    @FXML
    private Button LogoutButton;
    @FXML
    private ListView<String> UsersListView;

    private ObservableList<String> obsList;
    private UserInformation userInfo;

    /**
     * Initializes the controller.
     *
     * @param location  The URL.
     * @param resources The ResourceBundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userInfo = UserInformation.load();
        obsList = FXCollections.observableArrayList(userInfo.getUsernames());
        UsersListView.setItems(obsList);
        root.setPrefWidth(550);
        root.setPrefHeight(400);
    }

    /**
     * Handles the "Create New User" button action, navigating to the Create New User window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void createUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewUser.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handles the "Delete User" button action
     * Deletes the selected user.
     */
    public void deleteUser(ActionEvent e) {
        String selectedUser = UsersListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userInfo.delete(selectedUser);
            UserInformation.store(userInfo);
            obsList.remove(selectedUser);
        }
    }

    /**
     * Handles the "Logout" button action
     * Returns User to the login window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
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
}
