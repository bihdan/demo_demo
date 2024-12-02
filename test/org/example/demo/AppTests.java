package org.example.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class AppTests {

    @Test
    public void Test_desk_name_null(){
        assertThrows(IllegalArgumentException.class, () -> {Desk d = new Desk(1, "");} );
    }
    @Test
    public void Test_desks_name_no_unique(){
        CollectionManager cm = new CollectionManager();
        cm.addDesk(1,"Desk1");
        cm.addDesk(2,"Desk1");

        String res = cm.desks_name_unique();
        assertEquals("Names are not unique", res);
    }

    @Test
    public void Test_desks_name_unique(){
        CollectionManager cm = new CollectionManager();
        cm.addDesk(1,"Desk1");
        cm.addDesk(2,"Desk2");
        cm.addDesk(2,"Desk3");

        String res = cm.desks_name_unique();
        assertEquals("Names are unique", res);
    }

    @Test
    public void Test_card_front_null(){
        assertThrows(IllegalArgumentException.class, () -> {Card c = new Card(1, "",
                "", null, null,  -1, 1);} );

    }
    @Test
    public void Test_card_long_flag(){
        assertThrows(IllegalArgumentException.class, () -> {Card c = new Card(1, "What is Java?",
                "", "PhahaPhahaPhahaPhahaPhahaPhahaPhahaPhahaPhahaPhaha", null,  -1, 1);} );
    }
    @Test
    public void Test_card_out_of_daysJump(){
        assertThrows(IllegalArgumentException.class, () -> {Card c = new Card(1, "What is Java?",
                "", null, null,  -10, 1);} );
    }

    @Test
    public void Test_cards_front_no_unique(){
        CollectionManager cm = new CollectionManager();
        cm.addCard(1, "What is Java?",
                "", null, null,  -1, 1);
        cm.addCard(1, "What is Java?",
                "", null, null,  -1, 1);

        String res = cm.cards_front_unique();
        assertEquals("Fronts are not unique", res);
    }

    @Test
    public void Test_cards_front_unique(){
        CollectionManager cm = new CollectionManager();
        cm.addCard(1, "What is Java?",
                "", null, null,  -1, 1);
        cm.addCard(1, "What is C++?",
                "", null, null,  -1, 1);

        String res = cm.cards_front_unique();
        assertEquals("Fronts are unique", res);
    }

}
