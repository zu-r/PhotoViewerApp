package controller;

import java.io.IOException;

import application.UserInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The {@code NewUser} class controls the user interface for creating a new user.
 * It provides functionality for creating a new user, canceling the process, and quitting the application.
 * @author Zaaim & Zuhayr Rashid
 */

public class NewUser extends Stage {

    // Instance Variables
    @FXML
    private Button CreateButton;
    @FXML
    private Button CancelButton;
    @FXML
    private TextField UsernameTextField;
    @FXML
    private Label ErrorText;

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
     * Creates a new user with the specified username, and goes back to the Admin page when done.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */

    public void createNewUser(ActionEvent event) throws IOException {

        String username = UsernameTextField.getText().trim();
        if (username.isEmpty()) {
            ErrorText.setText("Invalid: empty username");
            return;
        }
        if (username.equals("admin")){
            ErrorText.setText("Invalid username: admin");
            return;
        }

        userInfo = UserInformation.load();
        userInfo.addUser(username);
        UserInformation.store(userInfo);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Admin.fxml"));
        Parent root = (Parent) loader.load();

        setScene(event, root);

    }

    /**
     * Cancels the user creation process and navigates back to the AdminCtrl
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */

    public void cancel(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Admin.fxml"));
        Parent root = (Parent) loader.load();

        setScene(event, root);

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
