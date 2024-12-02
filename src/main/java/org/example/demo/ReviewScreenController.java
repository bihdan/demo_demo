package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReviewScreenController {
    @FXML
    private Label frontLabel;

    @FXML
    private Label backLabel;

    @FXML
    private Button showAnswerReviewButton;

    @FXML
    private ButtonBar barOfButtons;

    @FXML
    private void showAnswerReviewButtonClicked(){
        setReceivedData();

    }
    public void setReceivedData() {
        backLabel.setText("Для випадкового перехожого ми, мабуть, схожі коханці");
        barOfButtons.setVisible(true);
    }

    @FXML
    private void initialize() {
        frontLabel.setText("my heart began to ache");
        barOfButtons.setVisible(false);
    }


}
