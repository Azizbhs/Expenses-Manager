/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project2.Project2;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField nickname;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repeatPassword;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField surname;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BooleanBinding allFieldsFilled = Bindings.createBooleanBinding(() -> 
        !name.getText().isEmpty() && 
        !password.getText().isEmpty() && !nickname.getText().isEmpty() && !repeatPassword.getText().isEmpty() && 
        !email.getText().isEmpty() && !surname.getText().isEmpty(), nickname.textProperty(), password.textProperty(),
        repeatPassword.textProperty(), name.textProperty(), surname.textProperty(), email.textProperty());
        
        signUpButton.disableProperty().bind(allFieldsFilled.not());
    }    

    @FXML
    private void signUp(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        currentStage.close(); 
    }
    
}
