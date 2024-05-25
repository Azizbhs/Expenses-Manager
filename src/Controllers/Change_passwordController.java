/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Acount;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class Change_passwordController implements Initializable {

    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repeatPassword;
    @FXML
    private Button cancel;
    @FXML
    private Button save;

    private String tempPassword;
    private String tempRepeatPassword;
    @FXML
    private Label message;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tempPassword = "";
        tempRepeatPassword = "";
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("No changes will be saved.");
        confirmationDialog.setContentText("Do you wish to proceed?");

        confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                password.setText(tempPassword);
                repeatPassword.setText(tempRepeatPassword);
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
                if (!password.getText().equals(repeatPassword.getText())) {
                    Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                    errorDialog.setTitle("Error");
                    errorDialog.setHeaderText("Passwords do not match");
                    errorDialog.setContentText("Please ensure both passwords match.");
                    errorDialog.showAndWait();
                    return;
                } else if (password.getText().length() < 6){
                    Alert errorDialog = new Alert(Alert.AlertType.ERROR);
                    errorDialog.setTitle("Error");
                    errorDialog.setHeaderText("Password too short!");
                    errorDialog.setContentText("Please ensure your password contains at least 6 characters.");
                    errorDialog.showAndWait();
                    return;
                }

                User user = null;
                try {
                    user = Acount.getInstance().getLoggedUser();
                } catch (AcountDAOException ex) {
                    Logger.getLogger(Change_passwordController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Change_passwordController.class.getName()).log(Level.SEVERE, null, ex);
                }
                user.setPassword(password.getText());

                message.setStyle("-fx-text-fill: white;");
                message.setText("Password changed \nsuccessfully!");
                message.setVisible(true);

                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                    ((Stage) message.getScene().getWindow()).close();
                }));
                timeline.play();
                }
                
            
        });
    }
    
}