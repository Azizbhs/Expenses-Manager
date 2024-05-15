/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class Forgot_passwordController implements Initializable {

    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repeatPassword;
    @FXML
    private Button cancel;
    @FXML
    private Button save;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cancel(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("No changes will be saved.");
        confirmationDialog.setContentText(" Do you wish to proceed?");

        confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                currentStage.close();
            }
        });
    }

    @FXML
    private void save(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Save changes?");
        confirmationDialog.setContentText("Are you sure you want to perform this action?");

        confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                currentStage.close();
            }
        });
    }
    
}
