package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Photos extends Application{
    public void start(Stage stage) throws IOException {
        stage.setTitle("Photos44");
        FXMLLoader fxmlLoader = new FXMLLoader(Photos.class.getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700 , 550);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}