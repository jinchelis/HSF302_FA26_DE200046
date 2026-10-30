package edu.fpt.hsf302.demologin.b1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class DrinkOrderApp extends Application {

    private static final Map<String, Integer> MENU = new LinkedHashMap<>();

    static {
        MENU.put("Cà phê sữa", 25_000);
        MENU.put("Trà sữa", 30_000);
        MENU.put("Nước cam", 28_000);
        MENU.put("Sinh tố bơ", 35_000);
    }

    private static final int TOPPING_PRICE = 5_000;

    // TODO 1: Khai báo các control
    private final TextField tfName = new TextField();
    private final ComboBox<String> cbDrink = new ComboBox<>();

    private final ToggleGroup tgSize = new ToggleGroup();
    private final RadioButton rbS = new RadioButton("S");
    private final RadioButton rbM = new RadioButton("M (+5.000)");
    private final RadioButton rbL = new RadioButton("L (+10.000)");

    private final CheckBox chkPearl =
            new CheckBox("Trân châu (+5.000)");

    private final CheckBox chkJelly =
            new CheckBox("Thạch (+5.000)");

    private final CheckBox chkLessSugar =
            new CheckBox("Ít đường");

    private final TextField tfQty = new TextField("1");

    private final Button btnCalc =
            new Button("Tính tiền");

    private final Button btnClear =
            new Button("Làm lại");

    private final Label lblResult =
            new Label("Chưa có đơn hàng");

    private final TextArea taLog = new TextArea();

    @Override
    public void start(Stage stage) {

        // TODO 2: Cấu hình các control
        tfName.setPromptText("Nhập tên khách hàng");

        cbDrink.getItems().addAll(MENU.keySet());
        cbDrink.setPromptText("-- Chọn đồ uống --");
        cbDrink.setMaxWidth(Double.MAX_VALUE);

        rbS.setToggleGroup(tgSize);
        rbM.setToggleGroup(tgSize);
        rbL.setToggleGroup(tgSize);

        rbS.setUserData(0);
        rbM.setUserData(5_000);
        rbL.setUserData(10_000);

        // Size M mặc định
        tgSize.selectToggle(rbM);

        tfQty.setPrefColumnCount(4);

        taLog.setEditable(false);
        taLog.setPrefRowCount(8);
        taLog.setTooltip(
                new Tooltip("Double-click để xóa nhật ký")
        );

        // Enter mặc định sẽ kích hoạt Tính tiền
        btnCalc.setDefaultButton(true);

        // TODO 3: Xếp layout
        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        form.addRow(
                0,
                new Label("Tên khách:"),
                tfName
        );

        form.addRow(
                1,
                new Label("Đồ uống:"),
                cbDrink
        );

        form.addRow(
                2,
                new Label("Size:"),
                new HBox(15, rbS, rbM, rbL)
        );

        form.addRow(
                3,
                new Label("Tùy chọn:"),
                new HBox(
                        15,
                        chkPearl,
                        chkJelly,
                        chkLessSugar
                )
        );

        form.addRow(
                4,
                new Label("Số lượng:"),
                tfQty
        );

        HBox buttons =
                new HBox(10, btnCalc, btnClear);

        buttons.setAlignment(Pos.CENTER_RIGHT);

        Label title =
                new Label("ĐẶT ĐỒ UỐNG");

        title.setStyle(
                "-fx-font-size: 18;" +
                        "-fx-font-weight: bold;"
        );

        VBox root = new VBox(
                12,
                title,
                form,
                buttons,
                lblResult,
                new Label("Nhật ký sự kiện:"),
                taLog
        );

        root.setPadding(new Insets(16));

// Đăng ký các sự kiện
        registerEvents();

        Scene scene =
                new Scene(root, 540, 580);

// TODO 9: Event Filter - nhấn ESC để reset
        scene.addEventFilter(
                javafx.scene.input.KeyEvent.KEY_PRESSED,
                e -> {
                    if (e.getCode()
                            == javafx.scene.input.KeyCode.ESCAPE) {

                        reset();
                        log("Nhấn ESC → Làm lại");
                    }
                }
        );

        stage.setScene(scene);
        stage.setTitle(
                "B1 - Controls & Sự kiện"
        );
        stage.show();

        tfName.requestFocus();
    }

    private void registerEvents() {

        // TODO 4: Method Reference
        btnCalc.setOnAction(this::handleCalc);

        // TODO 5: Lambda
        btnClear.setOnAction(e -> {
            reset();
            log("Bấm Làm lại");
        });

        // TODO 6: Anonymous Class
        cbDrink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String drink = cbDrink.getValue();

                if (drink != null) {
                    log(
                            "Chọn đồ uống: %s (%,d đ)"
                                    .formatted(
                                            drink,
                                            MENU.get(drink)
                                    )
                    );
                }
            }
        });

        // Enter ở tên -> focus ComboBox
        tfName.setOnAction(e ->
                cbDrink.requestFocus()
        );

        // TODO 7: Handler dùng chung cho CheckBox
        EventHandler<ActionEvent> onCheck = e -> {

            CheckBox source =
                    (CheckBox) e.getSource();

            log(
                    source.getText()
                            + ": "
                            + (source.isSelected()
                            ? "chọn"
                            : "bỏ chọn")
            );
        };

        chkPearl.setOnAction(onCheck);
        chkJelly.setOnAction(onCheck);
        chkLessSugar.setOnAction(onCheck);

        // TODO 8: ChangeListener
        tgSize.selectedToggleProperty().addListener(
                (observable, oldValue, newValue) -> {

                    if (newValue != null) {

                        RadioButton selected =
                                (RadioButton) newValue;

                        log(
                                "Đổi size → "
                                        + selected.getText()
                        );
                    }
                }
        );

        tfQty.textProperty().addListener(
                (observable, oldValue, newValue) -> {

                    if (!newValue.matches("\\d{0,2}")) {
                        tfQty.setText(oldValue);
                    }
                }
        );

        // TODO 10: Mouse Event
        btnCalc.setOnMouseEntered(e ->
                btnCalc.setStyle(
                        "-fx-font-weight: bold;"
                )
        );

        btnCalc.setOnMouseExited(e ->
                btnCalc.setStyle("")
        );

        taLog.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                taLog.clear();
            }
        });
    }
    // TODO 11: Xử lý tính tiền
    private void handleCalc(ActionEvent event) {

        String name = tfName.getText().trim();
        String drink = cbDrink.getValue();

        // Kiểm tra tên khách hàng
        if (name.isEmpty()) {
            showError("Vui lòng nhập tên khách hàng");
            tfName.requestFocus();
            return;
        }

        // Kiểm tra đồ uống
        if (drink == null) {
            showError("Vui lòng chọn đồ uống");
            cbDrink.requestFocus();
            return;
        }

        // Kiểm tra số lượng
        int qty;

        try {
            qty = Integer.parseInt(tfQty.getText());

            if (qty < 1 || qty > 20) {
                showError("Số lượng phải từ 1 đến 20");
                tfQty.requestFocus();
                return;
            }

        } catch (NumberFormatException e) {
            showError("Số lượng không hợp lệ");
            tfQty.requestFocus();
            return;
        }

        // Giá đồ uống
        int drinkPrice = MENU.get(drink);

        // Phụ thu size
        int sizePrice =
                (int) tgSize
                        .getSelectedToggle()
                        .getUserData();

        // Đếm topping có tính phí
        int toppingCount = 0;

        if (chkPearl.isSelected()) {
            toppingCount++;
        }

        if (chkJelly.isSelected()) {
            toppingCount++;
        }

        // Giá một ly
        int unitPrice =
                drinkPrice
                        + sizePrice
                        + toppingCount * TOPPING_PRICE;

        // Tổng tiền
        int total = unitPrice * qty;

        // Số lượng từ 5 trở lên giảm 10%
        if (qty >= 5) {
            total = (int) (total * 0.9);
        }

        // Hiển thị kết quả
        lblResult.setStyle(
                "-fx-text-fill: green;" +
                        "-fx-font-weight: bold;"
        );

        lblResult.setText(
                "%s - %s x%d = %,d đ"
                        .formatted(
                                name,
                                drink,
                                qty,
                                total
                        )
        );

        log(
                "Tính tiền: "
                        + name
                        + " - "
                        + String.format("%,d đ", total)
        );
    }


    // Hàm hỗ trợ hiển thị lỗi cho TODO 11
    private void showError(String message) {

        lblResult.setText(message);

        lblResult.setStyle(
                "-fx-text-fill: red;" +
                        "-fx-font-weight: bold;"
        );

        log("Lỗi: " + message);
    }

    // TODO 12: Reset form
    private void reset() {

        tfName.clear();

        cbDrink
                .getSelectionModel()
                .clearSelection();

        // Size M mặc định
        tgSize.selectToggle(rbM);

        chkPearl.setSelected(false);
        chkJelly.setSelected(false);
        chkLessSugar.setSelected(false);

        tfQty.setText("1");

        lblResult.setText("Chưa có đơn hàng");
        lblResult.setStyle("");

        tfName.requestFocus();
    }


    // Ghi nhật ký sự kiện
    private void log(String message) {

        taLog.appendText(
                message + System.lineSeparator()
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}