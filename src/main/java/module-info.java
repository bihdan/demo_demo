module org.example.demo {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires junit;


    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
}