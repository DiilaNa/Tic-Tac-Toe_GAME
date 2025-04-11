module com.assignment.tictactoe.service.main {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.assignment.tictactoe.service.Controller to javafx.fxml;
    exports com.assignment.tictactoe.service;
    exports com.assignment.tictactoe.service.game;
    opens com.assignment.tictactoe.service.game to javafx.fxml;
}