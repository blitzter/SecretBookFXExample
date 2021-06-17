package org.blitz.secretbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.blitz.secretbook.controllers.SecretBookController;

public class SecretBook extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Thread.currentThread().getContextClassLoader().getResource("secretbook.fxml"));
        VBox root = loader.load();
        SecretBookController controller = loader.getController();
        controller.setStage(primaryStage);
        Scene scene = new Scene(root, 1024, 768);
        scene.setRoot(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Secret Book");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}