package org.example.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CollectionManager {
    // Список для зберігання об'єктів Desk
    private List<Desk> desks;

    // Список для зберігання об'єктів Card
    private List<Card> cards;

    // Конструктор
    public CollectionManager() {
        this.desks = new ArrayList<>();
        this.cards = new ArrayList<>();
    }

    public String desks_name_unique() {
        for (int i = 0 ; i < desks.size() - 1; i++ ){
            for (int j = i + 1; j < desks.size(); j++ ){
                Desk d1 = desks.get(i);
                Desk d2 = desks.get(j);

                if(Objects.equals(d1.getName(), d2.getName())){
                    return "Names are not unique";
                }

            }
        }

        return "Names are unique";
    }

    public String cards_front_unique() {
        for (int i = 0 ; i < cards.size() - 1; i++ ){
            for (int j = i + 1; j < cards.size(); j++ ){
                Card c1 = cards.get(i);
                Card c2 = cards.get(j);

                if(Objects.equals(c1.getFront(), c2.getFront())){
                    return "Fronts are not unique";
                }

            }
        }

        return "Fronts are unique";
    }


    public void feelCollection() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection conn = connectNow.getConnection();

        String deskQuery = "SELECT id, name FROM `desks-cards`.desks order by `name` ASC;";
        String cardQuery = "SELECT id, front, back, flag, endDate, daysJump, desk_id FROM cards";

        try {
            Statement statement = conn.createStatement();

            ResultSet deskResultSet = statement.executeQuery(deskQuery);
            while (deskResultSet.next()) {
                int id = deskResultSet.getInt("id");
                String name = deskResultSet.getString("name");

                this.addDesk(id, name);
            }

            // Отримання cards
            ResultSet cardResultSet = statement.executeQuery(cardQuery);
            while (cardResultSet.next()) {
                int id = cardResultSet.getInt("id");
                String front = cardResultSet.getString("front");
                String back = cardResultSet.getString("back");
                String flag = cardResultSet.getString("flag");
                Date endDate = cardResultSet.getDate("endDate");
                int daysJump = cardResultSet.getInt("daysJump");
                int deskId = cardResultSet.getInt("desk_id");

                this.addCard(id, front, back, flag, endDate, daysJump, deskId);
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            connectNow.shutdown();
        }
    }

    // Методи для роботи з Desk

    // Додати Desk до колекції
    public void addDesk(Desk desk) {
        this.desks.add(desk);
    }

    public void addDesk(Integer id, String name) {
        Desk dd = new Desk(id, name);

        this.desks.add(dd);
    }

    // Видалити Desk з колекції
    public void removeDesk(Desk desk) {
        this.desks.remove(desk);
    }

    // Отримати список всіх Desk
    public List<Desk> getDesks() {
        return desks;
    }

    // Отримати список всіх Desk
    public Desk getDeskByName(String name) {
        for (Desk desk : desks) {
            if (desk.getName().equals(name)) {
                return desk;
            }
        }
        return null; // Повернути null, якщо не знайдено
    }

    // Отримати Desk по id
    public Desk getDeskById(int id) {
        for (Desk desk : desks) {
            if (desk.getId() == id) {
                return desk;
            }
        }
        return null; // Повернути null, якщо не знайдено
    }



    // Методи для роботи з Card

    // Додати Card до колекції
    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void addCard(int id, String front, String back, String flag, Date endDate, int daysJump, int deskId) {
        Card card = new Card(id, front, back, flag, endDate, daysJump, deskId);

        this.cards.add(card);
    }

    // Видалити Card з колекції
    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    // Отримати список всіх Card
    public List<Card> getCards() {
        return cards;
    }

    // Отримати Card по id
    public Card getCardById(int id) {
        for (Card card : cards) {
            if (card.getId() == id) {
                return card;
            }
        }
        return null; // Повернути null, якщо не знайдено
    }

    // Отримати Card по desk_id
    public Card getCardByDeskId(int deskId) {
        for (Card card : cards) {
            if (card.getDeskId() == deskId) {
                return card;
            }
        }
        return null; // Повернути null, якщо не знайдено
    }


}

