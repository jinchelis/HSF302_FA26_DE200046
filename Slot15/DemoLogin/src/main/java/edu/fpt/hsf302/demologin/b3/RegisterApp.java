package edu.fpt.hsf302.demologin.b3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RegisterApp extends Application {

    private final TextField tfFullName = new TextField();
    private final TextField tfEmail = new TextField();
    private final TextField tfUsername = new TextField();

    private final PasswordField pfPassword = new PasswordField();
    private final PasswordField pfConfirm = new PasswordField();

    private final RadioButton rbMale = new RadioButton("Nam");
    private final RadioButton rbFemale = new RadioButton("Nữ");
    private final RadioButton rbOther = new RadioButton("Khác");
    private final ToggleGroup tgGender = new ToggleGroup();

    private final DatePicker dpDob = new DatePicker();

    private final ComboBox<String> cbMajor = new ComboBox<>();

    private final CheckBox chkSport = new CheckBox("Thể thao");
    private final CheckBox chkMusic = new CheckBox("Âm nhạc");
    private final CheckBox chkGame = new CheckBox("Game");
    private final CheckBox chkReading = new CheckBox("Đọc sách");

    private final CheckBox chkAgree =
            new CheckBox("Tôi đồng ý với điều khoản");

    private final ProgressBar pbStrength =
            new ProgressBar(0);

    private final Label lblStrength =
            new Label("Rất yếu");

    private final Button btnRegister =
            new Button("Đăng ký");

    private final Button btnReset =
            new Button("Làm lại");

    private final Label errFullName = new Label();
    private final Label errEmail = new Label();
    private final Label errUsername = new Label();
    private final Label errPassword = new Label();
    private final Label errConfirm = new Label();
    private final Label errGender = new Label();
    private final Label errDob = new Label();
    private final Label errMajor = new Label();

    @Override
   
    public void start(Stage stage) {

        setupControls();

        Parent registerView =
                buildRegisterView();

        Scene scene =
                new Scene(
                        registerView,
                        580,
                        660
                );

        stage.setScene(scene);
        stage.setTitle(
                "B3 - Đăng ký & Đăng nhập"
        );
        stage.show();
    }

    // TODO 2: Cấu hình control
    private void setupControls() {

        tfFullName.setPromptText(
                "Nguyễn Văn An"
        );

        tfEmail.setPromptText(
                "abc@example.com"
        );

        tfUsername.setPromptText(
                "3–20 ký tự: a-z, 0-9, _"
        );

        pfPassword.setPromptText(
                "≥ 8 ký tự, có hoa, thường, số, ký tự đặc biệt"
        );

        pfConfirm.setPromptText(
                "Nhập lại mật khẩu"
        );

        rbMale.setToggleGroup(tgGender);
        rbFemale.setToggleGroup(tgGender);
        rbOther.setToggleGroup(tgGender);

        dpDob.setPromptText(
                "Chọn ngày sinh"
        );

        dpDob.setEditable(false);
        dpDob.setMaxWidth(
                Double.MAX_VALUE
        );

        cbMajor.getItems().addAll(
                "Kỹ thuật phần mềm",
                "Trí tuệ nhân tạo",
                "An toàn thông tin",
                "Thiết kế đồ họa"
        );

        cbMajor.setPromptText(
                "-- Chọn ngành --"
        );

        cbMajor.setMaxWidth(
                Double.MAX_VALUE
        );

        pbStrength.setPrefWidth(140);

        btnRegister.disableProperty()
                .bind(
                        chkAgree.selectedProperty()
                                .not()
                );

        btnRegister.setDefaultButton(true);
    }

    // TODO 3: Giao diện đăng ký
    private Parent buildRegisterView() {

        GridPane grid = new GridPane();

        grid.setHgap(12);
        grid.setVgap(6);

        int r = 0;

        addField(
                grid, r++,
                "Họ tên *",
                tfFullName,
                errFullName
        );

        addField(
                grid, r++,
                "Email *",
                tfEmail,
                errEmail
        );

        addField(
                grid, r++,
                "Username *",
                tfUsername,
                errUsername
        );

        HBox strength =
                new HBox(
                        8,
                        pbStrength,
                        lblStrength
                );

        strength.setAlignment(
                Pos.CENTER_LEFT
        );

        addField(
                grid, r++,
                "Mật khẩu *",
                new VBox(
                        4,
                        pfPassword,
                        strength
                ),
                errPassword
        );

        addField(
                grid, r++,
                "Xác nhận MK *",
                pfConfirm,
                errConfirm
        );

        addField(
                grid, r++,
                "Giới tính *",
                new HBox(
                        15,
                        rbMale,
                        rbFemale,
                        rbOther
                ),
                errGender
        );

        addField(
                grid, r++,
                "Ngày sinh *",
                dpDob,
                errDob
        );

        addField(
                grid, r++,
                "Ngành *",
                cbMajor,
                errMajor
        );

        addField(
                grid, r++,
                "Sở thích",
                new FlowPane(
                        12,
                        6,
                        chkSport,
                        chkMusic,
                        chkGame,
                        chkReading
                ),
                null
        );

        ColumnConstraints c0 =
                new ColumnConstraints(110);

        ColumnConstraints c1 =
                new ColumnConstraints();

        c1.setHgrow(
                Priority.ALWAYS
        );

        grid.getColumnConstraints()
                .addAll(c0, c1);

        HBox actions =
                new HBox(
                        10,
                        btnRegister,
                        btnReset
                );

        actions.setAlignment(
                Pos.CENTER_RIGHT
        );

        VBox content =
                new VBox(
                        14,
                        new Label("ĐĂNG KÝ TÀI KHOẢN"),
                        grid,
                        chkAgree,
                        actions
                );

        content.setPadding(
                new Insets(20)
        );

        ScrollPane scroll =
                new ScrollPane(content);

        scroll.setFitToWidth(true);

        return scroll;
    }

    private void addField(
            GridPane grid,
            int row,
            String text,
            Node input,
            Label error
    ) {

        Label label =
                new Label(text);

        GridPane.setValignment(
                label,
                VPos.TOP
        );

        VBox box;

        if (error != null) {

            error.setStyle(
                    "-fx-text-fill: #d93025;"
            );

            error.setWrapText(true);

            box = new VBox(
                    3,
                    input,
                    error
            );

        } else {

            box = new VBox(
                    3,
                    input
            );
        }

        grid.add(label, 0, row);
        grid.add(box, 1, row);
    }

    public static void main(String[] args) {
        launch(args);
    }
}