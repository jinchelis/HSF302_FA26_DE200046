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
import java.time.LocalDate;
import java.time.Period;

public class RegisterApp extends Application {
    private static final String STRONG_PASSWORD =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$";

    private static final String[] LEVEL_TEXT = {
            "Rất yếu",
            "Yếu",
            "Trung bình",
            "Khá",
            "Mạnh"
    };

    private static final String[] LEVEL_COLOR = {
            "#d93025",
            "#d93025",
            "#f29900",
            "#1a73e8",
            "#188038"
    };

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
        registerEvents();

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

    // TODO 5: Gắn sự kiện validate
    private void registerEvents() {

        // Validate khi rời khỏi ô nhập
        tfFullName.focusedProperty()
                .addListener((obs, oldValue, focused) -> {
                    if (!focused) {
                        vFullName();
                    }
                });

        tfEmail.focusedProperty()
                .addListener((obs, oldValue, focused) -> {
                    if (!focused) {
                        vEmail();
                    }
                });

        tfUsername.focusedProperty()
                .addListener((obs, oldValue, focused) -> {
                    if (!focused) {
                        vUsername();
                    }
                });

        pfPassword.focusedProperty()
                .addListener((obs, oldValue, focused) -> {
                    if (!focused) {
                        vPassword();
                    }
                });

        pfConfirm.focusedProperty()
                .addListener((obs, oldValue, focused) -> {
                    if (!focused) {
                        vConfirm();
                    }
                });

        // Validate khi lựa chọn thay đổi
        tgGender.selectedToggleProperty()
                .addListener((obs, oldValue, newValue) ->
                        vGender()
                );

        dpDob.valueProperty()
                .addListener((obs, oldValue, newValue) ->
                        vDob()
                );

        cbMajor.valueProperty()
                .addListener((obs, oldValue, newValue) ->
                        vMajor()
                );

        // TODO 6: Cập nhật độ mạnh mật khẩu khi đang gõ
        pfPassword.textProperty()
                .addListener((obs, oldValue, newValue) -> {
                    updateStrength(newValue);

                    if (!pfConfirm.getText().isEmpty()) {
                        vConfirm();
                    }
                });

        btnRegister.setOnAction(e -> {
            // TODO 7: đăng ký tài khoản
        });

        btnReset.setOnAction(e -> {
            // TODO 8: reset form
        });
    }

    // TODO 6: Tính độ mạnh mật khẩu
    // TODO 6: Tính độ mạnh mật khẩu
    private void updateStrength(String password) {

        int score = 0;

        if (password.length() >= 8) {
            score++;
        }

        if (password.matches(".*[A-Z].*")) {
            score++;
        }

        if (password.matches(".*[0-9].*")) {
            score++;
        }

        if (password.matches(".*[!@#$%^&*()_+\\-=].*")) {
            score++;
        }

        int level = Math.min(score, 4);

        pbStrength.setProgress(
                level / 4.0
        );

        lblStrength.setText(
                LEVEL_TEXT[level]
        );

        lblStrength.setStyle(
                "-fx-text-fill: "
                        + LEVEL_COLOR[level]
                        + ";"
        );
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

    // TODO 4: Validate họ tên
    private boolean vFullName() {

        String value =
                tfFullName.getText().trim();

        boolean ok =
                value.matches(
                        "[\\p{L} ]{3,50}"
                );

        errFullName.setText(
                ok
                        ? ""
                        : "Họ tên phải từ 3–50 ký tự chữ và khoảng trắng"
        );

        return ok;
    }

    // Validate email
    private boolean vEmail() {

        String value =
                tfEmail.getText().trim();

        boolean ok =
                value.matches(
                        "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$"
                );

        errEmail.setText(
                ok
                        ? ""
                        : "Email không hợp lệ"
        );

        return ok;
    }

    // Validate username
    private boolean vUsername() {

        String value =
                tfUsername.getText()
                        .trim()
                        .toLowerCase();

        boolean formatOk =
                value.matches(
                        "[a-z0-9_]{3,20}"
                );

        if (!formatOk) {

            errUsername.setText(
                    "Username 3–20 ký tự, chỉ gồm a-z, 0-9, _"
            );

            return false;
        }

        if (UserStore.exists(value)) {

            errUsername.setText(
                    "Username đã tồn tại"
            );

            return false;
        }

        errUsername.setText("");
        return true;
    }

    // Validate mật khẩu
    private boolean vPassword() {

        String value =
                pfPassword.getText();

        boolean ok =
                value.matches(
                        STRONG_PASSWORD
                );

        errPassword.setText(
                ok
                        ? ""
                        : "Mật khẩu ≥ 8 ký tự, có hoa, thường, số và ký tự đặc biệt"
        );

        return ok;
    }

    // Validate xác nhận mật khẩu
    private boolean vConfirm() {

        boolean ok =
                !pfConfirm.getText().isEmpty()
                        && pfConfirm.getText()
                        .equals(pfPassword.getText());

        errConfirm.setText(
                ok
                        ? ""
                        : "Mật khẩu xác nhận không khớp"
        );

        return ok;
    }

    // Validate giới tính
    private boolean vGender() {

        boolean ok =
                tgGender.getSelectedToggle()
                        != null;

        errGender.setText(
                ok
                        ? ""
                        : "Vui lòng chọn giới tính"
        );

        return ok;
    }

    // Validate ngày sinh
    private boolean vDob() {

        LocalDate dob =
                dpDob.getValue();

        if (dob == null) {

            errDob.setText(
                    "Vui lòng chọn ngày sinh"
            );

            return false;
        }

        if (dob.isAfter(LocalDate.now())) {

            errDob.setText(
                    "Ngày sinh không được ở tương lai"
            );

            return false;
        }

        int age =
                Period.between(
                        dob,
                        LocalDate.now()
                ).getYears();

        if (age < 16) {

            errDob.setText(
                    "Người đăng ký phải từ 16 tuổi"
            );

            return false;
        }

        errDob.setText("");
        return true;
    }

    // Validate ngành
    private boolean vMajor() {

        boolean ok =
                cbMajor.getValue() != null;

        errMajor.setText(
                ok
                        ? ""
                        : "Vui lòng chọn ngành"
        );

        return ok;
    }

    public static void main(String[] args) {
        launch(args);
    }
}