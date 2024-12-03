package org.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

//import javax.persistence.EntityManager;

//import  static org.assertj.core.api.Assertions.assertThat;
//@SpringBootTest(classes = MainApplication.class)
//@Transactional
public class InsertDBSelectTest {
    public void insertCardIntoDatabase(String front, String back, String flag, int daysJump, int deskId) throws SQLException, SQLIntegrityConstraintViolationException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection conn = connectNow.getConnection();

        String insertQuery = "insert into `cards`(`front`,`back`, `flag`,`endDate`,`daysJump`,`desk_id`) " +
                "values(?, ?, ?, CURDATE(), ?, ?);"; // testCard тестоваКартка

        try {

            PreparedStatement statement = conn.prepareStatement(insertQuery);

            statement.setString(1, front);
            statement.setString(2, back);
            statement.setString(3, flag);
            statement.setInt(4, daysJump);
            statement.setInt(5, deskId);
            statement.execute();

        } catch (SQLIntegrityConstraintViolationException b) {
            throw b;
        } catch (SQLException e) {
            throw e;
        } finally{
            connectNow.shutdown();
        }
    }

    public void deleteCardsByCardIdFromDB(int deskId) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection conn = connectNow.getConnection();

        String deleteQuery = "delete from `desks-cards`.cards where desk_id = ? ;";

        try {

            PreparedStatement statement = conn.prepareStatement(deleteQuery);

            statement.setInt(1, deskId);
            statement.execute();

        } catch (SQLException e) {
            throw e;
        } finally{
            connectNow.shutdown();
        }
    }

    public void deleteDesksByNameFromDB(String  name) throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection conn = connectNow.getConnection();

        String deleteQuery = "delete from `desks-cards`.desks where name = ? ;";

        try {

            PreparedStatement statement = conn.prepareStatement(deleteQuery);

            statement.setString(1, name);
            statement.execute();

        } catch (SQLException e) {
            throw e;
        } finally{
            connectNow.shutdown();
        }
    }

    @Test
    public void insertDeskWithBlankNameTest(){
        assertThrows(SQLException.class, () ->
                (new AddDeskScreenController()).
                        insertDeskIntoDatabase(null));
    }

    @Test
    public void insertDeskAndCheckTest() throws SQLException {
//        deleteDesksByNameFromDB("Test_desk_222");

        assertAll(() -> (new AddDeskScreenController()).
                insertDeskIntoDatabase("Test_desk_222"));

        CollectionManager cm = new CollectionManager();
        cm.feelCollection();

        assertEquals("Test_desk_222", cm.getDeskByName("Test_desk_222").getName());

        deleteDesksByNameFromDB("Test_desk_222");

    }

    @Test
    public void insertDesksAndDuplicateCheckTest() throws SQLException {
        deleteDesksByNameFromDB("duplicate_test_desk");

        assertAll(() -> (new AddDeskScreenController()).
                insertDeskIntoDatabase("duplicate_test_desk"));
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> (new AddDeskScreenController()).
                insertDeskIntoDatabase("duplicate_test_desk"));

        deleteDesksByNameFromDB("duplicate_test_desk");

    }

    @Test
    public void insertCardWithBlankFrontTest(){
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> insertCardIntoDatabase( null,
                "", "",  -1, 10));
    }

    @Test
    public void insertCardWithWrongDaysJumpTest(){
        assertThrows(SQLException.class, () -> insertCardIntoDatabase( "test1",
                "", "",  -10, 10));
    }

    @Test
    public void insertCardWithWrongFlagTest(){
        assertThrows(SQLException.class, () -> insertCardIntoDatabase( "test1",
                "", "PhahaPhahaPhahaPhahaPhahaPhahaPhahaPhahaPhahaPhaha",  -1, 10));
    }

    @Test
    public void insertCardAndCheckTest() throws SQLException {
        assertAll(() -> insertCardIntoDatabase( "test111", "", "",  -1, 10));
        CollectionManager cm = new CollectionManager();
        cm.feelCollection();

        Card c = cm.getCardByDeskId(10);
        assertEquals("test111", c.getFront());

        deleteCardsByCardIdFromDB(10);
    }

}
