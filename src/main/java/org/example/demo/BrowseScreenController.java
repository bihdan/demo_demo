package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class BrowseScreenController {

    CollectionManager collection = new CollectionManager();
    @FXML
    public TextField searchField;

    @FXML
    private AnchorPane anchorPaneForCards;

    @FXML
    private TextField fieldForFront;

    @FXML
    private TextField fieldForBack;






    @FXML
    private void initialize() {
        populatePaneByCards();
    }


    public void populatePaneByCards() {
        anchorPaneForCards.getChildren().clear();

        double layoutY = 5.0;
        double spacing = 30.0;

        try {
            collection.feelCollection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        VBox vbox = new VBox(2);
        vbox.getStyleClass().clear();
        vbox.getStyleClass().add("VBoxesForCards");
//        vbox.setLayoutX(10);
//        vbox.setLayoutY(10);
//        vbox.setPrefHeight(418);
//        vbox.setPrefWidth(386);


        for (Card card : collection.getCards()) {
            HBox hbox = new HBox(10);


            Label leftLabel = new Label(card.getFront());
            leftLabel.setPrefHeight(18.0);
            leftLabel.setPrefWidth(165.0);
            leftLabel.getStyleClass().clear();
            leftLabel.getStyleClass().add("customTextForLabels");


            Label rightLabel = new Label(findDeskName(card.getDeskId()));
            rightLabel.setPrefHeight(18.0);
            rightLabel.setPrefWidth(55.0);
            rightLabel.getStyleClass().add("customTextForLabels");

            hbox.getChildren().addAll(leftLabel, rightLabel);
//            hbox.getStyleClass().add("HBoxesForCards");

            hbox.setUserData(card.getId());
            hbox.setOnMouseClicked(this::handleHBoxClick);

            vbox.getChildren().add(hbox);
        }

        // Add the VBox to the AnchorPane
        anchorPaneForCards.getChildren().add(vbox);

    }

    private String findDeskName(int deskId){
        for (Desk desk : collection.getDesks()){
            if (desk.getId() == deskId){
                return desk.getName();
            }
        }
        return null;
    }

    private void handleHBoxClick(MouseEvent event) {
        if (event.getSource() instanceof HBox) {
            HBox hbox = (HBox) event.getSource();
            Integer cardId = (Integer) hbox.getUserData();

            displayCardDetails(cardId);
        }
    }

    private void displayCardDetails(Integer cardId) {
        for (Card card : collection.getCards()){
            if (card.getId() == cardId){
                fieldForFront.setText(card.getFront());
                fieldForBack.setText(card.getBack());
            }
        }

    }
}
