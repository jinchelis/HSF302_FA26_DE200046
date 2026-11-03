package edu.fpt.hsf302.demologin;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class LoginController {

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPasswordVisible;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private CheckBox chkShow;

    @FXML
    private ComboBox<String> cbRole;

    @FXML
    private Label lblError;

    @FXML
    private Button btnLogin;

    private static final Map<String, String[]> ACCOUNTS = Map.of(
            "student", new String[]{"Student@123", "Sinh viên"},
            "teacher", new String[]{"Teacher@123", "Giảng viên"}
    );

    private int failedCount = 0;

    @FXML
    public void initialize() {

        // Đồng bộ PasswordField và TextField hiện mật khẩu
        tfPasswordVisible.textProperty()
                .bindBidirectional(pfPassword.textProperty());

        // Hiện / ẩn mật khẩu
        tfPasswordVisible.visibleProperty()
                .bind(chkShow.selectedProperty());

        tfPasswordVisible.managedProperty()
                .bind(chkShow.selectedProperty());

        pfPassword.visibleProperty()
                .bind(chkShow.selectedProperty().not());

        pfPassword.managedProperty()
                .bind(chkShow.selectedProperty().not());

        // Chỉ cho đăng nhập khi nhập đủ dữ liệu
        btnLogin.disableProperty().bind(
                tfUsername.textProperty().isEmpty()
                        .or(pfPassword.textProperty().isEmpty())
                        .or(cbRole.valueProperty().isNull())
        );

        Platform.runLater(tfUsername::requestFocus);
    }

    @FXML
    private void handleLogin() {

        String user = tfUsername.getText().trim().toLowerCase();
        String pass = pfPassword.getText();
        String role = cbRole.getValue();

        // Kiểm tra định dạng username
        if (!user.matches("[a-z0-9_]{3,20}")) {
            lblError.setText(
                    "Username 3-20 ký tự, chỉ gồm chữ thường, số và _"
            );
            return;
        }

        String[] account = ACCOUNTS.get(user);

        // Sai tài khoản / mật khẩu / vai trò
        if (account == null
                || !account[0].equals(pass)
                || !account[1].equals(role)) {

            failedCount++;
            pfPassword.clear();

            if (failedCount >= 3) {
                lblError.setText(
                        "Bạn đã nhập sai 3 lần. Vui lòng khởi động lại ứng dụng."
                );

                // disableProperty đang được bind nên phải unbind trước
                btnLogin.disableProperty().unbind();
                btnLogin.setDisable(true);

            } else {
                lblError.setText(
                        "Sai tài khoản, mật khẩu hoặc vai trò ("
                                + failedCount + "/3)"
                );
            }

            return;
        }

        lblError.setText("");

        openWelcome(
                user,
                role,
                (Stage) btnLogin.getScene().getWindow()
        );
    }

    private void openWelcome(
            String user,
            String role,
            Stage loginStage) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("welcome-view.fxml")
            );

            Parent root = loader.load();

            // Lấy controller của màn hình Welcome
            WelcomeController controller = loader.getController();

            // Truyền dữ liệu từ Login sang Welcome
            controller.setUser(user, role);

            Stage welcomeStage = new Stage();
            welcomeStage.setTitle("Chào mừng");
            welcomeStage.setScene(new Scene(root, 360, 200));
            welcomeStage.show();

            loginStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleForgot() {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Quên mật khẩu");
        dialog.setHeaderText(
                "Nhập email để lấy lại mật khẩu"
        );

        dialog.showAndWait().ifPresent(email -> {

            Alert alert;

            if (email.matches(
                    "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$"
            )) {

                alert = new Alert(
                        Alert.AlertType.INFORMATION
                );

                alert.setContentText(
                        "Đã gửi hướng dẫn tới " + email
                );

            } else {

                alert = new Alert(
                        Alert.AlertType.WARNING
                );

                alert.setContentText(
                        "Email không hợp lệ."
                );
            }

            alert.setHeaderText(null);
            alert.showAndWait();
        });
    }
}