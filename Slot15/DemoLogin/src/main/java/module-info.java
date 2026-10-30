module edu.fpt.hsf302.demologin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens edu.fpt.hsf302.demologin to javafx.fxml;
    opens edu.fpt.hsf302.demologin.b4 to javafx.fxml;

    exports edu.fpt.hsf302.demologin;
    exports edu.fpt.hsf302.demologin.b1;
    exports edu.fpt.hsf302.demologin.b2;
    exports edu.fpt.hsf302.demologin.b3;
    exports edu.fpt.hsf302.demologin.b4;
}