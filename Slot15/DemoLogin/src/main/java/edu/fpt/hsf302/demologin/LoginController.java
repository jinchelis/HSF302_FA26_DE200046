package edu.fpt.hsf302.demologin;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox rememberCheckBox;

    @FXML
    private void handleLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        // Kiểm tra thông tin đăng nhập
        if ("admin".equals(username) && "1234567".equals(password)) {
            showSuccessModal(
                    username,
                    rememberCheckBox.isSelected()
            );
        } else {
            showErrorAlert();
        }
    }

    // Hiển thị Modal khi đăng nhập thành công
    private void showSuccessModal(String username, boolean remember) {

        Alert infoModal =
                new Alert(Alert.AlertType.INFORMATION);

        infoModal.setTitle("Thông báo");
        infoModal.setHeaderText("Đăng nhập thành công!");

        infoModal.setContentText(
                "Tài khoản: " + username + "\n" +
                        "Trạng thái ghi nhớ: "
                        + (remember ? "Có" : "Không")
        );

        infoModal.showAndWait();
    }

    // Hiển thị Alert khi đăng nhập thất bại
    private void showErrorAlert() {

        Alert errorAlert =
                new Alert(Alert.AlertType.ERROR);

        errorAlert.setTitle("Lỗi");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(
                "Đăng nhập không hợp lệ."
        );

        errorAlert.showAndWait();
    }
}