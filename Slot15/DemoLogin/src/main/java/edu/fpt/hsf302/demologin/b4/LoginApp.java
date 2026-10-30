package edu.fpt.hsf302.demologin.b4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =
                new FXMLLoader(
                        LoginApp.class.getResource(
                                "login-view.fxml"
                        )
                );

        Scene scene =
                new Scene(
                        loader.load(),
                        450,
                        330
                );

        stage.setTitle(
                "B4 - Đăng nhập FXML"
        );

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}