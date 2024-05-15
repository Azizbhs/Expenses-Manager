/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.net.URL;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author MABENHAS
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField2;
    @FXML
    private PasswordField repeatPassword;
    @FXML
    private TextField realName;
    @FXML
    private TextField email;
    @FXML
    private Button signUpButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BooleanBinding allFieldsFilled = Bindings.createBooleanBinding(() -> 
            !userNameField.getText().isEmpty() && 
            !passwordField2.getText().isEmpty() && 
            !repeatPassword.getText().isEmpty() && 
            !realName.getText().isEmpty() && 
            !email.getText().isEmpty(), 
            userNameField.textProperty(), 
            passwordField2.textProperty(), 
            repeatPassword.textProperty(), 
            realName.textProperty(), 
            email.textProperty()
        );

        // Bind the disable property of the button to the negation of the BooleanBinding
        signUpButton.disableProperty().bind(allFieldsFilled.not());
    }    

    @FXML
    private void signUp(ActionEvent event) {
    }
    
}
