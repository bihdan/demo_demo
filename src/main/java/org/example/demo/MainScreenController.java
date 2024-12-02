package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainScreenController {

    CollectionManager collection = new CollectionManager();

    @FXML
    private Button desksMenuButton;

    @FXML
    private Button addMenuButton;

    @FXML
    private Button browseMenuButton;

    @FXML
    private Button synchronizeMenuButton;

    @FXML
    private Button addDeskMenuButton;

    @FXML
    private VBox vboxForHBoxOfDesk;

//    @FXML
//    private AnchorPane AnchorPaneForVBox;

    private Stage addCardStage, addDeskStage, browseStage, reviewStage;


    @FXML
    private void desksMenuButtonClicked() {
        initialize();
    }

    @FXML
    private void addMenuButtonClicked(){

        if (addCardStage == null || !addCardStage.isShowing()) {
            try {

                Parent root = FXMLLoader.load(getClass().getResource("add-card-screen.fxml"));
                addCardStage = new Stage();
                addCardStage.setTitle("Створення карток");
                addCardStage.setScene(new Scene(root));

                addCardStage.setOnHiding(event -> {
                    addCardStage = null;
                });

                addCardStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (addCardStage.isIconified()) {
                addCardStage.setIconified(false);
            }

            addCardStage.setAlwaysOnTop(true);
            addCardStage.setAlwaysOnTop(false);

//            addDeskStage.show();
//            addDeskStage.requestFocus();
//            addDeskStage.toFront();
        }

    }



    @FXML
    private void addDeskMenuButtonClicked() throws IOException {

        if (addDeskStage == null || !addDeskStage.isShowing()) {
            try {

                Parent root = FXMLLoader.load(getClass().getResource("add-desk-screen.fxml"));
                addDeskStage = new Stage();
                addDeskStage.setTitle("Створення нової колоди");
                addDeskStage.setScene(new Scene(root));

                addDeskStage.setOnHiding(event -> {
                    populatePaneByDesks();
                    addDeskStage = null;
                });

                addDeskStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (addDeskStage.isIconified()) {
                addDeskStage.setIconified(false);
            }

            addDeskStage.setAlwaysOnTop(true);
            addDeskStage.setAlwaysOnTop(false);

        }
    }

    @FXML
    private void browseMenuButtonClicked() throws IOException {

        if (browseStage == null || !browseStage.isShowing()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("browse-screen.fxml"));
                browseStage = new Stage();
                browseStage.setTitle("Перегляд");
                browseStage.setScene(new Scene(root));

                browseStage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (browseStage.isIconified()) {
                browseStage.setIconified(false);
            }

            browseStage.setAlwaysOnTop(true);
            browseStage.setAlwaysOnTop(false);

        }
    }

    @FXML
    private void synchronizeMenuButtonClicked() {
//        clearVBoxChildren();
        populatePaneByDesks();
    }

    private void clearVBoxChildren() {
        for (Node node : vboxForHBoxOfDesk.getChildren()) {
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                hbox.getChildren().clear();
//                hbox.getChildren().removeAll();

                hbox.setOnMouseClicked(null);
            }
        }
        vboxForHBoxOfDesk.getChildren().clear();
    }

    private void populatePaneByDesks(){

        try {
            collection.feelCollection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        clearVBoxChildren();

        for (Desk desk : collection.getDesks()) {
            boolean alreadyExists = vboxForHBoxOfDesk.getChildren().stream().filter
                    (node -> node instanceof HBox).anyMatch
                    (node -> ((HBox) node).getUserData().equals(desk.getId()));

            if (alreadyExists) {
                continue;
            }

            HBox hbox = new HBox(10);

            Label nameLabel = new Label(desk.getName());
            nameLabel.setPrefHeight(32.0);
            nameLabel.setPrefWidth(210.0);
            nameLabel.getStyleClass().add("customTextForLabels");

            int newCards = cardsCounter(desk.getId());
            Label leftLabel = new Label(Integer.toString(newCards));
            leftLabel.setPrefHeight(32.0);
            leftLabel.setPrefWidth(45.0);
            leftLabel.getStyleClass().add("customTextForLabels");
            if (newCards != 0){
                leftLabel.setStyle("-fx-text-fill: #71c0f8");
            }

            Label middleLabel = new Label(Integer.toString(0));
            middleLabel.setPrefHeight(32.0);
            middleLabel.setPrefWidth(45.0);
            middleLabel.getStyleClass().add("customTextForLabels");

            Label rightLabel = new Label(Integer.toString(0));
            rightLabel.setPrefHeight(32.0);
            rightLabel.setPrefWidth(45.0);
            rightLabel.getStyleClass().add("customTextForLabels");

            hbox.getChildren().addAll(nameLabel, leftLabel, middleLabel, rightLabel);
            hbox.getStyleClass().add("HBoxesForDesks");

            hbox.setUserData(desk.getId());

            hbox.setOnMouseClicked(this::handleDeskHBoxClick);

            vboxForHBoxOfDesk.getChildren().add(hbox);

        }
    }

    @FXML
    private void handleDeskHBoxClick(MouseEvent event){
        if (event.getSource() instanceof HBox) {
            HBox hbox = (HBox) event.getSource();
            Integer deskId = (Integer) hbox.getUserData();

            reviewScreenShow(deskId);
        }
    }

    @FXML
    private void reviewScreenShow(int desk_id){
        if (reviewStage == null || !reviewStage.isShowing()) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("review-screen.fxml"));
                reviewStage = new Stage();
                reviewStage.setTitle("Проходження");
                reviewStage.setScene(new Scene(root));

                reviewStage.show();



            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (reviewStage.isIconified()) {
                reviewStage.setIconified(false);
            }

            reviewStage.setAlwaysOnTop(true);
            reviewStage.setAlwaysOnTop(false);

        }
    }

    private int cardsCounter(int deskID){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection conn = connectNow.getConnection();

//        String deskQuery = "select COUNT(*) as numb from cards where desk_id = 8;";

        int numberOfCards = 0;

        for (Card card : collection.getCards()){
            if (deskID == card.getDeskId()){
                numberOfCards++;
            }
        }

        return numberOfCards;
    }

    @FXML
    private void initialize() {
        desksMenuButton.setText("Колоди");
        addMenuButton.setText("Додати");
        browseMenuButton.setText("Перегляд");
        synchronizeMenuButton.setText("Оновити");
        addDeskMenuButton.setText("Додати колоду");
    }


}