package edu.fpt.hsf302.demologin.b2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;

public class LoginLayoutApp extends Application {

    private static final Map<String, String> ACCOUNTS = Map.of(
            "admin", "Admin@123",
            "student", "Student@123"
    );
    private static final Border DEBUG_BORDER =
            new Border(
                    new BorderStroke(
                            Color.RED,
                            BorderStrokeStyle.DASHED,
                            CornerRadii.EMPTY,
                            new BorderWidths(1)
                    )
            );

    private Scene scene;
    private String rememberedUser;

    private final List<Region> containers =
            new ArrayList<>();

    @Override
    public void start(Stage stage) {

        scene = new Scene(
                buildLoginView(),
                680,
                480
        );

        stage.setScene(scene);
        stage.setTitle(
                "B2 - Layout & Form đăng nhập"
        );

        stage.setMinWidth(480);
        stage.setMinHeight(400);

        stage.show();
    }

    // TODO 1: BorderPane chính
    private Parent buildLoginView() {

        containers.clear();

        BorderPane root =
                new BorderPane();

        root.setTop(buildHeader());
        root.setCenter(buildCenter());
        root.setBottom(buildFooter());

        return track(root);
    }

    // TODO 2: Header - HBox + spacer
    private Node buildHeader() {

        Label logo =
                new Label("HSF");

        logo.setStyle(
                "-fx-background-color: #f27024;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 4 8;"
        );

        Label title =
                new Label(
                        "Hệ thống quản lý sinh viên"
                );

        title.setStyle(
                "-fx-font-size: 16;" +
                        "-fx-text-fill: white;"
        );

        Label version =
                new Label("v1.0");

        version.setStyle(
                "-fx-text-fill: #cfd8dc;"
        );

        // Spacer đẩy version sang phải
        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        HBox header =
                new HBox(
                        10,
                        logo,
                        title,
                        spacer,
                        version
                );

        header.setAlignment(
                Pos.CENTER_LEFT
        );

        header.setPadding(
                new Insets(
                        10,
                        16,
                        10,
                        16
                )
        );

        header.setStyle(
                "-fx-background-color: #263238;"
        );

        return track(header);
    }

    // TODO 3: GridPane -> VBox -> StackPane
    private Node buildCenter() {

        Label lblTitle =
                new Label("ĐĂNG NHẬP");

        lblTitle.setStyle(
                "-fx-font-size: 20;" +
                        "-fx-font-weight: bold;"
        );

        TextField tfUser =
                new TextField(
                        rememberedUser == null
                                ? ""
                                : rememberedUser
                );

        tfUser.setPromptText("Username");

        PasswordField pfPass =
                new PasswordField();

        pfPass.setPromptText("Password");

        CheckBox chkRemember =
                new CheckBox(
                        "Ghi nhớ đăng nhập"
                );

        chkRemember.setSelected(
                rememberedUser != null
        );

        Label lblError =
                new Label();

        lblError.setStyle(
                "-fx-text-fill: #d93025;"
        );

        lblError.setWrapText(true);

        // GridPane form
        GridPane grid =
                new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(
                new Label("Username"),
                0,
                0
        );

        grid.add(
                tfUser,
                1,
                0
        );

        grid.add(
                new Label("Password"),
                0,
                1
        );

        grid.add(
                pfPass,
                1,
                1
        );

        grid.add(
                chkRemember,
                1,
                2
        );

        // Cột label cố định 80px
        ColumnConstraints c0 =
                new ColumnConstraints(80);

        // Cột input được giãn
        ColumnConstraints c1 =
                new ColumnConstraints();

        c1.setHgrow(
                Priority.ALWAYS
        );

        grid.getColumnConstraints()
                .addAll(c0, c1);

        // Hai nút giãn đều
        Button btnLogin =
                new Button("Đăng nhập");

        Button btnExit =
                new Button("Thoát");

        btnLogin.setDefaultButton(true);
        btnExit.setCancelButton(true);

        for (Button b :
                List.of(btnLogin, btnExit)) {

            b.setMaxWidth(
                    Double.MAX_VALUE
            );

            HBox.setHgrow(
                    b,
                    Priority.ALWAYS
            );
        }

        HBox actions =
                new HBox(
                        10,
                        btnLogin,
                        btnExit
                );

        // Card
        VBox card =
                new VBox(
                        14,
                        lblTitle,
                        grid,
                        lblError,
                        actions
                );

        card.setAlignment(
                Pos.TOP_CENTER
        );

        card.setPadding(
                new Insets(24)
        );

        card.setMaxWidth(380);

        card.setMaxHeight(
                Region.USE_PREF_SIZE
        );

        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-effect: dropshadow(" +
                        "gaussian, rgba(0,0,0,0.2)," +
                        "12, 0, 0, 3);"
        );

        // StackPane căn card giữa
        StackPane center =
                new StackPane(card);

        center.setPadding(
                new Insets(20)
        );

        center.setStyle(
                "-fx-background-color: #eceff1;"
        );

        // TODO 4: Xử lý sự kiện
        btnLogin.setOnAction(e -> {

            String user =
                    tfUser.getText()
                            .trim()
                            .toLowerCase();

            String pass =
                    pfPass.getText();

            // Kiểm tra bỏ trống
            if (user.isEmpty() || pass.isEmpty()) {

                lblError.setText(
                        "Vui lòng nhập đầy đủ username và password"
                );

                if (user.isEmpty()) {
                    tfUser.requestFocus();
                } else {
                    pfPass.requestFocus();
                }

                return;
            }

            // Kiểm tra tài khoản
            if (!pass.equals(ACCOUNTS.get(user))) {

                lblError.setText(
                        "Sai username hoặc mật khẩu"
                );

                pfPass.clear();
                pfPass.requestFocus();

                return;
            }

            // Ghi nhớ username nếu được chọn
            rememberedUser =
                    chkRemember.isSelected()
                            ? user
                            : null;

            // TODO 6 sẽ viết buildHomeView()
            // Tạm thời chưa chuyển màn hình ở TODO 4.
            lblError.setStyle(
                    "-fx-text-fill: #188038;"
            );

            lblError.setText(
                    "Đăng nhập thành công: " + user
            );
        });

// Nút Thoát
        btnExit.setOnAction(e -> {

            Alert alert =
                    new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Bạn muốn thoát ứng dụng?",
                            ButtonType.YES,
                            ButtonType.NO
                    );

            alert.setHeaderText(null);

            alert.showAndWait()
                    .filter(button ->
                            button == ButtonType.YES)
                    .ifPresent(button ->
                            Platform.exit());
        });

// Gõ lại username -> xóa lỗi
        tfUser.textProperty()
                .addListener((obs, oldValue, newValue) -> {

                    lblError.setText("");
                    lblError.setStyle(
                            "-fx-text-fill: #d93025;"
                    );
                });

// Gõ lại password -> xóa lỗi
        pfPass.textProperty()
                .addListener((obs, oldValue, newValue) -> {

                    if (!newValue.isEmpty()) {
                        lblError.setText("");
                        lblError.setStyle(
                                "-fx-text-fill: #d93025;"
                        );
                    }
                });

        Platform.runLater(() -> {

            if (tfUser.getText().isEmpty()) {
                tfUser.requestFocus();
            } else {
                pfPass.requestFocus();
            }
        });

        track(grid);
        track(actions);
        track(card);

        return track(center);
    }

    // TODO 5: Footer - HBox + debug checkbox + spacer
    private Node buildFooter() {

        CheckBox chkDebug =
                new CheckBox(
                        "Hiện viền layout (debug)"
                );

        chkDebug.setOnAction(e -> {

            for (Region region : containers) {

                if (chkDebug.isSelected()) {
                    region.setBorder(DEBUG_BORDER);
                } else {
                    region.setBorder(null);
                }
            }
        });

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label copyright =
                new Label(
                        "© 2026 HSF302 — Chapter 08"
                );

        HBox footer =
                new HBox(
                        10,
                        chkDebug,
                        spacer,
                        copyright
                );

        footer.setAlignment(
                Pos.CENTER_LEFT
        );

        footer.setPadding(
                new Insets(
                        6,
                        16,
                        6,
                        16
                )
        );

        footer.setStyle(
                "-fx-background-color: #cfd8dc;"
        );

        return track(footer);
    }

    private <T extends Region> T track(
            T region
    ) {
        containers.add(region);
        return region;
    }

    public static void main(String[] args) {
        launch(args);
    }
}