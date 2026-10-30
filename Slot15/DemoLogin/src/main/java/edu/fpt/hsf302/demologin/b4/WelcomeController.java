package edu.fpt.hsf302.demologin.b4;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WelcomeController {

    @FXML
    private Label lblWelcome;

    public void setUser(String username, String role) {

        lblWelcome.setText(
                "Xin chào, " + username
                        + "!\nVai trò: " + role
        );
    }
}