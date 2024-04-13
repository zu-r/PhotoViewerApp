package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.User;
import application.UserInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

/**
 * The {@code LoginController} class controls the user interface for the login functionality.
 * It provides methods for handling user login, navigating to the main application window or admin system, and quitting the application.
 * @author Zaaim and Zuhayr Rashid
 */

public class LoginController implements Initializable {

    @FXML
    TextField userInput = new TextField();
    @FXML
    private VBox root;
    @FXML
    private Label ErrorText;
    private User user;
    private UserInformation userInfo;


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
     * Handles the login action, directing the user to the appropriate interface based on the entered username.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */

    public void login (ActionEvent event) throws IOException {
        String username = userInput.getText().trim();
        if (username.equals("admin")) {
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/fxml/Admin.fxml"));
           Parent root = (Parent) loader.load();
           setScene(event,root);

        } else if(username.isEmpty()) {
            ErrorText.setText("Please enter a username");
        } else {
            user = userInfo.getUser(username);
            if (user == null) {
                ErrorText.setText("User doesn't exist");
                return;
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MainWindow.fxml"));
            Parent root = (Parent) loader.load();

            MainCtrl controller = loader.<MainCtrl>getController();
            controller.setUser(user);
            controller.loadUsers(userInfo);
            controller.view();

            setScene(event, root);

        }
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
     * Initializes the controller with the specified URL and ResourceBundle and specifies preferred window dimensions.
     *
     * @param url The URL location to resolve relative paths of the root object, or null if the location is not known.
     * @param resourceBundle The ResourceBundle that was passed to the FXMLLoader, or null.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userInfo = UserInformation.load();
        root.setPrefWidth(500);
        root.setPrefHeight(200);
    }
}