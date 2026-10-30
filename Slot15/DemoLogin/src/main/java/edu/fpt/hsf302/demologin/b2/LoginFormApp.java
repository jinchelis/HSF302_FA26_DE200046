package edu.fpt.hsf302.demologin.b2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginFormApp extends Application {

    // TODO 1: Khai báo các control
    private final TextField tfUsername = new TextField();
    private final PasswordField pfPassword = new PasswordField();

    private final CheckBox chkRemember =
            new CheckBox("Ghi nhớ đăng nhập");

    private final Button btnLogin =
            new Button("Đăng nhập");

    private final Button btnClear =
            new Button("Làm lại");

    private final Label lblMessage =
            new Label();

    @Override
    public void start(Stage stage) {

        // TODO 2: Cấu hình các control
        tfUsername.setPromptText("Nhập tên đăng nhập");
        pfPassword.setPromptText("Nhập mật khẩu");

        btnLogin.setDefaultButton(true);

        lblMessage.setWrapText(true);

        // TODO 3: Xây dựng layout
        Label title =
                new Label("ĐĂNG NHẬP HỆ THỐNG");

        title.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;"
        );

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(12);

        form.add(
                new Label("Tên đăng nhập:"),
                0,
                0
        );

        form.add(
                tfUsername,
                1,
                0
        );

        form.add(
                new Label("Mật khẩu:"),
                0,
                1
        );

        form.add(
                pfPassword,
                1,
                1
        );

        form.add(
                chkRemember,
                1,
                2
        );

        HBox buttons =
                new HBox(
                        10,
                        btnLogin,
                        btnClear
                );

        buttons.setAlignment(Pos.CENTER_RIGHT);

        VBox root =
                new VBox(
                        15,
                        title,
                        form,
                        buttons,
                        lblMessage
                );

        root.setPadding(new Insets(20));

        Scene scene =
                new Scene(root, 430, 280);

        stage.setScene(scene);
        stage.setTitle("B2 - Login Form");
        stage.show();

        tfUsername.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}