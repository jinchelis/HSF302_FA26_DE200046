module edu.fpt.hsf302.demologin {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.fpt.hsf302.demologin to javafx.fxml;
    exports edu.fpt.hsf302.demologin;
    exports edu.fpt.hsf302.demologin.b1;
    exports edu.fpt.hsf302.demologin.b2;
}