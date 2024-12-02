package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AddCardScreenController {

    CollectionManager collection = new CollectionManager();

    @FXML
    private ComboBox<String> ComboBoxForDesks;


    @FXML
    private TextField frontTextField;

    @FXML
    private TextField backTextField;



    public void deskChoiceBoxClicked() throws SQLException {


    }

    private void fillChoiceBoxByDesks(){

        for (Desk desk : collection.getDesks()){
            ComboBoxForDesks.getItems().add(desk.getName());
        }
    }

    private void insertCardIntoDatabase(String front, String back, int deskId) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection conn = connectNow.getConnection();

        String insertQuery = "insert into `cards`(`front`,`back`,`endDate`,`daysJump`,`desk_id`) " +
                "values(?, ?, CURDATE(), ?, ?);"; // testCard тестоваКартка

        try {

            PreparedStatement statement = conn.prepareStatement(insertQuery);

            statement.setString(1, front);
            statement.setString(2, back);
            statement.setInt(3, -1);
            statement.setInt(4, deskId);
            statement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectNow.shutdown();
        }
    }

    private void SeedDatabase() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection conn = connectNow.getConnection();
        try {

            Statement statement = conn.createStatement();

            // Очищення
            statement.executeUpdate("delete from `cards`;");
            statement.executeUpdate("delete from `desks`;");

            // Додавання
            statement.executeUpdate("insert into `desks`(name) values ('TestName');");
            statement.executeUpdate("insert into `desks`(name) values ('');");
            statement.executeUpdate("insert into `desks`(name) values ('TestName');");

            statement.executeUpdate("insert into `cards`(`front`,`back`,`endDate`,`daysJump`,`desk_id`, `flag`) " +
                    "values ('What is Java?', '', CURDATE(), -1, 1, '');");
            statement.executeUpdate("insert into `cards`(`front`,`back`,`endDate`,`daysJump`,`desk_id`, `flag`) " +
                    "values ('What is С++?', 'A programming language', CURDATE(),-1, 1, '');");
            statement.executeUpdate("insert into `cards`(`front`,`back`,`endDate`,`daysJump`,`desk_id`, `flag`) " +
                    "values ('What is Java?', '', CURDATE(), -1, 1, '');");
            statement.executeUpdate("insert into `cards`(`front`,`back`,`endDate`,`daysJump`,`desk_id`, `flag`) " +
                    "values ('What is this?', '', CURDATE(), -100, 1, ''));");
            statement.executeUpdate("insert into `cards`(`front`,`back`,`endDate`,`daysJump`,`desk_id`, `flag`) " +
                    "values ('What is that?', '', CURDATE(), -1, 1, 'PhahaPhaha PhahaPhaha PhahaPhaha PhahaPhaha PhahaPhaha' );");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectNow.shutdown();
        }
    }

    @FXML
    private void addNewCardButtonClicked() throws SQLException {
        String front = frontTextField.getText();
        String back = backTextField.getText();

        if ( ComboBoxForDesks.getSelectionModel().isEmpty()) {
//            frontTextField.setPromptText("Картка повинна мати щось!!!");
//            frontTextField.setStyle("-fx-border-color: red ");

        } else {
            if (front != null && !front.isEmpty()) {
                String deskName = ComboBoxForDesks.getValue();

                int deskId = 1;

                for (Desk desk : collection.getDesks()) {
                    if (desk.getName().equals(deskName)) {
                        deskId = desk.getId();
                    }
                }

                insertCardIntoDatabase(front, back, deskId);

                frontTextField.clear();
                frontTextField.setPromptText("Додано!!!");
                frontTextField.setStyle("-fx-border-color: #40ff00; ");

                backTextField.clear();
                backTextField.setStyle("-fx-border-color: #40ff00; ");

            } else {

                frontTextField.setPromptText("Картка повинна мати щось!!!");
                frontTextField.setStyle("-fx-border-color: #FF0000; ");
            }
        }
    }

    @FXML
    private void frontTextFieldClicked() throws IOException{
        frontTextField.setPromptText(null);
        frontTextField.setStyle("-fx-border-color:black;");
    }

    @FXML
    private void backTextFieldClicked() throws IOException{
        backTextField.setStyle("-fx-border-color:black;");
    }

    @FXML
    private void initialize() {
        try {
            collection.feelCollection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        fillChoiceBoxByDesks();
        if (ComboBoxForDesks.getItems().isEmpty()){
            ComboBoxForDesks.setPromptText("Спочатку створіть колоду!!!");
        } else {
            ComboBoxForDesks.setPromptText("Оберіть колоду");
            ComboBoxForDesks.getSelectionModel().select(0);
        }
    }

}
