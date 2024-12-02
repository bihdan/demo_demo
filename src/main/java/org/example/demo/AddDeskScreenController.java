package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AddDeskScreenController {

//    CollectionManager collection = new CollectionManager();
    @FXML
    private TextField deskNameTextField;

    @FXML
    private Button addNewDeskButton;

    @FXML
    private Button canselAddDeskButton;

    @FXML
    private void canselAddDeskButtonClicked() throws IOException {
        Stage stage = (Stage) canselAddDeskButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addNewDeskButtonClicked() throws IOException, SQLException {
        String cardName = deskNameTextField.getText();
        if (cardName != null && !cardName.isEmpty()){
            insertDeskIntoDatabase(cardName);

            Stage stage = (Stage) canselAddDeskButton.getScene().getWindow();
            stage.close();
        } else {
            deskNameTextField.setPromptText("Колода повинна мати назву!!!");
            deskNameTextField.setStyle("-fx-border-color: red ");
        }
    }

    @FXML
    private void deskNameClicked() throws IOException{
        deskNameTextField.setStyle("-fx-border-color:black");
    }

    public void insertDeskIntoDatabase(String deskName) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection conn = connectNow.getConnection();

        String insertQuery = "insert into `desks`(`name`) values(?);";

        try {

            PreparedStatement statement = conn.prepareStatement(insertQuery);

            statement.setString(1, deskName);
            statement.execute();

//            ResultSet deskResultSet = statement.executeQuery(deskQuery);
//            int rowsInserted = statement.executeUpdate();
//
//            if (rowsInserted > 0) {
//                System.out.println("New card was added successfully!");
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectNow.shutdown();
        }
    }


}
