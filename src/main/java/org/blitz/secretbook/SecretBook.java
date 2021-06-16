package org.blitz.secretbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SecretBook extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("secretbook.fxml"));
        primaryStage.setTitle("Secret Book");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}