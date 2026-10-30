package edu.fpt.hsf302.demologin.b4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.Map;

public class LoginController {

    // TODO 3: Các field phải trùng fx:id trong FXML
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

    private static final Map<String, String[]> ACCOUNTS =
            Map.of(
                    "student",
                    new String[]{
                            "Student@123",
                            "Sinh viên"
                    },

                    "teacher",
                    new String[]{
                            "Teacher@123",
                            "Giảng viên"
                    }
            );

    private int failedCount = 0;

    // TODO 4: Khởi tạo sau khi các @FXML đã được inject
    @FXML
    public void initialize() {

        // Đồng bộ 2 ô password
        tfPasswordVisible.textProperty()
                .bindBidirectional(
                        pfPassword.textProperty()
                );

        // Tick "Hiện mật khẩu"
        tfPasswordVisible.visibleProperty()
                .bind(
                        chkShow.selectedProperty()
                );

        tfPasswordVisible.managedProperty()
                .bind(
                        chkShow.selectedProperty()
                );

        // Không tick -> hiện PasswordField
        pfPassword.visibleProperty()
                .bind(
                        chkShow.selectedProperty()
                                .not()
                );

        pfPassword.managedProperty()
                .bind(
                        chkShow.selectedProperty()
                                .not()
                );

        // Chưa nhập đủ thì không cho đăng nhập
        btnLogin.disableProperty()
                .bind(
                        tfUsername.textProperty()
                                .isEmpty()
                                .or(
                                        pfPassword.textProperty()
                                                .isEmpty()
                                )
                                .or(
                                        cbRole.valueProperty()
                                                .isNull()
                                )
                );

        // Khi mở form, focus vào Username
        Platform.runLater(
                tfUsername::requestFocus
        );
    }

    // TODO 5: Xử lý đăng nhập
    @FXML
    private void handleLogin(ActionEvent event) {

        String user =
                tfUsername.getText()
                        .trim()
                        .toLowerCase();

        String pass =
                pfPassword.getText();

        String role =
                cbRole.getValue();

        // Sai quá 3 lần
        if (failedCount >= 3) {

            lblError.setText(
                    "Sai quá 3 lần. Vui lòng khởi động lại ứng dụng."
            );

            return;
        }

        // Kiểm tra format username
        if (!user.matches("[a-z0-9_]{3,20}")) {

            lblError.setText(
                    "Username 3–20 ký tự, chỉ chữ thường/số/_"
            );

            tfUsername.requestFocus();
            return;
        }

        String[] account =
                ACCOUNTS.get(user);

        // Sai username hoặc password
        if (account == null
                || !account[0].equals(pass)) {

            failedCount++;

            lblError.setText(
                    "Sai username hoặc mật khẩu (còn "
                            + (3 - failedCount)
                            + " lần)"
            );

            pfPassword.clear();
            pfPassword.requestFocus();

            return;
        }

        // Sai vai trò
        if (!account[1].equals(role)) {

            lblError.setText(
                    "Vai trò không đúng với tài khoản"
            );

            return;
        }

        // Đăng nhập đúng
        lblError.setText("");

        openWelcome(
                user,
                role,
                (Stage) btnLogin
                        .getScene()
                        .getWindow()
        );
    }

    // TODO 6: Mở màn hình Welcome và truyền dữ liệu
    private void openWelcome(
            String user,
            String role,
            Stage loginStage
    ) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "welcome-view.fxml"
                            )
                    );

            Parent root =
                    loader.load();

            WelcomeController controller =
                    loader.getController();

            controller.setUser(
                    user,
                    role
            );

            Stage stage =
                    new Stage();

            stage.setTitle("Chào mừng");

            stage.setScene(
                    new Scene(
                            root,
                            360,
                            200
                    )
            );

            stage.show();

            loginStage.close();

        } catch (IOException ex) {

            lblError.setText(
                    "Không mở được màn hình chào mừng: "
                            + ex.getMessage()
            );
        }
    }

    // TODO 7: Xử lý quên mật khẩu
    @FXML
    private void handleForgot() {

        TextInputDialog dialog =
                new TextInputDialog();

        dialog.setTitle(
                "Quên mật khẩu"
        );

        dialog.setHeaderText(
                "Nhập email để lấy lại mật khẩu"
        );

        dialog.setContentText(
                "Email:"
        );

        dialog.showAndWait()
                .ifPresent(email -> {

                    if (email.matches(
                            "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$"
                    )) {

                        Alert alert =
                                new Alert(
                                        Alert.AlertType.INFORMATION
                                );

                        alert.setTitle(
                                "Thông báo"
                        );

                        alert.setHeaderText(null);

                        alert.setContentText(
                                "Đã gửi hướng dẫn tới "
                                        + email
                        );

                        alert.showAndWait();

                    } else {

                        Alert alert =
                                new Alert(
                                        Alert.AlertType.WARNING
                                );

                        alert.setTitle(
                                "Cảnh báo"
                        );

                        alert.setHeaderText(null);

                        alert.setContentText(
                                "Email không hợp lệ"
                        );

                        alert.showAndWait();
                    }
                });
    }
}