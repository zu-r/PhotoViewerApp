module photos {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;

    opens application to javafx.fxml;
    opens controller to javafx.fxml;
    exports application;
    exports controller;
}