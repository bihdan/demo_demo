package org.example.demo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestsWithMock {

    @Test
    public void testInsertDeskWithMockDatabase() throws SQLException {
        DatabaseConnection mockDatabaseConnection = mock(DatabaseConnection.class);
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        AddDeskScreenController controller = new AddDeskScreenController(mockDatabaseConnection);
        controller.insertDeskIntoDatabase("Test_desk_mock");

        verify(mockConnection).prepareStatement("insert into `desks`(`name`) values(?);");
        verify(mockPreparedStatement).setString(1, "Test_desk_mock");
        verify(mockPreparedStatement).execute();
    }
}
