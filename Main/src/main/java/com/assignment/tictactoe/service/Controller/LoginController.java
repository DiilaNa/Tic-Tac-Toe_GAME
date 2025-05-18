package com.assignment.tictactoe.service.Controller;

import com.assignment.tictactoe.service.Main;
import com.assignment.tictactoe.service.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField name;

    @FXML
    private Button play;

    @FXML
    void playAction(ActionEvent event) {

        String playerName = name.getText();

        if (playerName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter your name", ButtonType.OK).show();
        }else {
            try {
                Util.name = playerName;
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/tictactoe.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("TIC TAC TOE");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
