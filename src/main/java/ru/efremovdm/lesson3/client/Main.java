package ru.efremovdm.lesson3.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        URL asd = getClass().getResource("form.fxml");

        Parent root = FXMLLoader.load(asd);
        primaryStage.setTitle("JavaFX client");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }
}
