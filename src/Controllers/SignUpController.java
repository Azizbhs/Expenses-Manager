/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.net.URL;
import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML
    private Label errorLabel;
    
    private String firstName;
    private String surName;
    

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

        signUpButton.disableProperty().bind(allFieldsFilled.not());
        
        errorLabel.setVisible(false);
        
    }

    public String getUserName() {
        return userNameField.getText();
    }

    // Method to get text from PasswordField2
    public String getPassword() {
        return passwordField2.getText();
    }

    // Method to get text from RepeatPassword
    public String getRepeatPassword() {
        return repeatPassword.getText();
    }

    // Method to get text from RealName
    public String getRealName() {
        return realName.getText();
    }

    // Method to get text from Email
    public String getEmail() {
        return email.getText();
    }    
    
    private void separateName(String fullName ){
     int spaceIndex = fullName.indexOf(" ");
        
        if (spaceIndex != -1) {
            // Extract the first name
             this.firstName = fullName.substring(0, spaceIndex);
            
            // Extract the last name (everything after the first space)
            this.surName = fullName.substring(spaceIndex + 1);
            
        } else {
            this.firstName = fullName;
        }
    }
    
    public boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
    
    public boolean areEqualPasswords(String password, String repeatPassword){
     return password.equals(repeatPassword);
    }
    
    

    @FXML
    private void signUp(ActionEvent event) throws IOException {
        boolean isValid = true;
        
        if (!isValidEmail(getEmail())) {
        errorLabel.setText("Invalid email address.");
        errorLabel.setVisible(true);
        isValid = false;
    }

    // Check if the passwords match
        else if(!areEqualPasswords(getPassword(), getRepeatPassword())) {
        errorLabel.setText("Passwords do not match.");
        errorLabel.setVisible(true);
        isValid = false;
    }
        if(!isValid){
          realName.clear();
          email.clear();
          passwordField2.clear();
          repeatPassword.clear();
          userNameField.clear();
        }else{
            errorLabel.setStyle("-fx-text-fill: " + "green");
            errorLabel.setText("You have been registered!");
            errorLabel.setVisible(true);
   
    
          Platform.runLater(() -> {
        ((Stage) userNameField.getScene().getWindow()).close();
    });
        }
        

     }
    
   }

